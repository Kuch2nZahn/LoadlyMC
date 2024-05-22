package io.github.kuchenzahn.registry;

import io.github.kuchenzahn.packet.LoadlyPacket;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RegisteredPacket {

    private final Class<? extends LoadlyPacket> packetClass;
    private final Constructor<? extends LoadlyPacket> constructor;

    public RegisteredPacket(Class<? extends LoadlyPacket> packetClass) throws NoSuchMethodException {
        this.packetClass = packetClass;

        List<Constructor<?>> emptyConstructorList = Arrays.stream(packetClass.getConstructors())
                .filter(constructor -> constructor.getParameterCount() == 0).collect(Collectors.toList());
        if (emptyConstructorList.size() == 0) {
            throw new NoSuchMethodException("Packet is missing no-args-constructor");
        }
        this.constructor = (Constructor<? extends LoadlyPacket>) emptyConstructorList.get(0);
    }

    public Class<? extends LoadlyPacket> getPacketClass() {
        return packetClass;
    }

    public Constructor<? extends LoadlyPacket> getConstructor() {
        return constructor;
    }
}
