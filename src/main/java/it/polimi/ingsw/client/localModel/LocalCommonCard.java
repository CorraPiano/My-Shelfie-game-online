package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.Token;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocalCommonCard implements Serializable {
    public List<Token> tokenList;
    public int type;

    public LocalCommonCard(int type, List<Token> tokenList) {
        this.type = type;
        this.tokenList = tokenList;
    }

}
