package io.github.kuchenzahn.io;

import io.github.kuchenzahn.packet.LoadlyPacket;
import io.netty.channel.ChannelOutboundInvoker;

public interface Responder {

    void respond(LoadlyPacket loadlyPacket);

    static Responder forId(Long sessionId, ChannelOutboundInvoker channelOutboundInvoker) {
        return packet -> {
            packet.setSessionId(sessionId);
            channelOutboundInvoker.writeAndFlush(packet);
        };
    }

}
