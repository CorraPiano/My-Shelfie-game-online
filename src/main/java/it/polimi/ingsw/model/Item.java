package it.polimi.ingsw.model;

public class Item {
    //OPTION private boolean state;
    private final ItemType type;
    private Coordinates coordinates;

    public Item (ItemType type) { this.type = type;}
    public Item(ItemType type, Coordinates coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }
    public ItemType getType() { return type; }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
