package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class PutMessage implements Sendable{
    // -> cmd: put
    // <- notify: put

    public final int column;
    public final String name;

    public PutMessage(int column){
        this.column=column;
        this.name=null;
    }

    public PutMessage(int column, String name){
        this.column=column;
        this.name=name;
    }

    @Override
    public MessageHeader getHeader(){
        return MessageHeader.PUT;
    }
}
