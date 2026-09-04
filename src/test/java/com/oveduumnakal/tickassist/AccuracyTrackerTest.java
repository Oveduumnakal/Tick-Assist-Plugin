/*
 * Copyright (c) 2026, Oveduumnakal
 * All rights reserved.
 */
package com.oveduumnakal.tickassist;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Unit tests for {@link AccuracyTracker} — exact vs forgiving scoring and hourly rates. */
public class AccuracyTrackerTest
{
	@Test
	public void exactCadenceGathersScoreAsHits()
	{
		AccuracyTracker tracker = new AccuracyTracker(3);
		tracker.onGather(0);
		tracker.onGather(3);
		tracker.onGather(6);
		assertEquals(1.0, tracker.successRate(), 0.0001);
		assertEquals(2, tracker.currentStreak());
	}

	@Test
	public void offCadenceBreaksTheExactStreakButKeepsForgiving()
	{
		AccuracyTracker tracker = new AccuracyTracker(3);
		tracker.onGather(0);
		tracker.onGather(3);
		tracker.onGather(6);
		tracker.onGather(10);
		assertEquals(2.0 / 3.0, tracker.successRate(), 0.0001);
		assertEquals(0, tracker.currentStreak());
		assertEquals(2, tracker.bestStreak());
		assertEquals(3, tracker.forgivingStreak());
	}

	@Test
	public void wildlyOffMissesBothStreaks()
	{
		AccuracyTracker tracker = new AccuracyTracker(3);
		tracker.onGather(0);
		tracker.onGather(8);
		assertEquals(0, tracker.currentStreak());
		assertEquals(0, tracker.forgivingStreak());
		assertEquals(0.0, tracker.successRate(), 0.0001);
	}

	@Test
	public void hourlyRatesProjectFromTicks()
	{
		AccuracyTracker tracker = new AccuracyTracker(3);
		tracker.onGather(0);
		tracker.onGather(3);
		tracker.onGather(6);
		tracker.onXp(90);
		for (int i = 0; i < 600; i++)
			tracker.onTick();

		assertEquals(30.0, tracker.actionsPerHour(), 0.0001);
		assertEquals(900.0, tracker.xpPerHour(), 0.0001);
	}

	@Test
	public void resetClearsEverything()
	{
		AccuracyTracker tracker = new AccuracyTracker(3);
		tracker.onGather(0);
		tracker.onGather(3);
		tracker.reset();
		assertEquals(0, tracker.currentStreak());
		assertEquals(0.0, tracker.successRate(), 0.0001);
	}
}
