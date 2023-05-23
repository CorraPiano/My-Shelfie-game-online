package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class StartGameMessage implements Sendable{
    // <- notify: start game

    public String name;

    public StartGameMessage(String name){
        this.name=name;
    }

    @Override
    public MessageHeader getHeader() {
        return MessageHeader.STARTGAME;
    }
}