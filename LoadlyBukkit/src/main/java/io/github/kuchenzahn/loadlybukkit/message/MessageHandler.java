package io.github.kuchenzahn.loadlybukkit.message;

import io.github.kuchenzahn.LoadlyUUID;
import io.github.kuchenzahn.loadlybukkit.LoadlyBukkit;
import io.github.kuchenzahn.loadlybukkit.config.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.UUID;

public class MessageHandler {
    public static String PREFIX = "§8[§6Loadly§8] §7";

    private PriorityQueue<Message> messageQueue;

    public MessageHandler() {
        messageQueue = new PriorityQueue<>(Comparator.comparingInt(Message::getPriority));

        new BukkitRunnable(){
            @Override
            public void run() {
                sendMessage();
            }
        }.runTaskTimer(LoadlyBukkit.getInstance(), 1, 20);
    }

    public void addMessage(Message message) {
        messageQueue.add(message);
    }

    public void sendMessage() {
            Message message = messageQueue.poll();
            if(message == null) return;
            MessageReceiver receiver = message.getReceiver();
            MessageType type = message.getType();

            switch (type){
                case INFO:
                    message.setContent("§a" + message.getContent());
                    break;
                case WARNING:
                    message.setContent("§e" + message.getContent());
                    break;
                case ERROR:
                    message.setContent("§c" + message.getContent());
                    break;
            }

            if(receiver == MessageReceiver.PLAYER){
                Player player = LoadlyBukkit.getInstance().getServer().getPlayer(message.getReceiver().uuid.getUuid());

                if(player != null){
                    switch (message.getDisplay()){
                        case CHAT:
                            player.sendMessage(PREFIX + message.getContent());
                            break;
                        case ACTIONBAR:
                            player.sendActionBar(message.getContent());
                            break;
                        case TITLE:
                            player.sendTitle(message.getContent(), "", 10, 70, 20);
                            break;
                        case SUBTITLE:
                            player.sendTitle("", message.getContent(), 10, 70, 20);
                            break;
                    }
                }

            } else if(receiver == MessageReceiver.CONSOLE){
                LoadlyBukkit.getInstance().getServer().getConsoleSender().sendMessage(PREFIX + message.getContent());
            } else if(receiver == MessageReceiver.ALL){
                for(Player player : LoadlyBukkit.getInstance().getServer().getOnlinePlayers()){
                    switch (message.getDisplay()){
                        case CHAT:
                            player.sendMessage(PREFIX + message.getContent());
                            break;
                        case ACTIONBAR:
                            player.sendActionBar(message.getContent());
                            break;
                        case TITLE:
                            player.sendTitle(message.getContent(), "", 10, 70, 20);
                            break;
                        case SUBTITLE:
                            player.sendTitle("", message.getContent(), 10, 70, 20);
                            break;
                    }
                }
            }
    }

    public static void addMessageToQueue(Message message){
        LoadlyBukkit.getInstance().getMessageHandler().addMessage(message);
    }

    public static void sendConsoleMessage(String content, int priority, MessageType type){
        addMessageToQueue(new Message(content, priority, MessageDisplay.CHAT, type, MessageReceiver.CONSOLE));
    }

    public static void sendConsoleMessage(String content, MessageType type){
        sendConsoleMessage(content, type.getPriority(), type);
    }


    public enum MessageType{
        INFO(0),
        WARNING(1),
        ERROR(2);

        private final int priority;

        MessageType(int priority){
            this.priority = priority;
        }

        public int getPriority(){
            return priority;
        }
    }

    public enum MessageDisplay{
        CHAT,
        ACTIONBAR,
        TITLE,
        SUBTITLE
    }

    public enum MessageReceiver{
        PLAYER(LoadlyUUID.LoadlyUUIDS.SPECIFIC.get()),
        CONSOLE(LoadlyUUID.LoadlyUUIDS.SERVER.get()),
        ALL(LoadlyUUID.LoadlyUUIDS.EVERYONE.get());

        private LoadlyUUID uuid;

        MessageReceiver(LoadlyUUID uuid){
            this.uuid = uuid;
        }

        public MessageReceiver setUUID(LoadlyUUID uuid){
            this.uuid = uuid;
            return this;
        }

        public static MessageReceiver forPlayer(UUID uuid){
            return PLAYER.setUUID(LoadlyUUID.fromUUID(uuid));
        }

        public static MessageReceiver forPlayer(LoadlyUUID uuid){
            return MessageReceiver.forPlayer(uuid.getUuid());
        }
    }
}
