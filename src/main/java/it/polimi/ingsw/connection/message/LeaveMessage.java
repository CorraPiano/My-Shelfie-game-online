package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class LeaveMessage implements Sendable{
    // -> cmd: leave
    // <- notify: player leave

    public final String name;

    public LeaveMessage(){
        this.name=null;
    }

    public LeaveMessage(String name){
        this.name=name;
    }

    @Override
    public MessageHeader getHeader(){
        return MessageHeader.LEAVE;
    }
}
