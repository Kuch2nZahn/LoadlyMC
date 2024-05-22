package io.github.kuchenzahn.response;

import io.github.kuchenzahn.packet.LoadlyPacket;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class PendingResponse<T extends LoadlyPacket> {

    private final Long sent;
    private final Consumer<T> responseCallable;
    private final long timeout;

    public PendingResponse(Class<T> type, Consumer<T> responseCallable) {
        this(type, responseCallable, TimeUnit.SECONDS.toMillis(10));
    }

    public PendingResponse(Class<T> type, Consumer<T> responseCallable, long timeout) {
        this.timeout = timeout;
        this.sent = System.currentTimeMillis();
        this.responseCallable = responseCallable;
    }

    public void callResponseReceived(LoadlyPacket loadlyPacket) {
        responseCallable.accept((T) loadlyPacket);
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - sent > timeout;
    }

}
