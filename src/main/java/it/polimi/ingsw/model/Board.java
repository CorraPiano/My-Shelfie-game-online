package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.EmptySlotException;
import it.polimi.ingsw.exception.GameRulesViolationException;
import it.polimi.ingsw.exception.OutOfBoardException;

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

    private Hand hand;

    //BOARD CREATORS
    public Board(int numPlayers, Hand hand) {
        //numero di giocatori deve essere compreso tra 2-4, IllegalArgumentException altrimenti
        this.livingRoom = new Item[nRows][nColumns];
        this.bagItem = new BagItem();
        this.numPlayers = numPlayers;
        this.hand = hand;
        //drawBoardItems(); used by Gameplay class
    }

    public void drawBoardItems()  {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mask[i][j] <= numPlayers) {
                    livingRoom[i][j] = bagItem.drawItem();
                }
            }
        }
    }

    //OTHER METHODS

    public boolean isCatchable(int row, int column) throws OutOfBoardException, EmptySlotException, GameRulesViolationException {
        //out of bound coordinates
        if ((row < 0 || row > 8) || (column < 0 || column > 8)) {
            throw new OutOfBoardException();
        }
        //empty
        if (livingRoom[row][column] == null) {
            throw new EmptySlotException();
        }
        //bordi
        if (row == 0 || column == 0 || row == 8 || column == 8) {
            return true;
        }

        //non bordi
        if (livingRoom[row - 1][column] == null && !hand.containsCoords(new Coordinates(row - 1, column))) {
            return true;
        } else if (livingRoom[row + 1][column] == null && !hand.containsCoords(new Coordinates(row + 1, column))) {
            return true;
        } else if (livingRoom[row][column - 1] == null && !hand.containsCoords(new Coordinates(row, column - 1))) {
            return true;
        } else if (livingRoom[row][column + 1] == null && !hand.containsCoords(new Coordinates(row, column + 1))) {
            return true;
        }
        throw new GameRulesViolationException();
    }

    /* Returns true if the board has to be refilled, not to call too soon */
    public boolean checkRefill() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mask[i][j] <= numPlayers && livingRoom[i][j] != null) {
                    if ((i - 1 >= 0) && livingRoom[i - 1][j] != null) {
                        return false;
                    } else if ((i + 1 <= 8) && livingRoom[i + 1][j] != null) {
                        return false;
                    } else if ((j - 1 >= 0) && livingRoom[i][j - 1] != null) {
                        return false;
                    } else if ((j + 1 <= 8) && livingRoom[i][j + 1] != null) {
                        return false;
                    }

                }
            }
        }
        return true;
    }
    public void putBackInBag() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (livingRoom[i][j] != null){
                    bagItem.addBack(livingRoom[i][j]);
                    livingRoom[i][j] = null;
                }
            }
        }
    }
    public void refillBoard() {
        if (checkRefill()) {
            putBackInBag();
            drawBoardItems();
        }
    }

    /* WARNING: method isEmpty() does NOT check the null condition, so we have to check before the empty condition */
    public void getItem (Coordinates coordinates) throws EmptySlotException, GameRulesViolationException, OutOfBoardException {
        if (coordinates != null) {
            int row = coordinates.getRow();
            int column = coordinates.getColumn();
            if (isCatchable(row,column)) {
                hand.putItem(livingRoom[row][column], new Coordinates(row,column)); //da chiamare così
                livingRoom[row][column] = null;
            }
        }
    }

/* rimette la mano nella board*/
    public void releaseHand () {
        ArrayList<Item> itemList = hand.getHand();
        ArrayList<Coordinates> coordList = hand.getCoordinatesList(); // da chiamare così

        if (itemList != null && !itemList.isEmpty()) {
            for (int i = 0; i < itemList.size(); i++) {
                int row = coordList.get(i).getRow();
                int column = coordList.get(i).getColumn();
                livingRoom[row][column] = itemList.get(i);
            }
            hand.clear();
        }
    }
    public Item[][] getLivingRoom() {return livingRoom;}
    public Item getLivingRoomItem (int row,int column) { return livingRoom[row][column];}
    public int[][] getMask() {
        return mask;
    }
    public int getNumPlayers() {
        return numPlayers;
    }
}
