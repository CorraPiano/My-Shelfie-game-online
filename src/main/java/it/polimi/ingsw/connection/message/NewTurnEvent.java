package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class NewTurnEvent implements Sendable{
    public String name;

    public NewTurnEvent(String name){
        this.name=name;
    }

    @Override
    public MessageHeader getHeader() {
        return MessageHeader.NEWTURN;
    }
}
