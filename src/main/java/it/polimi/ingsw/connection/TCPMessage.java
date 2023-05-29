package it.polimi.ingsw.connection;

import it.polimi.ingsw.connection.message.Sendable;

public class TCPMessage {
    private MessageHeader header;
    private String body;

    public TCPMessage(MessageHeader header, String body){
        this.header=header;
        this.body=body;
    }

    public MessageHeader getHeader(){return header;}

    public String getBody(){return body;}
}

