package it.polimi.ingsw.model;
import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.connection.message.*;

import java.util.ArrayList;

/**
 * The `Gameplay` class represents the gameplay logic of the game. It manages the game state, players, turns, and actions
 * performed during the game.
 */
public class Gameplay extends Listenable{

    private final GameMode gameMode;
    private GameState gameState;
    private final int gameID;
    private final int numPlayers;
    private final Board board;
    private CommonGoalCard commonGoalCard1;
    private CommonGoalCard commonGoalCard2;
    private final BagPersonal bagPersonal;
    private final BagCommon bagCommon;
    private PlayerHandler playerHandler;
    private final Hand hand;
    //private ArrayList<Player> playerList;
    private EventKeeper eventKeeper;
    private int numDisconnection;

    //da eliminare
    //private final BroadcasterRMI broadcasterRMI;
    //private final OldListener listener;

    /**
     * Constructs a `Gameplay` instance with the specified game mode, number of players, and game ID.
     *
     * @param gameMode   game mode.
     * @param numPlayers number of players.
     * @param gameID     game ID.
     * @throws NumPlayersException if the number of players is invalid.
     * @throws GameModeException   if the game mode is invalid.
     */
    public Gameplay(GameMode gameMode, int numPlayers, int gameID) throws NumPlayersException, GameModeException {
        if(!gameMode.equals(GameMode.EASY) && !gameMode.equals(GameMode.EXPERT))
            throw new GameModeException();
        if(numPlayers<=1 || numPlayers>4)
            throw new NumPlayersException();

        numDisconnection=0;
        this.gameID = gameID;
        this.gameMode = gameMode;
        this.numPlayers = numPlayers;
        //.playerList = new ArrayList<Player>();
        this.hand = new Hand();
        this.board = new Board(numPlayers,hand);
        this.playerHandler =  new PlayerHandler(this);

        eventKeeper = new EventKeeper(this);
        setEventKeeper(eventKeeper);
        hand.setEventKeeper(eventKeeper);
        board.setEventKeeper(eventKeeper);
        playerHandler.setEventKeeper(eventKeeper);

        bagPersonal = new BagPersonal();
        if (gameMode.equals(GameMode.EXPERT))
            bagCommon = new BagCommon();
        else
            bagCommon = null;
        gameState = GameState.WAIT;

        notifyEvent(new CreateMessage(gameMode,gameID,numPlayers));
    }

    /**
     * Adds a player to the game with the specified name.
     *
     * @param name name of the player to add.
     * @return created `Player` object.
     * @throws GameFullException             if the game is already full.
     * @throws NameAlreadyExistentException  if the player name already exists.
     */
    public Player addPlayer(String name) throws GameFullException, NameAlreadyExistentException {
        if(!playerHandler.checkName(name))
            throw new NameAlreadyExistentException();

        Player player = new Player(name,gameID);
        player.setEventKeeper(eventKeeper);
        player.getLibrary().setEventKeeper(eventKeeper);
        eventKeeper.addPersonalList(player.getID());
        playerHandler.addPlayer(player);

        //playerList.add(player);
        //notifyUpdate();
        notifyEvent(new JoinMessage(name));

        return player;
    }

    /**
     * Checks if the game is ready to start.
     *
     * @return `true` if the game is ready, `false` otherwise.
     */
    public boolean isReady(){
        return playerHandler.getNumPlayer()==numPlayers;
        //return playerList.size() == numPlayers;
    }

    /**
     * Checks if the game has finished.
     *
     * @return `true` if the game has finished, `false` otherwise.
     */
    public boolean isFinished(){
        return gameState.equals(GameState.END);
    }

