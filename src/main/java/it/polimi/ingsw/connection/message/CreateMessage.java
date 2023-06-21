package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.model.GameMode;

public class CreateMessage implements Sendable{
    // ->  cmd: create + add
    // <-  notify: create game

    public final String name;
    public final GameMode gameMode;
    public final int numPlayers;
    public final int gameID;

    public CreateMessage(String name, GameMode gameMode, int numPlayers){
        this.name=name;
        this.gameMode=gameMode;
        this.numPlayers=numPlayers;
        this.gameID=0;
    }

    public CreateMessage(GameMode gameMode,int gameID, int numPlayers){
        this.name=null;
        this.gameMode=gameMode;
        this.numPlayers=numPlayers;
        this.gameID=gameID;
    }

    @Override
    public MessageHeader getHeader(){
        return MessageHeader.CREATE;
    }
}
