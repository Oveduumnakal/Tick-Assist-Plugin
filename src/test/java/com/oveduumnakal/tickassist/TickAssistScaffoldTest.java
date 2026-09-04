/*
 * Copyright (c) 2026, Oveduumnakal
 * All rights reserved.
 */
package com.oveduumnakal.tickassist;

import org.junit.Test;

import net.runelite.client.plugins.PluginDescriptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/** Scaffold smoke test: confirms the test harness runs and the plugin is wired with its descriptor. */
public class TickAssistScaffoldTest
{
	@Test
	public void pluginCarriesItsDescriptor()
	{
		PluginDescriptor descriptor = TickAssistPlugin.class.getAnnotation(PluginDescriptor.class);
		assertNotNull("TickAssistPlugin must carry a @PluginDescriptor", descriptor);
		assertEquals("Tick Assist", descriptor.name());
	}
}
