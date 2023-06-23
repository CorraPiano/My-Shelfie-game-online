package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Represents a game item.
 */
public class Item implements Serializable {
    private final ItemType type;
    private String imagePath;

    /**
     * Constructs a new game item with the specified type.
     *
     * @param type The type of the game item.
     */
    public Item(ItemType type) {
        this.type = type;
    }

    /**
     * Constructs a new game item with the specified type and image path.
     *
     * @param type      The type of the game item.
     * @param imagePath The path of the image associated with the game item.
     */
    public Item(ItemType type, String imagePath) {
        this.type = type;
        this.imagePath = imagePath;
    }

    /**
     * Returns the type of the game item.
     *
     * @return The type of the game item.
     */
    public ItemType getType() {
        return type;
    }

    /**
     * Returns the image path associated with the game item.
     *
     * @return The image path associated with the game item.
     */
    public String getImagePath() {
        return imagePath;
    }
}