package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BagItemTest {
    private BagItem bagItem;

    @BeforeEach
    public void setUp() {
        bagItem = new BagItem();
    }

    @Test
    public void testDrawItem() {
        int initialSize = bagItem.getBagItemSize();
        Item drawnItem = bagItem.drawItem();
        int newSize = bagItem.getBagItemSize();

        assertNotNull(drawnItem);
        assertEquals(initialSize - 1, newSize);
    }

    @Test
    public void testDrawItemFromEmptyBag() {
        // Draw all items from the bag
        for (int i = 0; i < 132; i++) {
            bagItem.drawItem();
        }

        // Try to draw an item from an empty bag
        Item drawnItem = bagItem.drawItem();

        assertNull(drawnItem);
    }

    @Test
    public void testGetBagItemSize() {
        int initialSize = bagItem.getBagItemSize();
        assertEquals(132, initialSize);

        // Draw all items from the bag
        for (int i = 0; i < 132; i++) {
            bagItem.drawItem();
        }

        int newSize = bagItem.getBagItemSize();
        assertEquals(0, newSize);
    }

    @Test
    public void testAddBack() {
        Item item = new Item(ItemType.BLUE);
        int initialSize = bagItem.getBagItemSize();

        bagItem.addBack(item);

        int newSize = bagItem.getBagItemSize();
        assertEquals(initialSize + 1, newSize);
    }

    @Test
    public void testGetItemPathByType() {
        Set<String> itemPaths = new HashSet<>();
        for (ItemType type : ItemType.values()) {
            String itemPath = BagItem.getItemPathByType(type);
            assertNotNull(itemPath);
            itemPaths.add(itemPath);
        }

        assertTrue(itemPaths.size() >= ItemType.values().length);
    }

    @Test
    public void testGetItemPathByTypeForAllColors() {
        for (ItemType type : ItemType.values()) {
            String itemPath = BagItem.getItemPathByType(type);
            assertNotNull(itemPath);
        }
    }


}