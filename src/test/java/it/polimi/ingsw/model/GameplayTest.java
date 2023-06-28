package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.*;
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
    void createTokenListTest() throws GameModeException, NumPlayersException {
        Gameplay gameplay1 = new Gameplay(GameMode.EXPERT,3,0);
        Gameplay gameplay2 = new Gameplay(GameMode.EXPERT,4,1);

       /* ArrayList<Token> list1 = gameplay1.createTokenList();
        ArrayList<Token> list2 = gameplay2.createTokenList();

        Assertions.assertNotNull(list1);
        Assertions.assertNotNull(list2);*/

    }

    @Test
    void pickItemTest() throws GameModeException, NumPlayersException, NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, OutOfBoardPickException {
        Gameplay gameplay1 = new Gameplay(GameMode.EXPERT,3,0);
        gameplay1.startGame();
        /*gameplay1.getBoard();
        Assertions.assertNotNull(gameplay1.getBoard());*/
        gameplay1.pickItem(new Coordinates(3,5));
    }
    @Test
    void getterSetterTests() throws GameModeException, NumPlayersException {
        Gameplay gameplay1 = new Gameplay(GameMode.EXPERT,3,0);
        Assertions.assertEquals(0,gameplay1.getNumPlayersConnected());
        Assertions.assertEquals(0,gameplay1.getNumPlayersAvaiable());
        Assertions.assertEquals(3,gameplay1.getNumPlayers());
        Assertions.assertEquals(0,gameplay1.getGameID());
    }
    @Test
    void getPlayerNameByIDTest() throws GameModeException, NumPlayersException {
        Gameplay gameplay1 = new Gameplay(GameMode.EXPERT,3,0);
        PlayerHandler playerHandler = new PlayerHandler(gameplay1);
        playerHandler.addPlayer(new Player("flavio", 0));
        Assertions.assertEquals("flavio", gameplay1.getPlayerIDByName("flavio"));

    }

    @Test
    void getGameStateTest() throws GameModeException, NumPlayersException {
        Gameplay gameplay1 = new Gameplay(GameMode.EXPERT,3,0);
        Assertions.assertNotNull(gameplay1.getGameMode());
    }



}
