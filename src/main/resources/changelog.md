<!--
Tick Assist changelog. Newest release first. Each release is a top-level heading
"# <version> - <written-out date>" followed by a Quick Overview, a Detailed
Breakdown (features grouped by area, each with the issues that make it up), and
Bug Fixes. Order features within a section by user impact. Bug Fixes lists only
bugs that shipped in a previous release; bugs introduced and fixed within the same
release cycle are omitted, since users never saw them.
-->

# 0.1 - September 3 2026

## Quick Overview

The first release of Tick Assist. It watches the resources around you and the items in your bag, works out which skilling tick-manipulation method you're set up for, and shows you the timing — the tick item to click, the resource to work, and a live countdown to the next action — plus how accurately you're keeping the rhythm. It never clicks for you; it only shows the beat.

## Detailed Breakdown

### Detection

Tick Assist recognises a setup from context: hold the tick items for a method near a manipulable resource and it arms a "ready" hint; start skilling and it switches to full guidance. Distinctive setups arm on sight, while common items wait until you're actually skilling so nothing lights up by mistake. You can pin a technique or fall back to a plain metronome from the side panel.

### Guidance

A ping-pong highlight follows the action: the tick item glows when it's due, then the nearest resource glows with a countdown to the next action, shown as a ring, a number, or both. A manual metronome covers anything not yet detected.

### Stats

A live panel and infobox show your success rate, current and best streak, and projected actions and XP per hour, with a warning when a consumable tick item runs low. An optional beep marks each action tick, and an optional notification fires if you fall off the cadence.

### Techniques

Ships recognising 3-tick fishing, 3-tick mining, 1-tick karambwan cooking, 3-tick herblore, and 3-tick snake weed.
