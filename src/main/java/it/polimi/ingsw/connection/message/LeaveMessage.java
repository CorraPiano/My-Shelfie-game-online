package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class LeaveMessage implements Sendable{

    public MessageHeader getHeader(){
        return MessageHeader.LEAVE;
    }
}
