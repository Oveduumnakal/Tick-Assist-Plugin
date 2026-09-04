# Stockpile — JavaDoc Reference

<!-- GENERATED FILE — DO NOT EDIT BY HAND.
     Run `./gradlew generateJavaDocs` and commit the result. -->

## Contents

- [com.oveduumnakal.tickassist.Confidence](#comoveduumnakaltickassistconfidence)
- [com.oveduumnakal.tickassist.CountdownStyle](#comoveduumnakaltickassistcountdownstyle)
- [com.oveduumnakal.tickassist.GatherSignal](#comoveduumnakaltickassistgathersignal)
- [com.oveduumnakal.tickassist.GatherSignal.Kind](#comoveduumnakaltickassistgathersignalkind)
- [com.oveduumnakal.tickassist.HighlightFocus](#comoveduumnakaltickassisthighlightfocus)
- [com.oveduumnakal.tickassist.MetronomeStyle](#comoveduumnakaltickassistmetronomestyle)
- [com.oveduumnakal.tickassist.RecipeCatalog](#comoveduumnakaltickassistrecipecatalog)
- [com.oveduumnakal.tickassist.StepKind](#comoveduumnakaltickassiststepkind)
- [com.oveduumnakal.tickassist.TickAssistConfig](#comoveduumnakaltickassisttickassistconfig)
- [com.oveduumnakal.tickassist.TickAssistPlugin](#comoveduumnakaltickassisttickassistplugin)
- [com.oveduumnakal.tickassist.TickClock](#comoveduumnakaltickassisttickclock)
- [com.oveduumnakal.tickassist.TickMetronomeOverlay](#comoveduumnakaltickassisttickmetronomeoverlay)
- [com.oveduumnakal.tickassist.TickRecipe](#comoveduumnakaltickassisttickrecipe)
- [com.oveduumnakal.tickassist.TickStep](#comoveduumnakaltickassisttickstep)

---

## com.oveduumnakal.tickassist.Confidence

_enum_

`public enum Confidence`

How distinctive a recipe's tick-item setup is, which decides how eagerly detection may arm it.

### Enum Constant Summary

| Enum Constant | Description |
|---|---|
| `GENERIC` | Common items with many non-tick uses (e.g. |
| `HIGH` | A distinctive setup (e.g. |

### Enum Constant Detail

#### GENERIC

`GENERIC`

Common items with many non-tick uses (e.g. a bare knife) — only activate once skilling is confirmed.

#### HIGH

`HIGH`

A distinctive setup (e.g. swamp tar + herb, celastrus bark) — safe to arm before skilling.

---

## com.oveduumnakal.tickassist.CountdownStyle

_enum_

`public enum CountdownStyle`

How the overlays render the tick countdown on the current target. The `displayName` is
the label shown in the config dropdown; `#RING_NUMBER` is the default.

### Enum Constant Summary

| Enum Constant | Description |
|---|---|
| `NONE` | No countdown drawn. |
| `NUMBER` | Just the remaining tick count. |
| `PIE` | A filled pie wedge that sweeps down over the window. |
| `RING` | A ring that empties over the window. |
| `RING_NUMBER` | A ring with the remaining tick count inside it. |

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private final String` | `displayName` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `CountdownStyle(String displayName)` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public String` | `toString()` | Returns the display label shown in the config dropdown. |

### Enum Constant Detail

#### NONE

`NONE`

No countdown drawn.

#### NUMBER

`NUMBER`

Just the remaining tick count.

#### PIE

`PIE`

A filled pie wedge that sweeps down over the window.

#### RING

`RING`

A ring that empties over the window.

#### RING_NUMBER

`RING_NUMBER`

A ring with the remaining tick count inside it.

### Field Detail

#### displayName

`private final String displayName`

### Constructor Detail

#### CountdownStyle

`CountdownStyle(String displayName)`

### Method Detail

#### toString

`public String toString()`

Returns the display label shown in the config dropdown.

- **Returns:** the display label

---

## com.oveduumnakal.tickassist.GatherSignal

_class_

`public final class GatherSignal`

The rule that marks a successful gather for a recipe. XP-granting methods watch a skill's XP
for an increase; item-only methods (e.g. snake weed, which grants no XP) watch a specific
item's inventory count instead.

### Nested Type Summary

| Type | Description |
|---|---|
| _enum_ [`Kind`](#comoveduumnakaltickassistgathersignalkind) | Which kind of change marks a successful gather. |

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private final int` | `itemId` |  |
| `private final Kind` | `kind` |  |
| `private final Skill` | `skill` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `GatherSignal(Kind kind, Skill skill, int itemId)` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public static GatherSignal` | `itemCount(int itemId)` | Creates an item-count signal for the given item id. |
| `public int` | `itemId()` | Returns the item id for an `Kind#ITEM_COUNT` signal. |
| `public Kind` | `kind()` | Returns the kind of signal. |
| `public Skill` | `skill()` | Returns the skill for an `Kind#XP_DELTA` signal. |
| `public static GatherSignal` | `xp(Skill skill)` | Creates an XP-delta signal for the given skill. |

### Field Detail

#### itemId

`private final int itemId`

#### kind

`private final Kind kind`

#### skill

`private final Skill skill`

### Constructor Detail

#### GatherSignal

`private GatherSignal(Kind kind, Skill skill, int itemId)`

### Method Detail

#### itemCount

`public static GatherSignal itemCount(int itemId)`

Creates an item-count signal for the given item id.

- **Parameter** `itemId` — the item whose count increase marks a gather
- **Returns:** the signal

#### itemId

`public int itemId()`

Returns the item id for an `Kind#ITEM_COUNT` signal.

- **Returns:** the item id, or `-1` for an XP signal

#### kind

`public Kind kind()`

Returns the kind of signal.

- **Returns:** the signal kind

#### skill

`public Skill skill()`

Returns the skill for an `Kind#XP_DELTA` signal.

- **Returns:** the skill, or `null` for an item-count signal

#### xp

`public static GatherSignal xp(Skill skill)`

Creates an XP-delta signal for the given skill.

- **Parameter** `skill` — the skill whose XP increase marks a gather
- **Returns:** the signal

---

## com.oveduumnakal.tickassist.GatherSignal.Kind

_enum_

`public enum Kind`

Which kind of change marks a successful gather.

### Enum Constant Summary

| Enum Constant | Description |
|---|---|
| `ITEM_COUNT` | An increase in a specific item's inventory count. |
| `XP_DELTA` | An increase in a skill's XP. |

### Enum Constant Detail

#### ITEM_COUNT

`ITEM_COUNT`

An increase in a specific item's inventory count.

#### XP_DELTA

`XP_DELTA`

An increase in a skill's XP.

---

## com.oveduumnakal.tickassist.HighlightFocus

_enum_

`public enum HighlightFocus`

Where the guidance should point the player's attention for the current `TickStep`.

### Enum Constant Summary

| Enum Constant | Description |
|---|---|
| `GROUND` | Highlight the resource on the ground (spot, rock, tree, vine). |
| `INVENTORY` | Highlight the tick item(s) in the inventory. |
| `NONE` | Highlight nothing this step. |

### Enum Constant Detail

#### GROUND

`GROUND`

Highlight the resource on the ground (spot, rock, tree, vine).

#### INVENTORY

`INVENTORY`

Highlight the tick item(s) in the inventory.

#### NONE

`NONE`

Highlight nothing this step.

---

## com.oveduumnakal.tickassist.MetronomeStyle

_enum_

`public enum MetronomeStyle`

How the beat is shown. `#TARGET_FOLLOW` is the ping-pong inventory/ground highlight
(lands in a later phase); the others draw an on-screen beat. The `displayName` is the
config-dropdown label.

### Enum Constant Summary

| Enum Constant | Description |
|---|---|
| `BAR` | A single sweeping bar. |
| `INFOBOX_ONLY` | No on-screen beat; rely on the infobox and stats only. |
| `PIPS` | A row of pips, one per tick in the cycle, with the current tick lit. |
| `PULSE` | A pulse that flashes on the action tick. |
| `TARGET_FOLLOW` | The ping-pong inventory/ground highlight that follows the due action. |

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private final String` | `displayName` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `MetronomeStyle(String displayName)` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public String` | `toString()` | Returns the config-dropdown label. |

### Enum Constant Detail

#### BAR

`BAR`

A single sweeping bar.

#### INFOBOX_ONLY

`INFOBOX_ONLY`

No on-screen beat; rely on the infobox and stats only.

#### PIPS

`PIPS`

A row of pips, one per tick in the cycle, with the current tick lit.

#### PULSE

`PULSE`

A pulse that flashes on the action tick.

#### TARGET_FOLLOW

`TARGET_FOLLOW`

The ping-pong inventory/ground highlight that follows the due action.

### Field Detail

#### displayName

`private final String displayName`

### Constructor Detail

#### MetronomeStyle

`MetronomeStyle(String displayName)`

### Method Detail

#### toString

`public String toString()`

Returns the config-dropdown label.

- **Returns:** the display label

---

## com.oveduumnakal.tickassist.RecipeCatalog

_class_

`public final class RecipeCatalog`

The seed set of tick-manipulation `TickRecipe`s, plus the ad-hoc custom metronome.

<p>The catalog is data: adding a method is one entry here (and its ids in the id table added in
Phase 3), with no change to the tick clock or overlays. Phase-2 recipes carry the timing and
scoring shape only; the resource and tick-item matchers are filled in Phase 3.

### Constructor Summary

| Constructor | Description |
|---|---|
| `RecipeCatalog()` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public static TickRecipe` | `customMetronome(int cadenceTicks)` | Builds an ad-hoc plain metronome of the given cadence, for the manual "just give me a beat" pin. |
| `public static List<TickRecipe>` | `seedRecipes()` | Builds the seed recipes shipped with the plugin. |

### Constructor Detail

#### RecipeCatalog

`private RecipeCatalog()`

### Method Detail

#### customMetronome

`public static TickRecipe customMetronome(int cadenceTicks)`

Builds an ad-hoc plain metronome of the given cadence, for the manual "just give me a beat"
pin. It carries no resource, tick item, or gather signal.

- **Parameter** `cadenceTicks` — the beat length in ticks (at least 1)
- **Returns:** the custom-metronome recipe

#### seedRecipes

`public static List<TickRecipe> seedRecipes()`

Builds the seed recipes shipped with the plugin.

- **Returns:** an unmodifiable list of the built-in recipes

---

## com.oveduumnakal.tickassist.StepKind

_enum_

`public enum StepKind`

The kind of action a `TickStep` represents within a tick-manipulation cycle.

### Enum Constant Summary

| Enum Constant | Description |
|---|---|
| `GATHER` | Perform (or restart) the gathering action on the resource. |
| `MOVE` | Move to the next node; movement itself is the 1-tick delay. |
| `TICK_ITEM` | Use the 1-tick "tick item" in the inventory that resets the trailing timer. |
| `WAIT` | Do nothing this tick; the cycle simply waits. |

### Enum Constant Detail

#### GATHER

`GATHER`

Perform (or restart) the gathering action on the resource.

#### MOVE

`MOVE`

Move to the next node; movement itself is the 1-tick delay.

#### TICK_ITEM

`TICK_ITEM`

Use the 1-tick "tick item" in the inventory that resets the trailing timer.

#### WAIT

`WAIT`

Do nothing this tick; the cycle simply waits.

---

## com.oveduumnakal.tickassist.TickAssistConfig

_interface_

`public interface TickAssistConfig`

RuneLite configuration for Tick Assist.

<p>Grows a subsystem at a time. Phase 2 adds the on-screen beat: a metronome style and, until
detection lands, a manual cadence to drive it. The full surface (countdown style, confidence,
accuracy stats, audio cue, tick-item warnings) arrives in later phases.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `String` | `GROUP` | The config group key, shared with `ConfigChanged` handling. |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `default boolean` | `autoDetect()` | Whether the plugin auto-detects tick-manipulation setups from nearby resources and the items the player is carrying. |
| `default int` | `customCadence()` | The cadence, in ticks, of the manual beat used until context detection selects a technique. |
| `default MetronomeStyle` | `metronomeStyle()` | How the beat is displayed. |

### Field Detail

#### GROUP

`String GROUP`

The config group key, shared with `ConfigChanged` handling.

### Method Detail

#### autoDetect

`default boolean autoDetect()`

Whether the plugin auto-detects tick-manipulation setups from nearby resources and the
items the player is carrying.

- **Returns:** true when context detection is enabled

#### customCadence

`default int customCadence()`

The cadence, in ticks, of the manual beat used until context detection selects a technique.

- **Returns:** the manual cadence in ticks

#### metronomeStyle

`default MetronomeStyle metronomeStyle()`

How the beat is displayed. Phase 2 renders `MetronomeStyle#PIPS`; the default becomes
`MetronomeStyle#TARGET_FOLLOW` once that highlight lands.

- **Returns:** the chosen metronome style

---

## com.oveduumnakal.tickassist.TickAssistPlugin

_class_

`public class TickAssistPlugin`

Tick Assist — detects skilling tick-manipulation setups and visualises their timing.

<p>The plugin watches the resources around the player and the items they carry; when a known
tick-manipulation setup is present it shows which item to click and when, with a live countdown
and accuracy feedback. It never clicks anything — it only visualises the beat.

<p>Phase 2 wires the tick clock to the game and draws a manual metronome. Context detection, the
ping-pong highlight, and accuracy stats land in later phases; until then the beat runs at the
manual cadence from the config.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private TickClock` | `clock` |  |
| `private TickAssistConfig` | `config` |  |
| `private TickMetronomeOverlay` | `metronomeOverlay` |  |
| `private OverlayManager` | `overlayManager` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `TickClock` | `clock()` | Returns the clock currently driving the beat, or `null` when the plugin is stopped. |
| `public void` | `onConfigChanged(ConfigChanged event)` | Rebuilds the clock when the manual cadence changes. |
| `public void` | `onGameTick(GameTick event)` | Advances the beat by one game tick. |
| `TickAssistConfig` | `provideConfig(ConfigManager configManager)` | Supplies the plugin's configuration proxy to RuneLite's injector. |
| `private void` | `rebuildClock()` |  |
| `protected void` | `shutDown()` | Stops the plugin: removes the overlay and drops the clock. |
| `protected void` | `startUp()` | Starts the plugin: builds the manual-cadence clock and registers the metronome overlay. |

### Field Detail

#### clock

`private TickClock clock`

#### config

`private TickAssistConfig config`

#### metronomeOverlay

`private TickMetronomeOverlay metronomeOverlay`

#### overlayManager

`private OverlayManager overlayManager`

### Method Detail

#### clock

`TickClock clock()`

Returns the clock currently driving the beat, or `null` when the plugin is stopped.

- **Returns:** the tick clock, or `null`

#### onConfigChanged

`public void onConfigChanged(ConfigChanged event)`

Rebuilds the clock when the manual cadence changes.

- **Parameter** `event` — the config-changed event

#### onGameTick

`public void onGameTick(GameTick event)`

Advances the beat by one game tick.

- **Parameter** `event` — the game-tick event

#### provideConfig

`TickAssistConfig provideConfig(ConfigManager configManager)`

Supplies the plugin's configuration proxy to RuneLite's injector.

- **Parameter** `configManager` — the client configuration manager
- **Returns:** the Tick Assist configuration

#### rebuildClock

`private void rebuildClock()`

#### shutDown

`protected void shutDown()`

Stops the plugin: removes the overlay and drops the clock.

#### startUp

`protected void startUp()`

Starts the plugin: builds the manual-cadence clock and registers the metronome overlay.

---

## com.oveduumnakal.tickassist.TickClock

_class_

`public final class TickClock`

The pure timing engine: walks a recipe's `TickStep` list one game tick at a time and
reports where in the cycle it is.

<p>It holds no client state and does no drawing, so it is fully unit-testable. The plugin advances
it once per `GameTick` and re-anchors it with `#resyncTo` when a gather event (or, as
a fallback, a gather animation) pins the true phase.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private final int` | `cycleLength` |  |
| `private int` | `stepIndex` |  |
| `private final List<TickStep>` | `steps` |  |
| `private int` | `tickInStep` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `TickClock(List<TickStep> steps)` | Creates a clock over the given cycle steps, positioned at the start of the first step. |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public TickStep` | `currentStep()` | Returns the step the clock is currently in. |
| `public int` | `cycleLength()` | Returns the total length of one cycle in ticks. |
| `public int` | `phase()` | Returns the absolute tick position within the cycle, from 0 to `cycleLength - 1`. |
| `public void` | `resyncTo(int stepIndex, int tickInStep)` | Re-anchors the clock to an exact position, used when an external event pins the true phase. |
| `public int` | `stepIndex()` | Returns the index of the current step. |
| `public void` | `tick()` | Advances the clock by one game tick, rolling over to the next step (and wrapping the cycle) when the current step's duration is spent. |
| `public int` | `tickInStep()` | Returns the tick offset within the current step. |
| `public int` | `ticksUntilNext(StepKind kind)` | Returns the number of ticks until the next step of the given kind begins. |

### Field Detail

#### cycleLength

`private final int cycleLength`

#### stepIndex

`private int stepIndex`

#### steps

`private final List<TickStep> steps`

#### tickInStep

`private int tickInStep`

### Constructor Detail

#### TickClock

`public TickClock(List<TickStep> steps)`

Creates a clock over the given cycle steps, positioned at the start of the first step.

- **Parameter** `steps` — the ordered cycle steps (at least one)

### Method Detail

#### currentStep

`public TickStep currentStep()`

Returns the step the clock is currently in.

- **Returns:** the current step

#### cycleLength

`public int cycleLength()`

Returns the total length of one cycle in ticks.

- **Returns:** the cycle length

#### phase

`public int phase()`

Returns the absolute tick position within the cycle, from 0 to `cycleLength - 1`.

- **Returns:** the current phase

#### resyncTo

`public void resyncTo(int stepIndex, int tickInStep)`

Re-anchors the clock to an exact position, used when an external event pins the true phase.

- **Parameter** `stepIndex` — the step to jump to
- **Parameter** `tickInStep` — the tick offset within that step

#### stepIndex

`public int stepIndex()`

Returns the index of the current step.

- **Returns:** the current step index

#### tick

`public void tick()`

Advances the clock by one game tick, rolling over to the next step (and wrapping the cycle)
when the current step's duration is spent.

#### tickInStep

`public int tickInStep()`

Returns the tick offset within the current step.

- **Returns:** the tick offset within the step

#### ticksUntilNext

`public int ticksUntilNext(StepKind kind)`

Returns the number of ticks until the next step of the given kind begins. Returns 0 when the
current step is that kind and is just starting, or -1 when no step of that kind exists.

- **Parameter** `kind` — the step kind to look ahead for
- **Returns:** ticks until that kind's next start, 0 if it starts now, or -1 if absent

---

## com.oveduumnakal.tickassist.TickMetronomeOverlay

_class_

`public class TickMetronomeOverlay`

Draws the on-screen beat as a row of pips — one per tick in the cycle — with the current tick
lit and the action tick (phase 0) coloured. It reads the plugin's `TickClock`, which the
plugin advances once per game tick, so the row steps in time with the game.

<p>Phase-2 renderer for `MetronomeStyle#PIPS`; the other styles and the target-follow
highlight arrive in later phases.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private static final Color` | `BACKGROUND` |  |
| `private static final Color` | `BEAT` |  |
| `private static final int` | `GAP` |  |
| `private static final Color` | `NOW` |  |
| `private static final Color` | `OFF_BEAT` |  |
| `private static final int` | `PAD` |  |
| `private static final int` | `PIP` |  |
| `private static final Color` | `TITLE` |  |
| `private static final int` | `TITLE_H` |  |
| `private final TickAssistConfig` | `config` |  |
| `private final TickAssistPlugin` | `plugin` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `TickMetronomeOverlay(TickAssistPlugin plugin, TickAssistConfig config)` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public Dimension` | `render(Graphics2D graphics)` | Renders the pip row for the current clock, or nothing when there is no clock or the pip style is not selected. |

### Field Detail

#### BACKGROUND

`private static final Color BACKGROUND`

#### BEAT

`private static final Color BEAT`

#### GAP

`private static final int GAP`

#### NOW

`private static final Color NOW`

#### OFF_BEAT

`private static final Color OFF_BEAT`

#### PAD

`private static final int PAD`

#### PIP

`private static final int PIP`

#### TITLE

`private static final Color TITLE`

#### TITLE_H

`private static final int TITLE_H`

#### config

`private final TickAssistConfig config`

#### plugin

`private final TickAssistPlugin plugin`

### Constructor Detail

#### TickMetronomeOverlay

`TickMetronomeOverlay(TickAssistPlugin plugin, TickAssistConfig config)`

### Method Detail

#### render

`public Dimension render(Graphics2D graphics)`

Renders the pip row for the current clock, or nothing when there is no clock or the pip
style is not selected.

- **Parameter** `graphics` — the overlay graphics context
- **Returns:** the rendered size, or `null` when nothing is drawn

---

## com.oveduumnakal.tickassist.TickRecipe

_class_

`public final class TickRecipe`

One entry in the detection catalog: a named tick-manipulation method described as an ordered
list of `TickStep`s plus how to recognise a successful gather and how eagerly to arm it.

<p>Phase-2 scaffold holds the timing/scoring shape; the resource and tick-item id matchers used
by detection are added in Phase 3.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private final String` | `blurb` |  |
| `private final Confidence` | `confidence` |  |
| `private final String` | `displayName` |  |
| `private final String` | `id` |  |
| `private final GatherSignal` | `signal` |  |
| `private final List<TickStep>` | `steps` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `TickRecipe(String id, String displayName, List<TickStep> steps, Confidence confidence, GatherSignal signal, String blurb)` | Creates a recipe. |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public String` | `blurb()` | Returns the short "how it works" explainer. |
| `public int` | `cadenceTicks()` | Returns the cadence in ticks: the sum of every step's duration (the length of one cycle). |
| `public Confidence` | `confidence()` | Returns the recipe's confidence tier. |
| `public String` | `displayName()` | Returns the panel display name. |
| `public String` | `id()` | Returns the stable identifier. |
| `public GatherSignal` | `signal()` | Returns the successful-gather signal. |
| `public List<TickStep>` | `steps()` | Returns the ordered, unmodifiable list of cycle steps. |

### Field Detail

#### blurb

`private final String blurb`

#### confidence

`private final Confidence confidence`

#### displayName

`private final String displayName`

#### id

`private final String id`

#### signal

`private final GatherSignal signal`

#### steps

`private final List<TickStep> steps`

### Constructor Detail

#### TickRecipe

`public TickRecipe(String id, String displayName, List<TickStep> steps, Confidence confidence, GatherSignal signal, String blurb)`

Creates a recipe.

- **Parameter** `id` — a stable lowercase identifier ("three_tick_fishing")
- **Parameter** `displayName` — the label shown in the panel ("3-tick fishing")
- **Parameter** `steps` — the ordered cycle steps (at least one)
- **Parameter** `confidence` — how distinctive the setup is
- **Parameter** `signal` — the successful-gather signal
- **Parameter** `blurb` — a short "how it works" explainer

### Method Detail

#### blurb

`public String blurb()`

Returns the short "how it works" explainer.

- **Returns:** the blurb

#### cadenceTicks

`public int cadenceTicks()`

Returns the cadence in ticks: the sum of every step's duration (the length of one cycle).

- **Returns:** the cadence in ticks

#### confidence

`public Confidence confidence()`

Returns the recipe's confidence tier.

- **Returns:** the confidence

#### displayName

`public String displayName()`

Returns the panel display name.

- **Returns:** the display name

#### id

`public String id()`

Returns the stable identifier.

- **Returns:** the recipe id

#### signal

`public GatherSignal signal()`

Returns the successful-gather signal.

- **Returns:** the gather signal

#### steps

`public List<TickStep> steps()`

Returns the ordered, unmodifiable list of cycle steps.

- **Returns:** the steps

---

## com.oveduumnakal.tickassist.TickStep

_class_

`public final class TickStep`

One beat of a tick-manipulation cycle: what to do, for how many ticks, and where to point the
player's attention. Steps are immutable value objects assembled into a `TickRecipe`.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private final int` | `durationTicks` |  |
| `private final HighlightFocus` | `focus` |  |
| `private final StepKind` | `kind` |  |
| `private final String` | `label` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `TickStep(StepKind kind, int durationTicks, String label, HighlightFocus focus)` | Creates a step. |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public int` | `durationTicks()` | Returns how many ticks the step lasts. |
| `public HighlightFocus` | `focus()` | Returns where to point the highlight. |
| `public StepKind` | `kind()` | Returns the kind of action. |
| `public String` | `label()` | Returns the short human label. |

### Field Detail

#### durationTicks

`private final int durationTicks`

#### focus

`private final HighlightFocus focus`

#### kind

`private final StepKind kind`

#### label

`private final String label`

### Constructor Detail

#### TickStep

`public TickStep(StepKind kind, int durationTicks, String label, HighlightFocus focus)`

Creates a step.

- **Parameter** `kind` — what the player does this step
- **Parameter** `durationTicks` — how many ticks the step lasts (at least 1)
- **Parameter** `label` — a short human label shown in the guidance ("Click item", "Fish")
- **Parameter** `focus` — where to point the highlight for this step

### Method Detail

#### durationTicks

`public int durationTicks()`

Returns how many ticks the step lasts.

- **Returns:** the duration in ticks

#### focus

`public HighlightFocus focus()`

Returns where to point the highlight.

- **Returns:** the highlight focus

#### kind

`public StepKind kind()`

Returns the kind of action.

- **Returns:** the step kind

#### label

`public String label()`

Returns the short human label.

- **Returns:** the label
