package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.PlayerState;
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
    public final PlayerState playerState;

    public LocalPlayer(String name, boolean firstPlayerSeat, Token endGameToke, Token token1, Token token2, int points, int numPersonalCard, PlayerState playerState){
        this.name = name;
        this.firstPlayerSeat = firstPlayerSeat;
        this.endGameToke = endGameToke;
        this.token1 = token1;
        this.token2 = token2;
        this.points = points;
        this.numPersonalCard=numPersonalCard;
        this.playerState = playerState;
    }

    public LocalPlayer(String name, boolean firstPlayerSeat, Token endGameToke, Token token1, Token token2, int points, PlayerState playerState){
        this.name = name;
        this.firstPlayerSeat = firstPlayerSeat;
        this.endGameToke = endGameToke;
        this.token1 = token1;
        this.token2 = token2;
        this.points = points;
        this.numPersonalCard=-1;
        this.playerState = playerState;
    }

    public MessageHeader getHeader(){ return MessageHeader.PLAYER; }
    public int getPoints() { return points; }
    public String getName() { return name; }
    public Token getEndGameToke() { return endGameToke; }
    public Token getToken1() { return token1; }
    public Token getToken2() { return token2; }
    public boolean isFirstPlayerSeat() { return firstPlayerSeat; }
    public int getNumPersonalCard() { return numPersonalCard; }
    public PlayerState getPlayerState() { return playerState; }
}
