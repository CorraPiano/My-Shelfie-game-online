package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.Token;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The `LocalCommonCard` class represents a common goal card used to send messages over the network.
 * It stores the type of the card and the list of tokens associated with it.
 */
public class LocalCommonCard implements Serializable, Sendable {

    public final ArrayList<Token> tokenList;
    public final int type;

    /**
     * Constructs a `LocalCommonCard` object with the specified type and token list.
     *
     * @param type       The type of the common goal card.
     * @param tokenList  The list of tokens associated with the common goal card.
     */
    public LocalCommonCard(int type, ArrayList<Token> tokenList) {
        this.type = type;
        this.tokenList = tokenList;
    }

    /**
     * Returns the type of the common goal card.
     *
     * @return The type of the common goal card.
     */
    public int getType() {
        return type;
    }

    /**
     * Returns the list of tokens associated with the common goal card.
     *
     * @return The list of tokens.
     */
    public ArrayList<Token> showToken() {
        return tokenList;
    }

    /**
     * Returns the message header associated with the `LocalCommonCard` object.
     *
     * @return The message header.
     */
    public MessageHeader getHeader() {
        return MessageHeader.COMMONGOALCARD;
    }
    public Boolean isRecurrentUpdate(){
        return false;
    }
}