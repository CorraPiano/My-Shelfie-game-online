package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * The GameState enum represents the different states of the game.
 */
public enum GameState implements Serializable {

    /**
     * Waiting state, indicating that the game is waiting for players to join or start.
     */
    WAIT,

    /**
     * Active game state, indicating that the game is currently being played.
     */
    GAME,

    /**
     * End state, indicating that the game has ended.
     */
    END,
}