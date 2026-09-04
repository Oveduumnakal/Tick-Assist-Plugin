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
import java.util.Set;

import net.runelite.api.gameval.AnimationID;
import net.runelite.api.gameval.ItemID;

/**
 * Game id sets that drive detection, grouped by recipe. Item and animation ids use verified
 * {@code net.runelite.api.gameval} constants.
 *
 * <p><b>Step-0 (in-game) status:</b> the tick-item and mining/fishing/cooking animation sets are
 * resolved. The resource-entity sets (fishing-spot NPC ids, rock and vine object ids) still need
 * capturing with the RuneLite dev tools; until then they are empty, and a recipe with an empty
 * resource set arms on its held tick items alone rather than also requiring the resource in range.
 */
public final class TickAssistIds
{
	/** Tick items for 3-tick fishing: swamp tar + guam (with pestle) is the distinctive setup. */
	public static final Set<Integer> FISHING_TICK_ITEMS =
			Set.of(ItemID.SWAMP_TAR, ItemID.GUAM_LEAF, ItemID.PESTLE_AND_MORTAR, ItemID.VIAL_WATER);

	/** Barbarian/large-net fishing animation. */
	public static final Set<Integer> FISHING_ANIMS = Set.of(AnimationID.HUMAN_LARGENET);

	/** Fishing-spot NPC ids — capture in-game (Step-0). */
	public static final Set<Integer> FISHING_SPOTS = Collections.emptySet();

	/** Tick items for 3-tick mining: swamp tar + guam (with pestle). */
	public static final Set<Integer> MINING_TICK_ITEMS =
			Set.of(ItemID.SWAMP_TAR, ItemID.GUAM_LEAF, ItemID.PESTLE_AND_MORTAR);

	/** Mining animations across pickaxe tiers. */
	public static final Set<Integer> MINING_ANIMS = Set.of(
			AnimationID.HUMAN_MINING_BRONZE_PICKAXE, AnimationID.HUMAN_MINING_IRON_PICKAXE,
			AnimationID.HUMAN_MINING_STEEL_PICKAXE, AnimationID.HUMAN_MINING_BLACK_PICKAXE,
			AnimationID.HUMAN_MINING_MITHRIL_PICKAXE, AnimationID.HUMAN_MINING_ADAMANT_PICKAXE,
			AnimationID.HUMAN_MINING_RUNE_PICKAXE, AnimationID.HUMAN_MINING_DRAGON_PICKAXE_PRETTY,
			AnimationID.HUMAN_MINING_INFERNAL_PICKAXE);

	/** Rock object ids — capture in-game (Step-0). */
	public static final Set<Integer> MINING_ROCKS = Collections.emptySet();

	/** Raw karambwan for 1-tick cooking. Resolve the exact item id in-game (Step-0). */
	public static final Set<Integer> KARAMBWAN_TICK_ITEMS = Collections.emptySet();

	/** Cooking animation. */
	public static final Set<Integer> COOKING_ANIMS = Set.of(AnimationID.HUMAN_COOKING);

	/** Tick items for 3-tick herblore (herb tar): swamp tar + guam + pestle, inventory only. */
	public static final Set<Integer> HERBLORE_TICK_ITEMS =
			Set.of(ItemID.SWAMP_TAR, ItemID.GUAM_LEAF, ItemID.PESTLE_AND_MORTAR);

	/** Tick items alongside snake-weed picking. */
	public static final Set<Integer> SNAKE_WEED_TICK_ITEMS = Set.of(ItemID.SNAKE_WEED, ItemID.KNIFE);

	/** Marshy-vine object ids for snake weed — capture in-game (Step-0). */
	public static final Set<Integer> SNAKE_WEED_VINES = Collections.emptySet();

	/** Tick items that deplete with use, so a low-stock warning applies (not pestle/knife). */
	public static final Set<Integer> CONSUMABLE_TICK_ITEMS =
			Set.of(ItemID.SWAMP_TAR, ItemID.GUAM_LEAF, ItemID.VIAL_WATER);

	private TickAssistIds()
	{
	}
}
