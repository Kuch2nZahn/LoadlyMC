package io.github.kuchenzahn.loadlybukkit;

import io.github.kuchenzahn.LoadlyServer;
import io.github.kuchenzahn.LoadlyUUID;
import io.github.kuchenzahn.event.EventRegistry;
import io.github.kuchenzahn.loadlybukkit.commands.CommandManager;
import io.github.kuchenzahn.loadlybukkit.commands.LoadlyCommand;
import io.github.kuchenzahn.loadlybukkit.config.ConfigManager;
import io.github.kuchenzahn.loadlybukkit.event.EventHandler;
import io.github.kuchenzahn.loadlybukkit.message.MessageHandler;
import io.github.kuchenzahn.registry.LoadlyPacketRegistry;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.InetSocketAddress;

public final class LoadlyBukkit extends JavaPlugin {
    private static LoadlyBukkit instance;

    private ConfigManager configManager;
    private MessageHandler messageHandler;

    private LoadlyServer loadlyServer;
    private EventRegistry eventRegistry;
    private EventHandler eventHandler;
    private LoadlyPacketRegistry packetRegistry;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        instance = this;

        this.configManager = new ConfigManager();
        configManager.checkConfig();

        this.eventHandler = new EventHandler();
        this.messageHandler = new MessageHandler();

        startLoadlyServer();

        this.commandManager = new CommandManager();
        LoadlyCommand loadlyCommand = new LoadlyCommand();
        getCommand("loadly").setExecutor(loadlyCommand);
        getCommand("loadly").setTabCompleter(loadlyCommand);
    }

    @Override
    public void onDisable() {
        if(this.loadlyServer != null){
            loadlyServer.shutdown();
        }
    }

    private void startLoadlyServer() {
        FileConfiguration config = getConfig();
        String host = config.contains("host") ? config.getString("host") : "localhost";
        int port = config.contains("port") ? config.getInt("port") : 2244;

        this.packetRegistry = new LoadlyPacketRegistry();
        this.eventRegistry = new EventRegistry();

        eventRegistry.registerEvents(eventHandler);

        InetSocketAddress address = new InetSocketAddress(host, port);

        MessageHandler.sendConsoleMessage("Starting LoadlyServer on " + address.toString(), MessageHandler.MessageType.INFO);

        LoadlyUUID loadlyServerUUID = LoadlyUUID.LoadlyUUIDS.SERVER.get();
        this.loadlyServer = new LoadlyServer(address, packetRegistry, future -> {}, loadlyServerUUID, eventRegistry);

        MessageHandler.sendConsoleMessage("LoadlyServer started with Server ID: " + loadlyServerUUID.getUuid().toString(), MessageHandler.MessageType.INFO);
        MessageHandler.sendConsoleMessage("LoadlyServer ID 00000000-0000-0000-0000-000000000000 is reserved for the server itself, this is not an ERROR!", MessageHandler.MessageType.WARNING);
    }

    public LoadlyServer getLoadlyServer() {
        return loadlyServer;
    }

    public EventRegistry getEventRegistry() {
        return eventRegistry;
    }

    public LoadlyPacketRegistry getPacketRegistry() {
        return packetRegistry;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public static LoadlyBukkit getInstance() {
        return instance;
    }
}
