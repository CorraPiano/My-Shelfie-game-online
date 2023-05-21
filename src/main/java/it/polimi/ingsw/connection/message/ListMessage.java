package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.model.GameMode;

public class ListMessage implements Sendable{

    public final GameMode gameMode;
    public final int numPlayer;
    public ListMessage(int numPlayer, GameMode gameMode){
        this.gameMode=gameMode;
        this.numPlayer=numPlayer;
    }
    public ListMessage(int numPlayer){
        this.gameMode=null;
        this.numPlayer=numPlayer;
    }
    public ListMessage(GameMode gameMode){
        this.gameMode=gameMode;
        this.numPlayer=0;
    }

    public ListMessage(){
        this.gameMode=null;
        this.numPlayer=0;
    }

    public MessageHeader getHeader(){
        return MessageHeader.LIST;
    }
}
