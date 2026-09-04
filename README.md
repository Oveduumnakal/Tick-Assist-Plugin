<h1 align="center">Tick Assist</h1>

<p align="center"><em>See the beat. Hit the tick.</em></p>

Tick Assist is a RuneLite plugin that helps you time skilling **tick-manipulation** methods (3-tick fishing, 3-tick mining, and more). It watches the resources around you and the items in your bag, works out which technique you're set up for, and shows you what to click and exactly when — a glowing item, a glowing resource, and a live countdown to the next action, plus honest accuracy feedback.

It is **visual only**. Tick Assist never clicks, moves, or interacts for you — you perform every action; the plugin just shows the timing. That keeps it within the RuneLite plugin-hub rules.

> **Status: in development (v0.1).** This is the initial scaffold. Detection, the timing overlay, and stats are being built phase by phase — see the build plan.

## How it will work

1. **It detects your setup.** Carry the tick items for a method and stand near a manipulable resource (a fishing spot, a rock, a herb table); Tick Assist recognises the technique.
2. **Armed → Active.** Before you start, a subtle "ready" hint appears. Once you're skilling, the full guidance kicks in.
3. **Follow the glow.** The tick item glows when it's due; then focus flips to the resource with a countdown ring and number; a successful gather snaps it back. Miss the window and it shows a restart cue.
4. **See how you're doing.** Success %, current streak, actions/hour and XP/hour update live in the side panel.

## Planned technique catalog

The techniques are data — each is a "recipe" the plugin matches from context, so new ones are easy to add.

- 3-tick barbarian fishing
- 3-tick mining
- 1-tick karambwan cooking
- 3-tick herblore
- 3-tick snake weed

Combat and prayer-flick timing are out of scope for v1.

## Building

Requires JDK 11.

```
./gradlew build      # compile, tests, style check, javadoc lint
./gradlew run        # launch a dev RuneLite client with the plugin loaded
```

The JavaDocs.md reference is generated and gated by a separate build:

```
./gradlew -p javadoc-tools checkJavaDocs     # fail if JavaDocs.md is stale
./gradlew -p javadoc-tools generateJavaDocs  # regenerate it
```

## License

BSD 2-Clause — see [LICENSE](LICENSE).
