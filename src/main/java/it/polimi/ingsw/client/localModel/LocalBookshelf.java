package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.util.Constants;

import java.io.Serializable;

/**
 * The `LocalBookshelf` class represents the bookshelf data structure used to send messages over the network.
 * It stores the name of the bookshelf owner and the arrangement of items on the bookshelf.
 */
public class LocalBookshelf implements Serializable, Sendable {

    public final String name;
    public final Item[][] bookshelf;

    /**
     * Constructs a `LocalBookshelf` object with the specified name and bookshelf arrangement.
     *
     * @param name      The name of the bookshelf owner.
     * @param bookshelf The two-dimensional array representing the arrangement of items on the bookshelf.
     */
    public LocalBookshelf(String name, Item[][] bookshelf) {
        this.name = name;
        this.bookshelf = bookshelf;
    }

    /**
     * Constructs a `LocalBookshelf` object with the specified name and initializes an empty bookshelf.
     *
     * @param name The name of the bookshelf owner.
     */
    public LocalBookshelf(String name) {
        this.name = name;
        this.bookshelf = new Item[Constants.nRowBookshelf][Constants.nColumnBookshelf];
    }

    /**
     * Returns the message header associated with the `LocalBookshelf` object.
     *
     * @return The message header.
     */
    public MessageHeader getHeader() {
        return MessageHeader.BOOKSHELF;
    }
    public Boolean isRecurrentUpdate(){
        return true;
    }
}