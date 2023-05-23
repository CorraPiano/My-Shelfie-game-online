package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class JoinMessage implements Sendable{
    // -> cmd: join
    // <- notify: player join

    public final String name;
    public final int gameID;

    public JoinMessage(String name,int gameID){
        this.name=name;
        this.gameID=gameID;
    }

    public JoinMessage(String name){
        this.name=name;
        this.gameID=-1;
    }

    public MessageHeader getHeader(){
        return MessageHeader.JOIN;
    }
}
