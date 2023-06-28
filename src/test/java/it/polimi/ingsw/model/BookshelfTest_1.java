package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.GameModeException;
import it.polimi.ingsw.exception.InvalidColumnPutException;
import it.polimi.ingsw.exception.NotEnoughSpacePutException;
import it.polimi.ingsw.exception.NumPlayersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookshelfTest_1 {

    private Bookshelf bookshelf;

    @BeforeEach
    void setUp() throws GameModeException, NumPlayersException {

        bookshelf = new Bookshelf("Test");
    }

    @Test
    void testPutItemList_validColumn_noSpaceLeft() {
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item(ItemType.GREEN, "path"));
        itemList.add(new Item(ItemType.GREEN, "path"));
        itemList.add(new Item(ItemType.GREEN, "path"));
        itemList.add(new Item(ItemType.GREEN, "path"));
        itemList.add(new Item(ItemType.GREEN, "path"));

        assertDoesNotThrow(() -> bookshelf.putItemList(itemList, 0));
    }

    @Test
    void testPutItemList_validColumn_notEnoughSpace() throws GameModeException, NumPlayersException {
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item(ItemType.GREEN, "Images/items/Green1.png"));
        itemList.add(new Item(ItemType.GREEN, "Images/items/Green1.png"));
        itemList.add(new Item(ItemType.GREEN, "Images/items/Green1.png"));
        itemList.add(new Item(ItemType.GREEN, "Images/items/Green1.png"));
        itemList.add(new Item(ItemType.GREEN, "Images/items/Green1.png"));
        itemList.add(new Item(ItemType.GREEN, "Images/items/Green1.png"));
        itemList.add(new Item(ItemType.GREEN, "Images/items/Green1.png"));


        assertThrows(NotEnoughSpacePutException.class, () -> bookshelf.putItemList(itemList, 0));
    }

    @Test
    void testPutItemList_invalidColumn() {
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item(ItemType.GREEN, "path"));

        assertThrows(InvalidColumnPutException.class, () -> bookshelf.putItemList(itemList, 5));
    }

    @Test
    void testIsFull_emptyBookshelf() {
        assertFalse(bookshelf.isFull());
    }

    @Test
    void testIsFull_fullBookshelf() {
        int[][] matrix = {
                {0, 1, 2, 3, 4},
                {5, 0, 1, 2, 3},
                {4, 5, 0, 1, 2},
                {3, 4, 5, 0, 1},
                {2, 3, 4, 5, 0},
                {1, 2, 3, 4, 5}
        };
        bookshelf.fillBookshelf(matrix);
        assertTrue(bookshelf.isFull());
    }


    @Test
    void testGetItem_nonExistingItem() {
        Optional<Item> item = bookshelf.getItem(new Coordinates(0, 0));
        assertFalse(item.isPresent());
    }

    @Test
    void testCalculatePoints_noMatches() {
        int[][] matrix = {
                {0, 1, 2, 3, 4},
                {5, 0, 1, 2, 3},
                {4, 5, 0, 1, 2},
                {3, 4, 5, 0, 1},
                {2, 3, 4, 5, 0},
                {1, 2, 3, 4, 5}
        };
        bookshelf.fillBookshelf(matrix);

        int points = bookshelf.calculatePoints();
        assertEquals(0, points);
    }

    // Add more tests for the remaining methods...

}