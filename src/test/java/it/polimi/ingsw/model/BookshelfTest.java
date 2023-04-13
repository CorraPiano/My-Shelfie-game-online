package it.polimi.ingsw.model;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.model.util.TestFactory;
import it.polimi.ingsw.model.util.InputTest;

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
    void getItem() {
        // TODO
    }

    @Test
    void calculatePoints() throws Exception {

        // carico le matrici dal file BookshelfTestFile.txt in InputTest
        InputTest input = TestFactory.createTest();

        // problemi con la matrice vuota forse perchè il -1 non va bene come numero
        // TODO: Assertions.assertEquals(input.getResult(0), input.getInputLibrary(0).calculatePoints());

        // testo tutte le matrici del file BookshelfTestFile.txt
        for(int i = 1; i < input.numberOfTests(); i++) {
            Assertions.assertEquals(input.getResult(i), input.getInputLibrary(i).calculatePoints());
        }
    }
}