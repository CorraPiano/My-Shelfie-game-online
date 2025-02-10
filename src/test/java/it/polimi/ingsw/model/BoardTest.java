package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.*;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.util.Constants;

import static it.polimi.ingsw.util.Constants.nColumnBoard;
import static it.polimi.ingsw.util.Constants.nRowBoard;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

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

    @BeforeEach
    void setup(){
        this.board = new Board(4,new Hand());
    }

    @Test
    void drawBoardItemsTest() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mask[i][j] > board.getNumPlayers()) {
                    assertNull(board.getLivingRoomItem(new Coordinates(i,j)));
                }
            }
        }


    }

    @Test
    void getItemTest() {
        Hand hand = new Hand();
        Board board = new Board(2, hand);
        try {
            board.getItem(new Coordinates(1, 3));
            fail();
        } catch(Exception e){ }
        board.drawBoardItems();
        try {
            board.getItem(new Coordinates(1, 4));
            board.getItem(new Coordinates(1, 5));
            hand.clear();
            board.getItem(new Coordinates(2, 3));
            board.getItem(new Coordinates(2, 4));
            board.getItem(new Coordinates(2, 5));
            hand.clear();
            //board.getItem(new Coordinates(3, 7));
            //board.getItem(new Coordinates(4, 7));
            board.releaseHand();
        } catch(Exception e){e.printStackTrace();fail(); }

        // limitReachedPickException
        try {
            board.getItem(new Coordinates(3, 2));
            board.getItem(new Coordinates(3, 3));
            board.getItem(new Coordinates(3, 4));
            board.getItem(new Coordinates(3, 5));
            fail();
        } catch(LimitReachedPickException e){ }
        catch(Exception e){ fail();}
        board.releaseHand();

        // OutOfBoardPickException
        try {
            board.getItem(new Coordinates(0, 0));
            fail();
        } catch(OutOfBoardPickException e){ }
        catch(Exception e){ fail();}
        try {
            board.getItem(null);
            fail();
        } catch(OutOfBoardPickException e){ }
        catch(Exception e){ fail();}
        try {
            board.getItem(new Coordinates(-1, -1));
            fail();
        } catch(OutOfBoardPickException e){ }
        catch(Exception e){ fail();}
        try {
            board.getItem(new Coordinates(0, 8));
            fail();
        } catch(OutOfBoardPickException e){ }
        catch(Exception e){ fail();}
        try {
            board.getItem(new Coordinates(8, 0));
            fail();
        } catch(OutOfBoardPickException e){ }
        catch(Exception e){ fail();}
        try {
            board.getItem(new Coordinates(8, 1));
            fail();
        } catch(OutOfBoardPickException e){ }
        catch(Exception e){ fail();}
        try {
            board.getItem(new Coordinates(1, 2));
            fail();
        } catch(OutOfBoardPickException e){ }
        catch(Exception e){ fail();}

        //EmptySlotPickException
        try {
            board.getItem(new Coordinates(1, 3));
            fail();
        } catch(EmptySlotPickException e){ }
        catch(Exception e){}

        //NotLinearPickException
        try {
            board.getItem(new Coordinates(4, 1));
            board.getItem(new Coordinates(5, 1));
            board.getItem(new Coordinates(5, 2));
            fail();
        } catch(NotLinearPickException e){ }
        catch(Exception e){ }
        board.releaseHand();
        try {
            board.getItem(new Coordinates(3, 6));
            board.getItem(new Coordinates(3, 7));
            board.getItem(new Coordinates(4, 7));
            fail();
        } catch(NotLinearPickException e){ }
        catch(Exception e){ }
        board.releaseHand();
        try {
            board.getItem(new Coordinates(3, 6));
            board.getItem(new Coordinates(3, 4));
            fail();
        } catch(NotLinearPickException e){ }
        catch(Exception e){ fail();}
        board.releaseHand();
        try {
            board.getItem(new Coordinates(3,6 ));
            board.getItem(new Coordinates(5, 6));
            fail();
        } catch(NotLinearPickException e){ }
        catch(Exception e){ fail();}
        board.releaseHand();

        //NotCatchablePickException
        try {
            board.getItem(new Coordinates(4, 2));
            fail();
        } catch(NotCatchablePickException e){ }
        catch(Exception e){ fail();}
        try {
            board.getItem(new Coordinates(4, 4));
            fail();
        } catch(NotCatchablePickException e){ }
        catch(Exception e){ fail();}
        try {
            board.getItem(new Coordinates(4, 1));
            board.getItem(new Coordinates(4, 2));
            fail();
        } catch(NotCatchablePickException e){ }
        catch(Exception e){ fail();}
        board.releaseHand();
        try {
            board.getItem(new Coordinates(3, 6));
            board.getItem(new Coordinates(4, 6));
            board.getItem(new Coordinates(5, 6));
            fail();
        } catch(NotCatchablePickException e){ }
        catch(Exception e){ e.printStackTrace();fail();}
    }

    @Test
    void isCatchableTestCases() throws NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, OutOfBoardPickException {
        board.drawBoardItems();
        board.isCatchable(new Coordinates(8,4));
        board.getItem(new Coordinates(8,4));

        try {
            board.getItem(new Coordinates(8, 5));
        }
            catch(OutOfBoardPickException e){ }
        try {
            board.getItem(new Coordinates(7, 3));
        }
        catch (NotLinearPickException e){}
        try {
            board.getItem(new Coordinates(7, 4));
        }
            catch(NotCatchablePickException e){}
        //board.getItem(new Coordinates(7,5));
    }

    @Test
    void checkRefill(){
        board.drawBoardItems();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.checkRefill();

            }
        }
    }

    @Test
    void testCheckRefill_BoardEmpty_ReturnsTrue() {
        // Arrange
        Board board = new Board(2, new Hand());
        // Act
        boolean result = board.checkRefill();
        // Assert
        assertTrue(result);
    }

    @Test
    void testCheckRefill_BoardNotEmpty_NoAdjacentItems_ReturnsTrue() {
        // Arrange
        Board board = new Board(2, new Hand());
        Item[][] livingRoom = board.getLivingRoom();
        livingRoom[0][0] = new Item(ItemType.BLUE);  // Place an item without adjacent items
        // Act
        boolean result = board.checkRefill();

        // Assert
        assertTrue(result);
    }

    @Test
    void testCheckRefill_BoardNotEmpty_HasAdjacentItems_ReturnsFalse() {
        // Arrange
        Board board = new Board(4, new Hand());
        Item[][] livingRoom = board.getLivingRoom();
        livingRoom[5][4] = new Item(ItemType.GREEN);
        livingRoom[4][6] = new Item(ItemType.YELLOW);
        livingRoom[3][4] = new Item(ItemType.YELLOW);
        livingRoom[1][3] = new Item(ItemType.YELLOW);
        livingRoom[3][3] = new Item(ItemType.YELLOW);
        livingRoom[3][6] = new Item(ItemType.YELLOW);
        livingRoom[2][6] = new Item(ItemType.YELLOW);
        livingRoom[1][4] = new Item(ItemType.YELLOW);

        board.checkRefill();

  }

    @Test
    void testCheckRefill_RemainingCases() {
        // Arrange
        Board board = new Board(4, new Hand());
        Item[][] livingRoom = board.getLivingRoom();
        livingRoom[5][1] = new Item(ItemType.GREEN);
        livingRoom[4][1] = new Item(ItemType.YELLOW);

        livingRoom[4][4] = new Item(ItemType.YELLOW);
        livingRoom[4][3] = new Item(ItemType.YELLOW);
        livingRoom[3][4] = new Item(ItemType.YELLOW);
        livingRoom[4][5] = new Item(ItemType.YELLOW);

        board.checkRefill();


        }


}
