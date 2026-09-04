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
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;

/**
 * Reads the ids of manipulable resources near the player, used by {@link RecipeMatcher} to confirm
 * a setup is usable here.
 *
 * <p>Phase-3 scan covers nearby NPCs (fishing spots). Rock and vine game-object scanning is added
 * once those object ids are captured in-game (Step-0); until then the object resource sets are
 * empty and object-based recipes arm on their held tick items alone.
 */
public class ResourceScanner
{
	private final Client client;

	@Inject
	ResourceScanner(Client client)
	{
		this.client = client;
	}

	/**
	 * Returns the ids of resource NPCs within the given tile radius of the player.
	 *
	 * @param radius the search radius in tiles
	 * @return the nearby resource ids, or an empty set when the player is not loaded
	 */
	public Set<Integer> nearbyResourceIds(int radius)
	{
		Player local = client.getLocalPlayer();
		if (local == null)
			return Collections.emptySet();

		WorldPoint here = local.getWorldLocation();
		if (here == null)
			return Collections.emptySet();

		Set<Integer> ids = new HashSet<>();
		for (NPC npc : client.getTopLevelWorldView().npcs())
		{
			WorldPoint loc = npc.getWorldLocation();
			if (loc != null && loc.getPlane() == here.getPlane() && loc.distanceTo(here) <= radius)
				ids.add(npc.getId());
		}

		return ids;
	}
}
