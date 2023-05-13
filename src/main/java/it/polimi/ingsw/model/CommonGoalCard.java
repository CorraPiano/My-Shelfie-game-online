package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonGoalCard {
    private List<Token> token;
    private int type;
    public abstract boolean checkFullFil(Bookshelf library);

    public void setTokenList(List<Token> token){
        this.token = new ArrayList<>(token);
    }
    public List<Token> showToken(){
        return new ArrayList<>(token);
    }
    public Token popToken(){
        Token t = token.get(0);
        token.remove(0);
        return t;
    }

    public int getType() {
        return type;
    }
}

