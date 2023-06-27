package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.GameState;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The `LocalGame` class represents a local game in the client's game model.
 * It stores information about the game, such as the game mode, game ID, maximum number of players,
 * current number of players, and the game state.
 */
public class LocalGame implements Serializable, Sendable {

    public final GameMode gameMode;
    public final int gameID;
    public final int maxPerson;
    public final int currPerson;
    public final GameState gameState;

    /**
     * Constructs a `LocalGame` object with the specified game mode, game ID, maximum number of players,
     * current number of players, and game state.
     *
     * @param gameMode     The game mode of the local game.
     * @param gameID       The unique identifier of the local game.
     * @param maxPerson    The maximum number of players allowed in the local game.
     * @param currPerson   The current number of players in the local game.
     * @param gameState    The state of the local game.
     */
    public LocalGame(GameMode gameMode, int gameID, int maxPerson, int currPerson, GameState gameState) {
        this.gameMode = gameMode;
        this.gameID = gameID;
        this.maxPerson = maxPerson;
        this.currPerson = currPerson;
        this.gameState = gameState;
    }

    /**
     * Returns a string representation of the `LocalGame` object.
     *
     * @return A string representation of the local game.
     */
    @Override
    public String toString() {
        return String.format("ID: %s, N° PLAYER: %s, N° CURRENT PLAYER: %s, MODE: %s, STATUS: %s", gameID, maxPerson, currPerson, gameMode, gameState);
    }

    /**
     * Returns the message header associated with the `LocalGame` object.
     *
     * @return The message header.
     */
    public MessageHeader getHeader() {
        return MessageHeader.GAME;
    }
    public Boolean isRecurrentUpdate(){
        return false;
    }
}

