package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.Token;

import java.io.Serializable;

public class LocalPlayer implements Serializable, Sendable {
    public final String name;
    public final boolean firstPlayerSeat;
    public final Token endGameToke;
    public final Token token1;
    public final Token token2;
    public final int points;
    public final int numPersonalCard;

    public LocalPlayer(String name, boolean firstPlayerSeat, Token endGameToke, Token token1, Token token2, int points, int numPersonalCard){
        this.name = name;
        this.firstPlayerSeat = firstPlayerSeat;
        this.endGameToke = endGameToke;
        this.token1 = token1;
        this.token2 = token2;
        this.points = points;
        this.numPersonalCard=numPersonalCard;
    }

    public LocalPlayer(String name, boolean firstPlayerSeat, Token endGameToke, Token token1, Token token2, int points){
        this.name = name;
        this.firstPlayerSeat = firstPlayerSeat;
        this.endGameToke = endGameToke;
        this.token1 = token1;
        this.token2 = token2;
        this.points = points;
        this.numPersonalCard=-1;
    }

    public MessageHeader getHeader(){
        return MessageHeader.PLAYER;
    }
    public int getPoints() {
        return points;
    }
}
