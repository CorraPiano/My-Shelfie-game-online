package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class LastRoundEvent implements Sendable{
    public String name;

    public LastRoundEvent(String name){
        this.name=name;
    }

    @Override
    public MessageHeader getHeader() {
        return MessageHeader.LASTROUND;
    }
}

