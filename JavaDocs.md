# Stockpile — JavaDoc Reference

<!-- GENERATED FILE — DO NOT EDIT BY HAND.
     Run `./gradlew generateJavaDocs` and commit the result. -->

## Contents

- [com.oveduumnakal.tickassist.AccuracyTracker](#comoveduumnakaltickassistaccuracytracker)
- [com.oveduumnakal.tickassist.ActivityDetector](#comoveduumnakaltickassistactivitydetector)
- [com.oveduumnakal.tickassist.ActivityState](#comoveduumnakaltickassistactivitystate)
- [com.oveduumnakal.tickassist.Confidence](#comoveduumnakaltickassistconfidence)
- [com.oveduumnakal.tickassist.CountdownStyle](#comoveduumnakaltickassistcountdownstyle)
- [com.oveduumnakal.tickassist.DetectionState](#comoveduumnakaltickassistdetectionstate)
- [com.oveduumnakal.tickassist.GatherSignal](#comoveduumnakaltickassistgathersignal)
- [com.oveduumnakal.tickassist.GatherSignal.Kind](#comoveduumnakaltickassistgathersignalkind)
- [com.oveduumnakal.tickassist.GuidanceState](#comoveduumnakaltickassistguidancestate)
- [com.oveduumnakal.tickassist.HighlightFocus](#comoveduumnakaltickassisthighlightfocus)
- [com.oveduumnakal.tickassist.InventoryHighlightOverlay](#comoveduumnakaltickassistinventoryhighlightoverlay)
- [com.oveduumnakal.tickassist.InventoryScanner](#comoveduumnakaltickassistinventoryscanner)
- [com.oveduumnakal.tickassist.MetronomeStyle](#comoveduumnakaltickassistmetronomestyle)
- [com.oveduumnakal.tickassist.RecipeCatalog](#comoveduumnakaltickassistrecipecatalog)
- [com.oveduumnakal.tickassist.RecipeMatch](#comoveduumnakaltickassistrecipematch)
- [com.oveduumnakal.tickassist.RecipeMatcher](#comoveduumnakaltickassistrecipematcher)
- [com.oveduumnakal.tickassist.ResourceScanner](#comoveduumnakaltickassistresourcescanner)
- [com.oveduumnakal.tickassist.ShortFormat](#comoveduumnakaltickassistshortformat)
- [com.oveduumnakal.tickassist.StepKind](#comoveduumnakaltickassiststepkind)
- [com.oveduumnakal.tickassist.TargetHighlightOverlay](#comoveduumnakaltickassisttargethighlightoverlay)
- [com.oveduumnakal.tickassist.TargetLocator](#comoveduumnakaltickassisttargetlocator)
- [com.oveduumnakal.tickassist.TickAssistConfig](#comoveduumnakaltickassisttickassistconfig)
- [com.oveduumnakal.tickassist.TickAssistIds](#comoveduumnakaltickassisttickassistids)
- [com.oveduumnakal.tickassist.TickAssistPlugin](#comoveduumnakaltickassisttickassistplugin)
- [com.oveduumnakal.tickassist.TickClock](#comoveduumnakaltickassisttickclock)
- [com.oveduumnakal.tickassist.TickItemMonitor](#comoveduumnakaltickassisttickitemmonitor)
- [com.oveduumnakal.tickassist.TickMetronomeOverlay](#comoveduumnakaltickassisttickmetronomeoverlay)
- [com.oveduumnakal.tickassist.TickRecipe](#comoveduumnakaltickassisttickrecipe)
- [com.oveduumnakal.tickassist.TickStatsInfoBox](#comoveduumnakaltickassisttickstatsinfobox)
- [com.oveduumnakal.tickassist.TickStep](#comoveduumnakaltickassisttickstep)

---

## com.oveduumnakal.tickassist.AccuracyTracker

_class_

`public final class AccuracyTracker`

Pure accuracy scorer. It compares the tick gap between successive gathers against the target
cadence: an exact match feeds an honest success rate and streak, while a within-one-tick match
feeds a more forgiving streak (so double-roll skills and near misses still reward progress).
It also accumulates gathers, ticks, and XP to project actions/hour and XP/hour.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private static final double` | `TICKS_PER_HOUR` |  |
| `private int` | `attempts` |  |
| `private int` | `bestForgivingStreak` |  |
| `private int` | `bestStreak` |  |
| `private final int` | `cadence` |  |
| `private int` | `currentStreak` |  |
| `private int` | `forgivingStreak` |  |
| `private int` | `hits` |  |
| `private int` | `lastGatherTick` |  |
| `private long` | `totalGathers` |  |
| `private long` | `totalTicks` |  |
| `private long` | `totalXp` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `AccuracyTracker(int cadence)` | Creates a tracker for a target cadence. |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public double` | `actionsPerHour()` | Returns the projected gathers per hour from the elapsed ticks. |
| `public int` | `bestForgivingStreak()` | Returns the best within-one-tick streak so far. |
| `public int` | `bestStreak()` | Returns the best exact-cadence streak so far. |
| `public int` | `currentStreak()` | Returns the current exact-cadence streak. |
| `public int` | `forgivingStreak()` | Returns the current within-one-tick (forgiving) streak. |
| `public void` | `onGather(int gameTick)` | Records a successful gather at the given game tick. |
| `public void` | `onTick()` | Records one elapsed game tick, used to project the hourly rates. |
| `public void` | `onXp(int delta)` | Adds an XP gain, used to project XP/hour. |
| `public void` | `reset()` | Clears all counters. |
| `public double` | `successRate()` | Returns the honest success rate (exact-cadence hits over attempts), 0 to 1. |
| `public double` | `xpPerHour()` | Returns the projected XP per hour from the elapsed ticks. |

### Field Detail

#### TICKS_PER_HOUR

`private static final double TICKS_PER_HOUR`

#### attempts

`private int attempts`

#### bestForgivingStreak

`private int bestForgivingStreak`

#### bestStreak

`private int bestStreak`

#### cadence

`private final int cadence`

#### currentStreak

`private int currentStreak`

#### forgivingStreak

`private int forgivingStreak`

#### hits

`private int hits`

#### lastGatherTick

`private int lastGatherTick`

#### totalGathers

`private long totalGathers`

#### totalTicks

`private long totalTicks`

#### totalXp

`private long totalXp`

### Constructor Detail

#### AccuracyTracker

`public AccuracyTracker(int cadence)`

Creates a tracker for a target cadence.

- **Parameter** `cadence` — the target tick gap between gathers

### Method Detail

#### actionsPerHour

`public double actionsPerHour()`

Returns the projected gathers per hour from the elapsed ticks.

- **Returns:** actions per hour

#### bestForgivingStreak

`public int bestForgivingStreak()`

Returns the best within-one-tick streak so far.

- **Returns:** the best forgiving streak

#### bestStreak

`public int bestStreak()`

Returns the best exact-cadence streak so far.

- **Returns:** the best streak

#### currentStreak

`public int currentStreak()`

Returns the current exact-cadence streak.

- **Returns:** the current streak

#### forgivingStreak

`public int forgivingStreak()`

Returns the current within-one-tick (forgiving) streak.

- **Returns:** the forgiving streak

#### onGather

`public void onGather(int gameTick)`

Records a successful gather at the given game tick.

- **Parameter** `gameTick` — a monotonically increasing tick counter

#### onTick

`public void onTick()`

Records one elapsed game tick, used to project the hourly rates.

#### onXp

`public void onXp(int delta)`

Adds an XP gain, used to project XP/hour.

- **Parameter** `delta` — the XP gained

#### reset

`public void reset()`

Clears all counters.

#### successRate

`public double successRate()`

Returns the honest success rate (exact-cadence hits over attempts), 0 to 1.

- **Returns:** the success rate

#### xpPerHour

`public double xpPerHour()`

Returns the projected XP per hour from the elapsed ticks.

- **Returns:** XP per hour

---

## com.oveduumnakal.tickassist.ActivityDetector

_class_

`public final class ActivityDetector`

Pure state machine that classifies the player's activity from their animation each tick.

<p>While a gather animation plays the state is `ActivityState#RUNNING`; when it stops the
state holds at `ActivityState#STALLED` for a grace window (so the beat freezes rather than
drifting) before falling to `ActivityState#IDLE`. `#risingEdge()` is true on the tick
the gather starts, which the plugin uses as a fallback clock anchor.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private boolean` | `risingEdge` |  |
| `private final int` | `stallTicks` |  |
| `private int` | `stalledTicks` |  |
| `private ActivityState` | `state` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `ActivityDetector(int stallTicks)` | Creates a detector. |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public boolean` | `risingEdge()` | Whether the most recent `#update` was the tick a gather started. |
| `public ActivityState` | `state()` | Returns the current activity state. |
| `public ActivityState` | `update(int animationId, Set<Integer> gatherAnims)` | Advances the detector by one tick with the player's current animation. |

### Field Detail

#### risingEdge

`private boolean risingEdge`

#### stallTicks

`private final int stallTicks`

#### stalledTicks

`private int stalledTicks`

#### state

`private ActivityState state`

### Constructor Detail

#### ActivityDetector

`public ActivityDetector(int stallTicks)`

Creates a detector.

- **Parameter** `stallTicks` — how many ticks to hold `ActivityState#STALLED` before going idle

### Method Detail

#### risingEdge

`public boolean risingEdge()`

Whether the most recent `#update` was the tick a gather started.

- **Returns:** true on the rising edge of a gather

#### state

`public ActivityState state()`

Returns the current activity state.

- **Returns:** the current state

#### update

`public ActivityState update(int animationId, Set<Integer> gatherAnims)`

Advances the detector by one tick with the player's current animation.

- **Parameter** `animationId` — the player's animation id this tick (`-1` for none)
- **Parameter** `gatherAnims` — the animation ids that count as gathering for the active recipe
- **Returns:** the resulting activity state

---

## com.oveduumnakal.tickassist.ActivityState

_enum_

`public enum ActivityState`

Whether the player is currently performing a gather, as seen through their animation.

### Enum Constant Summary

| Enum Constant | Description |
|---|---|
| `IDLE` | Not gathering. |
| `RUNNING` | Actively gathering (a gather animation is playing). |
| `STALLED` | Was gathering but the animation has stopped, within the grace window before `#IDLE`. |

### Enum Constant Detail

#### IDLE

`IDLE`

Not gathering.

#### RUNNING

`RUNNING`

Actively gathering (a gather animation is playing).

#### STALLED

`STALLED`

Was gathering but the animation has stopped, within the grace window before `#IDLE`.

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

## com.oveduumnakal.tickassist.DetectionState

_enum_

`public enum DetectionState`

How far a detected recipe has progressed, gating how much guidance is shown.

### Enum Constant Summary

| Enum Constant | Description |
|---|---|
| `ACTIVE` | The player is performing the gather; show the full guidance. |
| `ARMED` | A setup is present but the player is not skilling yet; show a subtle "ready" hint. |
| `OFF` | No recipe matches; draw nothing. |

### Enum Constant Detail

#### ACTIVE

`ACTIVE`

The player is performing the gather; show the full guidance.

#### ARMED

`ARMED`

A setup is present but the player is not skilling yet; show a subtle "ready" hint.

#### OFF

`OFF`

No recipe matches; draw nothing.

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

## com.oveduumnakal.tickassist.GuidanceState

_class_

`public final class GuidanceState`

Pure ping-pong machine that turns the clock's current step and the detection state into what the
overlays draw: which target to highlight, the countdown, the pre-skill "armed" hint, and whether
the cadence has broken.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private boolean` | `armed` |  |
| `private boolean` | `broken` |  |
| `private int` | `countdown` |  |
| `private HighlightFocus` | `focus` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public boolean` | `armed()` | Whether a setup is present but the player is not skilling yet. |
| `public boolean` | `broken()` | Whether the cadence has broken (window elapsed without a gather). |
| `public int` | `countdown()` | Returns the ticks until the next action, or -1 when idle. |
| `public HighlightFocus` | `focus()` | Returns where to point the highlight this tick. |
| `public void` | `onCountdownExpired()` | Marks that the action window elapsed with no gather — the cadence broke. |
| `public void` | `onGather()` | Marks that a gather landed, clearing any broken state. |
| `public void` | `update(DetectionState detection, TickStep step, int ticksUntilAction)` | Recomputes the highlight for this tick. |

### Field Detail

#### armed

`private boolean armed`

#### broken

`private boolean broken`

#### countdown

`private int countdown`

#### focus

`private HighlightFocus focus`

### Method Detail

#### armed

`public boolean armed()`

Whether a setup is present but the player is not skilling yet.

- **Returns:** true when armed

#### broken

`public boolean broken()`

Whether the cadence has broken (window elapsed without a gather).

- **Returns:** true when broken

#### countdown

`public int countdown()`

Returns the ticks until the next action, or -1 when idle.

- **Returns:** the countdown in ticks

#### focus

`public HighlightFocus focus()`

Returns where to point the highlight this tick.

- **Returns:** the highlight focus

#### onCountdownExpired

`public void onCountdownExpired()`

Marks that the action window elapsed with no gather — the cadence broke.

#### onGather

`public void onGather()`

Marks that a gather landed, clearing any broken state.

#### update

`public void update(DetectionState detection, TickStep step, int ticksUntilAction)`

Recomputes the highlight for this tick.

- **Parameter** `detection` — the current detection state
- **Parameter** `step` — the clock's current step
- **Parameter** `ticksUntilAction` — ticks until the next action is due (the ground countdown)

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

## com.oveduumnakal.tickassist.InventoryHighlightOverlay

_class_

`public class InventoryHighlightOverlay`

Highlights the tick item(s) in the inventory while the guidance points there (`INVENTORY`
focus). Draws only an outline — it never clicks or moves anything.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private static final Color` | `INVENTORY_GLOW` |  |
| `private final TickAssistConfig` | `config` |  |
| `private final TickAssistPlugin` | `plugin` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `InventoryHighlightOverlay(TickAssistPlugin plugin, TickAssistConfig config)` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public void` | `renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem)` | Outlines an inventory item when it is the tick item currently due. |

### Field Detail

#### INVENTORY_GLOW

`private static final Color INVENTORY_GLOW`

#### config

`private final TickAssistConfig config`

#### plugin

`private final TickAssistPlugin plugin`

### Constructor Detail

#### InventoryHighlightOverlay

`InventoryHighlightOverlay(TickAssistPlugin plugin, TickAssistConfig config)`

### Method Detail

#### renderItemOverlay

`public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem)`

Outlines an inventory item when it is the tick item currently due.

- **Parameter** `graphics` — the overlay graphics context
- **Parameter** `itemId` — the id of the item in this slot
- **Parameter** `widgetItem` — the inventory slot widget

---

## com.oveduumnakal.tickassist.InventoryScanner

_class_

`public class InventoryScanner`

Reads the set of item ids currently in the player's inventory, used by `RecipeMatcher` to
spot a tick-manipulation setup.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private final Client` | `client` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `InventoryScanner(Client client)` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public int` | `count(int itemId)` | Returns the total quantity of an item held in the inventory. |
| `public Set<Integer>` | `heldItemIds()` | Returns the distinct item ids held in the inventory. |

### Field Detail

#### client

`private final Client client`

### Constructor Detail

#### InventoryScanner

`InventoryScanner(Client client)`

### Method Detail

#### count

`public int count(int itemId)`

Returns the total quantity of an item held in the inventory.

- **Parameter** `itemId` — the item id
- **Returns:** the total count, or 0 when the inventory is unavailable

#### heldItemIds

`public Set<Integer> heldItemIds()`

Returns the distinct item ids held in the inventory.

- **Returns:** the held item ids, or an empty set when the inventory is unavailable

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

## com.oveduumnakal.tickassist.RecipeMatch

_class_

`public final class RecipeMatch`

The outcome of `RecipeMatcher`: which recipe was matched, how far it has progressed, and
at what confidence.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private final Confidence` | `confidence` |  |
| `private final TickRecipe` | `recipe` |  |
| `private final DetectionState` | `state` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `RecipeMatch(TickRecipe recipe, DetectionState state, Confidence confidence)` | Creates a match. |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public Confidence` | `confidence()` | Returns the match confidence. |
| `public TickRecipe` | `recipe()` | Returns the matched recipe. |
| `public DetectionState` | `state()` | Returns the detection state. |

### Field Detail

#### confidence

`private final Confidence confidence`

#### recipe

`private final TickRecipe recipe`

#### state

`private final DetectionState state`

### Constructor Detail

#### RecipeMatch

`public RecipeMatch(TickRecipe recipe, DetectionState state, Confidence confidence)`

Creates a match.

- **Parameter** `recipe` — the matched recipe
- **Parameter** `state` — the detection state
- **Parameter** `confidence` — the match confidence

### Method Detail

#### confidence

`public Confidence confidence()`

Returns the match confidence.

- **Returns:** the confidence

#### recipe

`public TickRecipe recipe()`

Returns the matched recipe.

- **Returns:** the recipe

#### state

`public DetectionState state()`

Returns the detection state.

- **Returns:** the state

---

## com.oveduumnakal.tickassist.RecipeMatcher

_class_

`public final class RecipeMatcher`

Pure matcher that picks the active recipe from context: nearby resources, held items, and the
player's current animation.

<p>Priority, highest first: the current animation matches a recipe's gather animation
(`DetectionState#ACTIVE`); a required resource is in range and the tick items are held
(`DetectionState#ARMED`); the tick items of a `Confidence#HIGH` recipe are held
(`DetectionState#ARMED`). A `Confidence#GENERIC` recipe (common items such as a bare
knife) stays off until its animation confirms the player is skilling it. A pinned recipe always
wins. Ties fall to catalog order.

### Constructor Summary

| Constructor | Description |
|---|---|
| `RecipeMatcher()` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public static Optional<RecipeMatch>` | `match(Set<Integer> nearbyResourceIds, Set<Integer> heldItemIds, int currentAnimationId, TickRecipe pinned, List<TickRecipe> recipes)` | Matches the best recipe for the given context. |
| `private static int` | `priority(TickRecipe recipe, Set<Integer> nearby, Set<Integer> held, int anim)` |  |

### Constructor Detail

#### RecipeMatcher

`private RecipeMatcher()`

### Method Detail

#### match

`public static Optional<RecipeMatch> match(Set<Integer> nearbyResourceIds, Set<Integer> heldItemIds, int currentAnimationId, TickRecipe pinned, List<TickRecipe> recipes)`

Matches the best recipe for the given context.

- **Parameter** `nearbyResourceIds` — resource NPC/object ids currently in range
- **Parameter** `heldItemIds` — item ids the player is carrying
- **Parameter** `currentAnimationId` — the player's animation id this tick (`-1` for none)
- **Parameter** `pinned` — a forced recipe, or `null` for auto-detection
- **Parameter** `recipes` — the catalog to match against
- **Returns:** the best match, or empty when nothing matches

#### priority

`private static int priority(TickRecipe recipe, Set<Integer> nearby, Set<Integer> held, int anim)`

---

## com.oveduumnakal.tickassist.ResourceScanner

_class_

`public class ResourceScanner`

Reads the ids of manipulable resources near the player, used by `RecipeMatcher` to confirm
a setup is usable here.

<p>Phase-3 scan covers nearby NPCs (fishing spots). Rock and vine game-object scanning is added
once those object ids are captured in-game (Step-0); until then the object resource sets are
empty and object-based recipes arm on their held tick items alone.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private final Client` | `client` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `ResourceScanner(Client client)` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public Set<Integer>` | `nearbyResourceIds(int radius)` | Returns the ids of resource NPCs within the given tile radius of the player. |

### Field Detail

#### client

`private final Client client`

### Constructor Detail

#### ResourceScanner

`ResourceScanner(Client client)`

### Method Detail

#### nearbyResourceIds

`public Set<Integer> nearbyResourceIds(int radius)`

Returns the ids of resource NPCs within the given tile radius of the player.

- **Parameter** `radius` — the search radius in tiles
- **Returns:** the nearby resource ids, or an empty set when the player is not loaded

---

## com.oveduumnakal.tickassist.ShortFormat

_class_

`public final class ShortFormat`

Compact number formatting for the stats readout: `950`, `1.2k`, `62k`,
`1.1m`. A stateless utility that cannot be instantiated.

### Constructor Summary

| Constructor | Description |
|---|---|
| `ShortFormat()` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public static String` | `compact(double value)` | Formats a rate or count compactly with a lowercase k/m suffix. |
| `private static String` | `trim(double value)` |  |

### Constructor Detail

#### ShortFormat

`private ShortFormat()`

### Method Detail

#### compact

`public static String compact(double value)`

Formats a rate or count compactly with a lowercase k/m suffix.

- **Parameter** `value` — the value to format
- **Returns:** the compact string

#### trim

`private static String trim(double value)`

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

## com.oveduumnakal.tickassist.TargetHighlightOverlay

_class_

`public class TargetHighlightOverlay`

Highlights the nearest ground resource while the guidance points there, labelling it with the
countdown (per the chosen `CountdownStyle`), a pre-skill "Ready" hint when armed, or a
"Restart" cue when the cadence breaks.

<p>Anchors to resource NPCs (fishing spots); object resources join once their ids are captured
in-game (Step-0), so until then this draws only for NPC-based recipes.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private static final Color` | `ARMED_COLOR` |  |
| `private static final Color` | `BREAK_COLOR` |  |
| `private static final Color` | `GROUND_COLOR` |  |
| `private final Client` | `client` |  |
| `private final TickAssistConfig` | `config` |  |
| `private final TickAssistPlugin` | `plugin` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `TargetHighlightOverlay(TickAssistPlugin plugin, TickAssistConfig config, Client client)` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `private String` | `label(GuidanceState guidance, boolean armed)` |  |
| `public Dimension` | `render(Graphics2D graphics)` | Outlines and labels the nearest resource when the guidance points at the ground. |

### Field Detail

#### ARMED_COLOR

`private static final Color ARMED_COLOR`

#### BREAK_COLOR

`private static final Color BREAK_COLOR`

#### GROUND_COLOR

`private static final Color GROUND_COLOR`

#### client

`private final Client client`

#### config

`private final TickAssistConfig config`

#### plugin

`private final TickAssistPlugin plugin`

### Constructor Detail

#### TargetHighlightOverlay

`TargetHighlightOverlay(TickAssistPlugin plugin, TickAssistConfig config, Client client)`

### Method Detail

#### label

`private String label(GuidanceState guidance, boolean armed)`

#### render

`public Dimension render(Graphics2D graphics)`

Outlines and labels the nearest resource when the guidance points at the ground.

- **Parameter** `graphics` — the overlay graphics context
- **Returns:** always `null` (this overlay draws in the scene)

---

## com.oveduumnakal.tickassist.TargetLocator

_class_

`public final class TargetLocator`

Finds the resource the player should aim at. The nearest-of-candidates arithmetic is a pure,
tested helper; the client wrapper applies it to the live NPC list.

<p>Object-based resources (rocks, vines) join this once their object ids are captured in-game
(Step-0); for now only resource NPCs (fishing spots) are located.

### Constructor Summary

| Constructor | Description |
|---|---|
| `TargetLocator()` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public static OptionalInt` | `nearestIndex(List<Integer> distances)` | Returns the index of the smallest distance, or empty when the list is empty. |
| `public static Optional<NPC>` | `nearestResource(Client client, Set<Integer> resourceIds)` | Returns the nearest live NPC whose id is a resource for the active recipe. |

### Constructor Detail

#### TargetLocator

`private TargetLocator()`

### Method Detail

#### nearestIndex

`public static OptionalInt nearestIndex(List<Integer> distances)`

Returns the index of the smallest distance, or empty when the list is empty. Ties keep the
earliest index.

- **Parameter** `distances` — the candidate distances
- **Returns:** the index of the nearest, or empty

#### nearestResource

`public static Optional<NPC> nearestResource(Client client, Set<Integer> resourceIds)`

Returns the nearest live NPC whose id is a resource for the active recipe.

- **Parameter** `client` — the game client
- **Parameter** `resourceIds` — the recipe's resource NPC ids
- **Returns:** the nearest matching NPC, or empty

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
| `default CountdownStyle` | `countdownStyle()` | How the countdown to the next action is drawn on the ground target. |
| `default int` | `customCadence()` | The cadence, in ticks, of the manual beat used until context detection selects a technique. |
| `default int` | `lowTickItemThreshold()` | The count below which a consumable tick item is considered low. |
| `default MetronomeStyle` | `metronomeStyle()` | How the beat is displayed. |
| `default int` | `scanRadius()` | How far, in tiles, to look for a manipulable resource when detecting a setup. |
| `default boolean` | `showAccuracy()` | Whether to show the live accuracy infobox (success %, streak, actions/hour, XP/hour). |
| `default boolean` | `warnLowTickItems()` | Whether to warn when a consumable tick item is running low. |

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

#### countdownStyle

`default CountdownStyle countdownStyle()`

How the countdown to the next action is drawn on the ground target.

- **Returns:** the chosen countdown style

#### customCadence

`default int customCadence()`

The cadence, in ticks, of the manual beat used until context detection selects a technique.

- **Returns:** the manual cadence in ticks

#### lowTickItemThreshold

`default int lowTickItemThreshold()`

The count below which a consumable tick item is considered low.

- **Returns:** the low-stock threshold

#### metronomeStyle

`default MetronomeStyle metronomeStyle()`

How the beat is displayed. `MetronomeStyle#TARGET_FOLLOW` is the ping-pong highlight;
the other styles draw an on-screen beat instead.

- **Returns:** the chosen metronome style

#### scanRadius

`default int scanRadius()`

How far, in tiles, to look for a manipulable resource when detecting a setup.

- **Returns:** the scan radius in tiles

#### showAccuracy

`default boolean showAccuracy()`

Whether to show the live accuracy infobox (success %, streak, actions/hour, XP/hour).

- **Returns:** true when the stats infobox is shown

#### warnLowTickItems

`default boolean warnLowTickItems()`

Whether to warn when a consumable tick item is running low.

- **Returns:** true when the low-stock warning is on

---

## com.oveduumnakal.tickassist.TickAssistIds

_class_

`public final class TickAssistIds`

Game id sets that drive detection, grouped by recipe. Item and animation ids use verified
`net.runelite.api.gameval` constants.

<p><b>Step-0 (in-game) status:</b> the tick-item and mining/fishing/cooking animation sets are
resolved. The resource-entity sets (fishing-spot NPC ids, rock and vine object ids) still need
capturing with the RuneLite dev tools; until then they are empty, and a recipe with an empty
resource set arms on its held tick items alone rather than also requiring the resource in range.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `public static final Set<Integer>` | `CONSUMABLE_TICK_ITEMS` | Tick items that deplete with use, so a low-stock warning applies (not pestle/knife). |
| `public static final Set<Integer>` | `COOKING_ANIMS` | Cooking animation. |
| `public static final Set<Integer>` | `FISHING_ANIMS` | Barbarian/large-net fishing animation. |
| `public static final Set<Integer>` | `FISHING_SPOTS` | Fishing-spot NPC ids — capture in-game (Step-0). |
| `public static final Set<Integer>` | `FISHING_TICK_ITEMS` | Tick items for 3-tick fishing: swamp tar + guam (with pestle) is the distinctive setup. |
| `public static final Set<Integer>` | `HERBLORE_TICK_ITEMS` | Tick items for 3-tick herblore (herb tar): swamp tar + guam + pestle, inventory only. |
| `public static final Set<Integer>` | `KARAMBWAN_TICK_ITEMS` | Raw karambwan for 1-tick cooking. |
| `public static final Set<Integer>` | `MINING_ANIMS` | Mining animations across pickaxe tiers. |
| `public static final Set<Integer>` | `MINING_ROCKS` | Rock object ids — capture in-game (Step-0). |
| `public static final Set<Integer>` | `MINING_TICK_ITEMS` | Tick items for 3-tick mining: swamp tar + guam (with pestle). |
| `public static final Set<Integer>` | `SNAKE_WEED_TICK_ITEMS` | Tick items alongside snake-weed picking. |
| `public static final Set<Integer>` | `SNAKE_WEED_VINES` | Marshy-vine object ids for snake weed — capture in-game (Step-0). |

### Constructor Summary

| Constructor | Description |
|---|---|
| `TickAssistIds()` |  |

### Field Detail

#### CONSUMABLE_TICK_ITEMS

`public static final Set<Integer> CONSUMABLE_TICK_ITEMS`

Tick items that deplete with use, so a low-stock warning applies (not pestle/knife).

#### COOKING_ANIMS

`public static final Set<Integer> COOKING_ANIMS`

Cooking animation.

#### FISHING_ANIMS

`public static final Set<Integer> FISHING_ANIMS`

Barbarian/large-net fishing animation.

#### FISHING_SPOTS

`public static final Set<Integer> FISHING_SPOTS`

Fishing-spot NPC ids — capture in-game (Step-0).

#### FISHING_TICK_ITEMS

`public static final Set<Integer> FISHING_TICK_ITEMS`

Tick items for 3-tick fishing: swamp tar + guam (with pestle) is the distinctive setup.

#### HERBLORE_TICK_ITEMS

`public static final Set<Integer> HERBLORE_TICK_ITEMS`

Tick items for 3-tick herblore (herb tar): swamp tar + guam + pestle, inventory only.

#### KARAMBWAN_TICK_ITEMS

`public static final Set<Integer> KARAMBWAN_TICK_ITEMS`

Raw karambwan for 1-tick cooking. Resolve the exact item id in-game (Step-0).

#### MINING_ANIMS

`public static final Set<Integer> MINING_ANIMS`

Mining animations across pickaxe tiers.

#### MINING_ROCKS

`public static final Set<Integer> MINING_ROCKS`

Rock object ids — capture in-game (Step-0).

#### MINING_TICK_ITEMS

`public static final Set<Integer> MINING_TICK_ITEMS`

Tick items for 3-tick mining: swamp tar + guam (with pestle).

#### SNAKE_WEED_TICK_ITEMS

`public static final Set<Integer> SNAKE_WEED_TICK_ITEMS`

Tick items alongside snake-weed picking.

#### SNAKE_WEED_VINES

`public static final Set<Integer> SNAKE_WEED_VINES`

Marshy-vine object ids for snake weed — capture in-game (Step-0).

### Constructor Detail

#### TickAssistIds

`private TickAssistIds()`

---

## com.oveduumnakal.tickassist.TickAssistPlugin

_class_

`public class TickAssistPlugin`

Tick Assist — detects skilling tick-manipulation setups and visualises their timing.

<p>Each tick the plugin reads the player's animation, the resources in range, and the items they
carry, matches a recipe from the catalog, and drives the tick clock from it — falling back to a
plain manual metronome when nothing is detected. It never clicks anything; it only visualises the
beat. The ping-pong highlight and accuracy stats build on this in later phases.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private static final int` | `STALL_TICKS` |  |
| `private AccuracyTracker` | `accuracy` |  |
| `private TickRecipe` | `activeRecipe` |  |
| `private ActivityDetector` | `activityDetector` |  |
| `private List<TickRecipe>` | `catalog` |  |
| `private Client` | `client` |  |
| `private TickClock` | `clock` |  |
| `private TickAssistConfig` | `config` |  |
| `private RecipeMatch` | `currentMatch` |  |
| `private TickRecipe` | `fallback` |  |
| `private int` | `gameTick` |  |
| `private boolean` | `gatheredThisCycle` |  |
| `private GuidanceState` | `guidance` |  |
| `private InventoryHighlightOverlay` | `inventoryHighlightOverlay` |  |
| `private InventoryScanner` | `inventoryScanner` |  |
| `private int` | `lastItemCount` |  |
| `private int` | `lastSkillXp` |  |
| `private TickMetronomeOverlay` | `metronomeOverlay` |  |
| `private OverlayManager` | `overlayManager` |  |
| `private ResourceScanner` | `resourceScanner` |  |
| `private TickStatsInfoBox` | `statsInfoBox` |  |
| `private TargetHighlightOverlay` | `targetHighlightOverlay` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `AccuracyTracker` | `accuracy()` | Returns the accuracy tracker for the active recipe, or `null` when the plugin is stopped. |
| `TickRecipe` | `activeRecipe()` | Returns the recipe currently driving the beat, or `null` when the plugin is stopped. |
| `TickClock` | `clock()` | Returns the clock currently driving the beat, or `null` when the plugin is stopped. |
| `private int` | `currentAnimationId()` |  |
| `RecipeMatch` | `currentMatch()` | Returns the current detection result, or `null` when nothing is detected. |
| `GuidanceState` | `guidance()` | Returns the current guidance state, or `null` when the plugin is stopped. |
| `public void` | `onConfigChanged(ConfigChanged event)` | Rebuilds the fallback metronome when the manual cadence changes. |
| `public void` | `onGameTick(GameTick event)` | Runs detection for the tick, switches the active recipe when it changes, and advances the beat. |
| `public void` | `onItemContainerChanged(ItemContainerChanged event)` | Scores a successful gather from an item-count increase (for methods that grant no XP). |
| `public void` | `onMenuOptionClicked(MenuOptionClicked event)` | Re-anchors the beat to the tick-item step when the player actually clicks the tick item. |
| `public void` | `onStatChanged(StatChanged event)` | Scores a successful gather from an XP gain in the recipe's skill. |
| `TickAssistConfig` | `provideConfig(ConfigManager configManager)` | Supplies the plugin's configuration proxy to RuneLite's injector. |
| `private void` | `rebuildFallback()` |  |
| `private void` | `registerGather()` |  |
| `private TickRecipe` | `selectRecipe(int animationId)` |  |
| `protected void` | `shutDown()` | Stops the plugin: removes the overlay and drops all live state. |
| `protected void` | `startUp()` | Starts the plugin: seeds the catalog, builds the fallback clock, and registers the overlay. |
| `private int` | `stepIndexOf(TickRecipe recipe, StepKind kind)` |  |

### Field Detail

#### STALL_TICKS

`private static final int STALL_TICKS`

#### accuracy

`private AccuracyTracker accuracy`

#### activeRecipe

`private TickRecipe activeRecipe`

#### activityDetector

`private ActivityDetector activityDetector`

#### catalog

`private List<TickRecipe> catalog`

#### client

`private Client client`

#### clock

`private TickClock clock`

#### config

`private TickAssistConfig config`

#### currentMatch

`private RecipeMatch currentMatch`

#### fallback

`private TickRecipe fallback`

#### gameTick

`private int gameTick`

#### gatheredThisCycle

`private boolean gatheredThisCycle`

#### guidance

`private GuidanceState guidance`

#### inventoryHighlightOverlay

`private InventoryHighlightOverlay inventoryHighlightOverlay`

#### inventoryScanner

`private InventoryScanner inventoryScanner`

#### lastItemCount

`private int lastItemCount`

#### lastSkillXp

`private int lastSkillXp`

#### metronomeOverlay

`private TickMetronomeOverlay metronomeOverlay`

#### overlayManager

`private OverlayManager overlayManager`

#### resourceScanner

`private ResourceScanner resourceScanner`

#### statsInfoBox

`private TickStatsInfoBox statsInfoBox`

#### targetHighlightOverlay

`private TargetHighlightOverlay targetHighlightOverlay`

### Method Detail

#### accuracy

`AccuracyTracker accuracy()`

Returns the accuracy tracker for the active recipe, or `null` when the plugin is stopped.

- **Returns:** the accuracy tracker, or `null`

#### activeRecipe

`TickRecipe activeRecipe()`

Returns the recipe currently driving the beat, or `null` when the plugin is stopped.

- **Returns:** the active recipe, or `null`

#### clock

`TickClock clock()`

Returns the clock currently driving the beat, or `null` when the plugin is stopped.

- **Returns:** the tick clock, or `null`

#### currentAnimationId

`private int currentAnimationId()`

#### currentMatch

`RecipeMatch currentMatch()`

Returns the current detection result, or `null` when nothing is detected.

- **Returns:** the current match, or `null`

#### guidance

`GuidanceState guidance()`

Returns the current guidance state, or `null` when the plugin is stopped.

- **Returns:** the guidance state, or `null`

#### onConfigChanged

`public void onConfigChanged(ConfigChanged event)`

Rebuilds the fallback metronome when the manual cadence changes.

- **Parameter** `event` — the config-changed event

#### onGameTick

`public void onGameTick(GameTick event)`

Runs detection for the tick, switches the active recipe when it changes, and advances the beat.

- **Parameter** `event` — the game-tick event

#### onItemContainerChanged

`public void onItemContainerChanged(ItemContainerChanged event)`

Scores a successful gather from an item-count increase (for methods that grant no XP).

- **Parameter** `event` — the item-container-changed event

#### onMenuOptionClicked

`public void onMenuOptionClicked(MenuOptionClicked event)`

Re-anchors the beat to the tick-item step when the player actually clicks the tick item.

- **Parameter** `event` — the menu-click event

#### onStatChanged

`public void onStatChanged(StatChanged event)`

Scores a successful gather from an XP gain in the recipe's skill.

- **Parameter** `event` — the stat-changed event

#### provideConfig

`TickAssistConfig provideConfig(ConfigManager configManager)`

Supplies the plugin's configuration proxy to RuneLite's injector.

- **Parameter** `configManager` — the client configuration manager
- **Returns:** the Tick Assist configuration

#### rebuildFallback

`private void rebuildFallback()`

#### registerGather

`private void registerGather()`

#### selectRecipe

`private TickRecipe selectRecipe(int animationId)`

#### shutDown

`protected void shutDown()`

Stops the plugin: removes the overlay and drops all live state.

#### startUp

`protected void startUp()`

Starts the plugin: seeds the catalog, builds the fallback clock, and registers the overlay.

#### stepIndexOf

`private int stepIndexOf(TickRecipe recipe, StepKind kind)`

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

## com.oveduumnakal.tickassist.TickItemMonitor

_class_

`public class TickItemMonitor`

Warns when a consumable tick item is running low, so the player can restock before the cadence
stalls. Reusable items (pestle, knife) are never flagged.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private final InventoryScanner` | `inventoryScanner` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `TickItemMonitor(InventoryScanner inventoryScanner)` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public boolean` | `lowOnTickItems(TickRecipe recipe, int threshold)` | Whether any consumable tick item for the recipe is carried but below the threshold. |

### Field Detail

#### inventoryScanner

`private final InventoryScanner inventoryScanner`

### Constructor Detail

#### TickItemMonitor

`TickItemMonitor(InventoryScanner inventoryScanner)`

### Method Detail

#### lowOnTickItems

`public boolean lowOnTickItems(TickRecipe recipe, int threshold)`

Whether any consumable tick item for the recipe is carried but below the threshold.

- **Parameter** `recipe` — the active recipe
- **Parameter** `threshold` — the low-stock threshold
- **Returns:** true when a consumable tick item is running low

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
| `private final Set<Integer>` | `gatherAnimationIds` |  |
| `private final String` | `id` |  |
| `private final Set<Integer>` | `resourceIds` |  |
| `private final GatherSignal` | `signal` |  |
| `private final List<TickStep>` | `steps` |  |
| `private final Set<Integer>` | `tickItemIds` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `TickRecipe(String id, String displayName, List<TickStep> steps, Confidence confidence, GatherSignal signal, Set<Integer> resourceIds, Set<Integer> tickItemIds, Set<Integer> gatherAnimationIds, String blurb)` | Creates a recipe. |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `public String` | `blurb()` | Returns the short "how it works" explainer. |
| `public int` | `cadenceTicks()` | Returns the cadence in ticks: the sum of every step's duration (the length of one cycle). |
| `public Confidence` | `confidence()` | Returns the recipe's confidence tier. |
| `public String` | `displayName()` | Returns the panel display name. |
| `public Set<Integer>` | `gatherAnimationIds()` | Returns the animation ids that mean the player is performing the gather. |
| `public String` | `id()` | Returns the stable identifier. |
| `public boolean` | `requiresResource()` | Whether the recipe needs a ground resource in range (false for inventory-only recipes). |
| `public Set<Integer>` | `resourceIds()` | Returns the ground-resource NPC/object ids (empty for an inventory-only recipe). |
| `public GatherSignal` | `signal()` | Returns the successful-gather signal. |
| `public List<TickStep>` | `steps()` | Returns the ordered, unmodifiable list of cycle steps. |
| `public Set<Integer>` | `tickItemIds()` | Returns the item ids whose presence marks this setup. |

### Field Detail

#### blurb

`private final String blurb`

#### confidence

`private final Confidence confidence`

#### displayName

`private final String displayName`

#### gatherAnimationIds

`private final Set<Integer> gatherAnimationIds`

#### id

`private final String id`

#### resourceIds

`private final Set<Integer> resourceIds`

#### signal

`private final GatherSignal signal`

#### steps

`private final List<TickStep> steps`

#### tickItemIds

`private final Set<Integer> tickItemIds`

### Constructor Detail

#### TickRecipe

`public TickRecipe(String id, String displayName, List<TickStep> steps, Confidence confidence, GatherSignal signal, Set<Integer> resourceIds, Set<Integer> tickItemIds, Set<Integer> gatherAnimationIds, String blurb)`

Creates a recipe.

- **Parameter** `id` — a stable lowercase identifier ("three_tick_fishing")
- **Parameter** `displayName` — the label shown in the panel ("3-tick fishing")
- **Parameter** `steps` — the ordered cycle steps (at least one)
- **Parameter** `confidence` — how distinctive the setup is
- **Parameter** `signal` — the successful-gather signal
- **Parameter** `resourceIds` — NPC/object ids of the ground resource (empty for inventory-only)
- **Parameter** `tickItemIds` — item ids whose presence marks this setup
- **Parameter** `gatherAnimationIds` — animation ids that mean the player is performing the gather
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

#### gatherAnimationIds

`public Set<Integer> gatherAnimationIds()`

Returns the animation ids that mean the player is performing the gather.

- **Returns:** the gather animation ids

#### id

`public String id()`

Returns the stable identifier.

- **Returns:** the recipe id

#### requiresResource

`public boolean requiresResource()`

Whether the recipe needs a ground resource in range (false for inventory-only recipes).

- **Returns:** true when a ground resource is required

#### resourceIds

`public Set<Integer> resourceIds()`

Returns the ground-resource NPC/object ids (empty for an inventory-only recipe).

- **Returns:** the resource ids

#### signal

`public GatherSignal signal()`

Returns the successful-gather signal.

- **Returns:** the gather signal

#### steps

`public List<TickStep> steps()`

Returns the ordered, unmodifiable list of cycle steps.

- **Returns:** the steps

#### tickItemIds

`public Set<Integer> tickItemIds()`

Returns the item ids whose presence marks this setup.

- **Returns:** the tick-item ids

---

## com.oveduumnakal.tickassist.TickStatsInfoBox

_class_

`public class TickStatsInfoBox`

A small on-screen panel of live timing stats — success %, streak, actions/hour, and XP/hour —
plus a low-stock warning for consumable tick items. Shown only while a recipe is detected and the
stats setting is on.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private static final Color` | `WARNING` |  |
| `private final TickAssistConfig` | `config` |  |
| `private final PanelComponent` | `panel` |  |
| `private final TickAssistPlugin` | `plugin` |  |
| `private final TickItemMonitor` | `tickItemMonitor` |  |

### Constructor Summary

| Constructor | Description |
|---|---|
| `TickStatsInfoBox(TickAssistPlugin plugin, TickAssistConfig config, TickItemMonitor tickItemMonitor)` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `private void` | `addLine(String left, String right)` |  |
| `public Dimension` | `render(Graphics2D graphics)` | Renders the stats panel when a recipe is detected and stats are enabled. |

### Field Detail

#### WARNING

`private static final Color WARNING`

#### config

`private final TickAssistConfig config`

#### panel

`private final PanelComponent panel`

#### plugin

`private final TickAssistPlugin plugin`

#### tickItemMonitor

`private final TickItemMonitor tickItemMonitor`

### Constructor Detail

#### TickStatsInfoBox

`TickStatsInfoBox(TickAssistPlugin plugin, TickAssistConfig config, TickItemMonitor tickItemMonitor)`

### Method Detail

#### addLine

`private void addLine(String left, String right)`

#### render

`public Dimension render(Graphics2D graphics)`

Renders the stats panel when a recipe is detected and stats are enabled.

- **Parameter** `graphics` — the overlay graphics context
- **Returns:** the rendered size, or `null` when nothing is drawn

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
