package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.EmptySlotException;
import it.polimi.ingsw.exception.GameRulesViolationException;
import it.polimi.ingsw.exception.OutOfBoardException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class Board {
    private Item[][] livingRoom;
    static final int nColumns = 9;
    static final int nRows = 9;
    private int numPlayers;
    private BagItem bagItem;
    private int[][] mask = {{5, 5, 5, 3, 4, 5, 5, 5, 5},
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
    private PropertyChangeListener listener;

    public Board(int numPlayers, Hand hand) {
        this.livingRoom = new Item[nRows][nColumns];
        this.bagItem = new BagItem();
        this.numPlayers = numPlayers;
        this.hand = hand;
    }

    /* Draws random items to fill the board */
    public void drawBoardItems() {
        Item[][] living_tmp = getLivingRoom();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mask[i][j] <= numPlayers && livingRoom[i][j] == null) {
                    livingRoom[i][j] = bagItem.drawItem();
                }
            }
        }
        PropertyChangeEvent evt = new PropertyChangeEvent(this, "Items_drawn_change", living_tmp , this.livingRoom);
        this.listener.propertyChange(evt);
    }

    /* True if we can pick an item from the board */
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

    /* Returns true if the board has to be refilled, not to call too soon (optimization) */
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

    /* Puts items left on the board back into the bag */
    public void putBackInBag() {
        BagItem bag_tmp = bagItem;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (livingRoom[i][j] != null){
                    bagItem.addBack(livingRoom[i][j]);
                    livingRoom[i][j] = null;
                }
            }
        }
        PropertyChangeEvent evt = new PropertyChangeEvent(this, "Bag_Change",bag_tmp, this.livingRoom);
        this.listener.propertyChange(evt);
    }

    /* Refills the board when needed */
    public void refillBoard() {
        if (checkRefill()) {
            putBackInBag();
            drawBoardItems();
        }
    }

    /* Item pick */
    public void getItem (Coordinates coordinates) throws EmptySlotException, GameRulesViolationException, OutOfBoardException {
        /* Listener's purpose */
        Item[][] living_tmp = getLivingRoom();
        Hand hand_tmp = this.hand;

        if (coordinates != null) {
            int row = coordinates.getRow();
            int column = coordinates.getColumn();
            if (isCatchable(row,column)) {
                hand.putItem(livingRoom[row][column], new Coordinates(row,column));
                livingRoom[row][column] = null;
            }
        }
        PropertyChangeEvent evt = new PropertyChangeEvent(this, "Item_picked_change", living_tmp , this.livingRoom);
        PropertyChangeEvent evt_1 = new PropertyChangeEvent(this, "Hand_change", hand_tmp , this.hand);
        this.listener.propertyChange(evt);
        this.listener.propertyChange(evt_1);

    }

    /* Puts the hand back into the board, undo operation */
    public void releaseHand () {
        /* Listener's purpose */
        Item[][] living_tmp = getLivingRoom();
        Hand hand_tmp = this.hand;

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
        PropertyChangeEvent evt = new PropertyChangeEvent(this, "Item_picked_change", living_tmp , this.livingRoom);
        PropertyChangeEvent evt_1 = new PropertyChangeEvent(this, "Hand_change", hand_tmp , this.hand);
        this.listener.propertyChange(evt);
        this.listener.propertyChange(evt_1);

    }

    /* Getter e setter del listener */
    public PropertyChangeListener getListener() {
        return listener;
    }
    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
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
