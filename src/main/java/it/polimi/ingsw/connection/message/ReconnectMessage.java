package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class ReconnectMessage implements Sendable{
    public final String id;
    public final String name;
    public final boolean reset;

    public ReconnectMessage(String id,boolean reset){
        this.id=id;
        this.reset=reset;
        this.name=null;
    }
    public ReconnectMessage(String name){
        this.id=null;
        this.reset=false;
        this.name=name;
    }
    @Override
    public MessageHeader getHeader(){
        return MessageHeader.RECONNECTION;
    }
}
