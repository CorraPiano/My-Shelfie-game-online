package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Item;

import java.io.Serializable;
import java.util.ArrayList;

public class LocalHand implements Serializable, Sendable {
    public final Item[] hand;
    public final ArrayList<Coordinates> coordinatesList;
    public final int size;

    public LocalHand(Item[] hand, int size,ArrayList<Coordinates> coordinatesList){
        this.hand=hand;
        this.size=size;
        this.coordinatesList=coordinatesList;
    }

    public LocalHand(){
        this.hand=new Item[0];
        this.size=0;
        this.coordinatesList=new ArrayList<>();
    }

    public MessageHeader getHeader(){
        return MessageHeader.HAND;
    }

}
