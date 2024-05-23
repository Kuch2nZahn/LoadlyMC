package io.github.kuchenzahn.loadlybukkit.commands.impl;

import io.github.kuchenzahn.loadlybukkit.commands.ICommand;
import io.github.kuchenzahn.loadlybukkit.commands.PlayerRequieredICommand;
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
        return false;
    }


    @Override
    public HashMap<Integer, List<String>> getTabCompletions() {
        HashMap<Integer, List<String>> completions = new HashMap<>();
        completions.put(0, Arrays.asList("reload", "save"));
        return completions;
    }
}
