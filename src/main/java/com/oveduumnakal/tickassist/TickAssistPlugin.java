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

import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

/**
 * Tick Assist — detects skilling tick-manipulation setups and visualises their timing.
 *
 * <p>The plugin watches the resources around the player and the items they carry; when a known
 * tick-manipulation setup is present it shows which item to click and when, with a live countdown
 * and accuracy feedback. It never clicks anything — it only visualises the beat.
 *
 * <p>This is the Phase-1 scaffold: it wires the plugin lifecycle and configuration only. The
 * detection, tick-clock, guidance, and overlay subsystems land in later phases.
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
	 * Starts the plugin. Phase-1 scaffold performs no work beyond logging.
	 */
	@Override
	protected void startUp()
	{
		log.debug("Tick Assist started (auto-detect: {})", config.autoDetect());
	}

	/**
	 * Stops the plugin and releases any resources it holds.
	 */
	@Override
	protected void shutDown()
	{
		log.debug("Tick Assist stopped");
	}
}
