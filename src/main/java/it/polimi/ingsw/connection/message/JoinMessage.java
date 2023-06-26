package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

/**
 * The JoinMessage class represents a message sent by a client to join a game.
 * It implements the Sendable interface.
 */
public class JoinMessage implements Sendable {

    /**
     * The name of the player joining the game.
     */
    public final String name;

    /**
     * The ID of the game to join.
     * If set to -1, it indicates joining any available game.
     */
    public final int gameID;

    /**
     * Constructs a JoinMessage object with the specified name and game ID.
     *
     * @param name   The name of the player joining the game.
     * @param gameID The ID of the game to join.
     */
    public JoinMessage(String name, int gameID) {
        this.name = name;
        this.gameID = gameID;
    }

    /**
     * Constructs a JoinMessage object with the specified name.
     * The game ID is set to -1, indicating joining any available game.
     *
     * @param name The name of the player joining the game.
     */
    public JoinMessage(String name) {
        this.name = name;
        this.gameID = -1;
    }

    /**
     * Returns the message header of the JoinMessage, which is MessageHeader.JOIN.
     *
     * @return The message header.
     */
    public MessageHeader getHeader() {
        return MessageHeader.JOIN;
    }
}
