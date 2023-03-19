package it.polimi.ingsw;

public class Item {
// Attributes
    private final int value; // numerical value of card's type (0-->green,1->yellow...)
    private final ItemType type;

//Methods

    public Item( int value, ItemType type){
        this.value= value;
        this.type = type;
    }

    public int getValue() { return value; }

}
