package it.polimi.ingsw.model;

/**
 * The PlayerState enum represents the possible states of a player in the game.
 */
public enum PlayerState {
    /**
     * The player is active and participating in the game.
     */
    ACTIVE,

    /**
     * The player is disconnected from the game.
     */
    DISCONNECTED,

    /**
     * The player is inactive and not participating in the game.
     */
    INACTIVE;
}