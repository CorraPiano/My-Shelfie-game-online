package it.polimi.ingsw.model;

import it.polimi.ingsw.client.localModel.LocalBookshelf;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.exception.InvalidColumnPutException;
import it.polimi.ingsw.exception.NotEnoughSpacePutException;

import java.util.ArrayList;
import java.util.Optional;

import static it.polimi.ingsw.model.BagItem.getItemPathByType;

public class Bookshelf extends Listenable {
    //ATTRIBUTES
    private final int nColumns;
    private final String name;
    private final int nRows;
    private final Item[][] library;
    private final boolean[][] mask;

    /**
     * Default constructor.
     *
     * @param name String, name of player's bookshelf.
     */
    public Bookshelf(String name) {
        this.nColumns = 5;
        this.nRows = 6;
        this.name=name;
        this.library = new Item[nRows][nColumns];
        this.mask = new boolean[nRows][nColumns];
        resetMask();
    }

    public void sendBookshelf(){
        notifyUpdate();
    }

    /**
     * Puts items in the bookshelf
     *
     * @param itemList ArrayList<Item>, items to insert.
     * @param column int, column where to insert.
     * @throws NotEnoughSpacePutException not enough space left in the column to insert.
     * @throws InvalidColumnPutException if column index is out of bound.
     */
    public void putItemList(ArrayList<Item> itemList, int column) throws NotEnoughSpacePutException, InvalidColumnPutException {
        int index = 0;
        if(column >= 0 && column < 5) {
            if(noSpaceLeft(column, itemList.size())) {
                for(int i = 0; i < nRows; i++) {
                    if(library[i][column] == null && index < itemList.size()) {
                        library[i][column] = itemList.get(index);
                        index++;
                    }
                }
                notifyUpdate();
            }
            else{
                throw new NotEnoughSpacePutException();
            }
        }
        else{
            throw new InvalidColumnPutException();
        }
    }

    /**
     * Boolean method, checks if there's enough space left in a certain
     * column to insert items.
     *
     * @param column column to check.
     * @param itemListSize size of items to insert.
     * @return boolean, true if enough space left.
     */
    private boolean noSpaceLeft(int column, int itemListSize) {
        int counterSpace = 0;
        for(int i = (nRows - 1); i >= 0; i--) { // (nRow-1) because we count also the row 0
            if(library[i][column] == null) {
                counterSpace++;
            }
        }
        return (counterSpace >= itemListSize);
    }

    /**
     * Checks if bookshelf is full.
     *
     * @return boolean, true if full.
     */
    public boolean isFull() {
        for(int i = 0; i < nRows; i++) {
            for(int j = 0; j < nColumns; j++) {
                if(library[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Gets a library item.
     *
     * @param coordinate item's coordinates.
     * @return Optional<Item>, item in coordinates.
     */
    public Optional<Item> getItem(Coordinates coordinate){
        return Optional.ofNullable(library[coordinate.getRow()][coordinate.getColumn()]);
    }

    /**
     * Calculates player's bookshelf points.
     *
     * @return int, points calculated.
     */
    public int calculatePoints() {
        int points = 0;
        int counter;
        resetMask();
        for(int i = 0; i < nRows; i++) {
            for(int j = 0; j < nColumns; j++) {
                if(!mask[i][j] && library[i][j] != null) {
                    counter = 1;
                    mask[i][j] = true;
                    counter = countNearbyItems(i, j, counter, library[i][j].getType());
                    if (counter == 3) {
                        points = points + 2;
                    } else if (counter == 4) {
                        points = points + 3;
                    } else if (counter == 5) {
                        points = points + 5;
                    } else if (counter >= 6) {
                        points = points + 8;
                    }
                }

            }
        }
        return points;
    }

    /**
     * Resets mask.
     *
     */
    private void resetMask() {
        for(int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                mask[i][j] = false;
            }
        }
    }

    /**
     * Auxiliary method used in calculatePoints method to calculate points given by nearby elements
     *
     * @param i row index.
     * @param j column index.
     * @param counter parameter returned, points counter.
     * @param type item's type.
     * @return  int, points counted.
     */
    private int countNearbyItems(int i, int j, int counter, ItemType type) {
        if((j+1) < nColumns && library[i][j+1] != null) {
            if(!mask[i][j+1] && library[i][j+1].getType() == type) {
                mask[i][j+1] = true;
                counter = 1 + countNearbyItems(i, j+1, counter, type);
            }
        }
        if((j-1) >= 0 && library[i][j-1] != null) {
            if(!mask[i][j-1] && library[i][j-1].getType() == type) {
                mask[i][j-1] = true;
                counter = 1 + countNearbyItems(i, j-1, counter, type);
            }
        }
        if((i+1) < nRows && library[i+1][j] != null) {
            if(!mask[i+1][j] && library[i+1][j].getType() == type) {
                mask[i+1][j] = true;
                counter = 1 + countNearbyItems(i+1, j, counter, type);
            }
        }
        if((i-1) >= 0 && library[i-1][j] != null) {
            if(!mask[i-1][j] &&  library[i-1][j].getType() == type) {
                mask[i-1][j] = true;
                counter = 1 + countNearbyItems(i-1, j, counter, type);
            }
        }
        return counter;
    }

    /**
     * Fills bookshelf.
     *
     * @param matrix .
     */
    public void fillBookshelf(int[][] matrix) {
        for(int i = 0; i < nRows; i++) {
            for(int j = 0; j < nColumns; j++) {
                if(matrix[i][j] == 9) {
                    this.library[i][j] = null;
                }
                else if(matrix[i][j] == 0) {
                    this.library[i][j] = new Item(ItemType.GREEN,getItemPathByType(ItemType.GREEN));
                }
                else if(matrix[i][j] == 1){
                    this.library[i][j] = new Item(ItemType.YELLOW,getItemPathByType(ItemType.YELLOW));
                }
                else if(matrix[i][j] == 2){
                    this.library[i][j] = new Item(ItemType.BLUE,getItemPathByType(ItemType.BLUE));
                }
                else if(matrix[i][j] == 3){
                    this.library[i][j] = new Item(ItemType.PINK,getItemPathByType(ItemType.PINK));
                }
                else if(matrix[i][j] == 4){
                    this.library[i][j] = new Item(ItemType.CYAN,getItemPathByType(ItemType.CYAN));
                }
                else if(matrix[i][j] == 5){
                    this.library[i][j] = new Item(ItemType.WHITE,getItemPathByType(ItemType.WHITE));
                }
              }
        }
    }

    public void putItem(Item item, int row, int column) {
        library[row][column] = item;
    }

    /**
     * Library getter
     *
     * @return library, matrix of items.
     */
    public Item[][] getLibrary(){
        return library;
    }

    /**
     * Bookshelf's player name getter.
     *
     * @return String, name.
     */
    public String getName(){
        return name;
    }

    /**
     * Method used to create a LocalBoofshelf. This object is used to minimize information
     * sent on network.
     *
     * @return Sendable, returns a LocalBookshelf.
     */
    @Override
    public Sendable getLocal() {
        Item[][] bookshelf=new Item[nRows][nColumns];
        for(int i=0;i<nRows;i++)
            System.arraycopy(library[i], 0, bookshelf[i], 0, nColumns);
        return new LocalBookshelf(name,bookshelf);
    }
    //TODO: serve itemCounter per qualcosa?


}