package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GameplayTest {

    @Test
    void calculatePointsTest1(){
        Gameplay gameplay=null;
        try {
            gameplay = new Gameplay(GameMode.EXPERT, 4,0);
            gameplay.addPlayer("a");
            gameplay.addPlayer("b");
            gameplay.addPlayer("c");
            gameplay.addPlayer("d");
            ArrayList<Player> players=gameplay.getPlayerList();
            players.get(0).setToken1(new Token(8));
            players.get(1).setToken1(new Token(6));
            players.get(2).setToken1(new Token(4));
            players.get(3).setToken1(new Token(2));
            gameplay.endGame();
            players=gameplay.getPlayerList();
            Assertions.assertEquals("a",players.get(0).getName());
            Assertions.assertEquals("b",players.get(1).getName());
            Assertions.assertEquals("c",players.get(2).getName());
            Assertions.assertEquals("d",players.get(3).getName());
        } catch(Exception e){}
    }

    @Test
    void calculatePointsTest2(){
        Gameplay gameplay = null;
        try {
            gameplay = new Gameplay(GameMode.EXPERT, 4,0);
            gameplay.addPlayer("a");
            gameplay.addPlayer("b");
            gameplay.addPlayer("c");
            gameplay.addPlayer("d");
            ArrayList<Player> players = gameplay.getPlayerList();
            players.get(0).setToken1(new Token(2));
            players.get(1).setToken1(new Token(4));
            players.get(2).setToken1(new Token(6));
            players.get(3).setToken1(new Token(8));
            gameplay.endGame();
            players = gameplay.getPlayerList();
            Assertions.assertEquals("d", players.get(0).getName());
            Assertions.assertEquals("c", players.get(1).getName());
            Assertions.assertEquals("b", players.get(2).getName());
            Assertions.assertEquals("a", players.get(3).getName());
        } catch (Exception e){};
    }

    @Test
    void calculatePointsTest3(){
        Gameplay gameplay = null;
        try {
            gameplay = new Gameplay(GameMode.EXPERT, 4,0);
            gameplay.addPlayer("a");
            gameplay.addPlayer("b");
            gameplay.addPlayer("c");
            gameplay.addPlayer("d");
            ArrayList<Player> players=gameplay.getPlayerList();
            players.get(0).setToken1(new Token(2));
            players.get(1).setToken1(new Token(4));
            players.get(2).setToken1(new Token(4));
            players.get(3).setToken1(new Token(2));
            gameplay.endGame();
            players=gameplay.getPlayerList();
            Assertions.assertEquals("b",players.get(0).getName());
            Assertions.assertEquals("c",players.get(1).getName());
            Assertions.assertEquals("a",players.get(2).getName());
            Assertions.assertEquals("d",players.get(3).getName());
        } catch (Exception e){};
    }

    @Test
    void calculatePointsTest4(){
        Gameplay gameplay = null;
        try {
            gameplay = new Gameplay(GameMode.EXPERT,4,0);
            gameplay.addPlayer("a");
            gameplay.addPlayer("b");
            gameplay.addPlayer("c");
            gameplay.addPlayer("d");
            ArrayList<Player> players=gameplay.getPlayerList();
            players.get(0).setToken1(new Token(0));
            players.get(1).setToken1(new Token(3));
            players.get(2).setToken1(new Token(10));
            players.get(3).setToken1(new Token(3));
            players.get(3).setFirstPlayerSeat(true);
            gameplay.endGame();
            players=gameplay.getPlayerList();
            Assertions.assertEquals("c",players.get(0).getName());
            Assertions.assertEquals("d",players.get(1).getName());
            Assertions.assertEquals("b",players.get(2).getName());
            Assertions.assertEquals("a",players.get(3).getName());
        } catch (Exception e){};
    }

    @Test
    void calculatePointsTest5(){
        Gameplay gameplay = null;
        try {
            gameplay = new Gameplay(GameMode.EXPERT,4,0);
            gameplay.addPlayer("a");
            gameplay.addPlayer("b");
            gameplay.addPlayer("c");
            gameplay.addPlayer("d");
            ArrayList<Player> players=gameplay.getPlayerList();
            players.get(0).setToken1(new Token(0));
            players.get(1).setToken1(new Token(0));
            players.get(2).setToken1(new Token(1));
            players.get(3).setToken1(new Token(0));
            players.get(3).setFirstPlayerSeat(true);
            players.get(1).setFirstPlayerSeat(true);
            gameplay.endGame();
            players=gameplay.getPlayerList();
            Assertions.assertEquals("c",players.get(0).getName());
            Assertions.assertEquals("b",players.get(1).getName());
            Assertions.assertEquals("d",players.get(2).getName());
            Assertions.assertEquals("a",players.get(3).getName());
        } catch (Exception e){};
    }

    @Test
    void actionTest(){
        //board not working!
    }


}
