package it.polimi.ingsw;

public class Item {
// Attributes
    private int state = 0; // flag that shows if the Item has been added to a personal library. (0 not added yet)
    private final ItemType type;

//Methods

    public Item( int value, ItemType type){
        this.value= value;
        this.type = type;
    }

    public int getState() { return state;}

}
