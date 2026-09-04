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

/**
 * How the beat is shown. {@link #TARGET_FOLLOW} is the ping-pong inventory/ground highlight
 * (lands in a later phase); the others draw an on-screen beat. The {@code displayName} is the
 * config-dropdown label.
 */
public enum MetronomeStyle
{
	/** The ping-pong inventory/ground highlight that follows the due action. */
	TARGET_FOLLOW("Follow the target"),
	/** A row of pips, one per tick in the cycle, with the current tick lit. */
	PIPS("Pips"),
	/** A single sweeping bar. */
	BAR("Bar"),
	/** A pulse that flashes on the action tick. */
	PULSE("Pulse"),
	/** No on-screen beat; rely on the infobox and stats only. */
	INFOBOX_ONLY("Infobox only");

	private final String displayName;

	MetronomeStyle(String displayName)
	{
		this.displayName = displayName;
	}

	/**
	 * Returns the config-dropdown label.
	 *
	 * @return the display label
	 */
	@Override
	public String toString()
	{
		return displayName;
	}
}
