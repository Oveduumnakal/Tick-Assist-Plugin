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
 * Pure ping-pong machine that turns the clock's current step and the detection state into what the
 * overlays draw: which target to highlight, the countdown, the pre-skill "armed" hint, and whether
 * the cadence has broken.
 */
public final class GuidanceState
{
	private HighlightFocus focus = HighlightFocus.NONE;
	private int countdown = -1;
	private boolean armed;
	private boolean broken;

	/**
	 * Recomputes the highlight for this tick.
	 *
	 * @param detection        the current detection state
	 * @param step             the clock's current step
	 * @param ticksUntilAction ticks until the next action is due (the ground countdown)
	 */
	public void update(DetectionState detection, TickStep step, int ticksUntilAction)
	{
		armed = detection == DetectionState.ARMED;
		if (detection != DetectionState.ACTIVE)
		{
			focus = HighlightFocus.NONE;
			countdown = -1;
			return;
		}

		focus = step.focus();
		countdown = ticksUntilAction;
	}

	/**
	 * Marks that a gather landed, clearing any broken state.
	 */
	public void onGather()
	{
		broken = false;
	}

	/**
	 * Marks that the action window elapsed with no gather — the cadence broke.
	 */
	public void onCountdownExpired()
	{
		broken = true;
	}

	/**
	 * Returns where to point the highlight this tick.
	 *
	 * @return the highlight focus
	 */
	public HighlightFocus focus()
	{
		return focus;
	}

	/**
	 * Returns the ticks until the next action, or -1 when idle.
	 *
	 * @return the countdown in ticks
	 */
	public int countdown()
	{
		return countdown;
	}

	/**
	 * Whether a setup is present but the player is not skilling yet.
	 *
	 * @return true when armed
	 */
	public boolean armed()
	{
		return armed;
	}

	/**
	 * Whether the cadence has broken (window elapsed without a gather).
	 *
	 * @return true when broken
	 */
	public boolean broken()
	{
		return broken;
	}
}
