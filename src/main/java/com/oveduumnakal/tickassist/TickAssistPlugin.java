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
	private ResourceScanner resourceScanner;

	@Inject
	private InventoryScanner inventoryScanner;

	private List<TickRecipe> catalog;
	private ActivityDetector activityDetector;
	private TickRecipe fallback;
	private TickRecipe activeRecipe;
	private TickClock clock;
	private RecipeMatch currentMatch;

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
		fallback = RecipeCatalog.customMetronome(config.customCadence());
		activeRecipe = fallback;
		clock = new TickClock(fallback.steps());
		currentMatch = null;
		overlayManager.add(metronomeOverlay);
		log.debug("Tick Assist started");
	}

	/**
	 * Stops the plugin: removes the overlay and drops all live state.
	 */
	@Override
	protected void shutDown()
	{
		overlayManager.remove(metronomeOverlay);
		clock = null;
		activeRecipe = null;
		currentMatch = null;
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
		int animationId = currentAnimationId();
		activityDetector.update(animationId, activeRecipe.gatherAnimationIds());

		TickRecipe selected = selectRecipe(animationId);
		if (!selected.id().equals(activeRecipe.id()))
		{
			activeRecipe = selected;
			clock = new TickClock(selected.steps());
		}

		if (clock != null)
			clock.tick();
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
		}
	}

	private int currentAnimationId()
	{
		Player local = client.getLocalPlayer();
		return local == null ? -1 : local.getAnimation();
	}
}
