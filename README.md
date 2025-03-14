# ExarotonProxyHelper

ExarotonProxyHelper is a Velocity plugin designed to assist with proxy plugin messaging by listening for custom messages and triggering server switch commands for players. This lightweight tool helps streamline server transitions based on custom messaging channels.

## Features

- **Custom Plugin Messaging:** Listens on the `velocity:custom` channel.
- **Server Switching:** Processes the `exarotonSwitch` subchannel to switch players to a specified sub-server.
- **Asynchronous Command Execution:** Executes commands asynchronously to minimize performance impact.
- **Easy Integration:** Built using the Velocity API, making it a breeze to integrate into your existing proxy setup.

## Requirements

- **Velocity Proxy Server:** Compatible with Velocity API version 3.2.0-SNAPSHOT or later.
- **Java Version:** Java 17 or higher.
- **Build Tool:** Maven for compiling and packaging the plugin.

## Installation

### 1. Build the Plugin:

Clone the repository and use Maven to package the plugin:

```bash
mvn clean package
```

This command will generate the `ExarotonProxyHelper-1.0-SNAPSHOT.jar` file in the `target/` directory.

### 2. Deploy the Plugin:

Copy the generated JAR file into the `plugins/` directory of your Velocity installation.

### 3. Restart Velocity:

Restart your Velocity proxy server to load the new plugin.

## Configuration

No additional configuration is required for basic usage. The plugin automatically registers its custom messaging channel upon initialization. If needed, further customization can be added by modifying the source code in `ExarotonProxyHelper.java`.

## Development

- **Source Code Location:** All Java source files are located in `/java/com/AurorasChaos`.
- **Build System:** This project uses Maven; the configuration is managed in the `pom.xml` file.
- **Extending Functionality:** Developers can modify the code to add additional features or configurations as needed.

## Credits

- **Author:** AurorasChaos
- **Velocity API:** VelocityPowered

## License

This project is licensed under the MIT License. See the LICENSE file for more details.
