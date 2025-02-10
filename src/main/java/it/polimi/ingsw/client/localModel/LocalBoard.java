package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.Item;

import java.io.Serializable;

/**
 * The `LocalBoard` class represents the local game board in the client's game model.
 * It stores the state of each cell on the board, represented by an array of `Item` objects.
 */
public class LocalBoard implements Serializable, Sendable {

    public final Item[][] board;

    /**
     * Constructs a `LocalBoard` object with the specified board state.
     *
     * @param board The state of the game board represented by a 2D array of `Item` objects.
     */
    public LocalBoard(Item[][] board) {
        this.board = board;
    }

    /**
     * Returns the message header associated with the `LocalBoard` object.
     *
     * @return The message header.
     */
    public MessageHeader getHeader() {
        return MessageHeader.BOARD;
    }
    public Boolean isRecurrentUpdate(){
        return true;
    }
}