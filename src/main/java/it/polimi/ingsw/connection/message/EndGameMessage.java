package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

/**
 * The EndGameMessage class represents a message indicating the end of a game.
 * It implements the Sendable interface.
 */
public class EndGameMessage implements Sendable {

    /**
     * The name of the player associated with the end of the game.
     */
    public final String name;

    /**
     * The cause of the game's end.
     */
    public final EndCause cause;

    /**
     * Constructs an EndGameMessage object with the specified player name and end cause.
     *
     * @param name  The name of the player associated with the end of the game.
     * @param cause The cause of the game's end.
     */
    public EndGameMessage(String name, EndCause cause) {
        this.name = name;
        this.cause = cause;
    }

    /**
     * Returns the message header of the EndGameMessage, which is MessageHeader.ENDGAME.
     *
     * @return The message header.
     */
    @Override
    public MessageHeader getHeader() {
        return MessageHeader.ENDGAME;
    }
}