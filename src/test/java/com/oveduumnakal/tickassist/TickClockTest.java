/*
 * Copyright (c) 2026, Oveduumnakal
 * All rights reserved.
 */
package com.oveduumnakal.tickassist;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Unit tests for {@link TickClock} — step walking, phase, look-ahead, and resync. */
public class TickClockTest
{
	private static List<TickStep> fishingCycle()
	{
		return Arrays.asList(
				new TickStep(StepKind.GATHER, 1, "Fish", HighlightFocus.GROUND),
				new TickStep(StepKind.TICK_ITEM, 1, "Item", HighlightFocus.INVENTORY),
				new TickStep(StepKind.WAIT, 1, "Wait", HighlightFocus.NONE));
	}

	private static List<TickStep> gatherThenWaitTwo()
	{
		return Arrays.asList(
				new TickStep(StepKind.GATHER, 1, "Act", HighlightFocus.NONE),
				new TickStep(StepKind.WAIT, 2, "Wait", HighlightFocus.NONE));
	}

	@Test
	public void cycleLengthIsSumOfDurations()
	{
		assertEquals(3, new TickClock(fishingCycle()).cycleLength());
		assertEquals(3, new TickClock(gatherThenWaitTwo()).cycleLength());
	}

	@Test
	public void tickWalksStepsAndWraps()
	{
		TickClock clock = new TickClock(fishingCycle());
		assertEquals(StepKind.GATHER, clock.currentStep().kind());
		assertEquals(0, clock.phase());

		clock.tick();
		assertEquals(StepKind.TICK_ITEM, clock.currentStep().kind());
		assertEquals(1, clock.phase());

		clock.tick();
		assertEquals(StepKind.WAIT, clock.currentStep().kind());
		assertEquals(2, clock.phase());

		clock.tick();
		assertEquals(StepKind.GATHER, clock.currentStep().kind());
		assertEquals(0, clock.phase());
	}

	@Test
	public void multiTickStepHoldsBeforeAdvancing()
	{
		TickClock clock = new TickClock(gatherThenWaitTwo());
		clock.tick();
		assertEquals(StepKind.WAIT, clock.currentStep().kind());
		assertEquals(0, clock.tickInStep());

		clock.tick();
		assertEquals(StepKind.WAIT, clock.currentStep().kind());
		assertEquals(1, clock.tickInStep());

		clock.tick();
		assertEquals(StepKind.GATHER, clock.currentStep().kind());
		assertEquals(0, clock.phase());
	}

	@Test
	public void ticksUntilNextCountsToTheStartOfThatKind()
	{
		TickClock clock = new TickClock(fishingCycle());
		assertEquals(1, clock.ticksUntilNext(StepKind.TICK_ITEM));
		assertEquals(2, clock.ticksUntilNext(StepKind.WAIT));
		assertEquals(0, clock.ticksUntilNext(StepKind.GATHER));

		clock.tick();
		assertEquals(0, clock.ticksUntilNext(StepKind.TICK_ITEM));
		assertEquals(2, clock.ticksUntilNext(StepKind.GATHER));
	}

	@Test
	public void ticksUntilNextIsMinusOneWhenAbsent()
	{
		TickClock clock = new TickClock(gatherThenWaitTwo());
		assertEquals(-1, clock.ticksUntilNext(StepKind.TICK_ITEM));
	}

	@Test
	public void resyncJumpsToTheGivenPosition()
	{
		TickClock clock = new TickClock(gatherThenWaitTwo());
		clock.resyncTo(1, 1);
		assertEquals(StepKind.WAIT, clock.currentStep().kind());
		assertEquals(2, clock.phase());
	}

	@Test(expected = IllegalArgumentException.class)
	public void resyncRejectsOutOfRangeStep()
	{
		new TickClock(fishingCycle()).resyncTo(3, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void resyncRejectsOutOfRangeTickInStep()
	{
		new TickClock(fishingCycle()).resyncTo(0, 1);
	}
}
