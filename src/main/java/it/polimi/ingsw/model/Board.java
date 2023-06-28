package it.polimi.ingsw.model;

import it.polimi.ingsw.client.localModel.LocalBoard;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.util.Loader;

import java.util.*;

public class Board extends Listenable {
    private final Item[][] livingRoom;
    static final int nColumns = 9;
    static final int nRows = 9;
    private final int numPlayers;
    private final BagItem bagItem;
    private final int[][] mask;
    private final Hand hand;

    /**
     * Default constructor.
     *
     * @param numPlayers int, number of players.
     * @param hand empty initial hand.
     */
    public Board(int numPlayers, Hand hand) {
        this.livingRoom = new Item[nRows][nColumns];
        this.bagItem = new BagItem();
        this.numPlayers = numPlayers;
        this.hand = hand;
        this.mask = new Loader().LoadBoard();
    }

    /**
     * Draws random items to fill the board.
     *
     */
    public void drawBoardItems() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mask[i][j] <= numPlayers) {
                    livingRoom[i][j] = bagItem.drawItem();
                }
            }
        }
        this.notifyUpdate();
    }

    /**
     * This is called when a player picks an item from the board.
     *
     * @param coordinates coordinates of element to pick.
     * @throws LimitReachedPickException when too many items get selected.
     * @throws OutOfBoardPickException when player tries to pick an element that's out of the board.
     * @throws EmptySlotPickException when player tries to pick an item in an empty spot.
     * @throws NotLinearPickException when player tries to make a non-linear pick.
     * @throws NotCatchablePickException every other not pickable cases.
     */
    public void getItem (Coordinates coordinates) throws LimitReachedPickException, OutOfBoardPickException, EmptySlotPickException, NotLinearPickException, NotCatchablePickException {
        if (coordinates != null) {
            if (isCatchable(coordinates)) {
                int row = coordinates.getRow();
                int column = coordinates.getColumn();
                hand.putItem(livingRoom[row][column], coordinates);
                livingRoom[row][column] = null;
                this.notifyUpdate();
            }
            else
                throw new NotLinearPickException();
        }
        else
            throw new OutOfBoardPickException();
    }

    /**
     * True if we can pick an item from the board.
     *
     * @param coordinates coordinates to check.
     * @return boolean, true if an item can be picked from the board.
     * @throws LimitReachedPickException when too many items get selected.
     * @throws OutOfBoardPickException when player tries to pick an element that's out of the board.
     * @throws EmptySlotPickException when player tries to pick an item in an empty spot.
     * @throws NotLinearPickException when player tries to make a non-linear pick.
     * @throws NotCatchablePickException every other not pickable cases.
     */
    public boolean isCatchable(Coordinates coordinates) throws LimitReachedPickException, OutOfBoardPickException, EmptySlotPickException, NotCatchablePickException, NotLinearPickException {
        int row = coordinates.getRow();
        int column = coordinates.getColumn();
        boolean catchable = false;


        if(hand.getSize() > 2) {
            throw new LimitReachedPickException();
        }
        if ((row < 0 || row > 8) || (column < 0 || column > 8)) {
            throw new OutOfBoardPickException();
        }
        if (mask[row][column]>numPlayers) {
            throw new OutOfBoardPickException();
        }

        if (livingRoom[row][column] == null) {
            throw new EmptySlotPickException();
        }
        if (row == 0 || row == 8 || column == 0 || column == 8){
            catchable = true;
        }
        else if (livingRoom[row - 1][column] == null && !hand.containsCoords(new Coordinates(row - 1, column))) {
            catchable = true;
        }
        else if (livingRoom[row + 1][column] == null && !hand.containsCoords(new Coordinates(row + 1, column))) {
            catchable = true;
        }
        else if (livingRoom[row][column - 1] == null && !hand.containsCoords(new Coordinates(row, column - 1))) {
            catchable = true;
        }
        else if (livingRoom[row][column + 1] == null && !hand.containsCoords(new Coordinates(row, column + 1))) {
            catchable = true;
        }
        if(catchable)
              return hand.checkNewCoordinates(coordinates);
        throw new NotCatchablePickException();
    }

    /**
     * Puts the hand back into the board, undo operation.
     *
     */
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
            this.notifyUpdate();
        }
    }

    /**
     * Does everything that needs to be done once player's turn is ended.
     *
     */
    public void endTurn() {
        hand.clear();
        if (checkRefill()) {
            putBackInBag();
            drawBoardItems();
        }
        this.notifyUpdate();
    }

    /**
     * Puts items left on the board back into the bag.
     *
     */
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

    /**
     * Checks if the board needs to be refilled.
     *
     * @return boolean, returns true if the board has to be refilled.
     */
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

    /**
     * Living room getter. The living room is the actual table.
     *
     * @return returns living room, a matrix of items.
     */
    public Item[][] getLivingRoom() { return livingRoom; }

    /**
     * Getter for just an item.
     *
     * @param coordinates of the elements to get.
     * @return returns an item from the board.
     */
    public Item getLivingRoomItem (Coordinates coordinates) {
        return livingRoom[coordinates.getRow()][coordinates.getColumn()];
    }

    /**
     * Getter for the mask. The mask is a matrix used to check the actual fillable spots
     * based on the number of players.
     *
     * @return matrix of int, board's mask.
     */
    public int[][] getMask() {
        return mask;
    }

    /**
     * Gets the number of players.
     *
     * @return int, number of players.
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    public void setLivingRoomItem(Coordinates coordinates, Item item){
        this.livingRoom[coordinates.getRow()][coordinates.getColumn()] = item;
    }

    /**
     * Method used to create a LocalBoard. This object is used to minimize information
     * sent on network.
     *
     * @return Sendable, returns a LocalBoard.
     */
    @Override
    public Sendable getLocal() {
        // passing a copy of matrix's state, not a reference
        Item[][] board = new Item[nRows][nColumns];
        for(int i=0;i<nRows;i++)
            System.arraycopy(livingRoom[i], 0, board[i], 0, nRows);
        return new LocalBoard(board);
    }
}
