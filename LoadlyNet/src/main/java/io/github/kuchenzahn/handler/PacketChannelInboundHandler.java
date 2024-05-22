package io.github.kuchenzahn.handler;

import io.github.kuchenzahn.packet.LoadlyPacket;
import io.github.kuchenzahn.event.EventRegistry;
import io.github.kuchenzahn.response.RespondingPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PacketChannelInboundHandler extends SimpleChannelInboundHandler<LoadlyPacket> {

    private final EventRegistry eventRegistry;

    public PacketChannelInboundHandler(EventRegistry eventRegistry) {
        this.eventRegistry = eventRegistry;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoadlyPacket loadlyPacket) throws Exception {
        RespondingPacket.callReceive(loadlyPacket);
        eventRegistry.invoke(loadlyPacket, channelHandlerContext);
    }

}