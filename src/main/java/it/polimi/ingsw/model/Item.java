package it.polimi.ingsw.model;

import java.io.Serializable;

public class Item implements Serializable {
    //OPTION private boolean state;
    private final ItemType type;
    public Item (ItemType type) { this.type = type;}
    public ItemType getType() { return type; }
}
