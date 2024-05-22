package io.github.kuchenzahn.io;


import io.github.kuchenzahn.buffer.PacketBuffer;

public interface Decoder {

    void read(PacketBuffer buffer);

}
