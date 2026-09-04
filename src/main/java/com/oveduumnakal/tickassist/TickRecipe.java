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

import java.util.Collections;
import java.util.List;

/**
 * One entry in the detection catalog: a named tick-manipulation method described as an ordered
 * list of {@link TickStep}s plus how to recognise a successful gather and how eagerly to arm it.
 *
 * <p>Phase-2 scaffold holds the timing/scoring shape; the resource and tick-item id matchers used
 * by detection are added in Phase 3.
 */
public final class TickRecipe
{
	private final String id;
	private final String displayName;
	private final List<TickStep> steps;
	private final Confidence confidence;
	private final GatherSignal signal;
	private final String blurb;

	/**
	 * Creates a recipe.
	 *
	 * @param id          a stable lowercase identifier ("three_tick_fishing")
	 * @param displayName the label shown in the panel ("3-tick fishing")
	 * @param steps       the ordered cycle steps (at least one)
	 * @param confidence  how distinctive the setup is
	 * @param signal      the successful-gather signal
	 * @param blurb       a short "how it works" explainer
	 */
	public TickRecipe(String id, String displayName, List<TickStep> steps, Confidence confidence,
			GatherSignal signal, String blurb)
	{
		if (steps == null || steps.isEmpty())
			throw new IllegalArgumentException("a recipe needs at least one step");

		this.id = id;
		this.displayName = displayName;
		this.steps = Collections.unmodifiableList(steps);
		this.confidence = confidence;
		this.signal = signal;
		this.blurb = blurb;
	}

	/**
	 * Returns the stable identifier.
	 *
	 * @return the recipe id
	 */
	public String id()
	{
		return id;
	}

	/**
	 * Returns the panel display name.
	 *
	 * @return the display name
	 */
	public String displayName()
	{
		return displayName;
	}

	/**
	 * Returns the ordered, unmodifiable list of cycle steps.
	 *
	 * @return the steps
	 */
	public List<TickStep> steps()
	{
		return steps;
	}

	/**
	 * Returns the recipe's confidence tier.
	 *
	 * @return the confidence
	 */
	public Confidence confidence()
	{
		return confidence;
	}

	/**
	 * Returns the successful-gather signal.
	 *
	 * @return the gather signal
	 */
	public GatherSignal signal()
	{
		return signal;
	}

	/**
	 * Returns the short "how it works" explainer.
	 *
	 * @return the blurb
	 */
	public String blurb()
	{
		return blurb;
	}

	/**
	 * Returns the cadence in ticks: the sum of every step's duration (the length of one cycle).
	 *
	 * @return the cadence in ticks
	 */
	public int cadenceTicks()
	{
		return steps.stream()
				.mapToInt(TickStep::durationTicks)
				.sum();
	}
}
