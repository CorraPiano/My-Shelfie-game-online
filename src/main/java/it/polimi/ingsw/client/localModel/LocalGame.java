package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.GameState;

import java.io.Serializable;
import java.util.ArrayList;

public class LocalGame implements Serializable, Sendable {
    public final GameMode gameMode;
    public final int gameID;
    public final int maxPerson;
    public final int currPerson;
    public final GameState gameState;

    public final ArrayList<LocalPlayer> playerList;

    public LocalGame(GameMode gameMode, int gameID, int maxPerson, int currPerson, GameState gameState, ArrayList<LocalPlayer> playerList){
        this.gameMode=gameMode;
        this.gameID=gameID;
        this.maxPerson=maxPerson;
        this.currPerson=currPerson;
        this.gameState=gameState;
        this.playerList=playerList;
    }

    public LocalGame(){
        this.gameMode=null;
        this.gameID=0;
        this.maxPerson=0;
        this.currPerson=0;
        this.gameState=null;
        this.playerList=null;
    }

    public String toString(){
        return String.format("ID: %s, N° PLAYER: %s, N° CURRENT PLAYER: %s, MODE: %s, STATUS: %s", gameID,maxPerson,currPerson, gameMode, gameState);
    }

    public MessageHeader getHeader(){
        return MessageHeader.GAME;
    }
}

