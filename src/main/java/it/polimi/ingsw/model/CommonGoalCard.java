package it.polimi.ingsw.model;

import it.polimi.ingsw.client.localModel.LocalCommonCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class CommonGoalCard extends Listenable {
    protected List<Token> token;
    protected int type;

    public CommonGoalCard(int type) {
        this.type = type;
    }

    public abstract boolean checkFullFil(Bookshelf library);

    public void setTokenList(List<Token> token){
        this.token = token;
        notifyUpdate();
    }
    public ArrayList<Token> showToken(){
        return new ArrayList<>(token);
    }
    public Token popToken(){
        Token t = token.get(0);
        token.remove(0);
        notifyUpdate();
        return t;
    }

    public int getType() {
        return type;
    }

    public LocalCommonCard getLocal(){
        return new LocalCommonCard(type,new ArrayList<>(token));
    }
}

