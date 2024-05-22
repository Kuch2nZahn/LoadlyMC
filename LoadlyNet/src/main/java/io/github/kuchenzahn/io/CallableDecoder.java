package io.github.kuchenzahn.io;


import io.github.kuchenzahn.buffer.PacketBuffer;

public interface CallableDecoder<T> {

    T read(PacketBuffer buffer);

}
