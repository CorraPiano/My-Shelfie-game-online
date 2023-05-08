package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Listener;
import it.polimi.ingsw.exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
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

    @Test
    void drawBoardItemsTest() {
        int num=0;
        Board board;
        while(num<6) {
            board = new Board(num, null,new Listener(0,null));
            board.drawBoardItems();
            int i, j;
            System.out.println("NUMERO GIOCATORI: "+num);
            for (i = -1; i < 9; i++) {
                for (j = -1; j < 9; j++) {
                    if(i==-1 && j==-1)
                        System.out.print("/  ");
                    else if(i==-1 )
                        System.out.print(j+"  ");
                    else if(j==-1 )
                        System.out.print(i+"  ");
                    else if (mask[i][j] > num) {
                        Assertions.assertNull(board.getLivingRoomItem(new Coordinates(i,j)));
                        System.out.print("*  ");
                    }
                    else {
                        Assertions.assertNotNull(board.getLivingRoomItem(new Coordinates(i,j)));
                        System.out.print(board.getLivingRoomItem(new Coordinates(i,j)).getType().getValue()+"  ");
                    }
                }
                System.out.print("\n");
            }
            num++;
            System.out.print("\n");
        }
    }

    @Test
    void getItemTest() {
        Hand hand = new Hand(null);
        Board board = new Board(2, hand,new Listener(0,null));
        try {
            board.getItem(new Coordinates(1, 3));
            fail();
        } catch(Exception e){ }
        board.drawBoardItems();
        try {
            board.getItem(new Coordinates(1, 3));
            board.getItem(new Coordinates(1, 4));
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
        catch(Exception e){ fail();}

        //NotLinearPickException
        try {
            board.getItem(new Coordinates(4, 1));
            board.getItem(new Coordinates(5, 1));
            board.getItem(new Coordinates(5, 2));
            fail();
        } catch(NotLinearPickException e){ }
        catch(Exception e){ fail();}
        board.releaseHand();
        try {
            board.getItem(new Coordinates(3, 6));
            board.getItem(new Coordinates(3, 7));
            board.getItem(new Coordinates(4, 7));
            fail();
        } catch(NotLinearPickException e){ }
        catch(Exception e){ fail();}
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
    void checkRefill() {
    }

    @Test
    void emptyBoard() {
    }

    @Test
    void refillBoard() {
    }
}


