package io.github.kuchenzahn.handler;

import io.github.kuchenzahn.LoadlyUUID;
import io.github.kuchenzahn.packet.LoadlyPacket;
import io.github.kuchenzahn.buffer.PacketBuffer;
import io.github.kuchenzahn.registry.IPacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<LoadlyPacket> {
    private final LoadlyUUID uuid;

    private final IPacketRegistry packetRegistry;

    public PacketEncoder(IPacketRegistry packetRegistry, LoadlyUUID uuid) {
        this.packetRegistry = packetRegistry;
        this.uuid = uuid;
    }

    protected void encode(ChannelHandlerContext channelHandlerContext, LoadlyPacket loadlyPacket, ByteBuf byteBuf) throws Exception {
        int packetId = packetRegistry.getPacketId(loadlyPacket.getClass());
        if (packetId < 0) {
            throw new EncoderException("Returned PacketId by registry is < 0");
        }
        byteBuf.writeInt(packetId);
        byteBuf.writeLong(loadlyPacket.getSessionId());

        PacketBuffer buffer = new PacketBuffer();
        buffer.writeUUID(loadlyPacket.getPacketReceiverUUID().getUuid());
        buffer.writeUUID(uuid.getUuid());
        loadlyPacket.write(buffer);
        byteBuf.writeBytes(buffer);
    }

}