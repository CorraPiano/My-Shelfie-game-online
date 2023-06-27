package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void getImagePath() {
        // Create an Item object with a specific image path
        String imagePath = "Images/items/Blue1.png";
        Item item = new Item(ItemType.BLUE, imagePath);

        // Test the getImagePath() method
        String result = item.getImagePath();
        assertEquals(imagePath, result);
    }
}