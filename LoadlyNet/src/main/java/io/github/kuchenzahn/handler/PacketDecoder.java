package io.github.kuchenzahn.handler;

import io.github.kuchenzahn.LoadlyUUID;
import io.github.kuchenzahn.packet.LoadlyPacket;
import io.github.kuchenzahn.buffer.PacketBuffer;
import io.github.kuchenzahn.registry.IPacketRegistry;
import io.github.kuchenzahn.utils.UUIDUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;

import java.util.List;
import java.util.UUID;

public class PacketDecoder extends ByteToMessageDecoder {

    private final IPacketRegistry packetRegistry;
    private final LoadlyUUID uuid;

    public PacketDecoder(IPacketRegistry packetRegistry, LoadlyUUID uuid) {
        this.packetRegistry = packetRegistry;
        this.uuid = uuid;
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int packetId = byteBuf.readInt();
        if (!packetRegistry.containsPacketId(packetId)) {
            throw new DecoderException("Received invalid packet id");
        }
        long sessionId = byteBuf.readLong();
        PacketBuffer buffer = new PacketBuffer(byteBuf.readBytes(byteBuf.readableBytes()));
        UUID receiverUUID = buffer.readUUID();

        System.out.println("receiverUUID = " + receiverUUID);

        if (receiverUUID.equals(LoadlyUUID.LoadlyUUIDS.ALL_PLAYERS.getUUID()) || receiverUUID.equals(LoadlyUUID.LoadlyUUIDS.EVERYONE.getUUID()) || receiverUUID.equals(uuid.getUuid())){
            LoadlyPacket loadlyPacket = packetRegistry.constructPacket(packetId);
            loadlyPacket.setSessionId(sessionId);
            loadlyPacket.read(buffer);

            list.add(loadlyPacket);
        }
    }

}