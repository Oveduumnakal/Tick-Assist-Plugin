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
import java.util.Optional;
import java.util.Set;

/**
 * Pure matcher that picks the active recipe from context: nearby resources, held items, and the
 * player's current animation.
 *
 * <p>Priority, highest first: the current animation matches a recipe's gather animation
 * ({@link DetectionState#ACTIVE}); a required resource is in range and the tick items are held
 * ({@link DetectionState#ARMED}); the tick items of a {@link Confidence#HIGH} recipe are held
 * ({@link DetectionState#ARMED}). A {@link Confidence#GENERIC} recipe (common items such as a bare
 * knife) stays off until its animation confirms the player is skilling it. A pinned recipe always
 * wins. Ties fall to catalog order.
 */
public final class RecipeMatcher
{
	private RecipeMatcher()
	{
	}

	/**
	 * Matches the best recipe for the given context.
	 *
	 * @param nearbyResourceIds   resource NPC/object ids currently in range
	 * @param heldItemIds         item ids the player is carrying
	 * @param currentAnimationId  the player's animation id this tick ({@code -1} for none)
	 * @param pinned              a forced recipe, or {@code null} for auto-detection
	 * @param recipes             the catalog to match against
	 * @return the best match, or empty when nothing matches
	 */
	public static Optional<RecipeMatch> match(Set<Integer> nearbyResourceIds, Set<Integer> heldItemIds,
			int currentAnimationId, TickRecipe pinned, List<TickRecipe> recipes)
	{
		if (pinned != null)
		{
			boolean active = pinned.gatherAnimationIds().contains(currentAnimationId);
			DetectionState state = active ? DetectionState.ACTIVE : DetectionState.ARMED;
			return Optional.of(new RecipeMatch(pinned, state, pinned.confidence()));
		}

		TickRecipe best = null;
		int bestPriority = 0;
		for (TickRecipe recipe : recipes)
		{
			int p = priority(recipe, nearbyResourceIds, heldItemIds, currentAnimationId);
			if (p > bestPriority)
			{
				bestPriority = p;
				best = recipe;
			}
		}

		if (best == null)
			return Optional.empty();

		DetectionState state = bestPriority == 3 ? DetectionState.ACTIVE : DetectionState.ARMED;
		return Optional.of(new RecipeMatch(best, state, best.confidence()));
	}

	private static int priority(TickRecipe recipe, Set<Integer> nearby, Set<Integer> held, int anim)
	{
		if (recipe.gatherAnimationIds().contains(anim))
			return 3;

		boolean itemMatch = !Collections.disjoint(held, recipe.tickItemIds());
		boolean resourceMatch = recipe.requiresResource() && !Collections.disjoint(nearby, recipe.resourceIds());
		if (resourceMatch && itemMatch)
			return 2;

		if (itemMatch && recipe.confidence() == Confidence.HIGH)
			return 1;

		return 0;
	}
}
