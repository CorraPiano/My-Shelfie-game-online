package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.util.Constants;

import static it.polimi.ingsw.util.Constants.nColumnBoard;
import static it.polimi.ingsw.util.Constants.nRowBoard;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest1 {
    private Board board;
    private Hand hand;
    final int[][] mask = {
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

    @BeforeEach
    void setUp() {
        hand = new Hand();
        board = new Board(4, new Hand()); }

    @Test
    void testIsCatchableNullCoordinates() {
        Coordinates coordinates = new Coordinates(-1,4);
        assertThrows(OutOfBoardPickException.class, () -> board.isCatchable(coordinates));
    }

    @Test
    void checkRefillTest() {
        boolean check = false;
        for (int i = 0; i < nRowBoard; i++){
            for (int j = 0; j < nColumnBoard; j++){
                if (board.getLivingRoomItem(new Coordinates(i,j)) != null){
                    check = true;
                }
            }
        }
        if (check){
            assertEquals(false, board.checkRefill());
        }
        assertEquals(true, board.checkRefill());

    }

    @Test
    void drawBoardItemTest(){
        for (int i = 0; i < nRowBoard; i++) {
            for (int j = 0; j < nColumnBoard; j++) {
                if (mask[i][j] == 5) {
                    assertNull(board.getLivingRoomItem(new Coordinates(i,j)));
                }

            }
        }
    }

    @Test
    void endTurn() {
        board.endTurn();
        assertEquals(0, hand.getSize());
    }

    @Test
    void getLivingRoomTest() {
        assertNotNull(board.getLivingRoom());

    }

    @Test
    void getMaskTest() {
        assertNotNull(board.getMask());
    }

    @Test
    void getNumPlayersTest() {
        assertEquals(4, board.getNumPlayers());
    }

    @Test
    void getLocal() {
        assertNotNull(board.getLocal());
    }

    @Test
    void getItem_OutOfBoardCoordinates_ThrowsOutOfBoardPickException() {
        Coordinates coordinates = new Coordinates(-1, 4);

        assertThrows(OutOfBoardPickException.class, () -> board.getItem(coordinates));
        assertEquals(0, hand.getSize()); // Hand should remain empty
    }

    @Test
    void getItem_EmptySlot_ThrowsEmptySlotPickException() {
        Coordinates coordinates = new Coordinates(3, 5);

        assertThrows(EmptySlotPickException.class, () -> board.getItem(coordinates));
        assertEquals(0, hand.getSize()); // Hand should remain empty
    }

    @Test
    void isCatchable_OutOfBoardCoordinates_ThrowsOutOfBoardPickException() {
        Coordinates coordinates = new Coordinates(-1, 4);
        assertThrows(OutOfBoardPickException.class, () -> board.isCatchable(coordinates));
    }

    @Test
    void isCatchable_CatchableCoordinates_ReturnsTrue() throws NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, OutOfBoardPickException {
        Coordinates coordinates = new Coordinates(4, 4);
        Item item = new Item(ItemType.CYAN);
        board.getLivingRoom()[4][4] = item; // Set a non-null item at the given coordinates

        assertTrue(board.isCatchable(coordinates));
    }

    @Test
    void checkRefill_BoardIsFull() {
        for (int row = 0; row < nRowBoard; row++) {
            for (int col = 0; col < nColumnBoard; col++) {
                Item item = new Item(ItemType.CYAN);
                board.getLivingRoom()[row][col] = item;
            }
        }

        board.checkRefill();

        // Ensure that all slots still contain items
        for (int row = 0; row < nRowBoard; row++) {
            for (int col = 0; col < nColumnBoard; col++) {
                assertNotNull(board.getLivingRoom()[row][col]);
            }
        }
    }


}