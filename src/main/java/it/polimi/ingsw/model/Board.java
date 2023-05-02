package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.*;
import java.util.*;

public class Board {
    private final Item[][] livingRoom;
    static final int nColumns = 9;
    static final int nRows = 9;
    private final int numPlayers;
    private final BagItem bagItem;
    //da sostituire con un update da JSON
    private final int[][] mask = {
            {5, 5, 5, 3, 4, 5, 5, 5, 5},
            {5, 5, 5, 2, 2, 4, 5, 5, 5},
            {5, 5, 3, 2, 2, 2, 3, 5, 5},
            {5, 4, 2, 2, 2, 2, 2, 2, 3},
            {4, 2, 2, 2, 2, 2, 2, 2, 4},
            {3, 2, 2, 2, 2, 2, 2, 4, 5},
            {5, 5, 3, 2, 2, 2, 3, 5, 5},
            {5, 5, 5, 4, 2, 2, 5, 5, 5},
            {5, 5, 5, 5, 4, 3, 5, 5, 5}
    };
    private final Hand hand;

    public Board(int numPlayers, Hand hand) {
        this.livingRoom = new Item[nRows][nColumns];
        this.bagItem = new BagItem();
        this.numPlayers = numPlayers;
        this.hand = hand;
    }

    /* Draws random items to fill the board */
    public void drawBoardItems() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mask[i][j] <= numPlayers) {
                    livingRoom[i][j] = bagItem.drawItem();
                }
            }
        }
    }

    /* Item pick */
    public void getItem (Coordinates coordinates) throws LimitReachedPickException, OutOfBoardPickException, EmptySlotPickException, NotLinearPickException, NotCatchablePickException {
        if (coordinates != null) {
            if (isCatchable(coordinates)) {
                int row = coordinates.getRow();
                int column = coordinates.getColumn();
                hand.putItem(livingRoom[row][column], coordinates);
                livingRoom[row][column] = null;
            }
            else
                throw new NotLinearPickException();
        }
        else
            throw new OutOfBoardPickException();
    }

    /* True if we can pick an item from the board */
    private boolean isCatchable(Coordinates coordinates) throws LimitReachedPickException, OutOfBoardPickException, EmptySlotPickException, NotCatchablePickException {
        int row = coordinates.getRow();
        int column = coordinates.getColumn();
        boolean catchable = false;

        if(hand.getSize()>2)
            throw new LimitReachedPickException();

        if ((row < 0 || row > 8) || (column < 0 || column > 8))
            throw new OutOfBoardPickException();
        if (mask[row][column]>numPlayers)
            throw new OutOfBoardPickException();

        if (livingRoom[row][column] == null)
            throw new EmptySlotPickException();

        if (row == 0 || column == 0 || row == 8 || column == 8)
            catchable = true;
        else if (livingRoom[row - 1][column] == null && !hand.containsCoords(new Coordinates(row - 1, column)))
                catchable = true;
        else if (livingRoom[row + 1][column] == null && !hand.containsCoords(new Coordinates(row + 1, column)))
                catchable = true;
        else if (livingRoom[row][column - 1] == null && !hand.containsCoords(new Coordinates(row, column - 1)))
                    catchable = true;
        else if (livingRoom[row][column + 1] == null && !hand.containsCoords(new Coordinates(row, column + 1)))
                        catchable = true;

        if(catchable)
              return hand.checkNewCoordinates(coordinates);
        throw new NotCatchablePickException();
    }

    /* Puts the hand back into the board, undo operation */
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

    /* Refills the board when needed */
    public void endTurn() {
        hand.clear();
        if (checkRefill()) {
            putBackInBag();
            drawBoardItems();
        }
    }

    /* Puts items left on the board back into the bag */
    private void putBackInBag() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (livingRoom[i][j] != null){
                    bagItem.addBack(livingRoom[i][j]);
                    livingRoom[i][j] = null;
                }
            }
        }
    }

    /* Returns true if the board has to be refilled, not to call too soon (optimization) */
   private boolean checkRefill() {
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

    /* Getters and setters*/
    public Item[][] getLivingRoom() {return livingRoom;}
    public Item getLivingRoomItem (int row,int column) { return livingRoom[row][column];}
    public int[][] getMask() {
        return mask;
    }
    public int getNumPlayers() {
        return numPlayers;
    }
}
