package it.polimi.ingsw.model;

import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.common_card_classes.GroupCommonGoalCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
            gameplay.startGame();
            ArrayList<Player> players=gameplay.getPlayerList();
            players.get(0).setToken1(new Token(8));
            players.get(1).setToken1(new Token(6));
            players.get(2).setToken1(new Token(4));
            players.get(3).setToken1(new Token(2));
            gameplay.endGame();
            ArrayList<Player> players2=gameplay.getPlayerList();
            assertEquals(players.get(0).getName(),players2.get(0).getName());
            assertEquals(players.get(1).getName(),players2.get(1).getName());
            assertEquals(players.get(2).getName(),players2.get(2).getName());
            assertEquals(players.get(3).getName(),players2.get(3).getName());
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
            gameplay.startGame();
            ArrayList<Player> players = gameplay.getPlayerList();
            players.get(0).setToken1(new Token(2));
            players.get(1).setToken1(new Token(4));
            players.get(2).setToken1(new Token(6));
            players.get(3).setToken1(new Token(8));
            gameplay.endGame();
            ArrayList<Player> players2 = gameplay.getPlayerList();
            assertEquals(players.get(3).getName(), players2.get(0).getName());
            assertEquals(players.get(2).getName(), players2.get(1).getName());
            assertEquals(players.get(1).getName(), players2.get(2).getName());
            assertEquals(players.get(0).getName(), players2.get(3).getName());
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
            gameplay.startGame();
            ArrayList<Player> players=gameplay.getPlayerList();
            players.get(0).setToken1(new Token(2));
            players.get(1).setToken1(new Token(4));
            players.get(2).setToken1(new Token(4));
            players.get(3).setToken1(new Token(2));
            players.get(1).setFirstPlayerSeat(true);
            gameplay.endGame();
            ArrayList<Player> players2 = gameplay.getPlayerList();
            assertEquals(players.get(1).getName(), players2.get(0).getName());
            assertEquals(players.get(2).getName(), players2.get(1).getName());
            assertEquals(players.get(0).getName(), players2.get(2).getName());
            assertEquals(players.get(3).getName(), players2.get(3).getName());
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
            gameplay.startGame();
            ArrayList<Player> players=gameplay.getPlayerList();
            players.get(0).setToken1(new Token(0));
            players.get(1).setToken1(new Token(3));
            players.get(2).setToken1(new Token(10));
            players.get(3).setToken1(new Token(3));
            players.get(3).setFirstPlayerSeat(true);
            gameplay.endGame();
            ArrayList<Player> players2 = gameplay.getPlayerList();
            assertEquals(players.get(2).getName(), players2.get(0).getName());
            assertEquals(players.get(3).getName(), players2.get(1).getName());
            assertEquals(players.get(1).getName(), players2.get(2).getName());
            assertEquals(players.get(0).getName(), players2.get(3).getName());
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
            gameplay.startGame();
            ArrayList<Player> players=gameplay.getPlayerList();
            players.get(0).setToken1(new Token(12));
            players.get(1).setToken1(new Token(5));
            players.get(2).setToken1(new Token(7));
            players.get(3).setToken1(new Token(9));
            players.get(3).setFirstPlayerSeat(true);
            players.get(1).setEndGameToken();
            gameplay.endGame();
            ArrayList<Player> players2 = gameplay.getPlayerList();
            assertEquals(players.get(0).getName(), players2.get(0).getName());
            assertEquals(players.get(3).getName(), players2.get(1).getName());
            assertEquals(players.get(2).getName(), players2.get(2).getName());
            assertEquals(players.get(1).getName(), players2.get(3).getName());
        } catch (Exception e){};
    }

    @Test
    void getterSetterTests() throws GameModeException, NumPlayersException {
        Gameplay gameplay1 = new Gameplay(GameMode.EXPERT,3,0);
        assertEquals(0,gameplay1.getNumPlayersConnected());
        assertEquals(0,gameplay1.getNumPlayersAvaiable());
        assertEquals(GameMode.EXPERT,gameplay1.getGameMode());
        assertEquals(3,gameplay1.getNumPlayers());
        assertEquals(0,gameplay1.getGameID());
        assertNotNull(gameplay1.getLocal());
        assertNotNull(gameplay1.getEventKeeper());
        assertNull(gameplay1.getPlayerNameByID("name"));
    }

    private ArrayList<Coordinates> getBoardArrayList(){
        ArrayList<Coordinates> list = new ArrayList<>();
        list.add(new Coordinates(1,4));
        list.add(new Coordinates(1,5));
        list.add(new Coordinates(2,3));
        list.add(new Coordinates(2,4));
        list.add(new Coordinates(2,5));
        list.add(new Coordinates(3,1));
        list.add(new Coordinates(3,2));
        list.add(new Coordinates(3,3));
        list.add(new Coordinates(3,4));
        list.add(new Coordinates(3,5));
        list.add(new Coordinates(3,6));
        list.add(new Coordinates(4,1));
        list.add(new Coordinates(4,2));
        list.add(new Coordinates(4,3));
        list.add(new Coordinates(4,4));
        list.add(new Coordinates(4,5));
        list.add(new Coordinates(4,6));
        list.add(new Coordinates(4,7));
        list.add(new Coordinates(5,2));
        list.add(new Coordinates(5,3));
        list.add(new Coordinates(5,4));
        list.add(new Coordinates(5,5));
        list.add(new Coordinates(5,6));
        list.add(new Coordinates(5,7));
        list.add(new Coordinates(6,3));
        list.add(new Coordinates(6,4));
        list.add(new Coordinates(6,5));
        list.add(new Coordinates(7,3));
        list.add(new Coordinates(7,4));
        return list;
    }

    @Test
    void getGameStateTest0() throws Exception {
        Gameplay gameplay = new Gameplay(GameMode.EASY,3,0);
        Player pA = gameplay.addPlayer("A");
        String A = pA.getID();
        Player pB = gameplay.addPlayer("B");
        String B = pB.getID();
        gameplay.disconnect(B);
        Player p1 = gameplay.addPlayer("a");
        gameplay.leave(A);
        assertFalse(gameplay.isReady());
        Player p2 = gameplay.addPlayer("b");
        Player p3 = gameplay.addPlayer("c");
        assertTrue(gameplay.isReady());
    }
    @Test
    void getGameStateTest1() throws Exception {
        Gameplay gameplay = new Gameplay(GameMode.EASY,2,0);
        Player pA = gameplay.addPlayer("A");
        String A = pA.getID();
        Player pB = gameplay.addPlayer("B");
        String B = pB.getID();
        assertTrue(gameplay.isReady());
        gameplay.startGame();
        assertEquals(2,gameplay.getCurrentPlayers());

        ArrayList<Coordinates> list = getBoardArrayList();
        int i=0,j=0,z=0;
        while(!gameplay.isFinished()){
            gameplay.pickItem(list.get(i));
            gameplay.putItemList(j);
            i++;
            if(i==list.size()-1)
                i=0;
            gameplay.pickItem(list.get(i));
            gameplay.putItemList(j);
            i++;
            if(i==list.size()-1)
                i=0;
            z++;
            if(z==6) {
                j++;
                z=0;
            }
        }
        gameplay.endGame();
    }

    @Test
    void getGameStateTest2() throws Exception{
        Gameplay gameplay = new Gameplay(GameMode.EASY,2,0);
        Player pA = gameplay.addPlayer("A");
        String A = pA.getID();
        Player pB = gameplay.addPlayer("B");
        String B = pB.getID();
        assertTrue(gameplay.isReady());
        gameplay.startGame();

        gameplay.disconnect(pA.getID());
        gameplay.endGame();

    }

    @Test
    void getGameStateTest3() throws Exception {
        Gameplay gameplay = new Gameplay(GameMode.EXPERT,4,0);
        Player pA = gameplay.addPlayer("A");
        String A = pA.getID();
        assertEquals(gameplay.getGameState(),GameState.WAIT);

        try {
            Player p = gameplay.addPlayer("A");
            Assertions.fail();
        } catch(Exception e){

        }

        assertFalse(gameplay.isReady());
        Player pB = gameplay.addPlayer("B");
        String B = pB.getID();
        assertEquals(gameplay.getGameState(),GameState.WAIT);
        assertFalse(gameplay.isReady());
        Player pC = gameplay.addPlayer("C");
        String C = pC.getID();
        assertEquals(gameplay.getGameState(),GameState.WAIT);
        assertFalse(gameplay.isReady());
        Player pD = gameplay.addPlayer("D");
        String D = pD.getID();
        assertTrue(gameplay.isReady());
        gameplay.startGame();

        assertEquals(4,gameplay.getCurrentPlayers());
        assertTrue(gameplay.currentPlayerIsConnected());

        try {
            gameplay.putItemList(0);
            Assertions.fail();
        } catch(Exception e){

        }

        //1
        gameplay.pickItem(c(0,4));
        gameplay.pickItem(c(0,5));
        gameplay.releaseHand();
        gameplay.pickItem(c(0,4));
        gameplay.pickItem(c(0,5));
        gameplay.putItemList(0);
        assertFalse(gameplay.isFinished());
        //2
        gameplay.pickItem(c(1,3));
        gameplay.pickItem(c(1,4));
        gameplay.pickItem(c(1,5));
        gameplay.selectOrderHand(l(2,1,0));
        gameplay.putItemList(0);
        assertFalse(gameplay.isFinished());
        //3
        gameplay.pickItem(c(2,2));
        gameplay.pickItem(c(2,3));
        gameplay.pickItem(c(2,4));
        gameplay.releaseHand();
        gameplay.pickItem(c(2,2));
        gameplay.pickItem(c(2,3));
        gameplay.pickItem(c(2,4));
        gameplay.selectOrderHand(l(0,1,2));
        gameplay.putItemList(0);
        assertFalse(gameplay.isFinished());
        //4
        gameplay.pickItem(c(2,5));
        gameplay.pickItem(c(2,6));
        gameplay.putItemList(0);
        assertFalse(gameplay.isFinished());
        //5
        gameplay.pickItem(c(3,0));
        gameplay.pickItem(c(3,1));
        gameplay.pickItem(c(3,2));
        gameplay.selectOrderHand(l(1,0,2));
        gameplay.putItemList(0);
        assertFalse(gameplay.isFinished());
        //6
        gameplay.pickItem(c(3,3));
        gameplay.pickItem(c(3,4));
        gameplay.pickItem(c(3,5));
        gameplay.putItemList(0);
        assertFalse(gameplay.isFinished());
        //7
        gameplay.pickItem(c(3,6));
        gameplay.pickItem(c(3,7));
        gameplay.putItemList(0);
        assertFalse(gameplay.isFinished());
        //8
        gameplay.pickItem(c(4,0));
        gameplay.pickItem(c(4,1));
        gameplay.pickItem(c(4,2));
        gameplay.putItemList(0);
        assertFalse(gameplay.isFinished());
        //9
        gameplay.pickItem(c(4,3));
        gameplay.pickItem(c(4,4));
        gameplay.pickItem(c(4,5));
        gameplay.putItemList(1);
        assertFalse(gameplay.isFinished());
        //10
        gameplay.pickItem(c(4,6));
        gameplay.pickItem(c(4,7));
        gameplay.pickItem(c(4,8));
        gameplay.putItemList(1);
        assertFalse(gameplay.isFinished());
        //11
        gameplay.pickItem(c(5,1));
        gameplay.pickItem(c(5,2));
        gameplay.pickItem(c(5,3));
        gameplay.putItemList(1);
        assertFalse(gameplay.isFinished());
        //12
        gameplay.pickItem(c(5,4));
        gameplay.pickItem(c(5,5));
        gameplay.pickItem(c(5,6));
        gameplay.putItemList(1);
        assertFalse(gameplay.isFinished());
        //13
        gameplay.pickItem(c(5,7));
        gameplay.selectOrderHand(l(0));
        gameplay.releaseHand();
        gameplay.pickItem(c(5,7));
        gameplay.pickItem(c(5,8));
        gameplay.putItemList(1);
        assertFalse(gameplay.isFinished());
        //14
        gameplay.pickItem(c(6,2));
        gameplay.pickItem(c(6,3));
        gameplay.pickItem(c(6,4));
        gameplay.putItemList(1);
        assertFalse(gameplay.isFinished());
        //15
        gameplay.pickItem(c(6,5));
        gameplay.pickItem(c(6,6));
        gameplay.putItemList(1);
        assertFalse(gameplay.isFinished());
        //16
        gameplay.pickItem(c(7,3));
        gameplay.pickItem(c(7,4));
        gameplay.pickItem(c(7,5));
        gameplay.putItemList(2);
        assertFalse(gameplay.isFinished());
        //17
        gameplay.pickItem(c(8,3));
        gameplay.pickItem(c(8,4));
        gameplay.putItemList(2);
        assertFalse(gameplay.isFinished());
        //18
        gameplay.pickItem(c(8,3));
        gameplay.pickItem(c(8,4));
        gameplay.putItemList(2);
        assertFalse(gameplay.isFinished());
        //19
        gameplay.pickItem(c(7,3));
        gameplay.pickItem(c(7,4));
        gameplay.pickItem(c(7,5));
        gameplay.putItemList(2);
        assertFalse(gameplay.isFinished());

        try {
            gameplay.addChatMessage(new ChatMessage("A", "m", "A"));
            Assertions.fail();
        } catch(Exception e){}
        gameplay.addChatMessage(new ChatMessage("A","m","B"));
        gameplay.addChatMessage(new ChatMessage("A","m","C"));
        gameplay.addChatMessage(new ChatMessage("A","m","D"));
        gameplay.addChatMessage(new ChatMessage("B","m","A"));
        try {
        gameplay.addChatMessage(new ChatMessage("B","m","B"));
            Assertions.fail();
        } catch(Exception e){}
        gameplay.addChatMessage(new ChatMessage("B","m","C"));
        gameplay.addChatMessage(new ChatMessage("B","m","D"));
        gameplay.addChatMessage(new ChatMessage("C","m","A"));
        gameplay.addChatMessage(new ChatMessage("C","m","B"));
        try {
            gameplay.addChatMessage(new ChatMessage("C","m","C"));
            Assertions.fail();
        } catch(Exception e){}
        gameplay.addChatMessage(new ChatMessage("C","m","D"));
        gameplay.addChatMessage(new ChatMessage("D","m","A"));
        gameplay.addChatMessage(new ChatMessage("D","m","B"));
        gameplay.addChatMessage(new ChatMessage("D","m","C"));
        try {
            gameplay.addChatMessage(new ChatMessage("D","m","D"));
            Assertions.fail();
        } catch(Exception e){}
        gameplay.addChatMessage(new ChatMessage("A","m"));
        gameplay.addChatMessage(new ChatMessage("A","m"));
        gameplay.addChatMessage(new ChatMessage("A","m"));
        gameplay.addChatMessage(new ChatMessage("A","m"));

        String p = gameplay.getCurrentPlayerID();
        gameplay.disconnect(p);
        gameplay.pickItem(c(1,3));
        gameplay.reconnect(p);
        gameplay.releaseHand();

        gameplay.checkTimer(System.currentTimeMillis(),1);

        assertEquals(gameplay.getNumPlayersConnected(),4);
        gameplay.disconnect(A);
        assertEquals(gameplay.getNumPlayersConnected(),3);
        gameplay.disconnect(B);
        assertEquals(gameplay.getNumPlayersConnected(),2);
        gameplay.disconnect(C);
        assertEquals(gameplay.getNumPlayersConnected(),1);

        gameplay.checkTimer(System.currentTimeMillis(),4);

        gameplay.disconnect(D);
        assertEquals(gameplay.getNumPlayersConnected(),0);
        gameplay.reconnect(A);
        gameplay.reconnect(B);
        gameplay.pickItem(c(6,2));
        gameplay.putItemList(3);
        gameplay.pickItem(c(6,3));
        gameplay.putItemList(3);
        gameplay.pickItem(c(6,4));
        gameplay.putItemList(3);
        gameplay.reconnect(C);
        gameplay.pickItem(c(6,5));
        gameplay.putItemList(3);
        gameplay.reconnect(D);
        gameplay.pickItem(c(6,6));
        gameplay.putItemList(3);
        gameplay.disconnect(A);
        gameplay.pickItem(c(5,1));
        gameplay.putItemList(3);
        gameplay.reconnect(A);
        gameplay.pickItem(c(5,2));
        gameplay.putItemList(3);
        gameplay.pickItem(c(5,3));
        gameplay.putItemList(3);
        gameplay.pickItem(c(5,4));
        gameplay.putItemList(3);
        gameplay.leave(A);

        try{
            gameplay.reconnect(A);
            Assertions.fail();
        } catch(Exception e){}
        try{
            gameplay.reconnect("name");
            Assertions.fail();
        } catch(Exception e){}
        try{
            gameplay.reconnect(B);
            Assertions.fail();
        } catch(Exception e){}


        gameplay.pickItem(c(5,5));
        gameplay.putItemList(3);
        gameplay.pickItem(c(5,6));
        gameplay.putItemList(3);
        gameplay.pickItem(c(5,7));
        gameplay.putItemList(3);
        gameplay.pickItem(c(5,8));
        gameplay.leave(B);
        gameplay.disconnect(C);
        gameplay.pickItem(c(4,0));
        gameplay.putItemList(4);
        gameplay.pickItem(c(4,1));
        gameplay.putItemList(4);
        gameplay.pickItem(c(4,2));
        gameplay.putItemList(4);
        gameplay.pickItem(c(4,3));
        gameplay.putItemList(4);
        gameplay.reconnect(C);
        gameplay.pickItem(c(4,4));
        gameplay.putItemList(4);
        gameplay.pickItem(c(4,5));
        gameplay.putItemList(4);
        gameplay.pickItem(c(4,6));
        gameplay.putItemList(4);
        gameplay.pickItem(c(4,7));
        gameplay.putItemList(4);
        assertFalse(gameplay.isFinished());
        gameplay.leave(C);
        assertTrue(gameplay.isFinished());
        gameplay.endGame();
    }

    private Coordinates c(int x, int y){
        return new Coordinates(x,y);
    }

    private ArrayList<Integer> l(int a){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(a);
        return list;
    }

    private ArrayList<Integer> l(int a, int b){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        return list;
    }

    private ArrayList<Integer> l(int a, int b, int c){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        return list;
    }

}
