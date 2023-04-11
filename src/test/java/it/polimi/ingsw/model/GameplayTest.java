package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GameplayTest {

    @Test
    void calculatePointsTest(){
        Gameplay gameplay = new Gameplay(GameMode.EXPERT,4);
        try {
            gameplay.addPlayer("a");
            gameplay.addPlayer("b");
            gameplay.addPlayer("c");
            gameplay.addPlayer("d");
        }catch(Exception e){}
        ArrayList<Player> players=gameplay.TestGetPlayerList();
        players.get(0).setToken1(new Token(8));
        players.get(1).setToken1(new Token(6));
        players.get(2).setToken1(new Token(4));
        players.get(3).setToken1(new Token(2));
        gameplay.endGame();
        for(Player p: players){
            System.out.println(p.getPoints());
            System.out.println(p.getName());
        }

    }

}
