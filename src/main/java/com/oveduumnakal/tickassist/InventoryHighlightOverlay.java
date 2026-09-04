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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.inject.Inject;

import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

/**
 * Highlights the tick item(s) in the inventory while the guidance points there ({@code INVENTORY}
 * focus). Draws only an outline — it never clicks or moves anything.
 */
public class InventoryHighlightOverlay extends WidgetItemOverlay
{
	private static final Color INVENTORY_GLOW = new Color(0x43, 0xE0, 0x8A);

	private final TickAssistPlugin plugin;
	private final TickAssistConfig config;

	@Inject
	InventoryHighlightOverlay(TickAssistPlugin plugin, TickAssistConfig config)
	{
		this.plugin = plugin;
		this.config = config;
		showOnInventory();
	}

	/**
	 * Outlines an inventory item when it is the tick item currently due.
	 *
	 * @param graphics   the overlay graphics context
	 * @param itemId     the id of the item in this slot
	 * @param widgetItem the inventory slot widget
	 */
	@Override
	public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem)
	{
		if (config.metronomeStyle() != MetronomeStyle.TARGET_FOLLOW)
			return;

		GuidanceState guidance = plugin.guidance();
		TickRecipe recipe = plugin.activeRecipe();
		if (guidance == null || recipe == null || guidance.focus() != HighlightFocus.INVENTORY)
			return;

		if (!recipe.tickItemIds().contains(itemId))
			return;

		Rectangle bounds = widgetItem.getCanvasBounds();
		OverlayUtil.renderPolygon(graphics, bounds, INVENTORY_GLOW);
	}
}
