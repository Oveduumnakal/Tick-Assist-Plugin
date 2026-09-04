/*
 * Copyright (c) 2026, Oveduumnakal
 * All rights reserved.
 */
package com.oveduumnakal.tickassist;

import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Unit tests for {@link ActivityDetector} — running/stalled/idle transitions and the rising edge. */
public class ActivityDetectorTest
{
	private static final Set<Integer> ANIMS = Set.of(200);

	@Test
	public void runningWhileGatheringWithRisingEdgeOnStart()
	{
		ActivityDetector detector = new ActivityDetector(3);
		assertEquals(ActivityState.RUNNING, detector.update(200, ANIMS));
		assertTrue(detector.risingEdge());

		assertEquals(ActivityState.RUNNING, detector.update(200, ANIMS));
		assertFalse(detector.risingEdge());
	}

	@Test
	public void stallsForTheGraceWindowThenGoesIdle()
	{
		ActivityDetector detector = new ActivityDetector(3);
		detector.update(200, ANIMS);

		assertEquals(ActivityState.STALLED, detector.update(-1, ANIMS));
		assertEquals(ActivityState.STALLED, detector.update(-1, ANIMS));
		assertEquals(ActivityState.IDLE, detector.update(-1, ANIMS));
	}

	@Test
	public void unrelatedAnimationDoesNotCountAsGathering()
	{
		ActivityDetector detector = new ActivityDetector(3);
		assertEquals(ActivityState.IDLE, detector.update(999, ANIMS));
		assertFalse(detector.risingEdge());
	}

	@Test
	public void resumingGatheringRaisesTheEdgeAgain()
	{
		ActivityDetector detector = new ActivityDetector(1);
		detector.update(200, ANIMS);
		detector.update(-1, ANIMS);
		detector.update(-1, ANIMS);
		assertEquals(ActivityState.RUNNING, detector.update(200, ANIMS));
		assertTrue(detector.risingEdge());
	}
}
