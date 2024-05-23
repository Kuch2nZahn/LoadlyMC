package io.github.kuchenzahn.loadlybukkit.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ICommand {
    String name();
    String help();
    boolean playerRequired();

    boolean execute(CommandSender sender, String[] args);
    HashMap<Integer, List<String>> getTabCompletions();
}
