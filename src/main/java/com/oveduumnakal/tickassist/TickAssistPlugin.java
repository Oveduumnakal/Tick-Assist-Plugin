/*
 * Copyright (c) 2026, Oveduumnakal
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.oveduumnakal.tickassist;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;

import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.StatChanged;
import net.runelite.api.gameval.InventoryID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

/**
 * Tick Assist — detects skilling tick-manipulation setups and visualises their timing.
 *
 * <p>Each tick the plugin reads the player's animation, the resources in range, and the items they
 * carry, matches a recipe from the catalog, and drives the tick clock from it — falling back to a
 * plain manual metronome when nothing is detected. It never clicks anything; it only visualises the
 * beat. The ping-pong highlight and accuracy stats build on this in later phases.
 */
@Slf4j
@PluginDescriptor(
	name = "Tick Assist",
	description = "Detects tick-manipulation setups and shows the timing of each action",
	tags = {
		"tick", "manipulation", "3-tick", "fishing", "mining", "herblore",
		"cooking", "skilling", "timing", "metronome", "efficiency"
	}
)
public class TickAssistPlugin extends Plugin
{
	private static final int STALL_TICKS = 5;

	@Inject
	private Client client;

	@Inject
	private TickAssistConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private TickMetronomeOverlay metronomeOverlay;

	@Inject
	private InventoryHighlightOverlay inventoryHighlightOverlay;

	@Inject
	private TargetHighlightOverlay targetHighlightOverlay;

	@Inject
	private TickStatsInfoBox statsInfoBox;

	@Inject
	private ResourceScanner resourceScanner;

	@Inject
	private InventoryScanner inventoryScanner;

	private List<TickRecipe> catalog;
	private ActivityDetector activityDetector;
	private GuidanceState guidance;
	private AccuracyTracker accuracy;
	private TickRecipe fallback;
	private TickRecipe activeRecipe;
	private TickClock clock;
	private RecipeMatch currentMatch;
	private int gameTick;
	private int lastSkillXp = -1;
	private int lastItemCount = -1;
	private boolean gatheredThisCycle;

