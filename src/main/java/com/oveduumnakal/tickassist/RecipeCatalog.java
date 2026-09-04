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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.runelite.api.Skill;
import net.runelite.api.gameval.ItemID;

/**
 * The seed set of tick-manipulation {@link TickRecipe}s, plus the ad-hoc custom metronome.
 *
 * <p>The catalog is data: adding a method is one entry here (and its ids in the id table added in
 * Phase 3), with no change to the tick clock or overlays. Phase-2 recipes carry the timing and
 * scoring shape only; the resource and tick-item matchers are filled in Phase 3.
 */
public final class RecipeCatalog
{
	private RecipeCatalog()
	{
	}

	/**
	 * Builds the seed recipes shipped with the plugin.
	 *
	 * @return an unmodifiable list of the built-in recipes
	 */
	public static List<TickRecipe> seedRecipes()
	{
		List<TickRecipe> recipes = new ArrayList<>();

		recipes.add(new TickRecipe("three_tick_fishing", "3-tick fishing",
				Arrays.asList(
						new TickStep(StepKind.GATHER, 1, "Fish", HighlightFocus.GROUND),
						new TickStep(StepKind.TICK_ITEM, 1, "Tick item", HighlightFocus.INVENTORY),
						new TickStep(StepKind.WAIT, 1, "Wait", HighlightFocus.NONE)),
				Confidence.HIGH, GatherSignal.xp(Skill.FISHING),
				"Fish, then a 1-tick inventory action each cycle so the catch rolls every 3 ticks instead of 5."));

		recipes.add(new TickRecipe("three_tick_mining", "3-tick mining",
				Arrays.asList(
						new TickStep(StepKind.GATHER, 1, "Mine", HighlightFocus.GROUND),
						new TickStep(StepKind.TICK_ITEM, 1, "Tick item", HighlightFocus.INVENTORY),
						new TickStep(StepKind.WAIT, 1, "Wait", HighlightFocus.NONE)),
				Confidence.HIGH, GatherSignal.xp(Skill.MINING),
				"Mine, interleave a 1-tick item so the ore rolls every 3 ticks."));

		recipes.add(new TickRecipe("one_tick_karambwan", "1-tick karambwan cooking",
				Arrays.asList(
						new TickStep(StepKind.GATHER, 1, "Cook", HighlightFocus.INVENTORY)),
				Confidence.HIGH, GatherSignal.xp(Skill.COOKING),
				"Cook a raw karambwan every tick, synced to the XP drop — a rapid 1-tick beat."));

		recipes.add(new TickRecipe("three_tick_herblore", "3-tick herblore",
				Arrays.asList(
						new TickStep(StepKind.TICK_ITEM, 1, "Combine", HighlightFocus.INVENTORY),
						new TickStep(StepKind.TICK_ITEM, 1, "Combine", HighlightFocus.INVENTORY),
						new TickStep(StepKind.WAIT, 1, "Wait", HighlightFocus.NONE)),
				Confidence.HIGH, GatherSignal.xp(Skill.HERBLORE),
				"Combine on a 3-tick beat — inventory only, no ground target."));

		recipes.add(new TickRecipe("three_tick_snake_weed", "3-tick snake weed",
				Arrays.asList(
						new TickStep(StepKind.GATHER, 1, "Pick", HighlightFocus.GROUND),
						new TickStep(StepKind.TICK_ITEM, 1, "Tick item", HighlightFocus.INVENTORY),
						new TickStep(StepKind.WAIT, 1, "Wait", HighlightFocus.NONE)),
				Confidence.HIGH, GatherSignal.itemCount(ItemID.SNAKE_WEED),
				"Pick from the vines on a 3-tick beat; no XP, so scoring watches your snake-weed count."));

		return Collections.unmodifiableList(recipes);
	}

	/**
	 * Builds an ad-hoc plain metronome of the given cadence, for the manual "just give me a beat"
	 * pin. It carries no resource, tick item, or gather signal.
	 *
	 * @param cadenceTicks the beat length in ticks (at least 1)
	 * @return the custom-metronome recipe
	 */
	public static TickRecipe customMetronome(int cadenceTicks)
	{
		if (cadenceTicks < 1)
			throw new IllegalArgumentException("cadence must be >= 1, was " + cadenceTicks);

		List<TickStep> steps = new ArrayList<>();
		steps.add(new TickStep(StepKind.GATHER, 1, "Act", HighlightFocus.NONE));
		if (cadenceTicks > 1)
			steps.add(new TickStep(StepKind.WAIT, cadenceTicks - 1, "Wait", HighlightFocus.NONE));

		return new TickRecipe("custom_metronome", "Custom metronome", steps,
				Confidence.HIGH, null, "A plain " + cadenceTicks + "-tick beat with no resource or item awareness.");
	}
}
