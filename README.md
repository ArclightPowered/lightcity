# lightcity

A simple plugin that adds Velocity legacy player-info-forwarding support for Arclight 1.20.2 and later.

## Installing

Drop this plugin into Velocity plugin folder, and configure player-info-forwarding to `legacy`, enable `bungee` in spigot.yml

Arclight Net (1.20.2) supports Velocity out of the box if you don't need the player info forwarding.

Arclight Whisper (1.20.4) and later supports Velocity modern forward without this plugin. Configure `player-info-forwarding` to modern, enable `velocity` and fill the `secret` in `arclight.conf`.

|Status|Arclight Net (1.20.2)|Arclight Whisper (1.20.4) +|
|---|---|---|
|Velocity w/o forwarding|✅|✅|
|Velocity w/ legacy forwarding|✅ w/ lightcity|✅ w/ lightcity|
|Velocity w/ modern forwarding|❌|✅|
|lightfall|✅|✅|

## License

This project is licensed under [GPL v3](LICENSE).
