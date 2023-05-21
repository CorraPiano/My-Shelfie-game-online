package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class StartGameEvent implements Sendable{
    public String name;

    public StartGameEvent(String name){
        this.name=name;
    }

    @Override
    public MessageHeader getHeader() {
        return MessageHeader.STARTGAME;
    }
}