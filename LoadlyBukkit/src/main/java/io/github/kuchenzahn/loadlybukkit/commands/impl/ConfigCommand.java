package io.github.kuchenzahn.loadlybukkit.commands.impl;

import io.github.kuchenzahn.LoadlyUUID;
import io.github.kuchenzahn.loadlybukkit.commands.PlayerRequieredICommand;
import io.github.kuchenzahn.loadlybukkit.config.ConfigManager;
import io.github.kuchenzahn.loadlybukkit.message.Message;
import io.github.kuchenzahn.loadlybukkit.message.MessageHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigCommand implements PlayerRequieredICommand {


    @Override
    public String name() {
        return "config";
    }

    @Override
    public String help() {
        return "Manage the config of LoadlyBukkit";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(args.length == 0) return false;

        LoadlyUUID messageReceiverUUID = (sender instanceof Player) ? LoadlyUUID.fromUUID(((Player) sender).getUniqueId()) : LoadlyUUID.LoadlyUUIDS.SERVER.get();
        MessageHandler.MessageReceiver receiver = MessageHandler.MessageReceiver.forPlayer(messageReceiverUUID);

        switch (args[0]) {
            case "reload":
                ConfigManager.getInstance().reloadConfig();
                Message configReloadedMessage = new Message("Config reloaded", MessageHandler.MessageType.INFO, receiver);
                MessageHandler.addMessageToQueue(configReloadedMessage);
                return true;
            case "save":
                ConfigManager.save();
                Message configSavedMessage = new Message("Config saved", MessageHandler.MessageType.INFO, receiver);
                MessageHandler.addMessageToQueue(configSavedMessage);
                return true;
        }

        return false;
    }


    @Override
    public HashMap<Integer, List<String>> getTabCompletions() {
        HashMap<Integer, List<String>> completions = new HashMap<>();
        completions.put(0, Arrays.asList("reload", "save"));
        return completions;
    }
}
