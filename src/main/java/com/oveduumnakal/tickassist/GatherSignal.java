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

import net.runelite.api.Skill;

/**
 * The rule that marks a successful gather for a recipe. XP-granting methods watch a skill's XP
 * for an increase; item-only methods (e.g. snake weed, which grants no XP) watch a specific
 * item's inventory count instead.
 */
public final class GatherSignal
{
	/**
	 * Which kind of change marks a successful gather.
	 */
	public enum Kind
	{
		/** An increase in a skill's XP. */
		XP_DELTA,
		/** An increase in a specific item's inventory count. */
		ITEM_COUNT
	}

	private final Kind kind;
	private final Skill skill;
	private final int itemId;

	private GatherSignal(Kind kind, Skill skill, int itemId)
	{
		this.kind = kind;
		this.skill = skill;
		this.itemId = itemId;
	}

	/**
	 * Creates an XP-delta signal for the given skill.
	 *
	 * @param skill the skill whose XP increase marks a gather
	 * @return the signal
	 */
	public static GatherSignal xp(Skill skill)
	{
		return new GatherSignal(Kind.XP_DELTA, skill, -1);
	}

	/**
	 * Creates an item-count signal for the given item id.
	 *
	 * @param itemId the item whose count increase marks a gather
	 * @return the signal
	 */
	public static GatherSignal itemCount(int itemId)
	{
		return new GatherSignal(Kind.ITEM_COUNT, null, itemId);
	}

	/**
	 * Returns the kind of signal.
	 *
	 * @return the signal kind
	 */
	public Kind kind()
	{
		return kind;
	}

	/**
	 * Returns the skill for an {@link Kind#XP_DELTA} signal.
	 *
	 * @return the skill, or {@code null} for an item-count signal
	 */
	public Skill skill()
	{
		return skill;
	}

	/**
	 * Returns the item id for an {@link Kind#ITEM_COUNT} signal.
	 *
	 * @return the item id, or {@code -1} for an XP signal
	 */
	public int itemId()
	{
		return itemId;
	}
}
