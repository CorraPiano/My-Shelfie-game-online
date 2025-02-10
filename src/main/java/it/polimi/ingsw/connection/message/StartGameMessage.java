package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

/**
 * The StartGameMessage class represents a message indicating the start of the game.
 * It is a notification sent from the server to the client.
 */
public class StartGameMessage implements Sendable {

    /**
     * Constructs a StartGameMessage object.
     */
    public StartGameMessage() {
    }

    /**
     * Retrieves the message header associated with the StartGameMessage.
     *
     * @return the message header
     */
    @Override
    public MessageHeader getHeader() {
        return MessageHeader.STARTGAME;
    }
    public Boolean isRecurrentUpdate(){
        return false;
    }
}