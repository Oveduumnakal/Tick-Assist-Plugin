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
import java.util.Optional;
import javax.inject.Inject;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

/**
 * Highlights the nearest ground resource while the guidance points there, labelling it with the
 * countdown (per the chosen {@link CountdownStyle}), a pre-skill "Ready" hint when armed, or a
 * "Restart" cue when the cadence breaks.
 *
 * <p>Anchors to resource NPCs (fishing spots); object resources join once their ids are captured
 * in-game (Step-0), so until then this draws only for NPC-based recipes.
 */
public class TargetHighlightOverlay extends Overlay
{
	private static final Color GROUND_COLOR = new Color(0xFF, 0xC2, 0x3A);
	private static final Color ARMED_COLOR = new Color(0x5A, 0xA9, 0xE6);
	private static final Color BREAK_COLOR = new Color(0xE8, 0x50, 0x3A);

	private final TickAssistPlugin plugin;
	private final TickAssistConfig config;
	private final Client client;

	@Inject
	TargetHighlightOverlay(TickAssistPlugin plugin, TickAssistConfig config, Client client)
	{
		this.plugin = plugin;
		this.config = config;
		this.client = client;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	/**
	 * Outlines and labels the nearest resource when the guidance points at the ground.
	 *
	 * @param graphics the overlay graphics context
	 * @return always {@code null} (this overlay draws in the scene)
	 */
	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (config.metronomeStyle() != MetronomeStyle.TARGET_FOLLOW)
			return null;

		GuidanceState guidance = plugin.guidance();
		TickRecipe recipe = plugin.activeRecipe();
		if (guidance == null || recipe == null)
			return null;

		boolean armed = guidance.armed();
		boolean ground = guidance.focus() == HighlightFocus.GROUND;
		if (!armed && !ground)
			return null;

		Optional<NPC> target = TargetLocator.nearestResource(client, recipe.resourceIds());
		if (!target.isPresent())
			return null;

		Color color = armed ? ARMED_COLOR : (guidance.broken() ? BREAK_COLOR : GROUND_COLOR);
		OverlayUtil.renderActorOverlay(graphics, target.get(), label(guidance, armed), color);
		return null;
	}

	private String label(GuidanceState guidance, boolean armed)
	{
		if (armed)
			return "Ready";

		if (guidance.broken())
			return "Restart";

		CountdownStyle style = config.countdownStyle();
		boolean showNumber = style == CountdownStyle.NUMBER || style == CountdownStyle.RING_NUMBER;
		if (showNumber && guidance.countdown() >= 0)
			return Integer.toString(guidance.countdown());

		return "";
	}
}