	/**
	 * Supplies the plugin's configuration proxy to RuneLite's injector.
	 *
	 * @param configManager the client configuration manager
	 * @return the Tick Assist configuration
	 */
	@Provides
	TickAssistConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(TickAssistConfig.class);
	}

	/**
	 * Starts the plugin: seeds the catalog, builds the fallback clock, and registers the overlay.
	 */
	@Override
	protected void startUp()
	{
		catalog = RecipeCatalog.seedRecipes();
		activityDetector = new ActivityDetector(STALL_TICKS);
		guidance = new GuidanceState();
		fallback = RecipeCatalog.customMetronome(config.customCadence());
		activeRecipe = fallback;
		clock = new TickClock(fallback.steps());
		accuracy = new AccuracyTracker(fallback.cadenceTicks());
		currentMatch = null;
		gameTick = 0;
		lastSkillXp = -1;
		lastItemCount = -1;
		gatheredThisCycle = false;
		overlayManager.add(metronomeOverlay);
		overlayManager.add(inventoryHighlightOverlay);
		overlayManager.add(targetHighlightOverlay);
		overlayManager.add(statsInfoBox);
		log.debug("Tick Assist started");
	}

	/**
	 * Stops the plugin: removes the overlay and drops all live state.
	 */
	@Override
	protected void shutDown()
	{
		overlayManager.remove(metronomeOverlay);
		overlayManager.remove(inventoryHighlightOverlay);
		overlayManager.remove(targetHighlightOverlay);
		overlayManager.remove(statsInfoBox);
		clock = null;
		activeRecipe = null;
		currentMatch = null;
		accuracy = null;
		log.debug("Tick Assist stopped");
	}

	/**
	 * Runs detection for the tick, switches the active recipe when it changes, and advances the beat.
	 *
	 * @param event the game-tick event
	 */
	@Subscribe
	public void onGameTick(GameTick event)
	{
		gameTick++;
		accuracy.onTick();

		int animationId = currentAnimationId();
		activityDetector.update(animationId, activeRecipe.gatherAnimationIds());

		TickRecipe selected = selectRecipe(animationId);
		if (!selected.id().equals(activeRecipe.id()))
		{
			activeRecipe = selected;
			clock = new TickClock(selected.steps());
			accuracy = new AccuracyTracker(selected.cadenceTicks());
			gatheredThisCycle = false;
		}

		clock.tick();
		if (clock.phase() == 0)
		{
			DetectionState active = currentMatch != null ? currentMatch.state() : DetectionState.OFF;
			if (active == DetectionState.ACTIVE && !gatheredThisCycle)
				guidance.onCountdownExpired();

			gatheredThisCycle = false;
		}

		DetectionState detection = currentMatch != null ? currentMatch.state() : DetectionState.OFF;
		int action = clock.ticksUntilNext(StepKind.TICK_ITEM);
		if (action < 0)
			action = clock.ticksUntilNext(StepKind.GATHER);

		guidance.update(detection, clock.currentStep(), action);
	}

	/**
	 * Scores a successful gather from an XP gain in the recipe's skill.
	 *
	 * @param event the stat-changed event
	 */
	@Subscribe
	public void onStatChanged(StatChanged event)
	{
		if (activeRecipe == null || accuracy == null)
			return;

		GatherSignal signal = activeRecipe.signal();
		if (signal == null || signal.kind() != GatherSignal.Kind.XP_DELTA || event.getSkill() != signal.skill())
			return;

		int xp = event.getXp();
		if (lastSkillXp >= 0 && xp > lastSkillXp)
		{
			accuracy.onXp(xp - lastSkillXp);
			registerGather();
		}

		lastSkillXp = xp;
	}

	/**
	 * Scores a successful gather from an item-count increase (for methods that grant no XP).
	 *
	 * @param event the item-container-changed event
	 */
	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
		if (activeRecipe == null || accuracy == null || event.getContainerId() != InventoryID.INV)
			return;

		GatherSignal signal = activeRecipe.signal();
		if (signal == null || signal.kind() != GatherSignal.Kind.ITEM_COUNT)
			return;

		int count = event.getItemContainer().count(signal.itemId());
		if (lastItemCount >= 0 && count > lastItemCount)
			registerGather();

		lastItemCount = count;
	}

	/**
	 * Re-anchors the beat to the tick-item step when the player actually clicks the tick item.
	 *
	 * @param event the menu-click event
	 */
	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		if (activeRecipe == null || clock == null)
			return;

		if (!activeRecipe.tickItemIds().contains(event.getItemId()))
			return;

		int index = stepIndexOf(activeRecipe, StepKind.TICK_ITEM);
		if (index >= 0)
			clock.resyncTo(index, 0);
	}

	/**
	 * Rebuilds the fallback metronome when the manual cadence changes.
	 *
	 * @param event the config-changed event
	 */
	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!TickAssistConfig.GROUP.equals(event.getGroup()))
			return;

		if ("customCadence".equals(event.getKey()))
			rebuildFallback();
	}

	/**
	 * Returns the clock currently driving the beat, or {@code null} when the plugin is stopped.
	 *
	 * @return the tick clock, or {@code null}
	 */
	TickClock clock()
	{
		return clock;
	}

	/**
	 * Returns the current detection result, or {@code null} when nothing is detected.
	 *
	 * @return the current match, or {@code null}
	 */
	RecipeMatch currentMatch()
	{
		return currentMatch;
	}

	/**
	 * Returns the current guidance state, or {@code null} when the plugin is stopped.
	 *
	 * @return the guidance state, or {@code null}
	 */
	GuidanceState guidance()
	{
		return guidance;
	}

	/**
	 * Returns the recipe currently driving the beat, or {@code null} when the plugin is stopped.
	 *
	 * @return the active recipe, or {@code null}
	 */
	TickRecipe activeRecipe()
	{
		return activeRecipe;
	}

	/**
	 * Returns the accuracy tracker for the active recipe, or {@code null} when the plugin is stopped.
	 *
	 * @return the accuracy tracker, or {@code null}
	 */
	AccuracyTracker accuracy()
	{
		return accuracy;
	}

	private void registerGather()
	{
		accuracy.onGather(gameTick);
		guidance.onGather();
		gatheredThisCycle = true;
		int gatherStep = stepIndexOf(activeRecipe, StepKind.GATHER);
		if (gatherStep >= 0 && clock != null)
			clock.resyncTo(gatherStep, 0);
	}

	private int stepIndexOf(TickRecipe recipe, StepKind kind)
	{
		List<TickStep> steps = recipe.steps();
		for (int i = 0; i < steps.size(); i++)
		{
			if (steps.get(i).kind() == kind)
				return i;
		}

		return -1;
	}

	private TickRecipe selectRecipe(int animationId)
	{
		if (config.autoDetect())
		{
			Set<Integer> nearby = resourceScanner.nearbyResourceIds(config.scanRadius());
			Set<Integer> held = inventoryScanner.heldItemIds();
			Optional<RecipeMatch> match = RecipeMatcher.match(nearby, held, animationId, null, catalog);
			if (match.isPresent() && match.get().state() != DetectionState.OFF)
			{
				currentMatch = match.get();
				return currentMatch.recipe();
			}
		}

		currentMatch = null;
		return fallback;
	}

	private void rebuildFallback()
	{
		fallback = RecipeCatalog.customMetronome(config.customCadence());
		if (currentMatch == null)
		{
			activeRecipe = fallback;
			clock = new TickClock(fallback.steps());
			accuracy = new AccuracyTracker(fallback.cadenceTicks());
		}
	}

	private int currentAnimationId()
	{
		Player local = client.getLocalPlayer();
		return local == null ? -1 : local.getAnimation();
	}
}
