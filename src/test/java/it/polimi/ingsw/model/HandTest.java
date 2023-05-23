package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.WrongContentOrderException;
import it.polimi.ingsw.exception.WrongLengthOrderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class HandTest {

    @Test
    void putAndClearCheck(){
        Hand hand = new Hand();
        Assertions.assertEquals(hand.getSize(),0);
        Assertions.assertTrue(hand.getHand().isEmpty());
        Assertions.assertTrue(hand.getCoordinatesList().isEmpty());
        Assertions.assertFalse(hand.containsCoords(new Coordinates(0, 0)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(0, 1)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(1, 1)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(1, 0)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(-1, -1)));

        hand.putItem(new Item(ItemType.BLUE),new Coordinates(0,0));
        Assertions.assertEquals(hand.getSize(),1);
        Assertions.assertEquals(hand.getHand().size(),1);
        Assertions.assertEquals(hand.getCoordinatesList().size(),1);
        Assertions.assertEquals(hand.getHand().get(0).getType(),ItemType.BLUE);
        Assertions.assertEquals(hand.getCoordinatesList().get(0),new Coordinates(0,0));
        Assertions.assertTrue(hand.containsCoords(new Coordinates(0,0)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(0, 1)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(1, 1)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(1, 0)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(-1, -1)));

        hand.putItem(new Item(ItemType.GREEN),new Coordinates(0,1));
        Assertions.assertEquals(hand.getSize(),2);
        Assertions.assertEquals(hand.getHand().size(),2);
        Assertions.assertEquals(hand.getCoordinatesList().size(),2);
        Assertions.assertEquals(hand.getHand().get(0).getType(),ItemType.BLUE);
        Assertions.assertEquals(hand.getCoordinatesList().get(0),new Coordinates(0,0));
        Assertions.assertEquals(hand.getHand().get(1).getType(),ItemType.GREEN);
        Assertions.assertEquals(hand.getCoordinatesList().get(1),new Coordinates(0,1));
        Assertions.assertTrue(hand.containsCoords(new Coordinates(0,0)));
        Assertions.assertTrue(hand.containsCoords(new Coordinates(0,1)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(1, 1)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(1, 0)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(-1, -1)));

        hand.putItem(new Item(ItemType.CYAN),new Coordinates(1,1));
        Assertions.assertEquals(hand.getSize(),3);
        Assertions.assertEquals(hand.getHand().size(),3);
        Assertions.assertEquals(hand.getCoordinatesList().size(),3);
        Assertions.assertEquals(hand.getHand().get(0).getType(),ItemType.BLUE);
        Assertions.assertEquals(hand.getCoordinatesList().get(0),new Coordinates(0,0));
        Assertions.assertEquals(hand.getHand().get(1).getType(),ItemType.GREEN);
        Assertions.assertEquals(hand.getCoordinatesList().get(1),new Coordinates(0,1));
        Assertions.assertEquals(hand.getHand().get(2).getType(),ItemType.CYAN);
        Assertions.assertEquals(hand.getCoordinatesList().get(2),new Coordinates(1,1));
        Assertions.assertTrue(hand.containsCoords(new Coordinates(0,0)));
        Assertions.assertTrue(hand.containsCoords(new Coordinates(0,1)));
        Assertions.assertTrue(hand.containsCoords(new Coordinates(1,1)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(1, 0)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(-1, -1)));

        hand.putItem(new Item(ItemType.WHITE),new Coordinates(1,0));
        Assertions.assertEquals(hand.getSize(),4);
        Assertions.assertEquals(hand.getHand().size(),4);
        Assertions.assertEquals(hand.getCoordinatesList().size(),4);
        Assertions.assertEquals(hand.getHand().get(0).getType(),ItemType.BLUE);
        Assertions.assertEquals(hand.getCoordinatesList().get(0),new Coordinates(0,0));
        Assertions.assertEquals(hand.getHand().get(1).getType(),ItemType.GREEN);
        Assertions.assertEquals(hand.getCoordinatesList().get(1),new Coordinates(0,1));
        Assertions.assertEquals(hand.getHand().get(2).getType(),ItemType.CYAN);
        Assertions.assertEquals(hand.getCoordinatesList().get(2),new Coordinates(1,1));
        Assertions.assertEquals(hand.getHand().get(3).getType(),ItemType.WHITE);
        Assertions.assertEquals(hand.getCoordinatesList().get(3),new Coordinates(1,0));
        Assertions.assertTrue(hand.containsCoords(new Coordinates(0,0)));
        Assertions.assertTrue(hand.containsCoords(new Coordinates(0,1)));
        Assertions.assertTrue(hand.containsCoords(new Coordinates(1,1)));
        Assertions.assertTrue(hand.containsCoords(new Coordinates(1,0)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(-1, -1)));

        hand.clear();
        Assertions.assertEquals(hand.getSize(),0);
        Assertions.assertTrue(hand.getHand().isEmpty());
        Assertions.assertTrue(hand.getCoordinatesList().isEmpty());
        Assertions.assertFalse(hand.containsCoords(new Coordinates(0, 0)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(0, 1)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(1, 1)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(1, 0)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(-1, -1)));

        hand.putItem(new Item(ItemType.YELLOW),new Coordinates(-1,-1));
        Assertions.assertEquals(hand.getSize(),1);
        Assertions.assertEquals(hand.getHand().size(),1);
        Assertions.assertEquals(hand.getCoordinatesList().size(),1);
        Assertions.assertEquals(hand.getHand().get(0).getType(),ItemType.YELLOW);
        Assertions.assertEquals(hand.getCoordinatesList().get(0),new Coordinates(-1,-1));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(0, 0)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(0, 1)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(1, 1)));
        Assertions.assertFalse(hand.containsCoords(new Coordinates(1, 0)));
        Assertions.assertTrue(hand.containsCoords(new Coordinates(-1,-1)));
    }
    @Test
    void selectOrderHandCheck(){
        Hand hand = new Hand();
        hand.putItem(new Item(ItemType.BLUE),new Coordinates(0,0));
        hand.putItem(new Item(ItemType.YELLOW),new Coordinates(0,1));
        hand.putItem(new Item(ItemType.CYAN),new Coordinates(1,0));
        hand.putItem(new Item(ItemType.GREEN),new Coordinates(1,1));
        Assertions.assertEquals(hand.getSize(),4);
        Assertions.assertEquals(hand.getHand().get(0).getType(),ItemType.BLUE);
        Assertions.assertEquals(hand.getCoordinatesList().get(0),new Coordinates(0,0));
        Assertions.assertEquals(hand.getHand().get(1).getType(),ItemType.YELLOW);
        Assertions.assertEquals(hand.getCoordinatesList().get(1),new Coordinates(0,1));
        Assertions.assertEquals(hand.getHand().get(2).getType(),ItemType.CYAN);
        Assertions.assertEquals(hand.getCoordinatesList().get(2),new Coordinates(1,0));
        Assertions.assertEquals(hand.getHand().get(3).getType(),ItemType.GREEN);
        Assertions.assertEquals(hand.getCoordinatesList().get(3),new Coordinates(1,1));

        ArrayList<Integer> order = new ArrayList<Integer>();
        Boolean ex = false;
        order.add(2);
        order.add(1);
        order.add(3);
        order.add(0);
        try {
            hand.selectOrder(order);
            Assertions.assertEquals(hand.getSize(),4);
            Assertions.assertEquals(hand.getHand().get(0).getType(),ItemType.CYAN);
            Assertions.assertEquals(hand.getCoordinatesList().get(0),new Coordinates(1,0));
            Assertions.assertEquals(hand.getHand().get(1).getType(),ItemType.YELLOW);
            Assertions.assertEquals(hand.getCoordinatesList().get(1),new Coordinates(0,1));
            Assertions.assertEquals(hand.getHand().get(2).getType(),ItemType.GREEN);
            Assertions.assertEquals(hand.getCoordinatesList().get(2),new Coordinates(1,1));
            Assertions.assertEquals(hand.getHand().get(3).getType(),ItemType.BLUE);
            Assertions.assertEquals(hand.getCoordinatesList().get(3),new Coordinates(0,0));
            Assertions.assertEquals(hand.getHand().size(),4);
            Assertions.assertEquals(hand.getCoordinatesList().size(),4);
        } catch(Exception e){
            ex = true;
        }
        Assertions.assertFalse(ex);

        order = new ArrayList<Integer>();
        ex = false;
        order.add(3);
        order.add(2);
        order.add(1);
        order.add(0);
        try {
            hand.selectOrder(order);
            Assertions.assertEquals(hand.getSize(),4);
            Assertions.assertEquals(hand.getHand().get(0).getType(),ItemType.BLUE);
            Assertions.assertEquals(hand.getCoordinatesList().get(0),new Coordinates(0,0));
            Assertions.assertEquals(hand.getHand().get(1).getType(),ItemType.GREEN);
            Assertions.assertEquals(hand.getCoordinatesList().get(1),new Coordinates(1,1));
            Assertions.assertEquals(hand.getHand().get(2).getType(),ItemType.YELLOW);
            Assertions.assertEquals(hand.getCoordinatesList().get(2),new Coordinates(0,1));
            Assertions.assertEquals(hand.getHand().get(3).getType(),ItemType.CYAN);
            Assertions.assertEquals(hand.getCoordinatesList().get(3),new Coordinates(1,0));
            Assertions.assertEquals(hand.getHand().size(),4);
            Assertions.assertEquals(hand.getCoordinatesList().size(),4);
        } catch(Exception e){
            ex = true;
        }
        Assertions.assertFalse(ex);

        order = new ArrayList<Integer>();
        ex = false;
        order.add(0);
        order.add(1);
        order.add(2);
        order.add(3);
        try {
            hand.selectOrder(order);
            Assertions.assertEquals(hand.getSize(),4);
            Assertions.assertEquals(hand.getHand().get(0).getType(),ItemType.BLUE);
            Assertions.assertEquals(hand.getCoordinatesList().get(0),new Coordinates(0,0));
            Assertions.assertEquals(hand.getHand().get(1).getType(),ItemType.GREEN);
            Assertions.assertEquals(hand.getCoordinatesList().get(1),new Coordinates(1,1));
            Assertions.assertEquals(hand.getHand().get(2).getType(),ItemType.YELLOW);
            Assertions.assertEquals(hand.getCoordinatesList().get(2),new Coordinates(0,1));
            Assertions.assertEquals(hand.getHand().get(3).getType(),ItemType.CYAN);
            Assertions.assertEquals(hand.getCoordinatesList().get(3),new Coordinates(1,0));
            Assertions.assertEquals(hand.getHand().size(),4);
            Assertions.assertEquals(hand.getCoordinatesList().size(),4);
        } catch(Exception e){
            ex = true;
        }
        Assertions.assertFalse(ex);

        order = new ArrayList<Integer>();
        ex = false;
        order.add(0);
        order.add(1);
        order.add(1);
        order.add(3);
        try {
            hand.selectOrder(order);
        } catch(Exception e){
            ex = true;
        }
        Assertions.assertTrue(ex);

        order = new ArrayList<Integer>();
        ex = false;
        order.add(3);
        order.add(2);
        order.add(1);
        order.add(-1);
        try {
            hand.selectOrder(order);
        } catch(Exception e){
            ex = true;
        }
        Assertions.assertTrue(ex);

        order = new ArrayList<Integer>();
        ex = false;
        order.add(4);
        order.add(5);
        order.add(6);
        order.add(7);
        try {
            hand.selectOrder(order);
        } catch(Exception e){
            ex = true;
        }
        Assertions.assertTrue(ex);

        order = new ArrayList<Integer>();
        ex = false;
        try {
            hand.selectOrder(order);
        } catch(Exception e){
            ex = true;
        }
        Assertions.assertTrue(ex);

        order = new ArrayList<Integer>();
        ex = false;
        order.add(4);
        try {
            hand.selectOrder(order);
        } catch(Exception e){
            ex = true;
        }
        Assertions.assertTrue(ex);

        order = new ArrayList<Integer>();
        ex = false;
        order.add(1);
        order.add(2);
        order.add(3);
        order.add(0);
        order.add(4);
        try {
            hand.selectOrder(order);
        } catch(Exception e){
            ex = true;
        }
        Assertions.assertTrue(ex);

    }
    @Test
    void pickItemCheckOrizzontal() {
        Hand hand = new Hand();
        Assertions.assertTrue(hand.checkNewCoordinates(new Coordinates(0, 0)));
        Assertions.assertTrue(hand.checkNewCoordinates(new Coordinates(1, 1)));

        hand.putItem(new Item(ItemType.BLUE),new Coordinates(1,1));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, 0)));
        Assertions.assertTrue(hand.checkNewCoordinates(new Coordinates(1, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(2, 0)));
        Assertions.assertTrue(hand.checkNewCoordinates(new Coordinates(0, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, 1)));
        Assertions.assertTrue(hand.checkNewCoordinates(new Coordinates(2, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, 2)));
        Assertions.assertTrue(hand.checkNewCoordinates(new Coordinates(1, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(2, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(-1, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, -1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(-1, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, -1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(3, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, 3)));

        hand.putItem(new Item(ItemType.BLUE),new Coordinates(1,2));
        Assertions.assertTrue(hand.checkNewCoordinates(new Coordinates(1, 3)));
        Assertions.assertTrue(hand.checkNewCoordinates(new Coordinates(1, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, -1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, 4)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, 3)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(2, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(2, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(2, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(2, 3)));

        hand.putItem(new Item(ItemType.BLUE),new Coordinates(1,0));
        Assertions.assertTrue(hand.checkNewCoordinates(new Coordinates(1, 3)));
        Assertions.assertTrue(hand.checkNewCoordinates(new Coordinates(1, -1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, -2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, 4)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, -1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, 3)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(2, -1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(2, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(2, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(2, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(2, 3)));

        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(99, 9)));
        hand.clear();
        Assertions.assertTrue(hand.checkNewCoordinates(new Coordinates(1, 1)));

    }

    @Test
    void pickItemCheckVertical() {
        Hand hand = new Hand();
        hand.putItem(new Item(ItemType.BLUE),new Coordinates(1,1));
        hand.putItem(new Item(ItemType.BLUE),new Coordinates(0,1));
        Assertions.assertTrue(hand.checkNewCoordinates(new Coordinates(-1, 1)));
        Assertions.assertTrue(hand.checkNewCoordinates(new Coordinates(2, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(-1, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(2, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(-1, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(2, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(-2, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(3, 1)));

        hand.putItem(new Item(ItemType.BLUE),new Coordinates(-1,1));
        hand.putItem(new Item(ItemType.BLUE),new Coordinates(2,1));
        Assertions.assertTrue(hand.checkNewCoordinates(new Coordinates(-2, 1)));
        Assertions.assertTrue(hand.checkNewCoordinates(new Coordinates(3, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(-2, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(-1, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(2, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(3, 0)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(-2, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(-1, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(2, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(3, 2)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(-3, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(-1, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(0, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(1, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(2, 1)));
        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(4, 1)));

        Assertions.assertFalse(hand.checkNewCoordinates(new Coordinates(99, 9)));
        hand.clear();
        Assertions.assertTrue(hand.checkNewCoordinates(new Coordinates(1, 1)));
    }

}
