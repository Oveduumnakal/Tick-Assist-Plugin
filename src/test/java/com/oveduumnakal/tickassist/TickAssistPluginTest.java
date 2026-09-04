/*
 * Copyright (c) 2026, Oveduumnakal
 * All rights reserved.
 */
package com.oveduumnakal.tickassist;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

/** Development entry point that launches a RuneLite client with Tick Assist loaded (used by {@code ./gradlew run}). */
public class TickAssistPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(TickAssistPlugin.class);
		RuneLite.main(args);
	}
}
