package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.Token;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocalCommonCard implements Serializable, Sendable {
    public final ArrayList<Token> tokenList;
    public final int type;

    public LocalCommonCard(int type, ArrayList<Token> tokenList) {
        this.type = type;
        this.tokenList = tokenList;
    }

    public int getType(){
        return type;
    }

    public ArrayList<Token> showToken(){
        return tokenList;
    }

    public MessageHeader getHeader(){
        return MessageHeader.COMMONGOALCARD;
    }

}
