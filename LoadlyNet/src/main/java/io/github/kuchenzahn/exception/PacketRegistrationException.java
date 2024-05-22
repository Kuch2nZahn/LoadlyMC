package io.github.kuchenzahn.exception;

public class PacketRegistrationException extends Exception {

    public PacketRegistrationException(String message) {
        super(message);
    }

    public PacketRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
