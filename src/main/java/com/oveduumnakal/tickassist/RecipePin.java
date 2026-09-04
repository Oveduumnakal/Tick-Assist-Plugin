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
 * The manual override for detection: {@link #AUTO} lets the plugin choose, a specific value forces
 * that recipe, and {@link #CUSTOM_METRONOME} forces the plain manual beat. The {@code recipeId}
 * links a value to its {@link TickRecipe} id (null for the non-recipe options).
 */
public enum RecipePin
{
	/** Let detection choose the recipe. */
	AUTO("Auto-detect", null),
	/** Force 3-tick fishing. */
	FISHING("3-tick fishing", "three_tick_fishing"),
	/** Force 3-tick mining. */
	MINING("3-tick mining", "three_tick_mining"),
	/** Force 1-tick karambwan cooking. */
	KARAMBWAN("1-tick karambwan", "one_tick_karambwan"),
	/** Force 3-tick herblore. */
	HERBLORE("3-tick herblore", "three_tick_herblore"),
	/** Force 3-tick snake weed. */
	SNAKE_WEED("3-tick snake weed", "three_tick_snake_weed"),
	/** Force the plain manual metronome. */
	CUSTOM_METRONOME("Custom metronome", "custom_metronome");

	private final String displayName;
	private final String recipeId;

	RecipePin(String displayName, String recipeId)
	{
		this.displayName = displayName;
		this.recipeId = recipeId;
	}

	/**
	 * Returns the id of the recipe this pin forces, or {@code null} for {@link #AUTO}.
	 *
	 * @return the recipe id, or {@code null}
	 */
	public String recipeId()
	{
		return recipeId;
	}

	/**
	 * Returns the dropdown label.
	 *
	 * @return the display label
	 */
	@Override
	public String toString()
	{
		return displayName;
	}
}
