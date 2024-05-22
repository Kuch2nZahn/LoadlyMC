package io.github.kuchenzahn.packet.impl;

import io.github.kuchenzahn.buffer.PacketBuffer;
import io.github.kuchenzahn.packet.LoadlyPacket;

public class LoadlyHelloC2SPacket extends LoadlyPacket {
    private String message;

    public LoadlyHelloC2SPacket(){

    }

    public LoadlyHelloC2SPacket(String message){
        this.message = message;
    }

    @Override
    public void read(PacketBuffer buffer) {
        this.message = buffer.readUTF8();
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeUTF8(message);
    }

    public String getMessage() {
        return message;
    }
}
