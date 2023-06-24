package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class ReconnectMessage implements Sendable{
    public final String id;
    public final boolean reset;

    public ReconnectMessage(String id){
        this.id=id;
        this.reset=true;
    }
    @Override
    public MessageHeader getHeader(){
        return MessageHeader.RECONNECTION;
    }
}
