package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.model.Token;

import java.io.Serializable;

public class LocalPlayer implements Serializable {
    public final String name;
    public boolean firstPlayerSeat;
    public Token endGameToke;
    public Token token1;
    public Token token2;
    public int points;

    public LocalPlayer(String name, boolean firstPlayerSeat, Token endGameToke, Token token1, Token token2, int points){
        this.name = name;
        this.firstPlayerSeat = firstPlayerSeat;
        this.endGameToke = endGameToke;
        this.token1 = token1;
        this.token2 = token2;
        this.points = points;
    }

}
