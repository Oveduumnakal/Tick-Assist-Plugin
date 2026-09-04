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

import javax.inject.Inject;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;

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
 * <p>The plugin watches the resources around the player and the items they carry; when a known
 * tick-manipulation setup is present it shows which item to click and when, with a live countdown
 * and accuracy feedback. It never clicks anything — it only visualises the beat.
 *
 * <p>Phase 2 wires the tick clock to the game and draws a manual metronome. Context detection, the
 * ping-pong highlight, and accuracy stats land in later phases; until then the beat runs at the
 * manual cadence from the config.
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
	@Inject
	private TickAssistConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private TickMetronomeOverlay metronomeOverlay;

	private TickClock clock;

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
	 * Starts the plugin: builds the manual-cadence clock and registers the metronome overlay.
	 */
	@Override
	protected void startUp()
	{
		rebuildClock();
		overlayManager.add(metronomeOverlay);
		log.debug("Tick Assist started");
	}

	/**
	 * Stops the plugin: removes the overlay and drops the clock.
	 */
	@Override
	protected void shutDown()
	{
		overlayManager.remove(metronomeOverlay);
		clock = null;
		log.debug("Tick Assist stopped");
	}

	/**
	 * Advances the beat by one game tick.
	 *
	 * @param event the game-tick event
	 */
	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (clock != null)
			clock.tick();
	}

	/**
	 * Rebuilds the clock when the manual cadence changes.
	 *
	 * @param event the config-changed event
	 */
	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!TickAssistConfig.GROUP.equals(event.getGroup()))
			return;

		if ("customCadence".equals(event.getKey()))
			rebuildClock();
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

	private void rebuildClock()
	{
		TickRecipe metronome = RecipeCatalog.customMetronome(config.customCadence());
		clock = new TickClock(metronome.steps());
	}
}
