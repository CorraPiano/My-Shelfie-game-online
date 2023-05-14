package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Token;

import java.io.Serializable;

public class localPlayer implements Serializable {
    public final String name;
    public boolean firstPlayerSeat;
    public Token endGameToke;
    public Token token1;
    public Token token2;

    public int points;

    public localPlayer(String name){
        // da sistemare con l'inizializzazione dei token !!
        this.name = name;
    }
    public void setEndGameToken(Token endGameToke) {
        this.endGameToke = endGameToke;
    }

    public void setToken1(Token token1) {
        this.token1 = token1;
    }

    public void setToken2(Token token2) {
        this.token2 = token2;
    }

    public void setFirstPlayerSeat(boolean firstPlayerSeat) {
        this.firstPlayerSeat = firstPlayerSeat;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
