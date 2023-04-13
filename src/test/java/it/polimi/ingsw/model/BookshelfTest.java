package it.polimi.ingsw.model;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.model.util.TestFactory;
import it.polimi.ingsw.model.util.InputTest;

class BookshelfTest {
    // ATTRIBUTES
    private Bookshelf bookshelf = new Bookshelf();
    // private Bookshelf bookshelfTest = new Bookshelf();
    private ArrayList<Item> itemList1 = new ArrayList<>();
    private ArrayList<Item> itemList2 = new ArrayList<>();


    // METHODS
    private void createItemList() {
        ItemType type0 = ItemType.GREEN;
        ItemType type1 = ItemType.YELLOW;
        ItemType type2 = ItemType.BLUE;
        ItemType type3 = ItemType.PINK;
        ItemType type4 = ItemType.CYAN;
        ItemType type5 = ItemType.WHITE;

        Item item1 = new Item(type0);
        Item item2 = new Item(type1);
        Item item3 = new Item(type2);
        Item item4 = new Item(type3);
        Item item5 = new Item(type4);
        Item item6 = new Item(type5);

        itemList1.add(item1);
        itemList1.add(item2);
        itemList1.add(item3);
        itemList2.add(item3);
        itemList2.add(item2);
        itemList2.add(item1);
    }

    private int counterTotalValue(Bookshelf bookshelf) {
        int value = 0;
        Coordinates coordinates = new Coordinates();
        for(int row=0; row<6; row++) {
            for(int column=0; column<5; column++) {
                coordinates.setColumn(column);
                coordinates.setRow(row);
                if(bookshelf.getItem(coordinates).isPresent()){
                    value = value + bookshelf.getItem(coordinates).get().getType().getValue();
                }
            }
        }
        return value;
    }

    /*
    // funzione realizzata per cercare di utilizzare la putItemList con i file.txt ma c'è il problema degli oggetti a null
    private int itemValueAtCoordinates(int index, Coordinates coordinates) {
        InputTest input = TestFactory.createTest();
        if(input.getInputLibrary(index).getItem(coordinates).isPresent()){
            return input.getInputLibrary(index).getItem(coordinates).get().getType().getValue();

        }
        else {
            return 9;
        }
    }
     */

    // TESTS
    @Test
    void calculatePoints() throws Exception {
        // carico le matrici dal file BookshelfTestFile_updatePoints.txt in InputTest
        InputTest input = TestFactory.createTest("src/test/java/it/polimi/ingsw/model/util/BookshelfTestFile_putItemList.txt");

        // testo tutte le matrici del file BookshelfTestFile_updatePoints.txt
        for(int i = 0; i < input.numberOfTests(); i++) {
            Assertions.assertEquals(input.getResult(i), input.getInputLibrary(i).calculatePoints());
        }
    }

    /*
    @Test
    // test che utilizza i file.txt ma c'è il problema degli oggetti a null
    void putItemList1() throws Exception {
        // carico le matrici dal file BookshelfTestFile_putItemList.txt in InputTest
        InputTest input = TestFactory.createTest();

        input.getInputLibrary(0).putItemList(itemList1, 1);
        Coordinates coordinates = new Coordinates();

        // inserimento di 3 item in libreria piena
        for(int row=0; row<6; row++) {
            for(int column=0; column<5; column++) {
                coordinates.setColumn(column);
                coordinates.setRow(row);
                Assertions.assertEquals(itemValueAtCoordinates(1, coordinates), itemValueAtCoordinates(0, coordinates));
                System.out.print(itemValueAtCoordinates(0, coordinates) + " ");
            }
            System.out.print("\n");
        }
    }
     */

    @Test
    void putItemList() throws Exception {

        // creo le liste di Item da inserire
        createItemList();
        // inserisco gli Item
        bookshelf.putItemList(itemList1, 1);
        bookshelf.putItemList(itemList1, 2);
        bookshelf.putItemList(itemList2, 2);
        bookshelf.putItemList(itemList1, 4);

        // Stampo la matrice per vedfere se effettivamente li ha inseriti correttamente
        Coordinates coordinates = new Coordinates();
        for(int row = 5; row >= 0; row--) {
            for(int column = 0; column < 5; column++) {
                coordinates.setColumn(column);
                coordinates.setRow(row);
                if(bookshelf.getItem(coordinates).isPresent()){
                    System.out.print(bookshelf.getItem(coordinates).get().getType().getValue() + " ");
                }
                else {
                    System.out.print("9 ");
                    // il 9 rappresenta il valore null e in counterTotalValue() non viene contato
                }
            }
            System.out.print("\n");
        }

        // Forse non ha troppo senso farla così
        Assertions.assertEquals(12, counterTotalValue(bookshelf));
        // Stampa della somma dei valori degli Item inseriti
        System.out.println("Total value: "+ counterTotalValue(bookshelf));
    }

    /*
    ==============================================================================
    I metodi quali: noSpaceLeft(), isFull(), getItem(), putItem(), fillBookshelf()
    sono banali o verificati di conseguenza ai test precedenti
    ==============================================================================
     */
}