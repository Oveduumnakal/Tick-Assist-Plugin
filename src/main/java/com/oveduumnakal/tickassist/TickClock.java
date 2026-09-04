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
 * The pure timing engine: walks a recipe's {@link TickStep} list one game tick at a time and
 * reports where in the cycle it is.
 *
 * <p>It holds no client state and does no drawing, so it is fully unit-testable. The plugin advances
 * it once per {@code GameTick} and re-anchors it with {@link #resyncTo} when a gather event (or, as
 * a fallback, a gather animation) pins the true phase.
 */
public final class TickClock
{
	private final List<TickStep> steps;
	private final int cycleLength;
	private int stepIndex;
	private int tickInStep;

	/**
	 * Creates a clock over the given cycle steps, positioned at the start of the first step.
	 *
	 * @param steps the ordered cycle steps (at least one)
	 */
	public TickClock(List<TickStep> steps)
	{
		if (steps == null || steps.isEmpty())
			throw new IllegalArgumentException("a clock needs at least one step");

		this.steps = Collections.unmodifiableList(steps);
		this.cycleLength = steps.stream()
				.mapToInt(TickStep::durationTicks)
				.sum();
		this.stepIndex = 0;
		this.tickInStep = 0;
	}

	/**
	 * Advances the clock by one game tick, rolling over to the next step (and wrapping the cycle)
	 * when the current step's duration is spent.
	 */
	public void tick()
	{
		tickInStep++;
		if (tickInStep >= steps.get(stepIndex).durationTicks())
		{
			tickInStep = 0;
			stepIndex = (stepIndex + 1) % steps.size();
		}
	}

	/**
	 * Re-anchors the clock to an exact position, used when an external event pins the true phase.
	 *
	 * @param stepIndex  the step to jump to
	 * @param tickInStep the tick offset within that step
	 */
	public void resyncTo(int stepIndex, int tickInStep)
	{
		if (stepIndex < 0 || stepIndex >= steps.size())
			throw new IllegalArgumentException("stepIndex out of range: " + stepIndex);

		if (tickInStep < 0 || tickInStep >= steps.get(stepIndex).durationTicks())
			throw new IllegalArgumentException("tickInStep out of range: " + tickInStep);

		this.stepIndex = stepIndex;
		this.tickInStep = tickInStep;
	}

	/**
	 * Returns the step the clock is currently in.
	 *
	 * @return the current step
	 */
	public TickStep currentStep()
	{
		return steps.get(stepIndex);
	}

	/**
	 * Returns the number of ticks until the next step of the given kind begins. Returns 0 when the
	 * current step is that kind and is just starting, or -1 when no step of that kind exists.
	 *
	 * @param kind the step kind to look ahead for
	 * @return ticks until that kind's next start, 0 if it starts now, or -1 if absent
	 */
	public int ticksUntilNext(StepKind kind)
	{
		if (tickInStep == 0 && steps.get(stepIndex).kind() == kind)
			return 0;

		int offset = steps.get(stepIndex).durationTicks() - tickInStep;
		int idx = (stepIndex + 1) % steps.size();
		for (int scanned = 0; scanned < steps.size(); scanned++)
		{
			if (steps.get(idx).kind() == kind)
				return offset;

			offset += steps.get(idx).durationTicks();
			idx = (idx + 1) % steps.size();
		}

		return -1;
	}

	/**
	 * Returns the total length of one cycle in ticks.
	 *
	 * @return the cycle length
	 */
	public int cycleLength()
	{
		return cycleLength;
	}

	/**
	 * Returns the absolute tick position within the cycle, from 0 to {@code cycleLength - 1}.
	 *
	 * @return the current phase
	 */
	public int phase()
	{
		int p = tickInStep;
		for (int i = 0; i < stepIndex; i++)
			p += steps.get(i).durationTicks();

		return p;
	}

	/**
	 * Returns the index of the current step.
	 *
	 * @return the current step index
	 */
	public int stepIndex()
	{
		return stepIndex;
	}

	/**
	 * Returns the tick offset within the current step.
	 *
	 * @return the tick offset within the step
	 */
	public int tickInStep()
	{
		return tickInStep;
	}
}
