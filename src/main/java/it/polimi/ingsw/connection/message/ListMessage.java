package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.model.GameMode;

import java.util.ArrayList;

/**
 * The ListMessage class represents a message for retrieving the list of available games.
 * It is used to request the server to send the list of games or to notify clients about the available games.
 */
public class ListMessage implements Sendable {

    /**
     * The list of available games.
     */
    public final ArrayList<LocalGame> gamesList;

    /**
     * Constructs a ListMessage object without specifying the list of games (used for requesting the list).
     */
    public ListMessage() {
        this.gamesList = null;
    }

    /**
     * Constructs a ListMessage object with the specified list of games (used for notifying the list).
     *
     * @param gamesList the list of available games
     */
    public ListMessage(ArrayList<LocalGame> gamesList) {
        this.gamesList = gamesList;
    }

    /**
     * Retrieves the message header associated with the ListMessage.
     *
     * @return the message header
     */
    @Override
    public MessageHeader getHeader() {
        return MessageHeader.LIST;
    }
    public Boolean isRecurrentUpdate(){
        return false;
    }
}