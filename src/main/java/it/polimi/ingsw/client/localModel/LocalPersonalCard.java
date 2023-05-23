package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Item;

import java.io.Serializable;
import java.util.HashMap;

public class LocalPersonalCard implements Sendable, Serializable {
    public final Item[][] map;
    public final int num;

    public LocalPersonalCard(Item[][] map, int num){
        this.map=map;
        this.num=num;
    }

    public MessageHeader getHeader(){
        return MessageHeader.PERSONALGOALCARD;
    }
}
