package it.polimi.ingsw.model;

public class BagItem extends Bag{

    private Item item;

    public void initializeBag() {};

    public Item getObject() { return new Item(ItemType.GREEN); };
}
