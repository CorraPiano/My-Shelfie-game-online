package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class ReconnectMessage implements Sendable{
    public final String id;

    public ReconnectMessage(String id){
        this.id=id;
    }
    @Override
    public MessageHeader getHeader(){
        return MessageHeader.RECONNECTION;
    }
}
