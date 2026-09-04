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
 * Pure accuracy scorer. It compares the tick gap between successive gathers against the target
 * cadence: an exact match feeds an honest success rate and streak, while a within-one-tick match
 * feeds a more forgiving streak (so double-roll skills and near misses still reward progress).
 * It also accumulates gathers, ticks, and XP to project actions/hour and XP/hour.
 */
public final class AccuracyTracker
{
	private static final double TICKS_PER_HOUR = 6000.0;

	private final int cadence;
	private int lastGatherTick = -1;
	private int attempts;
	private int hits;
	private int currentStreak;
	private int bestStreak;
	private int forgivingStreak;
	private int bestForgivingStreak;
	private long totalGathers;
	private long totalTicks;
	private long totalXp;

	/**
	 * Creates a tracker for a target cadence.
	 *
	 * @param cadence the target tick gap between gathers
	 */
	public AccuracyTracker(int cadence)
	{
		this.cadence = cadence;
	}

	/**
	 * Records a successful gather at the given game tick.
	 *
	 * @param gameTick a monotonically increasing tick counter
	 */
	public void onGather(int gameTick)
	{
		totalGathers++;
		if (lastGatherTick >= 0)
		{
			int gap = gameTick - lastGatherTick;
			boolean exact = gap == cadence;
			boolean near = Math.abs(gap - cadence) <= 1;
			attempts++;
			if (exact)
			{
				hits++;
				currentStreak++;
				bestStreak = Math.max(bestStreak, currentStreak);
			}
			else
			{
				currentStreak = 0;
			}

			if (near)
			{
				forgivingStreak++;
				bestForgivingStreak = Math.max(bestForgivingStreak, forgivingStreak);
			}
			else
			{
				forgivingStreak = 0;
			}
		}

		lastGatherTick = gameTick;
	}

	/**
	 * Records one elapsed game tick, used to project the hourly rates.
	 */
	public void onTick()
	{
		totalTicks++;
	}

	/**
	 * Adds an XP gain, used to project XP/hour.
	 *
	 * @param delta the XP gained
	 */
	public void onXp(int delta)
	{
		totalXp += delta;
	}

	/**
	 * Returns the honest success rate (exact-cadence hits over attempts), 0 to 1.
	 *
	 * @return the success rate
	 */
	public double successRate()
	{
		return attempts == 0 ? 0.0 : (double) hits / attempts;
	}

	/**
	 * Returns the current exact-cadence streak.
	 *
	 * @return the current streak
	 */
	public int currentStreak()
	{
		return currentStreak;
	}

	/**
	 * Returns the best exact-cadence streak so far.
	 *
	 * @return the best streak
	 */
	public int bestStreak()
	{
		return bestStreak;
	}

	/**
	 * Returns the current within-one-tick (forgiving) streak.
	 *
	 * @return the forgiving streak
	 */
	public int forgivingStreak()
	{
		return forgivingStreak;
	}

	/**
	 * Returns the best within-one-tick streak so far.
	 *
	 * @return the best forgiving streak
	 */
	public int bestForgivingStreak()
	{
		return bestForgivingStreak;
	}

	/**
	 * Returns the projected gathers per hour from the elapsed ticks.
	 *
	 * @return actions per hour
	 */
	public double actionsPerHour()
	{
		return totalTicks == 0 ? 0.0 : totalGathers * TICKS_PER_HOUR / totalTicks;
	}

	/**
	 * Returns the projected XP per hour from the elapsed ticks.
	 *
	 * @return XP per hour
	 */
	public double xpPerHour()
	{
		return totalTicks == 0 ? 0.0 : totalXp * TICKS_PER_HOUR / totalTicks;
	}

	/**
	 * Clears all counters.
	 */
	public void reset()
	{
		lastGatherTick = -1;
		attempts = 0;
		hits = 0;
		currentStreak = 0;
		bestStreak = 0;
		forgivingStreak = 0;
		bestForgivingStreak = 0;
		totalGathers = 0;
		totalTicks = 0;
		totalXp = 0;
	}
}
