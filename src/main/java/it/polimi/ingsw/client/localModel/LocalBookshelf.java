package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.Item;

import java.io.Serializable;

public class LocalBookshelf implements Serializable, Sendable {
    public final String name;
    public final Item[][] bookshelf;

    public final int columns = 5;
    public final int rows = 6;

    public LocalBookshelf(String name, Item[][] bookshelf){
        this.name=name;
        this.bookshelf=bookshelf;
    }

    public MessageHeader getHeader(){
        return MessageHeader.BOOKSHELF;
    }
}
