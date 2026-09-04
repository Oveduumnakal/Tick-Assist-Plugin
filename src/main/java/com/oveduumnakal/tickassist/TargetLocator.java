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

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;

/**
 * Finds the resource the player should aim at. The nearest-of-candidates arithmetic is a pure,
 * tested helper; the client wrapper applies it to the live NPC list.
 *
 * <p>Object-based resources (rocks, vines) join this once their object ids are captured in-game
 * (Step-0); for now only resource NPCs (fishing spots) are located.
 */
public final class TargetLocator
{
	private TargetLocator()
	{
	}

	/**
	 * Returns the index of the smallest distance, or empty when the list is empty. Ties keep the
	 * earliest index.
	 *
	 * @param distances the candidate distances
	 * @return the index of the nearest, or empty
	 */
	public static OptionalInt nearestIndex(List<Integer> distances)
	{
		int best = -1;
		int bestDistance = Integer.MAX_VALUE;
		for (int i = 0; i < distances.size(); i++)
		{
			int d = distances.get(i);
			if (d < bestDistance)
			{
				bestDistance = d;
				best = i;
			}
		}

		return best < 0 ? OptionalInt.empty() : OptionalInt.of(best);
	}

	/**
	 * Returns the nearest live NPC whose id is a resource for the active recipe.
	 *
	 * @param client      the game client
	 * @param resourceIds the recipe's resource NPC ids
	 * @return the nearest matching NPC, or empty
	 */
	public static Optional<NPC> nearestResource(Client client, Set<Integer> resourceIds)
	{
		Player local = client.getLocalPlayer();
		if (local == null || resourceIds.isEmpty())
			return Optional.empty();

		WorldPoint here = local.getWorldLocation();
		if (here == null)
			return Optional.empty();

		NPC best = null;
		int bestDistance = Integer.MAX_VALUE;
		for (NPC npc : client.getNpcs())
		{
			if (!resourceIds.contains(npc.getId()))
				continue;

			WorldPoint loc = npc.getWorldLocation();
			if (loc == null || loc.getPlane() != here.getPlane())
				continue;

			int d = loc.distanceTo(here);
			if (d < bestDistance)
			{
				bestDistance = d;
				best = npc;
			}
		}

		return Optional.ofNullable(best);
	}
}
