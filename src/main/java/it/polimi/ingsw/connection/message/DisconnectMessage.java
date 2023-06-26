package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

/**
 * The DisconnectMessage class represents a message for notifying a disconnection.
 * It implements the Sendable interface.
 */
public class DisconnectMessage implements Sendable {

    /**
     * The name of the disconnected player.
     */
    public final String name;

    /**
     * Constructs a DisconnectMessage object with the specified player name.
     *
     * @param name the name of the disconnected player
     */
    public DisconnectMessage(String name) {
        this.name = name;
    }

    /**
     * Retrieves the message header associated with the DisconnectMessage.
     *
     * @return the message header
     */
    @Override
    public MessageHeader getHeader() {
        return MessageHeader.DISCONNECTION;
    }
}
