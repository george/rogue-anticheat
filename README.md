# Rogue AntiCheat

A Spigot AntiCheat plugin utilizing remote check processing

## Purpose & Structure

Rogue was designed to allow AntiCheat checks to be processed remotely. Rogue collections minimal, essential data before sending it remotely to be processed by the Rogue server. This structure prevents Rogue from causing server lag, while maintaining check accuracy.

## Libraries Used

The Rogue plugin was designed using the Spigot API for Minecraft 1.8.8, though it's compatible with previous and newer versions. The plugin is built using Pledge and ProtocolLib, while the server is written in C++ using the Crow HTTP library.

Alternatively, there is an outdated and unmaintained Python-based branch of Rogue using Flask.

## Other Important Information

Rogue does not support versions above 1.16.5. This is caused by Pledge's usage of Java features which are no longer supported in Java 17+.

## Installation

Rogue is designed to be installed as a normal plugin, while the server requires you to handle hosting separately.

Plugin

1. Compile the Rogue plugin by executing `mvn clean install`.
2. Move the generated JAR file from `target/` to your server's plugins directory.
3. Configure the generated config.yml in your `plugins/Rogue` directory with the IP address and Port of the server running the Rogue server.

Server

1. Compile the Rogue server by executing `cmake .`.
2. Move the compiled binary to the same directory as the configuration file.

## Credits

Thanks to sim0n for allowing me to use checks and utilities from Nemesis AntiCheat.