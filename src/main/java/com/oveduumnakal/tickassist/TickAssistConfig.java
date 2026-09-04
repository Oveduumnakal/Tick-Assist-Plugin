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
import net.runelite.client.config.Notification;
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
	 * A manual override: force a specific technique, or leave on {@link RecipePin#AUTO} to detect.
	 *
	 * @return the pinned recipe selection
	 */
	@ConfigItem(
		keyName = "pinnedRecipe",
		name = "Technique",
		description = "Force a technique, or leave on Auto-detect to choose from context."
	)
	default RecipePin pinnedRecipe()
	{
		return RecipePin.AUTO;
	}

	/**
	 * How far, in tiles, to look for a manipulable resource when detecting a setup.
	 *
	 * @return the scan radius in tiles
	 */
	@Range(min = 1, max = 15)
	@ConfigItem(
		keyName = "scanRadius",
		name = "Detection range",
		description = "How far, in tiles, to look for a manipulable resource."
	)
	default int scanRadius()
	{
		return 5;
	}

	/**
	 * How the beat is displayed. {@link MetronomeStyle#TARGET_FOLLOW} is the ping-pong highlight;
	 * the other styles draw an on-screen beat instead.
	 *
	 * @return the chosen metronome style
	 */
	@ConfigItem(
		keyName = "metronomeStyle",
		name = "Beat display",
		description = "How the tick beat is shown: follow the target (highlight) or an on-screen beat."
	)
	default MetronomeStyle metronomeStyle()
	{
		return MetronomeStyle.TARGET_FOLLOW;
	}

	/**
	 * How the countdown to the next action is drawn on the ground target.
	 *
	 * @return the chosen countdown style
	 */
	@ConfigItem(
		keyName = "countdownStyle",
		name = "Countdown",
		description = "How the countdown to the next action is shown on the target."
	)
	default CountdownStyle countdownStyle()
	{
		return CountdownStyle.RING_NUMBER;
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

	/**
	 * Whether to show the live accuracy infobox (success %, streak, actions/hour, XP/hour).
	 *
	 * @return true when the stats infobox is shown
	 */
	@ConfigItem(
		keyName = "showAccuracy",
		name = "Show stats",
		description = "Show the live success %, streak, and actions/hour while skilling."
	)
	default boolean showAccuracy()
	{
		return true;
	}

	/**
	 * Whether to play a short beep on each action tick.
	 *
	 * @return true when the beat beep is on
	 */
	@ConfigItem(
		keyName = "beepOnBeat",
		name = "Beep on the beat",
		description = "Play a short beep on each action tick."
	)
	default boolean beepOnBeat()
	{
		return false;
	}

	/**
	 * Notification fired when the cadence breaks (you fall off the rhythm).
	 *
	 * @return the break-notification style
	 */
	@ConfigItem(
		keyName = "notifyOnBreak",
		name = "Notify on break",
		description = "Notify when the tick cadence breaks."
	)
	default Notification notifyOnBreak()
	{
		return Notification.OFF;
	}

	/**
	 * Whether to warn when a consumable tick item is running low.
	 *
	 * @return true when the low-stock warning is on
	 */
	@ConfigItem(
		keyName = "warnLowTickItems",
		name = "Warn on low tick items",
		description = "Warn when a consumable tick item (swamp tar, herb, vial) is running low."
	)
	default boolean warnLowTickItems()
	{
		return true;
	}

	/**
	 * The count below which a consumable tick item is considered low.
	 *
	 * @return the low-stock threshold
	 */
	@Range(min = 1, max = 1000)
	@ConfigItem(
		keyName = "lowTickItemThreshold",
		name = "Low tick-item threshold",
		description = "Warn when a consumable tick item drops below this count."
	)
	default int lowTickItemThreshold()
	{
		return 25;
	}
}