    /**
     * Starts the game.
     */
    public void startGame(){
        gameState = GameState.GAME;

        //crea le common, che una volta ricevuta la tokenlist si aggiorneranno inviandosi
        if (gameMode.equals(GameMode.EXPERT)){
            commonGoalCard1 = bagCommon.drawCommonGoalCard();
            commonGoalCard1.setEventKeeper(eventKeeper);
            commonGoalCard1.setTokenList(createTokenList());
            commonGoalCard2 = bagCommon.drawCommonGoalCard();
            commonGoalCard2.setEventKeeper(eventKeeper);
            commonGoalCard2.setTokenList(createTokenList());
        }

        //crea le personal, che si autoinvia al giocatore corretto
        for(Player p: playerHandler.getPlayerList()) {
            PersonalGoalCard personalCard = bagPersonal.drawPersonalGoalCard();
            personalCard.setEventKeeper(eventKeeper);
            p.setPersonalGoalCard(personalCard);
        }

        //aggiorna la board, che si autoinvia
        board.drawBoardItems();

        // notifica l'avvio del gioco
        eventKeeper.notifyAll(new StartGameMessage());

        //sceglie il primo giocatore
        playerHandler.choseFirstPlayer();
        playerHandler.current().setFirstPlayerSeat(true);
        eventKeeper.notifyAll(new NewTurnMessage(playerHandler.current().getName()));



        // crea playerhandler
        //playerHandler = new PlayerHandler(playerList);

        //eventKeeper.notifyAll(getLocal());
        //for(Player p:playerList)
        //    p.getLibrary().sendBookshelf();
        //far iniziare a caso il primo giocatore


    }

    /**
     * Creates list of tokens
     * @return list of tokens created
     */
    private ArrayList<Token> createTokenList(){
        ArrayList<Token> list = new ArrayList<>();
        list.add(new Token(8));
        list.add(new Token(6));
        if(numPlayers>2)
            list.add(new Token(4));
        if(numPlayers>3)
            list.add(new Token(2));
        return list;
    }

    /**
     * Picks an item from the living room.
     *
     * @param coordinates The coordinates of the item to pick.
     * @throws NotLinearPickException    If the pick is not linear.
     * @throws LimitReachedPickException If the pick limit is reached.
     * @throws NotCatchablePickException If the item cannot be caught.
     * @throws EmptySlotPickException    If the slot is empty.
     * @throws OutOfBoardPickException   If the pick is out of the board.
     */
    public void pickItem(Coordinates coordinates) throws NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, OutOfBoardPickException {
            Item item = board.getLivingRoomItem(coordinates);
            board.getItem(coordinates);
            eventKeeper.notifyAll(new PickMessage(coordinates,playerHandler.current().getName(),item));
    }

    /**
     * Releases the hand by putting back the picked items in the living room.
     */
    public void releaseHand() {
        board.releaseHand();
        eventKeeper.notifyAll(new UndoMessage(playerHandler.current().getName()));
    }

    /**
     * Selects the order in which the items from the hand will be placed in the library.
     *
     * @param list The list representing the order of items.
     * @throws WrongLengthOrderException    If the length of the order list is incorrect.
     * @throws WrongContentOrderException   If the content of the order list is incorrect.
     */
    public void selectOrderHand(ArrayList<Integer> list) throws WrongLengthOrderException, WrongContentOrderException {
        hand.selectOrder(list);
        //broadcasterRMI.notifyOrder(gameID, "", list);
        eventKeeper.notifyAll(new OrderMessage(list,playerHandler.current().getName()));
    }

    /**
     * Puts the items from the hand into the specified column of the player's library.
     *
     * @param column column index where the items will be put.
     * @throws EmptyHandException           If the hand is empty.
     * @throws InvalidColumnPutException    If the specified column is invalid.
     * @throws NotEnoughSpacePutException   If there is not enough space in the column to put the items.
     */
    public void putItemList(int column) throws EmptyHandException, InvalidColumnPutException, NotEnoughSpacePutException {
        if(hand.getSize()==0)
            throw new EmptyHandException();
        Player currentPlayer = playerHandler.current();
        Bookshelf library = currentPlayer.getLibrary();
        library.putItemList(hand.getHand(),column);
        board.endTurn();

        eventKeeper.notifyAll(new PutMessage(column,playerHandler.current().getName()));

        if(gameMode.equals(GameMode.EXPERT))
            checkFullfillCommonGoalCard(currentPlayer);
        if(library.isFull()) {
            currentPlayer.setEndGameToken();
            playerHandler.notifyLastRound();
            notifyEvent(new LastRoundMessage(playerHandler.current().getName()));
        }
        currentPlayer.updatePoints(false);
        endTurn();
    }

