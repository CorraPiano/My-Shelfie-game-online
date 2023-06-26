package it.polimi.ingsw.connection.message;

/**
 * The EndCause enum represents the various causes for the end of a game.
 * It specifies different reasons why a game may have ended.
 */
public enum EndCause {

    /**
     * Indicates that the last round of the game has finished.
     */
    LAST_ROUND_FINISHED,

    /**
     * Indicates that there were not enough active players to continue the game.
     */
    NOT_ENOUGH_ACTIVE_PLAYER,

    /**
     * Indicates that disconnected players did not reconnect within the specified time.
     */
    DISCONNECTED_PLAYERS_NOT_RECONNECT_ON_TIME;
}
