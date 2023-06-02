package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.Random;

public class Item implements Serializable {
    //OPTION private boolean state;
    private final ItemType type;
    private String imagePath = null;

    public Item (ItemType type) {
        this.type = type;
    }

    public Item (ItemType type, String imagePath) {
        this.type = type;
        this.imagePath = imagePath;
    }
    public ItemType getType() { return type; }
    public String getImagePath() {
        return imagePath;
    }
}
