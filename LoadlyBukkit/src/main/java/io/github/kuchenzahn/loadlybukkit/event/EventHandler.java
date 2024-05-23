package io.github.kuchenzahn.loadlybukkit.event;

import io.github.kuchenzahn.event.PacketSubscriber;
import io.github.kuchenzahn.packet.impl.LoadlyHelloC2SPacket;
import io.netty.channel.ChannelHandlerContext;

public class EventHandler {
    @PacketSubscriber
    public void onLoadlyHelloPacket(LoadlyHelloC2SPacket packet, ChannelHandlerContext ctx){
        System.out.println("Received hello packet");
    }
}
