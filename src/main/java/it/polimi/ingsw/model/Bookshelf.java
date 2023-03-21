package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Optional;

public class Bookshelf {
    //ATTRIBUTES
    private final int nColumns;
    private final int nRows;
    private Item[][] library;

    public Bookshelf() {
        this.nColumns = 5;
        this.nRows = 6;
        this.library = new Item[nRows][nColumns];
    }

    //TODO: serve itemCounter per qualcosa?

    //METHODS
    public void putItemList(ArrayList<Item> itemList, int column) throws Exception {
        int index = 0;
        if(column > 0 && column <= 5) {
            if(!noSpaceLeft(column, itemList.size())) {
                for(int i = 0; i < nRows; i++) {
                    if(library[i][column] == null && index < itemList.size()) {
                        library[i][column] = itemList.get(index);
                        index++;
                    }
                }
            }
            else{
                throw new Exception("Not enough space at column " + column);
            }
        }
        else{
            throw new Exception("Invalid column");
        }
    }

    private boolean noSpaceLeft(int column, int itemListSize) {
        int counterSpace = 0;
        for(int i = nRows; i > 0; i--) {
            if(library[i][column] == null) {
                counterSpace++;
            }
        }
        return counterSpace >= itemListSize;
    }

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

    public Optional<Item> getItem(Coordinates coordinate){
        return Optional.of(library[coordinate.getRow()][coordinate.getColumn()]);
    }
}