package io.github.kuchenzahn.loadlybukkit.commands;

import io.github.kuchenzahn.loadlybukkit.commands.impl.ConfigCommand;
import io.github.kuchenzahn.loadlybukkit.config.ConfigManager;
import io.github.kuchenzahn.loadlybukkit.message.Message;
import io.github.kuchenzahn.loadlybukkit.message.MessageHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandManager {
    private ArrayList<ICommand> commands;

    public CommandManager(){
        commands = new ArrayList<>();

        commands.add(new ConfigCommand());
    }

    public void onExecution(CommandSender sender, String[] args, String label){
        for(ICommand command : commands){
            if(command.name().equalsIgnoreCase(label)){
                boolean executed = false;

                if(command.playerRequired() && !(sender instanceof Player)){
                    String errorMessage = (String) ConfigManager.get(ConfigManager.ConfigParam.MESSAGE_COMMAND_PLAYER_REQUIRED);
                    MessageHandler.addMessageToQueue(new Message(errorMessage, MessageHandler.MessageType.ERROR, MessageHandler.MessageReceiver.CONSOLE));
                    return;
                } else {
                    executed = command.execute(sender, args);
                }

                executed = command.execute((Player) sender, args);

                if(!executed){
                    String errorMessage = command.help();
                    MessageHandler.MessageReceiver receiver = command.playerRequired() ? MessageHandler.MessageReceiver.forPlayer(((Player) sender).getUniqueId()) : MessageHandler.MessageReceiver.CONSOLE;
                    MessageHandler.addMessageToQueue(new Message(errorMessage, MessageHandler.MessageType.ERROR, receiver));
                }
            }
        }
    }

    public List<String> getCommandCompletions(String label, int positon){
        if(positon <= 1){
            ArrayList<String> completions = new ArrayList<>();
            for(ICommand command : commands){
                completions.add(command.name());
            }

            return completions;
        }

        for(ICommand command : commands){
            if(command.name().equalsIgnoreCase(label)){
                HashMap<Integer, List<String>> tabCompletions = command.getTabCompletions();
                return tabCompletions.get(positon - 2);
            }
        }

        return null;
    }
}
