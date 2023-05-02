package it.polimi.ingsw.model;

public class Item {
    //OPTION private boolean state;
    private final ItemType type;
    public Item (ItemType type) { this.type = type;}
    public ItemType getType() { return type; }
}
