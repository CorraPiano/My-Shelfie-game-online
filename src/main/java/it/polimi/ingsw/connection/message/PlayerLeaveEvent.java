package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class PlayerLeaveEvent implements Sendable{
    public  String name;

    public PlayerLeaveEvent(String name){
        this.name=name;
    }

    @Override
    public MessageHeader getHeader() {
        return MessageHeader.PLAYERLEAVE;
    }
}
