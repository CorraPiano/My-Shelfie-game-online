package it.polimi.ingsw.model;

import it.polimi.ingsw.client.localModel.LocalPlayer;

import java.util.UUID;


public class Player extends Listenable{
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

    private boolean connected;

    //METHODS
    public Player(String name, int gameID){
        this.name = name;
        library = new Bookshelf(name);
        //personalCard = new PersonalGoalCard(library);
        //creation of the ID code
        //ID = UUID.randomUUID().toString();
        ID = name + "_" + gameID;
        connected = true;
    }

    public boolean connectionState(){
        return connected;
    }
    public void disconnect(){
        connected = false;
    }
    public void reconnect() {
        connected = true;
    }
    /*public void bindListner(OldListener listener){
        //library.bindListener(listener);
        personalCard.setListener(listener);
        personalCard.send();
    }*/
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
        personalCard.setBookshelf(library);
        personalCard.setID(ID);
    }
    public void setEndGameToken() {
        this.endGameToken = new Token(1);
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
        return token1 != null;
    }
    public boolean haveToken2() {
        return token2 != null;
    }

    public int getPoints() { return points; }
    public void updatePoints(boolean isLastRound){
        this.points = 0;

        // points from tokens
        if(token1 != null) { this.points = this.points + this.token1.getValue(); }
        if(token2 != null) { this.points = this.points + this.token2.getValue(); }
        if(endGameToken != null) { this.points = this.points + this.endGameToken.getValue(); }
        // points from bookshelf
        if(library != null ) { this.points = this.points + this.library.calculatePoints(); }
        // points from personalGoalCard
        if(isLastRound && personalCard != null) { this.points = this.points + this.personalCard.calculatePoints(); }
    }

    public Token getToken1() {
        return token1;
    }

    public Token getToken2() {
        return token1;
    }

    public Token getEndGameToken() {
        return endGameToken;
    }

    public LocalPlayer getLocal(){
        return new LocalPlayer(name,firstPlayerSeat,endGameToken,token1,token2,points);
    }
}
