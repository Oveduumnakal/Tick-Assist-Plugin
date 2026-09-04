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
import javax.inject.Inject;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

/**
 * Draws the on-screen beat as a row of pips — one per tick in the cycle — with the current tick
 * lit and the action tick (phase 0) coloured. It reads the plugin's {@link TickClock}, which the
 * plugin advances once per game tick, so the row steps in time with the game.
 *
 * <p>Phase-2 renderer for {@link MetronomeStyle#PIPS}; the other styles and the target-follow
 * highlight arrive in later phases.
 */
public class TickMetronomeOverlay extends Overlay
{
	private static final Color BACKGROUND = new Color(30, 25, 16, 180);
	private static final Color TITLE = new Color(0xE9, 0xDC, 0xC0);
	private static final Color BEAT = new Color(0x43, 0xE0, 0x8A);
	private static final Color OFF_BEAT = new Color(0x4A, 0x40, 0x30);
	private static final Color NOW = Color.WHITE;

	private static final int PIP = 10;
	private static final int GAP = 4;
	private static final int PAD = 6;
	private static final int TITLE_H = 14;

	private final TickAssistPlugin plugin;
	private final TickAssistConfig config;

	@Inject
	TickMetronomeOverlay(TickAssistPlugin plugin, TickAssistConfig config)
	{
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.BOTTOM_LEFT);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
	}

	/**
	 * Renders the pip row for the current clock, or nothing when there is no clock or the pip
	 * style is not selected.
	 *
	 * @param graphics the overlay graphics context
	 * @return the rendered size, or {@code null} when nothing is drawn
	 */
	@Override
	public Dimension render(Graphics2D graphics)
	{
		TickClock clock = plugin.clock();
		if (clock == null || config.metronomeStyle() != MetronomeStyle.PIPS)
			return null;

		int count = clock.cycleLength();
		int phase = clock.phase();
		String title = "Tick beat";

		int pipsWidth = count * PIP + Math.max(0, count - 1) * GAP;
		int textWidth = graphics.getFontMetrics().stringWidth(title);
		int width = Math.max(pipsWidth, textWidth) + PAD * 2;
		int height = PAD * 2 + TITLE_H + PIP;

		graphics.setColor(BACKGROUND);
		graphics.fillRoundRect(0, 0, width, height, 6, 6);

		graphics.setColor(TITLE);
		graphics.drawString(title, PAD, PAD + TITLE_H - 3);

		int y = PAD + TITLE_H;
		for (int i = 0; i < count; i++)
		{
			int x = PAD + i * (PIP + GAP);
			graphics.setColor(i == 0 ? BEAT : OFF_BEAT);
			graphics.fillRoundRect(x, y, PIP, PIP, 3, 3);
			if (i == phase)
			{
				graphics.setColor(NOW);
				graphics.drawRoundRect(x - 1, y - 1, PIP + 1, PIP + 1, 3, 3);
			}
		}

		return new Dimension(width, height);
	}
}
