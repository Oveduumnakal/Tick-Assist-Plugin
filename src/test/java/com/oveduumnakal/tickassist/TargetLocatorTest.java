/*
 * Copyright (c) 2026, Oveduumnakal
 * All rights reserved.
 */
package com.oveduumnakal.tickassist;

import java.util.Arrays;
import java.util.Collections;
import java.util.OptionalInt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Unit tests for {@link TargetLocator#nearestIndex} — the pure nearest-of-candidates math. */
public class TargetLocatorTest
{
	@Test
	public void picksTheSmallestDistance()
	{
		OptionalInt nearest = TargetLocator.nearestIndex(Arrays.asList(5, 2, 8));
		assertTrue(nearest.isPresent());
		assertEquals(1, nearest.getAsInt());
	}

	@Test
	public void tiesKeepTheEarliestIndex()
	{
		OptionalInt nearest = TargetLocator.nearestIndex(Arrays.asList(3, 3, 3));
		assertTrue(nearest.isPresent());
		assertEquals(0, nearest.getAsInt());
	}

	@Test
	public void emptyIsAbsent()
	{
		assertFalse(TargetLocator.nearestIndex(Collections.emptyList()).isPresent());
	}
}
