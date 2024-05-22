package io.github.kuchenzahn.registry;

import io.github.kuchenzahn.exception.PacketRegistrationException;
import io.github.kuchenzahn.packet.impl.LoadlyHelloC2SPacket;

public class LoadlyPacketRegistry extends SimplePacketRegistry{
    public LoadlyPacketRegistry(){
        super();

        try {
            registerPacket(1, LoadlyHelloC2SPacket.class);
        } catch (PacketRegistrationException exception){
            exception.printStackTrace();
        }
    }
}
