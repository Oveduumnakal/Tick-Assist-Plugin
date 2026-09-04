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
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Locale;
import javax.inject.Inject;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

/**
 * A small on-screen panel of live timing stats — success %, streak, actions/hour, and XP/hour —
 * plus a low-stock warning for consumable tick items. Shown only while a recipe is detected and the
 * stats setting is on.
 */
public class TickStatsInfoBox extends Overlay
{
	private static final Color WARNING = new Color(0xE8, 0x50, 0x3A);

	private final PanelComponent panel = new PanelComponent();
	private final TickAssistPlugin plugin;
	private final TickAssistConfig config;
	private final TickItemMonitor tickItemMonitor;

	@Inject
	TickStatsInfoBox(TickAssistPlugin plugin, TickAssistConfig config, TickItemMonitor tickItemMonitor)
	{
		this.plugin = plugin;
		this.config = config;
		this.tickItemMonitor = tickItemMonitor;
		setPosition(OverlayPosition.TOP_LEFT);
	}

	/**
	 * Renders the stats panel when a recipe is detected and stats are enabled.
	 *
	 * @param graphics the overlay graphics context
	 * @return the rendered size, or {@code null} when nothing is drawn
	 */
	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!config.showAccuracy())
			return null;

		AccuracyTracker accuracy = plugin.accuracy();
		RecipeMatch match = plugin.currentMatch();
		if (accuracy == null || match == null)
			return null;

		panel.getChildren().clear();
		TitleComponent title = TitleComponent.builder()
				.text(match.recipe().displayName())
				.build();
		panel.getChildren().add(title);
		addLine("Success", String.format(Locale.US, "%.0f%%", accuracy.successRate() * 100));
		addLine("Streak", accuracy.currentStreak() + " (" + accuracy.bestStreak() + ")");
		addLine("Actions/hr", ShortFormat.compact(accuracy.actionsPerHour()));
		addLine("XP/hr", ShortFormat.compact(accuracy.xpPerHour()));

		if (config.warnLowTickItems() && tickItemMonitor.lowOnTickItems(match.recipe(), config.lowTickItemThreshold()))
		{
			panel.getChildren().add(LineComponent.builder()
					.left("Low tick items")
					.leftColor(WARNING)
					.build());
		}

		return panel.render(graphics);
	}

	private void addLine(String left, String right)
	{
		panel.getChildren().add(LineComponent.builder()
				.left(left)
				.right(right)
				.build());
	}
}
