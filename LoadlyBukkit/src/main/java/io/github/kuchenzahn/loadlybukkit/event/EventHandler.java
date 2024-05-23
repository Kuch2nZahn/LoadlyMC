package io.github.kuchenzahn.loadlybukkit.event;

import io.github.kuchenzahn.LoadlyUUID;
import io.github.kuchenzahn.event.PacketSubscriber;
import io.github.kuchenzahn.loadlybukkit.config.ConfigManager;
import io.github.kuchenzahn.loadlybukkit.message.Message;
import io.github.kuchenzahn.loadlybukkit.message.MessageHandler;
import io.github.kuchenzahn.packet.impl.LoadlyHelloC2SPacket;
import io.netty.channel.ChannelHandlerContext;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EventHandler {
    @PacketSubscriber
    public void onLoadlyHelloPacket(LoadlyHelloC2SPacket packet, ChannelHandlerContext ctx){
        Player player = Bukkit.getPlayer(packet.getSenderUUID().getUuid());

        MessageHandler.sendConsoleMessage(String.format("Player %s has connected to the Loadly server and is using Loadly!", player.getName()), MessageHandler.MessageType.INFO);

        Object o = ConfigManager.get(ConfigManager.ConfigParam.AUTOMATIC_JOIN_MESSAGE);
        if(o instanceof Boolean && (Boolean) o){
            String content = String.format((String) ConfigManager.get(ConfigManager.ConfigParam.AUTOMATIC_JOIN_MESSAGE_TEXT), player.getName());
            MessageHandler.MessageDisplay display = MessageHandler.MessageDisplay.valueOf((String) ConfigManager.get(ConfigManager.ConfigParam.AUTOMATIC_JOIN_MESSAGE_DISPLAY)) == null ? MessageHandler.MessageDisplay.ACTIONBAR : MessageHandler.MessageDisplay.ACTIONBAR;
            int priority = (int) ConfigManager.get(ConfigManager.ConfigParam.AUTOMATIC_JOIN_MESSAGE_PRIORITY);
            MessageHandler.MessageType type = MessageHandler.MessageType.valueOf((String) ConfigManager.get(ConfigManager.ConfigParam.AUTOMATIC_JOIN_MESSAGE_TYPE)) == null ? MessageHandler.MessageType.INFO : MessageHandler.MessageType.INFO;


            Message joinMessage = new Message(content, priority, display, type, MessageHandler.MessageReceiver.PLAYER.setUUID(LoadlyUUID.fromUUID(player.getUniqueId())), 20);
            MessageHandler.addMessageToQueue(joinMessage);
        }
    }
}
