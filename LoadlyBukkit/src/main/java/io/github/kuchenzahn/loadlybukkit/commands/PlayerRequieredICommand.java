package io.github.kuchenzahn.loadlybukkit.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface PlayerRequieredICommand extends ICommand{
    default boolean playerRequired() {
        return true;
    }
}
