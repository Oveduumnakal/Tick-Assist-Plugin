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

import java.util.Locale;

/**
 * Compact number formatting for the stats readout: {@code 950}, {@code 1.2k}, {@code 62k},
 * {@code 1.1m}. A stateless utility that cannot be instantiated.
 */
public final class ShortFormat
{
	private ShortFormat()
	{
	}

	/**
	 * Formats a rate or count compactly with a lowercase k/m suffix.
	 *
	 * @param value the value to format
	 * @return the compact string
	 */
	public static String compact(double value)
	{
		double abs = Math.abs(value);
		if (abs >= 1_000_000)
			return trim(value / 1_000_000.0) + "m";

		if (abs >= 1_000)
			return trim(value / 1_000.0) + "k";

		return Long.toString(Math.round(value));
	}

	private static String trim(double value)
	{
		String s = String.format(Locale.US, "%.1f", value);
		if (s.endsWith(".0"))
			return s.substring(0, s.length() - 2);

		return s;
	}
}
