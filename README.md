[Modrinth](modrinth.com/project/voicechat-worldguard)
|
[Discord](https://discord.gg/yCdhVDgn4K)
|
[Wiki](https://docs.yleoft.me/voicechat-worldguard)

# Voicechat WorldGuard

Add custom WorldGuard flags for Simple Voice Chat.

This small plugin provides WorldGuard region flags to control Simple Voice Chat behaviour per-region. It is intended for server software based on Bukkit APIs: Bukkit, Spigot, Paper and Folia. It does NOT support mod loaders (Fabric, Forge, etc.).

## Features

- Per-region control of voice chat behaviour for players inside WorldGuard regions.
- Simple, focused set of flags:
    - voicechat-enabled — enable/disable voice in the region (boolean)
    - voicechat-distance — set voice range in blocks (integer)
    - voicechat-muted — mute voice in the region (boolean)

(Flags and names may be extended or changed in future versions — check the plugin's documentation or changelog.)

## Requirements

- Java 17 or above
- WorldGuard (installed and working)
- Simple Voice Chat plugin 2.1.12 or above
- Server platforms supported: Bukkit, Spigot, Paper, Folia

## Usage

Set flags on a WorldGuard region with the standard WorldGuard commands. Examples:

- Disable voice chat in a region:
    - /region flag <region> voicechat-enabled deny
    - or: /rg flag <region> voicechat-enabled false

- Set a custom voice distance for a region:
    - /region flag <region> voicechat-distance 30

- Mute voice inside a region:
    - /region flag <region> voicechat-muted allow
    - or: /rg flag <region> voicechat-muted true

Note: WorldGuard accepts values in slightly different formats depending on version. If "allow/deny" does not work, try "true/false" for boolean flags.

## Permissions

- voicechatworldguard.bypass.* — Allows players to bypass all region voice chat restrictions.
- voicechatworldguard.bypass.<flag> — Allows players to bypass a specific flag restriction (e.g., voicechatworldguard.bypass.voicechat-enabled).

## Reporting Issues

If you find a bug or have a feature request, please open an issue on this repository: https://github.com/yL3oft/VoicechatWorlguard/issues

When reporting, include:
- Server platform & version (Spigot/Paper/Folia/etc.)
- Versions of WorldGuard and Simple Voice Chat
- Plugin version and server logs if available