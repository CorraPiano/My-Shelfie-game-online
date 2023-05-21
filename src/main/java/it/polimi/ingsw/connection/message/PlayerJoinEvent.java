package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class PlayerJoinEvent implements Sendable{
    public  String name;

    public PlayerJoinEvent(String name){
        this.name=name;
    }

    @Override
    public MessageHeader getHeader() {
        return MessageHeader.PLAYERJOIN;
    }
}
