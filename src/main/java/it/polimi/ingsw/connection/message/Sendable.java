package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

/**
 * The Sendable interface represents a message that can be sent between client and server.
 * It provides a method to retrieve the message header.
 */
public interface Sendable {

    /**
     * Retrieves the message header associated with the Sendable object.
     *
     * @return the message header
     */
    MessageHeader getHeader();
}