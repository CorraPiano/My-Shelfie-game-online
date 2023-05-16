package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.model.Item;

import java.io.Serializable;

public class LocalBookshelf implements Serializable {
    public final String name;
    public final Item[][] bookshelf;

    public final int columns = 5;
    public final int rows = 6;

    public LocalBookshelf(String name, Item[][] bookshelf){
        this.name=name;
        this.bookshelf=bookshelf;
    }
}
