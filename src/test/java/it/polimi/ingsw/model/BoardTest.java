package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

/*drawBoardItems TESTS
 - drawBoardItemsNull : elements correctly "masked" have to be non-null items
 -
 */
    @Test
    void drawBoardItemsNull() {
        board = new Board(2); // contains drawBoardItems
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getMask()[i][j] <= 2) {
                    assertTrue(board.getLivingRoomItem(i,j) != null);
                }
                else assertFalse(board.getLivingRoomItem(i,j) != null);
            }
        }
    }

// isCatchable TESTS : every case working
    @Test
    void isCatchableTest() {}
    @Test
    void isCatchableTestNegativeInput(){
        board = new Board(2);
        assertFalse(board.isCatchable(-1,-2));
    }
    @Test
    void checkRefill() {}
    @Test
    void emptyBoard() {}
    @Test
    void refillBoard() {}
    @Test
    void getItemList() {}
}