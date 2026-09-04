# Stockpile — JavaDoc Reference

<!-- GENERATED FILE — DO NOT EDIT BY HAND.
     Run `./gradlew generateJavaDocs` and commit the result. -->

## Contents

- [com.oveduumnakal.tickassist.TickAssistConfig](#comoveduumnakaltickassisttickassistconfig)
- [com.oveduumnakal.tickassist.TickAssistPlugin](#comoveduumnakaltickassisttickassistplugin)

---

## com.oveduumnakal.tickassist.TickAssistConfig

_interface_

`public interface TickAssistConfig`

RuneLite configuration for Tick Assist.

<p>Phase-1 scaffold exposes only the master detection toggle. The full setting surface
(countdown style, match confidence, accuracy stats, audio cue, tick-item warnings) is added
as each subsystem lands in later phases.

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `default boolean` | `autoDetect()` | Whether the plugin auto-detects tick-manipulation setups from nearby resources and the items the player is carrying. |

### Method Detail

#### autoDetect

`default boolean autoDetect()`

Whether the plugin auto-detects tick-manipulation setups from nearby resources and the
items the player is carrying.

- **Returns:** true when context detection is enabled

---

## com.oveduumnakal.tickassist.TickAssistPlugin

_class_

`public class TickAssistPlugin`

Tick Assist — detects skilling tick-manipulation setups and visualises their timing.

<p>The plugin watches the resources around the player and the items they carry; when a known
tick-manipulation setup is present it shows which item to click and when, with a live countdown
and accuracy feedback. It never clicks anything — it only visualises the beat.

<p>This is the Phase-1 scaffold: it wires the plugin lifecycle and configuration only. The
detection, tick-clock, guidance, and overlay subsystems land in later phases.

### Field Summary

| Modifier and Type | Field | Description |
|---|---|---|
| `private TickAssistConfig` | `config` |  |

### Method Summary

| Modifier and Type | Method | Description |
|---|---|---|
| `TickAssistConfig` | `provideConfig(ConfigManager configManager)` | Supplies the plugin's configuration proxy to RuneLite's injector. |
| `protected void` | `shutDown()` | Stops the plugin and releases any resources it holds. |
| `protected void` | `startUp()` | Starts the plugin. |

### Field Detail

#### config

`private TickAssistConfig config`

### Method Detail

#### provideConfig

`TickAssistConfig provideConfig(ConfigManager configManager)`

Supplies the plugin's configuration proxy to RuneLite's injector.

- **Parameter** `configManager` — the client configuration manager
- **Returns:** the Tick Assist configuration

#### shutDown

`protected void shutDown()`

Stops the plugin and releases any resources it holds.

#### startUp

`protected void startUp()`

Starts the plugin. Phase-1 scaffold performs no work beyond logging.
