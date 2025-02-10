package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.EmptySlotPickException;
import it.polimi.ingsw.exception.LimitReachedPickException;
import it.polimi.ingsw.exception.NotCatchablePickException;
import it.polimi.ingsw.exception.OutOfBoardPickException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest3 {
        private Board board;

        private Hand hand;

        @Test
        void putBackInBag(){
            board = new Board(4, new Hand());
            board.drawBoardItems();
            board.putBackInBag();
        }

        @Test
        void checkRefillTest(){
            board = new Board(4, new Hand());
            board.setLivingRoomItem(new Coordinates(4,5), new Item(ItemType.BLUE));
            board.checkRefill();
            board = new Board(4, new Hand());
            board.drawBoardItems();
            board.checkRefill();


        }

        @Test
        public void testIsCatchable_HandSizeGreaterThan2_ThrowsLimitReachedPickException() {
            Hand hand = new Hand();
            board = new Board(4, hand);

            hand.getHand().add(new Item(ItemType.BLUE));
            hand.getHand().add(new Item(ItemType.BLUE));
            hand.getHand().add(new Item(ItemType.BLUE));

            Coordinates coordinates = new Coordinates(0, 0);

            assertThrows(LimitReachedPickException.class, () -> board.isCatchable(coordinates));
        }

        @Test
        public void testIsCatchable_CoordinatesOutOfBoard_ThrowsOutOfBoardPickException() {
            Hand hand = new Hand();
            board = new Board(4, hand);
            Coordinates coordinates = new Coordinates(-1, 0);

            assertThrows(OutOfBoardPickException.class, () -> board.isCatchable(coordinates));
        }

        @Test
        public void testIsCatchable_CoordinatesMaskExceedsNumPlayers_ThrowsOutOfBoardPickException() {
            Hand hand = new Hand();
            board = new Board(4, hand);
            board.drawBoardItems();
            Coordinates coordinates = new Coordinates(0, 0);
            board.getMask()[0][0] = board.getNumPlayers() + 1;

            assertThrows(OutOfBoardPickException.class, () -> board.isCatchable(coordinates));
        }

        @Test
        public void testIsCatchable_NotCatchableCoordinates_ThrowsNotCatchablePickException() {
            board = new Board(4,new Hand());
            board.drawBoardItems();
            Coordinates coordinates = new Coordinates(5, 5);
            board.getLivingRoom()[1][1] = new Item(ItemType.BLUE);
            assertThrows(NotCatchablePickException.class, () -> board.isCatchable(coordinates));
        }


}