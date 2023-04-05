package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

/*drawBoardItems TESTS
 - drawBoardItemsNull : elements correctly "masked" have to be non-null items, checked for all players cases
*/
    @Test
    void drawBoardItemsNull() {
        board = new Board(2); // contains drawBoardItems
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getMask()[i][j] <= board.getNumPlayers()) {
                    assertTrue(board.getLivingRoomItem(i,j) != null);
                }
                else assertFalse(board.getLivingRoomItem(i,j) != null);
            }
        }
    }
    // isCatchable TESTS : every single case working
    @Test
    void isCatchableTest() {
        board = new Board(2);
        if (board.getNumPlayers() == 3){
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
        else if (board.getNumPlayers() == 4) {
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
        else if (board.getNumPlayers() == 2){
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
        board = new Board(2);
        assertFalse(board.isCatchable(-1,-2));
    }
    @Test
    void checkRefill() {
        board = new Board (2);
        board.emptyBoard();
        assertTrue(board.checkRefill());
    }
    @Test
    void emptyBoard() {
        board = new Board(2);
        board.emptyBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertTrue(board.getLivingRoomItem(i,j)==null);
            }
        }
    }
    @Test
    void refillBoard() {
        board = new Board(2);
        board.emptyBoard();
        board.refillBoard();
        assertTrue(board.getLivingRoomItem(5,5)!= null);
        assertTrue ( board.getLivingRoomItem(2,0) == null);
    }
    @Test
    void getItemList() {

    }
    @Test
    void putItem(){

    }
}