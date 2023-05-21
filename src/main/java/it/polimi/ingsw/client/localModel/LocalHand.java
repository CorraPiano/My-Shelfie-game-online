package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.Item;

import java.io.Serializable;

public class LocalHand implements Serializable, Sendable {
    public final Item[] hand;
    public final int size;

    public LocalHand(Item[] hand, int size){
        this.hand=hand;
        this.size=size;
    }

    public MessageHeader getHeader(){
        return MessageHeader.HAND;
    }
}
