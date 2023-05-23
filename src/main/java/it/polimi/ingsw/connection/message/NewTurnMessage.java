package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class NewTurnMessage implements Sendable{
    // <- notify: now turn

    public String name;

    public NewTurnMessage(String name){
        this.name=name;
    }

    @Override
    public MessageHeader getHeader() {
        return MessageHeader.NEWTURN;
    }
}
