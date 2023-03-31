package it.polimi.ingsw.model;

public interface CommonGoalCard {
    public boolean checkFullFil(Bookshelf library);
    public void setTokenList();
    public Token showToken();
    public Token popToken();
}

