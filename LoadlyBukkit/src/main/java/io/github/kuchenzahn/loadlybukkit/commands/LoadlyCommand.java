package io.github.kuchenzahn.loadlybukkit.commands;

import io.github.kuchenzahn.loadlybukkit.LoadlyBukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LoadlyCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CommandManager commandManager = LoadlyBukkit.getInstance().getCommandManager();
        commandManager.onExecution(sender, args, label);
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        int position = args.length;
        CommandManager commandManager = LoadlyBukkit.getInstance().getCommandManager();

        if(args.length == 0){
            return commandManager.getCommandCompletions(label, position);
        }
        return commandManager.getCommandCompletions(args[0], position);
    }
}
