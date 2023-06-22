package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class EndGameMessage implements Sendable{
    // <- notify: end game

    public final String name;
    public final EndCause cause;

    public EndGameMessage(String name, EndCause cause){
        this.name=name;
        this.cause=cause;
    }

    @Override
    public MessageHeader getHeader() {
        return MessageHeader.ENDGAME;
    }
}