package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Bookshelf {
    //ATTRIBUTES
    private final int nColumns = 5;
    private final int nRows = 6;
    private Item library[][] = new Item[nRows][nColumns];

    //METHODS
    public void insertItems(ArrayList<Item> itemList, int column) throws Exception {
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
        if(counterSpace >= itemListSize) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkIfFull() {
        for(int i = 0; i < nRows; i++) {
            for(int j = 0; j < nColumns; j++) {
                if(library[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public Item getItem(Coordinates coordinate){
        return library[coordinate.getRow()][coordinate.getColumn()];
    }
}