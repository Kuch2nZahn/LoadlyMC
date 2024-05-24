package io.github.kuchenzahn.registry;

import io.github.kuchenzahn.exception.PacketRegistrationException;
import io.github.kuchenzahn.packet.impl.LoadlyHelloC2SPacket;
import io.github.kuchenzahn.packet.impl.LoadlyHelloS2CPacket;

public class LoadlyPacketRegistry extends SimplePacketRegistry{
    public LoadlyPacketRegistry(){
        super();

        try {
            registerPacket(1, LoadlyHelloC2SPacket.class);
            registerPacket(2, LoadlyHelloS2CPacket.class);
        } catch (PacketRegistrationException exception){
            exception.printStackTrace();
        }
    }
}
