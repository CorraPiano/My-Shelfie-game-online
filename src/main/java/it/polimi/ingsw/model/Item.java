package it.polimi.ingsw.model;

public class Item {
// Attributes
    //OPTION private int state;
    private final ItemType type;

//Methods

    public Item(ItemType type) {
        this.type = type;
    }

    public ItemType getType() { return type; }

}
