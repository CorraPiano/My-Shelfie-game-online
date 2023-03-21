package it.polimi.ingsw.model;

public class BagItem extends Bag{

    private Item item;

    public void initializeBag() {}; //sets the bag ready for the draw, size has to contain all the 132 items

    public Item getObject() { return new Item (ItemType.GREEN); };
}

