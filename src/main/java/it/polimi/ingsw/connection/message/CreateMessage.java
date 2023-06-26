package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.model.GameMode;

/**
 * The CreateMessage class represents a message for creating a game.
 * It implements the Sendable interface.
 */
public class CreateMessage implements Sendable {

    /**
     * The name of the game creator.
     */
    public final String name;

    /**
     * The game mode of the created game.
     */
    public final GameMode gameMode;

    /**
     * The number of players in the created game.
     */
    public final int numPlayers;

    /**
     * The ID of the game.
     */
    public final int gameID;

    /**
     * Constructs a CreateMessage object with the specified game creator name, game mode, and number of players.
     *
     * @param name       the name of the game creator
     * @param gameMode   the game mode of the created game
     * @param numPlayers the number of players in the created game
     */
    public CreateMessage(String name, GameMode gameMode, int numPlayers) {
        this.name = name;
        this.gameMode = gameMode;
        this.numPlayers = numPlayers;
        this.gameID = 0;
    }

    /**
     * Constructs a CreateMessage object with the specified game mode, game ID, and number of players.
     *
     * @param gameMode   the game mode of the created game
     * @param gameID     the ID of the game
     * @param numPlayers the number of players in the created game
     */
    public CreateMessage(GameMode gameMode, int gameID, int numPlayers) {
        this.name = null;
        this.gameMode = gameMode;
        this.numPlayers = numPlayers;
        this.gameID = gameID;
    }

    /**
     * Retrieves the message header associated with the CreateMessage.
     *
     * @return the message header
     */
    @Override
    public MessageHeader getHeader() {
        return MessageHeader.CREATE;
    }
}
