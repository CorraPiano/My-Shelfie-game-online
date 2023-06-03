package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class DisconnectMessage implements Sendable{
    public final String name;

    public DisconnectMessage(String name){
        this.name=name;
    }
    @Override
    public MessageHeader getHeader(){
        return MessageHeader.DISCONNECTION;
    }
}
