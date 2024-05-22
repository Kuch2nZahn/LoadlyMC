package io.github.kuchenzahn.io;


import io.github.kuchenzahn.buffer.PacketBuffer;

public interface Encoder {

    void write(PacketBuffer buffer);

}
