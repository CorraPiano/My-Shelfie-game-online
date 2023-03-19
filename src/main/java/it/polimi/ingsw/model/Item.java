package it.polimi.ingsw.model;

public class Item {
// Attributes
    private int state = 0; // flag that shows if the Item has been added to a personal library. (0 not added yet)
    private final ItemType type;

//Methods

    public Item( int state, ItemType type){
        this.state= state;
        this.type = type;
    }

    public int getState() { return state;}

}
