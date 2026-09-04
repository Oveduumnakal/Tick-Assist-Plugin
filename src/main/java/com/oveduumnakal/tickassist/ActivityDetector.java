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

import java.util.Set;

/**
 * Pure state machine that classifies the player's activity from their animation each tick.
 *
 * <p>While a gather animation plays the state is {@link ActivityState#RUNNING}; when it stops the
 * state holds at {@link ActivityState#STALLED} for a grace window (so the beat freezes rather than
 * drifting) before falling to {@link ActivityState#IDLE}. {@link #risingEdge()} is true on the tick
 * the gather starts, which the plugin uses as a fallback clock anchor.
 */
public final class ActivityDetector
{
	private final int stallTicks;
	private ActivityState state;
	private int stalledTicks;
	private boolean risingEdge;

	/**
	 * Creates a detector.
	 *
	 * @param stallTicks how many ticks to hold {@link ActivityState#STALLED} before going idle
	 */
	public ActivityDetector(int stallTicks)
	{
		this.stallTicks = Math.max(1, stallTicks);
		this.state = ActivityState.IDLE;
		this.stalledTicks = 0;
		this.risingEdge = false;
	}

	/**
	 * Advances the detector by one tick with the player's current animation.
	 *
	 * @param animationId the player's animation id this tick ({@code -1} for none)
	 * @param gatherAnims the animation ids that count as gathering for the active recipe
	 * @return the resulting activity state
	 */
	public ActivityState update(int animationId, Set<Integer> gatherAnims)
	{
		boolean gathering = gatherAnims.contains(animationId);
		if (gathering)
		{
			risingEdge = state != ActivityState.RUNNING;
			state = ActivityState.RUNNING;
			stalledTicks = 0;
			return state;
		}

		risingEdge = false;
		if (state == ActivityState.RUNNING)
		{
			state = ActivityState.STALLED;
			stalledTicks = 1;
		}
		else if (state == ActivityState.STALLED)
		{
			stalledTicks++;
			if (stalledTicks >= stallTicks)
				state = ActivityState.IDLE;
		}

		return state;
	}

	/**
	 * Returns the current activity state.
	 *
	 * @return the current state
	 */
	public ActivityState state()
	{
		return state;
	}

	/**
	 * Whether the most recent {@link #update} was the tick a gather started.
	 *
	 * @return true on the rising edge of a gather
	 */
	public boolean risingEdge()
	{
		return risingEdge;
	}
}
