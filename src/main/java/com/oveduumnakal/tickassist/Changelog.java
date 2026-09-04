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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Value;

/**
 * Parses the bundled {@code changelog.md} resource into an ordered list of releases (newest first).
 * Each release starts with a top-level {@code # <version> - <date>} heading; everything up to the
 * next such heading is that release's markdown body. The parser is offline and deterministic;
 * {@code ChangelogGuardTest} enforces that the newest entry matches {@code runelite-plugin.properties}.
 */
public final class Changelog
{
	/** Resource path of the bundled changelog, relative to the classpath root. */
	static final String RESOURCE = "/changelog.md";

	/**
	 * A release heading: {@code # 0.1 - September 3 2026}. The {@code (?!#)} keeps it to a single
	 * {@code #} so the body's {@code ##}/{@code ###} headings aren't release boundaries.
	 */
	private static final Pattern HEADING = Pattern.compile("^#(?!#)\\s+(\\S+)\\s*-\\s*(.*)$");

	private final List<Release> releases;

	private Changelog(List<Release> releases)
	{
		this.releases = releases;
	}

	/**
	 * Loads and parses the bundled changelog resource.
	 *
	 * @return the parsed changelog
	 */
	public static Changelog load()
	{
		try (InputStream in = Changelog.class.getResourceAsStream(RESOURCE))
		{
			if (in == null)
				return new Changelog(Collections.emptyList());

			return parse(read(in));
		}
		catch (IOException e)
		{
			return new Changelog(Collections.emptyList());
		}
	}

	/**
	 * Parses changelog markdown into releases in document order (expected newest first).
	 *
	 * @param markdown the changelog markdown
	 * @return the parsed changelog
	 */
	public static Changelog parse(String markdown)
	{
		List<Release> releases = new ArrayList<>();
		Release.ReleaseBuilder current = null;
		StringBuilder body = null;

		for (String line : markdown.split("\n", -1))
		{
			Matcher heading = HEADING.matcher(line);
			if (heading.matches())
			{
				if (current != null)
				{
					String text = body.toString().trim();
					releases.add(current.body(text).build());
				}

				current = Release.builder();
				current.version(heading.group(1));
				String date = heading.group(2).trim();
				current.date(date.isEmpty() ? null : date);
				body = new StringBuilder();
				continue;
			}

			if (body != null)
				body.append(line).append('\n');
		}

		if (current != null)
		{
			String text = body.toString().trim();
			releases.add(current.body(text).build());
		}

		return new Changelog(releases);
	}

	/**
	 * Returns the releases, newest first (document order).
	 *
	 * @return the releases
	 */
	public List<Release> releases()
	{
		return Collections.unmodifiableList(releases);
	}

	/**
	 * Returns the newest release's version, or {@code null} when the changelog is empty.
	 *
	 * @return the current version, or {@code null}
	 */
	public String currentVersion()
	{
		return releases.isEmpty() ? null : releases.get(0).getVersion();
	}

	/**
	 * Returns whether a release with exactly the given version exists.
	 *
	 * @param version the version to look for
	 * @return true when that version has an entry
	 */
	public boolean hasVersion(String version)
	{
		return releases.stream().anyMatch(r -> r.getVersion().equals(version));
	}

	private static String read(InputStream in) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)))
		{
			String line;
			while ((line = reader.readLine()) != null)
				sb.append(line).append('\n');
		}

		return sb.toString();
	}

	/**
	 * One release: its version, written-out date, and the raw markdown body beneath its heading.
	 */
	@Value
	@lombok.Builder
	public static class Release
	{
		String version;

		String date;

		@lombok.Builder.Default
		String body = "";
	}
}
