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
 * One beat of a tick-manipulation cycle: what to do, for how many ticks, and where to point the
 * player's attention. Steps are immutable value objects assembled into a {@link TickRecipe}.
 */
public final class TickStep
{
	private final StepKind kind;
	private final int durationTicks;
	private final String label;
	private final HighlightFocus focus;

	/**
	 * Creates a step.
	 *
	 * @param kind          what the player does this step
	 * @param durationTicks how many ticks the step lasts (at least 1)
	 * @param label         a short human label shown in the guidance ("Click item", "Fish")
	 * @param focus         where to point the highlight for this step
	 */
	public TickStep(StepKind kind, int durationTicks, String label, HighlightFocus focus)
	{
		if (durationTicks < 1)
			throw new IllegalArgumentException("durationTicks must be >= 1, was " + durationTicks);

		this.kind = kind;
		this.durationTicks = durationTicks;
		this.label = label;
		this.focus = focus;
	}

	/**
	 * Returns the kind of action.
	 *
	 * @return the step kind
	 */
	public StepKind kind()
	{
		return kind;
	}

	/**
	 * Returns how many ticks the step lasts.
	 *
	 * @return the duration in ticks
	 */
	public int durationTicks()
	{
		return durationTicks;
	}

	/**
	 * Returns the short human label.
	 *
	 * @return the label
	 */
	public String label()
	{
		return label;
	}

	/**
	 * Returns where to point the highlight.
	 *
	 * @return the highlight focus
	 */
	public HighlightFocus focus()
	{
		return focus;
	}
}
