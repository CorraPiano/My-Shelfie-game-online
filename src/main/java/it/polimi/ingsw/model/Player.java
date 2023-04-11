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
    public void setToken1(Token token1){
        this.token1 = token1;
    }
    public void setToken2(Token token2){
        this.token2 = token2;
    }
    public void setFirstPlayerSeat(Boolean firstPlayerSeat) {
        this.firstPlayerSeat = firstPlayerSeat;
    }
    public void setPersonalGoalCard(PersonalGoalCard personalCard) {
        this.personalCard = personalCard;
    }
    public void setEndGameToken() {
        Token endGameToken = new Token(1);
        this.endGameToken = endGameToken;
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
    public String getID() {
        return ID;
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
        this.points = 0;
        if(!isLastRoud) {
            // points from token
            this.points = this.points + this.token1.getValue();
            this.points = this.points + this.token2.getValue();
            this.points = this.points + this.endGameToken.getValue();
            // points from bookshelf
            this.points = this.points + this.library.calculatePoints();
        }
        else if (isLastRoud) {
            // points from token
            this.points = this.points + this.token1.getValue();
            this.points = this.points + this.token2.getValue();
            this.points = this.points + this.endGameToken.getValue();
            // points from bookshelf
            this.points = this.points + this.library.calculatePoints();
            // points from personalGoalCard
            this.points = this.points + this.personalCard.calculatePoints();
        }
    }

    //TODO: add method changeName()?
}
