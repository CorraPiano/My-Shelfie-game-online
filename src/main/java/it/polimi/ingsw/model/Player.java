package it.polimi.ingsw.model;

import it.polimi.ingsw.client.localModel.LocalPlayer;

/**
 * Represents a player in the game.
 */
public class Player extends Listenable{
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



    /**
     * Constructs a new player with the given name and game ID.
     * @param name the name of the player
     * @param gameID the game ID
     */
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

    /**
     * Gets the state of the player.
     * @return the player's state
     */
    public PlayerState getState(){
        return playerState;
    }

    /**
     * Checks if the player is connected.
     * @return true if the player is connected, false otherwise
     */
    public boolean isConnected(){
        return playerState.equals(PlayerState.ACTIVE);
    }

    /**
     * Checks if the player is inactive.
     * @return true if the player is inactive, false otherwise
     */
    public boolean isInactive(){ return playerState.equals(PlayerState.INACTIVE);}

    /**
     * Disconnects the player.
     */
    public void disconnect(){
        playerState = PlayerState.DISCONNECTED;
    }

    /**
     * Reconnects the player.
     */
    public void reconnect() {
        playerState = PlayerState.ACTIVE;
    }

    /**
     * Sets the player's state to inactive.
     */
    public void leave(){
        playerState = PlayerState.INACTIVE;
    }

    /*public void bindListner(OldListener listener){
        //library.bindListener(listener);
        personalCard.setListener(listener);
        personalCard.send();
    }*/

    /**
     * Sets token 1 for the player.
     * @param token1 the token 1 to set
     */
    public void setToken1(Token token1){
        this.token1 = token1;
    }

    /**
     * Sets token 2 for the player.
     * @param token2 the token 2 to set
     */
    public void setToken2(Token token2){
        this.token2 = token2;
    }

    /**
     * Sets the first player seat.
     * @param firstPlayerSeat true if the player has the first player seat, false otherwise
     */
    public void setFirstPlayerSeat(Boolean firstPlayerSeat) {
        this.firstPlayerSeat = firstPlayerSeat;
    }

    /**
     * Sets the personal goal card for the player.
     * @param personalCard the personal goal card to set
     */
    public void setPersonalGoalCard(PersonalGoalCard personalCard) {
        this.personalCard = personalCard;
        personalCard.setBookshelf(library);
        personalCard.setID(ID);
    }

    /**
     * Sets the end game token for the player.
     */
    public void setEndGameToken() {
        this.endGameToken = new Token(1);
    }

    /**
     * Retrieves the name of the player.
     * @return the player's name
     */
    public String getName(){
        return name;
    }

    /**
     * Retrieves the bookshelf of the player.
     * @return the player's bookshelf
     */
    public Bookshelf getLibrary() {
        return library;
    }

    /**
     * Checks if the player has the first player seat.
     * @return true if the player has the first player seat, false otherwise
     */
    public boolean getFirstPlayerSeat() {
        return firstPlayerSeat;
    }

    /**
     * Retrieves the ID of the player.
     * @return the player's ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Checks if the player has token 1.
     * @return true if the player has token 1, false otherwise
     */
    public boolean haveToken1() {
        return token1 != null;
    }

    /**
     * Checks if the player has token 2.
     * @return true if the player has token 2, false otherwise
     */
    public boolean haveToken2() {
        return token2 != null;
    }

    /**
     * Retrieves the points of the player.
     * @return the player's points
     */
    public int getPoints() { return points; }

    /**
     * Updates the player's points.
     * @param endGame true if it's the end game, false otherwise
     */
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

    /**
     * Retrieves token 1 of the player.
     * @return the player's token 1
     */
    public Token getToken1() {
        return token1;
    }

    /**
     * Retrieves token 2 of the player.
     * @return the player's token 2
     */
    public Token getToken2() {
        return token1;
    }

    /**
     * Retrieves the end game token of the player.
     * @return the player's end game token
     */
    public Token getEndGameToken() {
        return endGameToken;
    }
    public PersonalGoalCard getPersonalCard() {
        return personalCard;
    }

    /**
     * Converts into the local representation of the player.
     * @return the local representation of the player
     */
    public LocalPlayer getLocal(){
        if(endGame)
            return new LocalPlayer(name,firstPlayerSeat,endGameToken,token1,token2,points,personalCard.getNum(),playerState);
        return new LocalPlayer(name,firstPlayerSeat,endGameToken,token1,token2,points,playerState);
    }


}
