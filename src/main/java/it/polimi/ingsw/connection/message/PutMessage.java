package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

/**
 * The PutMessage class represents a message used to notify the server about a player's move to put an item in a column.
 * It contains the column number and the player's name associated with the move.
 */
public class PutMessage implements Sendable {

    /**
     * The column number where the item is put.
     */
    public final int column;

    /**
     * The name of the player performing the move.
     */
    public final String name;

    /**
     * Constructs a PutMessage object with the specified column number.
     *
     * @param column the column number where the item is put
     */
    public PutMessage(int column) {
        this.column = column;
        this.name = null;
    }

    /**
     * Constructs a PutMessage object with the specified column number and player's name.
     *
     * @param column the column number where the item is put
     * @param name   the name of the player performing the move
     */
    public PutMessage(int column, String name) {
        this.column = column;
        this.name = name;
    }

    /**
     * Retrieves the message header associated with the PutMessage.
     *
     * @return the message header
     */
    @Override
    public MessageHeader getHeader() {
        return MessageHeader.PUT;
    }
    public Boolean isRecurrentUpdate(){
        return false;
    }
}
