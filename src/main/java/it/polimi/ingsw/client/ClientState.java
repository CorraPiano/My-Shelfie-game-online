package it.polimi.ingsw.client;

/**
 * The ClientState enum represents the states that a client can be in.
 */
public enum ClientState {

    /**
     * The client is in the "WAIT" state, indicating that it is waiting for some action or event.
     */
    WAIT,

    /**
     * The client is in the "READY" state, indicating that it is ready to perform an action or proceed to the next step.
     */
    READY;
}
