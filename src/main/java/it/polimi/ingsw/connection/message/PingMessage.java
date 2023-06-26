package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

/**
 * The PingMessage class represents a message used for ping communication between the client and server.
 * It contains an integer value representing the ping value.
 */
public class PingMessage implements Sendable {

    /**
     * The ping value.
     */
    public final int n;

    /**
     * Constructs a PingMessage object with the specified ping value.
     *
     * @param n the ping value
     */
    public PingMessage(int n) {
        this.n = n;
    }

    /**
     * Retrieves the message header associated with the PingMessage.
     *
     * @return the message header
     */
    public MessageHeader getHeader() {
        return MessageHeader.PING;
    }
}