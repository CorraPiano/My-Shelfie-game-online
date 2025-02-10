package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

/**
 * The LeaveMessage class represents a message for leaving the game.
 * It is used to request a player to leave the game or to notify other clients that a player has left the game.
 */
public class LeaveMessage implements Sendable {

    /**
     * The name of the player leaving the game. Null if the message is used to request a player to leave.
     */
    public final String name;

    /**
     * Constructs a LeaveMessage object without specifying a player name (used for requesting a player to leave).
     */
    public LeaveMessage() {
        this.name = null;
    }

    /**
     * Constructs a LeaveMessage object with the specified player name (used for notifying a player leaving).
     *
     * @param name the name of the player leaving the game
     */
    public LeaveMessage(String name) {
        this.name = name;
    }

    /**
     * Retrieves the message header associated with the LeaveMessage.
     *
     * @return the message header
     */
    @Override
    public MessageHeader getHeader() {
        return MessageHeader.LEAVE;
    }
    public Boolean isRecurrentUpdate(){
        return false;
    }
}