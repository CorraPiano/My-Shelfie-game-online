package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Item;

import java.io.Serializable;
import java.util.HashMap;

public class LocalPersonalCard implements Sendable, Serializable {
    public Item[][] map;

    public LocalPersonalCard(Item[][] map){
        this.map=map;
    }

    public MessageHeader getHeader(){
        return MessageHeader.PERSONALGOALCARD;
    }
}
