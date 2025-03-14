package com.AurorasChaos; // Updated package to match file location

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.Optional;

/**
 * ExarotonProxyHelper plugin for Velocity.
 * 
 * This plugin listens for plugin messages on a custom channel and triggers
 * server switch commands for players.
 * 
 * Author: AurorasChaos
 */
@Plugin(id = "exarotonproxyhelper", name = "ExarotonProxyHelper", version = "1.0", description = "A Velocity plugin for plugin messaging.")
public class ExarotonProxyHelper {
    // Constant defining the plugin messaging channel identifier
    private static final String CHANNEL = "velocity:custom";
    
    private final ProxyServer proxy;
    private final Logger logger;

    /**
     * Constructs a new ExarotonProxyHelper instance.
     *
     * @param proxy         The proxy server instance.
     * @param logger        Logger for plugin logging.
     * @param dataDirectory Directory for plugin data storage.
     */
    @Inject
    public ExarotonProxyHelper(ProxyServer proxy, Logger logger, @DataDirectory Path dataDirectory) {
        this.proxy = proxy;
        this.logger = logger;
    }

    /**
     * Called on proxy initialization.
     * Registers the custom plugin messaging channel.
     *
     * @param event The proxy initialization event.
     */
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // Create the Minecraft channel identifier for plugin messaging
        MinecraftChannelIdentifier identifier = MinecraftChannelIdentifier.create("velocity", "custom");
        // Register the channel with the proxy
        proxy.getChannelRegistrar().register(identifier);
        logger.info("Registered plugin channel: velocity:custom");
    }

    /**
     * Handles incoming plugin messages on the registered channel.
     *
     * @param event The plugin message event.
     */
    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        // Ensure the message is for the expected channel
        if (!event.getIdentifier().getId().equals(CHANNEL)) {
            return;
        }

        // Read the message data
        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        String subChannel = in.readUTF();
        
        // Process the "exarotonSwitch" subchannel command
        if (subChannel.equals("exarotonSwitch")) {
            String playerName = in.readUTF();
            String subServer = in.readUTF();
            logger.info("Received switch server request for player " + playerName + " to server: " + subServer);
            switchServer(playerName, subServer);
        }
    }

    /**
     * Executes a server switch command for the given player.
     *
     * @param playerName The name of the player.
     * @param subServer  The target server name.
     */
    public void switchServer(String playerName, String subServer) {
        // Attempt to retrieve the player from the proxy
        Optional<Player> playerOptional = proxy.getPlayer(playerName);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            // Execute the switch server command asynchronously
            proxy.getCommandManager().executeAsync(player, "exaroton switch " + subServer);
            logger.info("Executing command: /exaroton switch " + subServer + " for player " + playerName);
        } else {
            logger.warn("Player " + playerName + " not found to execute Exaroton switch command.");
        }
    }
}
