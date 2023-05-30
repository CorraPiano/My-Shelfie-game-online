package it.polimi.ingsw.model;

public enum ItemType {
    GREEN(0),
    YELLOW(1),
    BLUE(2),
    PINK(3),
    CYAN(4),
    WHITE(5);
    private final int value;
    ItemType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

};
