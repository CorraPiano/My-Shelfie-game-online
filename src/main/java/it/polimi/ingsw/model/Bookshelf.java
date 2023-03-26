package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Optional;

public class Bookshelf {
    //ATTRIBUTES
    private final int nColumns;
    private final int nRows;
    private Item[][] library;
    private boolean[][] mask;


    //METHODS
    public Bookshelf() {
        this.nColumns = 5;
        this.nRows = 6;
        this.library = new Item[nRows][nColumns];

        this.mask = new boolean[nRows][nColumns];
        for(int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                mask[i][j] = false;
            }
        }
    }

    public void putItemList(ArrayList<Item> itemList, int column) throws Exception {
        int index = 0;
        if(column >= 0 && column < 5) {
            if(noSpaceLeft(column, itemList.size())) {
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
        for(int i = (nRows - 1); i >= 0; i--) { // (nRow-1) because we count also the row 0
            if(library[i][column] == null) {
                counterSpace++;
            }
        }
        return (counterSpace >= itemListSize);
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

    public int calculatePoints() {
        int points = 0;
        int counter;
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

    private int countNearbyItems(int i, int j, int counter, ItemType type) {
        if((j+1) < nColumns && library[i][j+1] != null) {
            if(!mask[i][j+1] && library[i][j+1].getType() == type) {
                //System.out.println("vado a dx e: " + counter);
                mask[i][j+1] = true;
                counter = 1 + countNearbyItems(i, j+1, counter, type);
            }
        }
        if((j-1) >= 0 && library[i][j-1] != null) {
            if(!mask[i][j-1] && library[i][j-1].getType() == type) {
                //System.out.println("vado a sx e: " + counter);
                mask[i][j-1] = true;
                counter = 1 + countNearbyItems(i, j-1, counter, type);
            }
        }
        if((i+1) < nRows && library[i+1][j] != null) {
            if(!mask[i+1][j] && library[i+1][j].getType() == type) {
                //System.out.println("vado su e: " + counter);
                mask[i+1][j] = true;
                counter = 1 + countNearbyItems(i+1, j, counter, type);
            }
        }
        if((i-1) >= 0 && library[i-1][j] != null) {
            if(!mask[i-1][j] &&  library[i-1][j].getType() == type) {
                //System.out.println("vado giù e: " + counter);
                mask[i-1][j] = true;
                counter = 1 + countNearbyItems(i-1, j, counter, type);
            }
        }
        return counter;
    }

    //TODO: serve itemCounter per qualcosa?

}