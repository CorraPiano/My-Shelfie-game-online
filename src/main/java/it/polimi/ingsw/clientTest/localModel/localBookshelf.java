package it.polimi.ingsw.clientTest.localModel;

import it.polimi.ingsw.model.Item;

import java.io.Serializable;

public class localBookshelf implements Serializable {
    public final String name;
    public final Item[][] bookshelf;

    public final int columns = 5;
    public final int rows = 6;

    public localBookshelf(String name, Item[][] bookshelf){
        this.name=name;
        this.bookshelf=bookshelf;
    }
}
