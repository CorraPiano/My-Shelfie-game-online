package it.polimi.ingsw.model;

public class Item {
// Attributes
    //TODO: private int state;
    private final ItemType type;

//Methods

    public Item(ItemType type) {
        this.type = type;
    }

    public ItemType getType() { return type; }

}
