package it.polimi.ingsw.client;

/**
 * The ClientPhase enum represents the phases that a client can be in during the game.
 */
public enum ClientPhase {
    /**
     * The client is in the "HOME" phase, indicating that it is at the home screen.
     */
    HOME,

    /**
     * The client is in the "LOBBY" phase, indicating that it is in the lobby before the game starts.
     */
    LOBBY,

    /**
     * The client is in the "GAME" phase, indicating that it is currently playing the game.
     */
    GAME,

    /**
     * The client is in the "CHAT" phase, indicating that it is participating in the chat during the game.
     */
    CHAT,

    /**
     * The client is in the "HOME_RECONNECTION" phase, indicating that it is attempting to reconnect to the home screen.
     */
    HOME_RECONNECTION,

    /**
     * The client is in the "MATCH_RECONNECTION" phase, indicating that it is attempting to reconnect to his match.
     */
    MATCH_RECONNECTION,

    /**
     * The client is in the "CLOSE" phase, indicating that it is closing or has closed the application.
     */
    CLOSE;
}
