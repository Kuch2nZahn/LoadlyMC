package io.github.kuchenzahn.io;


import io.github.kuchenzahn.buffer.PacketBuffer;

public interface CallableEncoder<T> {

    void write(T data, PacketBuffer buffer);

}
