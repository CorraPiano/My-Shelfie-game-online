package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.model.GameMode;

import java.util.ArrayList;

public class ListMessage implements Sendable{
    // -> cmd: list
    // <- notify: games list

    public final ArrayList<LocalGame> gamesList;

    public ListMessage(){
        this.gamesList = null;
    }

    public ListMessage(ArrayList<LocalGame> gamesList){
        this.gamesList = gamesList;
    }

    @Override
    public MessageHeader getHeader(){
        return MessageHeader.LIST;
    }
}
