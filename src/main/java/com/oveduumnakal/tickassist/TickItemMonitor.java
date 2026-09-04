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

import javax.inject.Inject;

/**
 * Warns when a consumable tick item is running low, so the player can restock before the cadence
 * stalls. Reusable items (pestle, knife) are never flagged.
 */
public class TickItemMonitor
{
	private final InventoryScanner inventoryScanner;

	@Inject
	TickItemMonitor(InventoryScanner inventoryScanner)
	{
		this.inventoryScanner = inventoryScanner;
	}

	/**
	 * Whether any consumable tick item for the recipe is carried but below the threshold.
	 *
	 * @param recipe    the active recipe
	 * @param threshold the low-stock threshold
	 * @return true when a consumable tick item is running low
	 */
	public boolean lowOnTickItems(TickRecipe recipe, int threshold)
	{
		if (recipe == null)
			return false;

		for (int itemId : recipe.tickItemIds())
		{
			if (!TickAssistIds.CONSUMABLE_TICK_ITEMS.contains(itemId))
				continue;

			int count = inventoryScanner.count(itemId);
			if (count > 0 && count < threshold)
				return true;
		}

		return false;
	}
}
