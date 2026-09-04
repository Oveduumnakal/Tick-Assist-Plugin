/*
 * Copyright (c) 2026, Oveduumnakal
 * All rights reserved.
 */
package com.oveduumnakal.tickassist;

import org.junit.Test;

import net.runelite.api.Skill;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/** Unit tests for {@link RecipeCatalog} — seed integrity and the custom metronome builder. */
public class RecipeCatalogTest
{
	@Test
	public void shipsFiveSeedRecipes()
	{
		assertEquals(5, RecipeCatalog.seedRecipes().size());
	}

	@Test
	public void everyRecipeIsInternallyConsistent()
	{
		for (TickRecipe recipe : RecipeCatalog.seedRecipes())
		{
			assertNotNull("id", recipe.id());
			assertNotNull("displayName for " + recipe.id(), recipe.displayName());
			assertNotNull("blurb for " + recipe.id(), recipe.blurb());
			assertNotNull("signal for " + recipe.id(), recipe.signal());
			assertFalse("steps for " + recipe.id(), recipe.steps().isEmpty());

			int sum = recipe.steps().stream()
					.mapToInt(TickStep::durationTicks)
					.sum();
			assertEquals("cadence equals sum of durations for " + recipe.id(), sum, recipe.cadenceTicks());
		}
	}

	@Test
	public void recipeIdsAreUnique()
	{
		long distinct = RecipeCatalog.seedRecipes().stream()
				.map(TickRecipe::id)
				.distinct()
				.count();
		assertEquals(5, distinct);
	}

	@Test
	public void knownCadencesAreCorrect()
	{
		TickRecipe fishing = findById("three_tick_fishing");
		assertEquals(3, fishing.cadenceTicks());
		assertEquals(GatherSignal.Kind.XP_DELTA, fishing.signal().kind());
		assertEquals(Skill.FISHING, fishing.signal().skill());

		TickRecipe karambwan = findById("one_tick_karambwan");
		assertEquals(1, karambwan.cadenceTicks());
	}

	@Test
	public void snakeWeedUsesAnItemCountSignal()
	{
		TickRecipe snakeWeed = findById("three_tick_snake_weed");
		assertEquals(GatherSignal.Kind.ITEM_COUNT, snakeWeed.signal().kind());
	}

	@Test
	public void customMetronomeHasTheGivenCadence()
	{
		assertEquals(3, RecipeCatalog.customMetronome(3).cadenceTicks());

		TickRecipe oneTick = RecipeCatalog.customMetronome(1);
		assertEquals(1, oneTick.cadenceTicks());

		TickStep firstStep = oneTick.steps().get(0);
		assertEquals(StepKind.GATHER, firstStep.kind());
	}

	@Test(expected = IllegalArgumentException.class)
	public void customMetronomeRejectsZeroCadence()
	{
		RecipeCatalog.customMetronome(0);
	}

	private static TickRecipe findById(String id)
	{
		return RecipeCatalog.seedRecipes().stream()
				.filter(recipe -> recipe.id().equals(id))
				.findFirst()
				.orElseThrow(() -> new AssertionError("missing recipe: " + id));
	}
}
