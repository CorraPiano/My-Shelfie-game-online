package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.connection.MessageHeader;

import java.io.Serializable;
import java.util.ArrayList;

public class GamesList implements Sendable {
    public final ArrayList<LocalGame> gamesList;

    public GamesList(ArrayList<LocalGame> gamesList){
        this.gamesList = gamesList;
    }

    public MessageHeader getHeader(){
        return MessageHeader.GAMESLIST;
    }


}
