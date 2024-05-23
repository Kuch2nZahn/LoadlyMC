package io.github.kuchenzahn.packet;


import io.github.kuchenzahn.LoadlyUUID;
import io.github.kuchenzahn.io.Decoder;
import io.github.kuchenzahn.io.Encoder;

import java.util.concurrent.ThreadLocalRandom;

public abstract class LoadlyPacket implements Encoder, Decoder {

    private long sessionId = ThreadLocalRandom.current().nextLong();
    private LoadlyUUID packetReceiverUUID;
    private LoadlyUUID senderUUID;

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public void setPacketReceiverUUID(LoadlyUUID packetReceiverUUID) {
        this.packetReceiverUUID = packetReceiverUUID;
    }

    public void setSenderUUID(LoadlyUUID senderUUID) {
        this.senderUUID = senderUUID;
    }

    public LoadlyUUID getSenderUUID() {
        return senderUUID;
    }

    public LoadlyUUID getPacketReceiverUUID() {
        return packetReceiverUUID;
    }

    public long getSessionId() {
        return sessionId;
    }
}