package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.model.GameMode;

public class CreateMessage implements Sendable{

    public final String name;
    public final GameMode gameMode;
    public final int numPlayers;

    public CreateMessage(String name, GameMode gameMode, int numPlayers){
        this.name=name;
        this.gameMode=gameMode;
        this.numPlayers=numPlayers;
    }

    public MessageHeader getHeader(){
        return MessageHeader.CREATE;
    }
}
