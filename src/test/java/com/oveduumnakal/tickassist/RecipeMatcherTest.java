/*
 * Copyright (c) 2026, Oveduumnakal
 * All rights reserved.
 */
package com.oveduumnakal.tickassist;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Unit tests for {@link RecipeMatcher} — confidence gating, priority, and the pin override. */
public class RecipeMatcherTest
{
	private static final Set<Integer> NONE = Collections.emptySet();

	private static TickRecipe recipe(String id, Confidence confidence, Set<Integer> resources,
			Set<Integer> items, Set<Integer> anims)
	{
		return new TickRecipe(id, id,
				Arrays.asList(new TickStep(StepKind.GATHER, 1, "x", HighlightFocus.NONE)),
				confidence, null, resources, items, anims, "b");
	}

	private final TickRecipe fish = recipe("fish", Confidence.HIGH, Set.of(300), Set.of(100), Set.of(200));
	private final TickRecipe woodcut = recipe("woodcut", Confidence.GENERIC, NONE, Set.of(101), Set.of(201));
	private final List<TickRecipe> catalog = Arrays.asList(fish, woodcut);

	@Test
	public void highConfidenceItemsArmBeforeSkilling()
	{
		Optional<RecipeMatch> result = RecipeMatcher.match(NONE, Set.of(100), -1, null, catalog);
		assertTrue(result.isPresent());

		RecipeMatch match = result.get();
		assertEquals("fish", match.recipe().id());
		assertEquals(DetectionState.ARMED, match.state());
	}

	@Test
	public void genericItemsDoNotArmUntilSkilling()
	{
		Optional<RecipeMatch> idle = RecipeMatcher.match(NONE, Set.of(101), -1, null, catalog);
		assertFalse(idle.isPresent());

		Optional<RecipeMatch> skilling = RecipeMatcher.match(NONE, Set.of(101), 201, null, catalog);
		assertTrue(skilling.isPresent());

		RecipeMatch match = skilling.get();
		assertEquals("woodcut", match.recipe().id());
		assertEquals(DetectionState.ACTIVE, match.state());
	}

	@Test
	public void matchingAnimationIsActive()
	{
		Optional<RecipeMatch> result = RecipeMatcher.match(NONE, NONE, 200, null, catalog);
		assertTrue(result.isPresent());
		assertEquals(DetectionState.ACTIVE, result.get().state());
	}

	@Test
	public void resourceInRangeStillArms()
	{
		Optional<RecipeMatch> result = RecipeMatcher.match(Set.of(300), Set.of(100), -1, null, catalog);
		assertTrue(result.isPresent());

		RecipeMatch match = result.get();
		assertEquals("fish", match.recipe().id());
		assertEquals(DetectionState.ARMED, match.state());
	}

	@Test
	public void pinnedRecipeAlwaysWins()
	{
		Optional<RecipeMatch> result = RecipeMatcher.match(NONE, NONE, -1, woodcut, catalog);
		assertTrue(result.isPresent());

		RecipeMatch match = result.get();
		assertEquals("woodcut", match.recipe().id());
		assertEquals(DetectionState.ARMED, match.state());
	}

	@Test
	public void nothingMatchesWithNoSetup()
	{
		Optional<RecipeMatch> result = RecipeMatcher.match(NONE, NONE, -1, null, catalog);
		assertFalse(result.isPresent());
	}
}
