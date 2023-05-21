package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class CommonGoalCard extends Listenable implements Serializable {
    private List<Token> token;
    protected int type;

    public CommonGoalCard(int type) {
        this.type = type;
    }

    public abstract boolean checkFullFil(Bookshelf library);

    public void setTokenList(List<Token> token){
        this.token = token;
        notifyListener("COMMON");
    }
    public ArrayList<Token> showToken(){
        return new ArrayList<>(token);
    }
    public Token popToken(){
        Token t = token.get(0);
        token.remove(0);
        notifyListener("COMMON");
        return t;
    }

    public int getType() {
        return type;
    }
}

