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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Locale;
import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.runelite.client.config.ConfigManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

/**
 * The Tick Assist side panel: shows the detected technique and its explainer, lets the player pin
 * or disable detection, displays the live stats, and resets them.
 */
public class TickAssistPanel extends PluginPanel
{
	private final ConfigManager configManager;
	private final TickAssistPlugin plugin;
	private final JComboBox<RecipePin> pinBox = new JComboBox<>(RecipePin.values());
	private final JLabel detectedLabel = new JLabel();
	private final JLabel blurbLabel = new JLabel();
	private final JLabel successLabel = new JLabel();
	private final JLabel streakLabel = new JLabel();
	private final JLabel actionsLabel = new JLabel();
	private final JLabel xpLabel = new JLabel();

	@Inject
	TickAssistPanel(ConfigManager configManager, TickAssistPlugin plugin)
	{
		this.configManager = configManager;
		this.plugin = plugin;
		buildUi();
	}

	private void buildUi()
	{
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setBackground(ColorScheme.DARK_GRAY_COLOR);

		pinBox.setSelectedItem(configManager.getConfiguration(TickAssistConfig.GROUP, "pinnedRecipe",
				RecipePin.class));
		pinBox.addActionListener(e -> onPinChanged());
		pinBox.setAlignmentX(Component.LEFT_ALIGNMENT);

		blurbLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
		detectedLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		blurbLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JButton reset = new JButton("Reset stats");
		reset.addActionListener(e -> plugin.resetStats());
		reset.setAlignmentX(Component.LEFT_ALIGNMENT);

		content.add(new JLabel("Technique"));
		content.add(pinBox);
		content.add(spacer());
		content.add(detectedLabel);
		content.add(blurbLabel);
		content.add(spacer());
		content.add(statsPanel());
		content.add(spacer());
		content.add(reset);

		add(content, BorderLayout.NORTH);
		render(null, null);
	}

	private JPanel statsPanel()
	{
		JPanel stats = new JPanel(new GridLayout(0, 2, 4, 4));
		stats.setBackground(ColorScheme.DARK_GRAY_COLOR);
		stats.setAlignmentX(Component.LEFT_ALIGNMENT);
		stats.add(new JLabel("Success"));
		stats.add(successLabel);
		stats.add(new JLabel("Streak"));
		stats.add(streakLabel);
		stats.add(new JLabel("Actions/hr"));
		stats.add(actionsLabel);
		stats.add(new JLabel("XP/hr"));
		stats.add(xpLabel);
		return stats;
	}

	private Component spacer()
	{
		JPanel gap = new JPanel();
		gap.setBackground(ColorScheme.DARK_GRAY_COLOR);
		return gap;
	}

	/**
	 * Refreshes the panel from the current detection and stats, on the Swing thread.
	 *
	 * @param match    the current detection result, or {@code null}
	 * @param accuracy the current accuracy tracker, or {@code null}
	 */
	void update(RecipeMatch match, AccuracyTracker accuracy)
	{
		SwingUtilities.invokeLater(() -> render(match, accuracy));
	}

	private void render(RecipeMatch match, AccuracyTracker accuracy)
	{
		if (match == null)
		{
			detectedLabel.setText("Nothing detected");
			blurbLabel.setText("<html>Carry the tick items near a resource, or pin a technique.</html>");
		}
		else
		{
			detectedLabel.setText(match.recipe().displayName() + " — " + match.state());
			blurbLabel.setText("<html>" + match.recipe().blurb() + "</html>");
		}

		if (accuracy == null)
		{
			successLabel.setText("-");
			streakLabel.setText("-");
			actionsLabel.setText("-");
			xpLabel.setText("-");
			return;
		}

		successLabel.setText(String.format(Locale.US, "%.0f%%", accuracy.successRate() * 100));
		streakLabel.setText(accuracy.currentStreak() + " (" + accuracy.bestStreak() + ")");
		actionsLabel.setText(ShortFormat.compact(accuracy.actionsPerHour()));
		xpLabel.setText(ShortFormat.compact(accuracy.xpPerHour()));
	}

	private void onPinChanged()
	{
		RecipePin pin = (RecipePin) pinBox.getSelectedItem();
		configManager.setConfiguration(TickAssistConfig.GROUP, "pinnedRecipe", pin);
	}
}