    /**
     * Ends the current player's turn and switches to the next player.
     */
    public void endTurn() {
        if (playerHandler.next()){
            notifyEvent(new NewTurnMessage(playerHandler.current().getName()));
        }
        else
            gameState = GameState.END;
    }

    /**
     * Ends the game and determines the winner.
     */
    public void endGame(){
        gameState = GameState.END;
        String name = playerHandler.makeFinalClassification();
        if(playerHandler.numPlayersAvaiable()<2)
            eventKeeper.notifyAll(new EndGameMessage(name,EndCause.NOT_ENOUGH_ACTIVE_PLAYER));
        else if(playerHandler.numPlayersConnected()<2)
            eventKeeper.notifyAll(new EndGameMessage(name,EndCause.DISCONNECTED_PLAYERS_NOT_RECONNECT_ON_TIME));
        else
            eventKeeper.notifyAll(new EndGameMessage(name,EndCause.LAST_ROUND_FINISHED));
    }

    private void checkFullfillCommonGoalCard(Player currentPlayer ){
        if(!currentPlayer.haveToken1()){
            if(commonGoalCard1.checkFullFil(currentPlayer.getLibrary()))
                currentPlayer.setToken1(commonGoalCard1.popToken());
        }
        if(!currentPlayer.haveToken2()){
            if(commonGoalCard2.checkFullFil(currentPlayer.getLibrary()))
                currentPlayer.setToken2(commonGoalCard2.popToken());
        }
    }

    /**
     * Adds a chat message to the game.
     *
     * @param chatMessage The chat message to add.
     * @throws InvalidNameException If the sender name is invalid.
     */
    public void addChatMessage(ChatMessage chatMessage) throws InvalidNameException {
        if(chatMessage.all) {
            eventKeeper.notifyAll(chatMessage);
            return;
        }
        ArrayList<Player> playerList = playerHandler.getPlayerList();
        if(!chatMessage.receiver.equals(chatMessage.sender) && playerList.stream().map(Player::getName).anyMatch(s -> s.equals(chatMessage.receiver)))
        {
            for(Player p:playerList){
                if(p.getName().equals(chatMessage.receiver))
                    eventKeeper.notifyToID(p.getID(),chatMessage);
                if(p.getName().equals(chatMessage.sender))
                    eventKeeper.notifyToID(p.getID(),chatMessage);
            }
            return;
        }
        throw new InvalidNameException();
    }

    //si deve poter stoppare il gioco prima della fine

    /**
     * Removes a player from the game.
     *
     * @param id The ID of the player to be removed.
     */
    public void leave(String id){
        getPlayerByID(id).leave();
        notifyEvent(new LeaveMessage(getPlayerNameByID(id)));
        playerMiss(id);
    }

    /**
     * Disconnects a player from the game.
     *
     * @param id The ID of the player to be disconnected.
     */
    public void disconnect(String id){
        numDisconnection++;
        getPlayerByID(id).disconnect();
        notifyEvent(new DisconnectMessage(getPlayerNameByID(id)));
        //System.out.println(getPlayerNameByID(id)+" si è disconnesso");
        playerMiss(id);
    }

    /**
     * Returns the number of player disconnections that have occurred during the game.
     *
     * @return The number of player disconnections.
     */
    public int getNumDisconnection(){
        return numDisconnection;
    }

    /**
     * Handles the case when a player is missing (disconnected or left the game) during the game.
     *
     * @param id The ID of the missing player.
     */
    private void playerMiss(String id){
        if(gameState.equals(GameState.WAIT)){
            playerHandler.removePlayer(id);
        }
        else if(gameState.equals(GameState.GAME) && playerHandler.current().getID().equals(id))
        {
            board.releaseHand();
            endTurn();
        }
    }

