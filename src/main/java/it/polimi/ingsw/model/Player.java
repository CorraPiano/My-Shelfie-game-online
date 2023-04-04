package it.polimi.ingsw.model;

import java.util.UUID;


public class Player {
    //ATTRIBUTES
    private String name;
    private String ID; //example: 067e6162-3b6f-4ae2-a171-2470b63dff00
    private Token token1;
    private Token token2;
    private PersonalGoalCard personalCard;
    private int points;
    private Bookshelf library;
    private boolean firstPlayerSeat;
    private Token endGameToken;

    //METHODS
    public Player(String name){
        this.name = name;
        library = new Bookshelf();
        personalCard = new PersonalGoalCard(library);

        //creation of the ID code
        ID = UUID.randomUUID().toString();
    }
    public void setToken1(Token token){
        token1 = token;
    }
    public void setToken2(Token token){
        token2 = token;
    }
    public void setFirstPlayerSeat(Boolean firstPlayerSeat) {
        this.firstPlayerSeat = firstPlayerSeat;
    }
    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {
        this.personalCard = personalGoalCard;
    }

    public String getName(){
        return name;
    }
    public Bookshelf getLibrary() {
        return library;
    }
    public boolean getFirstPlayerSeat() {
        return firstPlayerSeat;
    }
    public boolean haveToken1() {
        if(token1 != null) {
            return true;
        }
        return false;
    }
    public boolean haveToken2() {
        if(token2 != null) {
            return true;
        }
        return false;
    }

    public int getPoints() { return points; }
    public void updatePoints(boolean isLastRoud){
        // calcolo i punti da Bookshelf
        // calcolo i punti dai Token
        // calcolo i punti da endGameToken
        return;
    }

    //TODO: add method changeName()?
}
