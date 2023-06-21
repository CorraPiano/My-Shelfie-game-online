package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.Item;

import java.io.Serializable;

public class LocalBoard implements Serializable, Sendable {
    public final Item[][] board;

    public LocalBoard(Item[][] board){
        this.board=board;
    }
    public MessageHeader getHeader(){
        return MessageHeader.BOARD;
    }
}