    /**
     * Reconnects a player to the game.
     *
     * @param id The ID of the player to be reconnected.
     * @throws GameFinishedException      If the game has already ended.
     * @throws AlreadyConnectedException  If the player is already connected.
     * @throws GameLeftException          If the player has left the game.
     */
    public void reconnect(String id) throws GameFinishedException, AlreadyConnectedException, GameLeftException {
        if(gameState.equals(GameState.END) || getPlayerByID(id).isConnected() )
            throw new GameFinishedException();
        if(getPlayerByID(id).isConnected())
            throw new AlreadyConnectedException();
        if(getPlayerByID(id).isInactive())
            throw new GameLeftException();
        getPlayerByID(id).reconnect();
        eventKeeper.notifyAll(new ReconnectMessage(getPlayerNameByID(id)));
    }

    /**
     * Returns the list of players in the game.
     *
     * @return The list of players in the game.
     */
    public ArrayList<Player> getPlayerList(){
        return playerHandler.getPlayerList();
    }

    /**
     * Returns the game mode of the game.
     *
     * @return The game mode of the game.
     */
    public GameMode getGameMode(){
        return gameMode;
    }

    /**
     * Returns the current state of the game.
     *
     * @return The current state of the game.
     */
    public GameState getGameState(){
        return gameState;
    }

    /**
     * Returns the ID of the current player.
     *
     * @return The ID of the current player.
     */
    public String getCurrentPlayerID(){
        return playerHandler.current().getID();
    }

    /**
     * Returns the player object with the specified ID.
     *
     * @param id The ID of the player.
     * @return The player object with the specified ID, or null if not found.
     */
    public Player getPlayerByID(String id){
        return playerHandler.getPlayerByID(id);
    }

    /**
     * Returns the name of the player with the specified ID.
     *
     * @param id The ID of the player.
     * @return The name of the player with the specified ID, or null if not found.
     */
    public String getPlayerNameByID(String id){
        Player player = playerHandler.getPlayerByID(id);
        if(player!=null)
            return player.getName();
        return null;
    }

    /**
     * Returns a LocalGame object representing the current state of the game.
     *
     * @return A LocalGame object representing the current state of the game.
     */
    public LocalGame getLocal(){
        return new LocalGame(gameMode,gameID,numPlayers,playerHandler.getNumPlayer(),gameState);
    }

    /**
     * Returns the ID of the game.
     *
     * @return The ID of the game.
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Returns the total number of players in the game.
     *
     * @return The total number of players in the game.
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Returns the current number of players in the game.
     *
     * @return The current number of players in the game.
     */
    public int getCurrentPlayers() {
        return playerHandler.getNumPlayer();
    }

    /**
     * Returns the event keeper object responsible for managing game events.
     *
     * @return The event keeper object.
     */
    public EventKeeper getEventKeeper(){
        return eventKeeper;
    }

    public String getPlayerIDByName(String name){
        return playerHandler.getPlayerIDByName(name);
    }

    /**
     * Returns a boolean indicating if the current player is connected.
     *
     * @return true if the current player is connected, false otherwise.
     */
    public boolean currentPlayerIsConnected(){
        return playerHandler.current().isConnected();
    }

    /**
     * Returns the number of players currently connected.
     *
     * @return The number of players currently connected.
     */
    public int getNumPlayersConnected(){
        return playerHandler.numPlayersConnected();
    }

    /**
     * Returns a boolean indicating if the player with the specified ID is connected.
     *
     * @param id The ID of the player.
     * @return true if the player is connected, false otherwise.
     */
    public boolean isConnected(String id){
        return playerHandler.getPlayerByID(id).isConnected();
    }

    /**
     * Checks if the timer for a player has expired.
     * If the conditions are met, a TimerMessage event is notified.
     *
     * @param time The time at which the timer started.
     * @return true if the timer has expired and the conditions are met, false otherwise.
     */
    public boolean checkTimer(Long time){
        long n = time + 60000 - System.currentTimeMillis();
        boolean b = (!isFinished() && getNumDisconnection() == numDisconnection && getNumPlayersConnected() < 2 && n>0 && !currentPlayerIsConnected());
        if(b)
            notifyEvent(new TimerMessage((int)n));
        return b;
        //System.currentTimeMillis() - time < 60000
    }


}



