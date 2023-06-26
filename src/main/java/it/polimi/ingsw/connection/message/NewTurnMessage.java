package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

/**
 * The NewTurnMessage class represents a message notifying clients that a new turn has started.
 * It contains the name of the player whose turn it is.
 */
public class NewTurnMessage implements Sendable {

    /**
     * The name of the player whose turn it is.
     */
    public String name;

    /**
     * Constructs a NewTurnMessage object with the specified player name.
     *
     * @param name the name of the player whose turn it is
     */
    public NewTurnMessage(String name) {
        this.name = name;
    }

    /**
     * Retrieves the message header associated with the NewTurnMessage.
     *
     * @return the message header
     */
    @Override
    public MessageHeader getHeader() {
        return MessageHeader.NEWTURN;
    }
}
