package io.github.kuchenzahn.registry;

import io.github.kuchenzahn.packet.LoadlyPacket;
import io.github.kuchenzahn.exception.PacketRegistrationException;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;

import java.lang.reflect.InvocationTargetException;

public class SimplePacketRegistry implements IPacketRegistry {

    private final Int2ObjectOpenHashMap<RegisteredPacket> packets = new Int2ObjectOpenHashMap<>();

    @Override
    public void registerPacket(int packetId, LoadlyPacket loadlyPacket) throws PacketRegistrationException {
        registerPacket(packetId, loadlyPacket.getClass());
    }

    @Override
    public void registerPacket(int packetId, Class<? extends LoadlyPacket> packet) throws PacketRegistrationException {
        if (containsPacketId(packetId)) {
            throw new PacketRegistrationException("PacketID is already in use");
        }
        try {
            RegisteredPacket registeredPacket = new RegisteredPacket(packet);
            this.packets.put(packetId, registeredPacket);
        } catch (NoSuchMethodException e) {
            throw new PacketRegistrationException("Failed to register packet", e);
        }
    }

    @Override
    public int getPacketId(Class<? extends LoadlyPacket> packetClass) {
        for (ObjectIterator<Int2ObjectMap.Entry<RegisteredPacket>> it = packets.int2ObjectEntrySet().fastIterator(); it.hasNext(); ) {
            Int2ObjectMap.Entry<RegisteredPacket> entry = it.next();
            if (entry.getValue().getPacketClass().equals(packetClass)) {
                return entry.getIntKey();
            }
        }
        return -1;
    }

    @Override
    public <T extends LoadlyPacket> T constructPacket(int packetId) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return (T) packets.get(packetId).getConstructor().newInstance();
    }

    @Override
    public boolean containsPacketId(int id) {
        return packets.containsKey(id);
    }

}
