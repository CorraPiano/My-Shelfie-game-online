package it.polimi.ingsw.model;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BookshelfTest {
    // ATTRIBUTES
    private Bookshelf bookshelf;
    private ArrayList<Item> itemList1 = new ArrayList<>();
    private ArrayList<Item> itemList2 = new ArrayList<>();


    // METHODS
    private void createItemList() {
        ItemType type1 = ItemType.GREEN;
        ItemType type2 = ItemType.YELLOW;
        ItemType type3 = ItemType.BLUE;
        ItemType type4 = ItemType.PINK;
        ItemType type5 = ItemType.CYAN;
        ItemType type6 = ItemType.WHITE;

        Item item1 = new Item(type1);
        Item item2 = new Item(type2);
        Item item3 = new Item(type3);
        Item item4 = new Item(type4);
        Item item5 = new Item(type5);
        Item item6 = new Item(type6);

        itemList1.add(item1);
        itemList1.add(item2);
        itemList1.add(item3);

        itemList2.add(item3);
        itemList2.add(item2);
        itemList2.add(item1);
    }

    // TESTS
    @Test
    void putItemList() throws Exception {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.putItemList(itemList1, 1);
        // dovrebbe funzionare, non ho ancora controllato bene
    }

    @Test
    void isFull() {
        // va sicuramente bene
    }

    @Test
    void getItem() {
        // TODO
    }

    @Test
    void calculatePoints() throws Exception {

        Bookshelf bookshelf = new Bookshelf();
        createItemList();

        // to add some Items in the Library
        bookshelf.putItemList(itemList1, 0);
        bookshelf.putItemList(itemList2, 0);
        bookshelf.putItemList(itemList1, 1);
        bookshelf.putItemList(itemList2, 1);
        bookshelf.putItemList(itemList1, 2);
        bookshelf.putItemList(itemList2, 2);
        bookshelf.putItemList(itemList2, 3);
        bookshelf.putItemList(itemList2, 3);
        bookshelf.putItemList(itemList2, 4);
        bookshelf.putItemList(itemList1, 4);

        // calculatePoints check
        assertEquals(25, bookshelf.calculatePoints());
    }
}