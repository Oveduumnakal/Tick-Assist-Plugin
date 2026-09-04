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
 * The outcome of {@link RecipeMatcher}: which recipe was matched, how far it has progressed, and
 * at what confidence.
 */
public final class RecipeMatch
{
	private final TickRecipe recipe;
	private final DetectionState state;
	private final Confidence confidence;

	/**
	 * Creates a match.
	 *
	 * @param recipe     the matched recipe
	 * @param state      the detection state
	 * @param confidence the match confidence
	 */
	public RecipeMatch(TickRecipe recipe, DetectionState state, Confidence confidence)
	{
		this.recipe = recipe;
		this.state = state;
		this.confidence = confidence;
	}

	/**
	 * Returns the matched recipe.
	 *
	 * @return the recipe
	 */
	public TickRecipe recipe()
	{
		return recipe;
	}

	/**
	 * Returns the detection state.
	 *
	 * @return the state
	 */
	public DetectionState state()
	{
		return state;
	}

	/**
	 * Returns the match confidence.
	 *
	 * @return the confidence
	 */
	public Confidence confidence()
	{
		return confidence;
	}
}
