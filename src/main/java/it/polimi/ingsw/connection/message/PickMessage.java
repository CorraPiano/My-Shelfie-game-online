package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Item;

public class PickMessage implements Sendable{

    public Coordinates coordinates;
    public String name;
    public Item item;
    public PickMessage(Coordinates coordinates){
        this.coordinates=coordinates;
        this.name=null;
        this.item=null;
    }
    public PickMessage(Coordinates coordinates, String name, Item item){
        this.coordinates=coordinates;
        this.name=name;
        this.item=item;
    }

    public MessageHeader getHeader(){
        return MessageHeader.PICK;
    }
}
