package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

/**
 * The LastRoundMessage class represents a message indicating that it is the last round of the game.
 * It is used to notify clients about the last round of the game.
 */
public class LastRoundMessage implements Sendable {

    /**
     * The name of the player for whom it is the last round.
     */
    public String name;

    /**
     * Constructs a LastRoundMessage object with the specified player name.
     *
     * @param name the name of the player for whom it is the last round
     */
    public LastRoundMessage(String name) {
        this.name = name;
    }

    /**
     * Retrieves the message header associated with the LastRoundMessage.
     *
     * @return the message header
     */
    @Override
    public MessageHeader getHeader() {
        return MessageHeader.LASTROUND;
    }
}

