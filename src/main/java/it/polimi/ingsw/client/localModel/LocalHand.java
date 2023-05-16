package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.model.Item;

import java.io.Serializable;

public class LocalHand implements Serializable {
    public final Item[] hand;
    public final int size;

    public LocalHand(Item[] hand, int size){
        this.hand=hand;
        this.size=size;
    }
}
