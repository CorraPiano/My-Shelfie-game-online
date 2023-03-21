package it.polimi.ingsw.model;

import java.util.UUID;


public class Player {
    //ATTRIBUTES
    private String name;
    private String ID; //example: 067e6162-3b6f-4ae2-a171-2470b63dff00
    private Token token1;
    private Token token2;
    private PersonalGoalCard personalCard;
    private int point; //TODO
    private Bookshelf library;
    private boolean firstPlayerSeat;

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

    public String getName(){
        return name;
    }
    public Token getToken1(){
        return token1;
    }
    public Token getToken2(){
        return token2;
    }
    public Bookshelf getLibrary(){
        return library;
    }
    public boolean getFirstPlayerSeat() {
        return firstPlayerSeat;
    }
    public void updatePoints(boolean isLastRoud){
        return;
    } //TODO
    public int getPoints() { return 0; } //TODO

    //TODO: add method changeName()?
}
