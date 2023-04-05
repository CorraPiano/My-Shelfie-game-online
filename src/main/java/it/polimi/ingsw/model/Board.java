package it.polimi.ingsw.model;

import java.util.*;

public class Board {
    private Item[][] livingRoom; // actual board
    static final int nColumns = 9;
    static final int nRows = 9;
    private int numPlayers;
    private BagItem bagItem;
    private int[][] mask = {{5, 5, 5, 3, 4, 5, 5, 5, 5}, // 5 if not fillable, 2,3,4, represent the number of players needed to be fillable
            {5, 5, 5, 2, 2, 4, 5, 5, 5},
            {5, 5, 3, 2, 2, 2, 3, 5, 5},
            {5, 4, 2, 2, 2, 2, 2, 2, 3},
            {4, 2, 2, 2, 2, 2, 2, 2, 4},
            {3, 2, 2, 2, 2, 2, 2, 4, 5},
            {5, 5, 3, 2, 2, 2, 3, 5, 5},
            {5, 5, 5, 4, 2, 2, 5, 5, 5},
            {5, 5, 5, 5, 4, 3, 5, 5, 5}
    };

    //BOARD CREATORS
    public Board(int numPlayers) {
        //numero di giocatori deve essere compreso tra 2-4, IllegalArgumentException altrimenti
        this.livingRoom = new Item[nRows][nColumns];
        this.bagItem = new BagItem();
        this.numPlayers = numPlayers;
        drawBoardItems();
    }
    public void drawBoardItems() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mask[i][j] <= numPlayers) {
                    livingRoom[i][j] = bagItem.drawItem();
                }
            }
        }
    }
    //OTHER METHODS
    public boolean isCatchable(int row, int column) {
        if (row < 0 || column < 0) { return false;}
        if (livingRoom[row][column] == null) { return false; }
        else {
            if ((row - 1 >= 0) && livingRoom[row - 1][column] != null) {
                return true;
            } else if ((row + 1 >= 0) && livingRoom[row + 1][column] != null) {
                return true;
            } else if ((column - 1 >= 0) && livingRoom[row][column - 1] != null) {
                return true;
            } else if ((column + 1 >= 0) && livingRoom[row - 1][column + 1] != null) {
                return true;
            }
            return false;
        }
    }

    /* Returns true if the board has to be refilled */
    public boolean checkRefill() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mask[i][j] <= numPlayers) {
                    if (isCatchable(i,j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public void emptyBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                livingRoom[i][j] = null;
            }
        }
    }
    public void refillBoard() {
        if (checkRefill()) {
            emptyBoard();
            drawBoardItems();
        }
    }
    /* WARNING: method isEmpty() does NOT check the null condition, so we have to check before the empty condition */
    public List<Item> getItemList(ArrayList<Coordinates> coordinatesList) {

        List<Item> itemList = new ArrayList<Item>(); //list that will be returned
        if (coordinatesList != null && !coordinatesList.isEmpty()){
            for (int i = 0; i < coordinatesList.size(); i++) {
                int row = coordinatesList.get(i).getRow();
                int column = coordinatesList.get(i).getColumn();
                    if (isCatchable(row,column)) {
                        itemList.add(livingRoom[row][column]);
                        livingRoom[row][column] = null;
                    }
            }
        }
        else throw new IllegalArgumentException("List has to contain something!");

        return itemList;
    }
    public void putItems (ArrayList<Item> hand) {
        if (hand != null && !hand.isEmpty()) {
            for (Item it : hand) {
                int row = it.getBoardCoordinates().getRow();
                int column = it.getBoardCoordinates().getColumn();
                livingRoom[row][column] = it;
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public Item[][] getLivingRoom() {return livingRoom;
    }
    public Item getLivingRoomItem (int row,int column){ return livingRoom[row][column];}
    public int[][] getMask() {
        return mask;
    }
    public int getNumPlayers() {
        return numPlayers;
    }
}
