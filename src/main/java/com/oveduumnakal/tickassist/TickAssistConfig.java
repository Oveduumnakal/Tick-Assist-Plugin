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

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

/**
 * RuneLite configuration for Tick Assist.
 *
 * <p>Grows a subsystem at a time. Phase 2 adds the on-screen beat: a metronome style and, until
 * detection lands, a manual cadence to drive it. The full surface (countdown style, confidence,
 * accuracy stats, audio cue, tick-item warnings) arrives in later phases.
 */
@ConfigGroup(TickAssistConfig.GROUP)
public interface TickAssistConfig extends Config
{
	/** The config group key, shared with {@code ConfigChanged} handling. */
	String GROUP = "tickassist";

	/**
	 * Whether the plugin auto-detects tick-manipulation setups from nearby resources and the
	 * items the player is carrying.
	 *
	 * @return true when context detection is enabled
	 */
	@ConfigItem(
		keyName = "autoDetect",
		name = "Auto-detect setups",
		description = "Detect tick-manipulation setups from nearby resources and the items you're carrying."
	)
	default boolean autoDetect()
	{
		return true;
	}

	/**
	 * How the beat is displayed. Phase 2 renders {@link MetronomeStyle#PIPS}; the default becomes
	 * {@link MetronomeStyle#TARGET_FOLLOW} once that highlight lands.
	 *
	 * @return the chosen metronome style
	 */
	@ConfigItem(
		keyName = "metronomeStyle",
		name = "Beat display",
		description = "How the tick beat is shown on screen."
	)
	default MetronomeStyle metronomeStyle()
	{
		return MetronomeStyle.PIPS;
	}

	/**
	 * The cadence, in ticks, of the manual beat used until context detection selects a technique.
	 *
	 * @return the manual cadence in ticks
	 */
	@Range(min = 1, max = 10)
	@ConfigItem(
		keyName = "customCadence",
		name = "Manual cadence",
		description = "Beat length in ticks for the manual metronome (used until a technique is detected)."
	)
	default int customCadence()
	{
		return 3;
	}
}
