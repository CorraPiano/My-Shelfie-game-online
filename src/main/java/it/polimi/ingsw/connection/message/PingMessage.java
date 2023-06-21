package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class PingMessage implements Sendable{
    public final int n;

    public PingMessage(int n){
        this.n = n;
    }

    public MessageHeader getHeader(){
        return MessageHeader.PING;
    }
}
