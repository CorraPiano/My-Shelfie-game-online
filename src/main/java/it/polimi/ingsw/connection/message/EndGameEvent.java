package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class EndGameEvent implements Sendable{
    public String name;

    public EndGameEvent(String name){
        this.name=name;
    }

    @Override
    public MessageHeader getHeader() {
        return MessageHeader.ENDGAME;
    }
}