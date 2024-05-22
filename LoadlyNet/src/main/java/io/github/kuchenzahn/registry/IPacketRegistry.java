package io.github.kuchenzahn.registry;


import io.github.kuchenzahn.packet.LoadlyPacket;
import io.github.kuchenzahn.exception.PacketRegistrationException;

import java.lang.reflect.InvocationTargetException;

public interface IPacketRegistry {

    void registerPacket(int packetId, LoadlyPacket loadlyPacket) throws PacketRegistrationException;

    void registerPacket(int packetId, Class<? extends LoadlyPacket> packet) throws PacketRegistrationException;

    int getPacketId(Class<? extends LoadlyPacket> packetClass);

    <T extends LoadlyPacket> T constructPacket(int packetId) throws InvocationTargetException, InstantiationException, IllegalAccessException;

    boolean containsPacketId(int id);

}