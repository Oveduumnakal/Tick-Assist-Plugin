/*
 * Copyright (c) 2026, Oveduumnakal
 * All rights reserved.
 */
package com.oveduumnakal.tickassist;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Unit tests for {@link GuidanceState} — focus derivation, armed hint, and break/recover. */
public class GuidanceStateTest
{
	private static final TickStep GROUND_STEP = new TickStep(StepKind.GATHER, 1, "Fish", HighlightFocus.GROUND);
	private static final TickStep ITEM_STEP = new TickStep(StepKind.TICK_ITEM, 1, "Item", HighlightFocus.INVENTORY);

	@Test
	public void offShowsNothing()
	{
		GuidanceState guidance = new GuidanceState();
		guidance.update(DetectionState.OFF, GROUND_STEP, 2);
		assertEquals(HighlightFocus.NONE, guidance.focus());
		assertFalse(guidance.armed());
	}

	@Test
	public void armedSetsTheHintWithoutFocus()
	{
		GuidanceState guidance = new GuidanceState();
		guidance.update(DetectionState.ARMED, GROUND_STEP, 2);
		assertTrue(guidance.armed());
		assertEquals(HighlightFocus.NONE, guidance.focus());
	}

	@Test
	public void activeFollowsTheStepFocusAndCountdown()
	{
		GuidanceState guidance = new GuidanceState();
		guidance.update(DetectionState.ACTIVE, GROUND_STEP, 2);
		assertEquals(HighlightFocus.GROUND, guidance.focus());
		assertEquals(2, guidance.countdown());

		guidance.update(DetectionState.ACTIVE, ITEM_STEP, 0);
		assertEquals(HighlightFocus.INVENTORY, guidance.focus());
	}

	@Test
	public void breakAndRecover()
	{
		GuidanceState guidance = new GuidanceState();
		guidance.onCountdownExpired();
		assertTrue(guidance.broken());

		guidance.onGather();
		assertFalse(guidance.broken());
	}
}
