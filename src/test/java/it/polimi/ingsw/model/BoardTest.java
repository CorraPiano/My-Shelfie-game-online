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
        board = new Board(); // contains drawBoardItems
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getMask()[i][j] <= board.getNumeroGiocatori()) {
                    assertTrue(board.getLivingRoomItem(i,j) != null);
                }
                else assertFalse(board.getLivingRoomItem(i,j) != null);
            }
        }
    }

// isCatchable TESTS : every case working
    @Test
    void isCatchableTest() {
        board = new Board();
        if (board.getNumeroGiocatori() == 3){
            assertTrue(board.isCatchable(2,2));
            assertTrue(board.isCatchable(2,6));
            assertTrue(board.isCatchable(3,8));
            assertTrue(board.isCatchable(5,0));
            assertTrue(board.isCatchable(6,2));
            assertTrue(board.isCatchable(6,6));
            assertTrue(board.isCatchable(8,5));
            assertFalse(board.isCatchable(0,4));
            assertFalse(board.isCatchable(1,5));
            assertFalse(board.isCatchable(3,1));
            assertFalse(board.isCatchable(4,0));
            assertFalse(board.isCatchable(4,8));
            assertFalse(board.isCatchable(5,7));
            assertFalse(board.isCatchable(7,3));
            assertFalse(board.isCatchable(8,4));
        }
        else if (board.getNumeroGiocatori() == 4) {
            assertTrue(board.isCatchable(2,2));
            assertTrue(board.isCatchable(2,6));
            assertTrue(board.isCatchable(3,8));
            assertTrue(board.isCatchable(5,0));
            assertTrue(board.isCatchable(6,2));
            assertTrue(board.isCatchable(6,6));
            assertTrue(board.isCatchable(8,5));
            assertTrue(board.isCatchable(0,4));
            assertTrue(board.isCatchable(1,5));
            assertTrue(board.isCatchable(3,1));
            assertTrue(board.isCatchable(4,0));
            assertTrue(board.isCatchable(4,8));
            assertTrue(board.isCatchable(5,7));
            assertTrue(board.isCatchable(7,3));
            assertTrue(board.isCatchable(8,4));
        }
        else if (board.getNumeroGiocatori() == 2){
            assertFalse(board.isCatchable(0,4));
            assertFalse(board.isCatchable(1,5));
            assertFalse(board.isCatchable(3,1));
            assertFalse(board.isCatchable(4,0));
            assertFalse(board.isCatchable(4,8));
            assertFalse(board.isCatchable(5,7));
            assertFalse(board.isCatchable(7,3));
            assertFalse(board.isCatchable(8,4));
            assertFalse(board.isCatchable(2, 2));
            assertFalse(board.isCatchable(2, 6));
            assertFalse(board.isCatchable(3, 8));
            assertFalse(board.isCatchable(5, 0));
            assertFalse(board.isCatchable(6, 2));
            assertFalse(board.isCatchable(6, 6));
            assertFalse(board.isCatchable(8, 5));
        }
    }
    @Test
    void isCatchableTestNegativeInput(){
        board = new Board();
        assertFalse(board.isCatchable(-1,-2));
    }

    @Test
    void checkRefill() {
    }

    @Test
    void emptyBoard() {}

    @Test
    void refillBoard() {}

    @Test
    void getItemList() {}
}