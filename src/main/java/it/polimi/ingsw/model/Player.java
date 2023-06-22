package it.polimi.ingsw.model;

import it.polimi.ingsw.client.localModel.LocalPlayer;


public class Player extends Listenable{
    //ATTRIBUTES
    private final String name;
    private final String ID; //example: 067e6162-3b6f-4ae2-a171-2470b63dff00
    private Token token1;
    private Token token2;
    private PersonalGoalCard personalCard;
    private int points;
    private final Bookshelf library;
    private boolean firstPlayerSeat;
    private Token endGameToken;

    private boolean endGame;

    private PlayerState playerState;
    private boolean connected;
    private boolean left;

    //METHODS
    public Player(String name, int gameID){
        this.name = name;
        library = new Bookshelf(name);
        //personalCard = new PersonalGoalCard(library);
        //creation of the ID code
        //ID = UUID.randomUUID().toString();
        //ID = "#" + name + "#" + gameID + "#" + UUID.randomUUID().toString();
        ID = name + "_" + gameID;
        //connected = true;
        //left = false;
        playerState = PlayerState.ACTIVE;
    }

    public PlayerState getState(){
        return playerState;
    }
    public boolean isConnected(){
        return playerState.equals(PlayerState.ACTIVE);
    }
    public boolean isInactive(){ return playerState.equals(PlayerState.INACTIVE);}

    public void disconnect(){
        playerState = PlayerState.DISCONNECTED;
    }
    public void reconnect() {
        playerState = PlayerState.ACTIVE;
    }
    public void leave(){
        playerState = PlayerState.INACTIVE;
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
    public void updatePoints(boolean endGame){
        this.points = 0;
        this.endGame=endGame;
        // points from tokens
        if(token1 != null) { this.points = this.points + this.token1.getValue(); }
        if(token2 != null) { this.points = this.points + this.token2.getValue(); }
        if(endGameToken != null) { this.points = this.points + this.endGameToken.getValue(); }
        // points from bookshelf
        this.points = this.points + this.library.calculatePoints();
        // points from personalGoalCard
        if(endGame && personalCard != null) {
            this.points = this.points + this.personalCard.calculatePoints();
        }
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
        if(endGame)
            return new LocalPlayer(name,firstPlayerSeat,endGameToken,token1,token2,points,personalCard.getNum(),playerState);
        return new LocalPlayer(name,firstPlayerSeat,endGameToken,token1,token2,points,playerState);
    }
}
