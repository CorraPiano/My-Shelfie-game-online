package it.polimi.ingsw.model;

/**
 * The ItemType enum represents the types of items in the game.
 * Each item type has a corresponding value.
 */
public enum ItemType {
    GREEN(0),
    YELLOW(1),
    BLUE(2),
    PINK(3),
    CYAN(4),
    WHITE(5);

    private final int value;

    /**
     * Constructs a new ItemType enum with the specified value.
     *
     * @param value The value associated with the item type.
     */
    ItemType(int value) {
        this.value = value;
    }

    /**
     * Returns the value associated with the item type.
     *
     * @return The value of the item type.
     */
    public int getValue() {
        return value;
    }
}
