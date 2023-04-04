package it.polimi.ingsw.model;

public class Item {
    //OPTION private boolean state;
    private final ItemType type;
    private Coordinates boardCoordinates;
    public Item (ItemType type) { this.type = type;}
    public Item(ItemType type, Coordinates boardCoordinates) {
        this.type = type;
        this.boardCoordinates = boardCoordinates;
    }
    public ItemType getType() { return type; }
    public Coordinates getBoardCoordinates() {
        return boardCoordinates;
    }
}
