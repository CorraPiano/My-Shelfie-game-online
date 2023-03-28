package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private Item[][] livingRoom;
    private BagItem bagItem;
    private int[][] mask = {{5, 5, 5, 3, 4, 5, 5, 5, 5}, // 5 if not fillable, 2,3,4, represent the number of players to be fillable
            {5, 5, 5, 2, 2, 4, 5, 5, 5},
            {5, 5, 3, 2, 2, 2, 3, 5, 5},
            {5, 4, 2, 2, 2, 2, 2, 2, 3},
            {4, 2, 2, 2, 2, 2, 2, 2, 4},
            {3, 2, 2, 2, 2, 2, 2, 4, 5},
            {5, 5, 3, 2, 2, 2, 3, 5, 5},
            {5, 5, 5, 4, 2, 2, 5, 5, 5},
            {5, 5, 5, 5, 4, 3, 5, 5, 5}
    };

    private int numeroGiocatori; // ONLY FOR TESTING,GAMEPLAY NEEDED

    //BOARD CREATORS
    public Board() {
        initializeBoard();
        generateNumPlayers();
        drawBoardItems();
    }

    private void generateNumPlayers() {
        int randomNum = new Random().nextInt(2, 4);
        this.numeroGiocatori = randomNum;
    }

    public void initializeBoard() {
        int nColumns = 9;
        int nRows = 9;
        this.livingRoom = new Item[nRows][nColumns];
        this.bagItem = new BagItem();
    }

    public void drawBoardItems() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mask[i][j] <= numeroGiocatori) {
                    livingRoom[i][j] = bagItem.drawItem();
                }
            }
        }
    }

    //OTHER METHODS
    public boolean isCatchable(int row, int column) {
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

    public boolean checkRefill() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mask[i][j] <= numeroGiocatori) {
                    if (isCatchable(i, j)) {
                        return true;
                    }
                }
            }
        }
        return false;
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

    public List<Item> getItemList(ArrayList<Coordinates> coordinatesList) {
        List<Item> itemList = new ArrayList<Item>();
        for (int i = 0; i < coordinatesList.size(); i++) {
            int row = coordinatesList.get(i).getRow();
            int column = coordinatesList.get(i).getColumn();
            if (isCatchable(row,column)){
                itemList.add(livingRoom[row][column]);
            }
        }
        return itemList;
    }

}
