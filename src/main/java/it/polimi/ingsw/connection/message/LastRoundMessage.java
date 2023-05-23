package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class LastRoundMessage implements Sendable{
    // <- notify: last round

    public String name;

    public LastRoundMessage(String name){
        this.name=name;
    }

    @Override
    public MessageHeader getHeader() {
        return MessageHeader.LASTROUND;
    }
}

