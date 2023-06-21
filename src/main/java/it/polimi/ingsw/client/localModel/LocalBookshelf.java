package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.util.Constants;

import java.io.Serializable;

public class LocalBookshelf implements Serializable, Sendable {

    public final String name;
    public final Item[][] bookshelf;

    public LocalBookshelf(String name, Item[][] bookshelf){
        this.name=name;
        this.bookshelf=bookshelf;
    }
    public LocalBookshelf(String name){
        this.name=name;
        this.bookshelf = new Item[Constants.nRowBookshelf][Constants.nColumnBookshelf];
    }

    public MessageHeader getHeader(){
        return MessageHeader.BOOKSHELF;
    }
}
