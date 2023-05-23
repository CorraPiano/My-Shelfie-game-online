package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class EndGameMessage implements Sendable{
    // <- notify: end game

    public String name;

    public EndGameMessage(String name){
        this.name=name;
    }

    @Override
    public MessageHeader getHeader() {
        return MessageHeader.ENDGAME;
    }
}