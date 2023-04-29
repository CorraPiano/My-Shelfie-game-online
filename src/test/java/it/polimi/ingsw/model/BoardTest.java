package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.EmptySlotException;
import it.polimi.ingsw.exception.GameRulesViolationException;
import it.polimi.ingsw.exception.OutOfBoardException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @Test
    void drawBoardItems() {
        board = new Board(3,null);

        /* Players different board check */
        board.drawBoardItems();
        assertTrue(board.getLivingRoomItem(3,1) == null);
    }

    @Test
    void isCatchableTest() throws EmptySlotException, GameRulesViolationException, OutOfBoardException {

    }


    @Test
    void checkRefill() {
    }

    @Test
    void emptyBoard() {
    }

    @Test
    void refillBoard() {
    }
}

