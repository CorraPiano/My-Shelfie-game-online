package it.polimi.ingsw.model;
import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.controller.Settings;
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
    private final PlayerHandler playerHandler;
    private final Hand hand;
    private final EventKeeper eventKeeper;
    private int numDisconnection;

    // creation of the gameplay and join of the players

    /**
     * Create a `Gameplay` instance with the specified game mode, number of players, and game ID.
     *
     * @param gameMode   game mode.
     * @param numPlayers number of players.
     * @param gameID     game ID.
     * @throws NumPlayersException if the number of players is invalid.
     * @throws GameModeException   if the game mode is invalid.
     */
    public Gameplay(GameMode gameMode, int numPlayers, int gameID) throws NumPlayersException, GameModeException {
        // exception handling
        if(!gameMode.equals(GameMode.EASY) && !gameMode.equals(GameMode.EXPERT))
            throw new GameModeException();
        if(numPlayers<=1 || numPlayers>4)
            throw new NumPlayersException();

        // used to stop the threads over Timer
        numDisconnection=0;

        // setting of the properties
        this.gameID = gameID;
        this.gameMode = gameMode;
        this.numPlayers = numPlayers;

        // creation of the model objects
        hand = new Hand();
        board = new Board(numPlayers,hand);
        playerHandler =  new PlayerHandler();
        eventKeeper = new EventKeeper();

        // setting of the eventKeeper
        this.setEventKeeper(eventKeeper);
        hand.setEventKeeper(eventKeeper);
        board.setEventKeeper(eventKeeper);
        playerHandler.setEventKeeper(eventKeeper);

        // creation of the bags
        bagPersonal = new BagPersonal();
        if (gameMode.equals(GameMode.EXPERT))
            bagCommon = new BagCommon();
        else
            bagCommon = null;

        //setting of the GameState
        gameState = GameState.WAIT;

        notifyEvent(new CreateMessage(gameMode,gameID,numPlayers));
    }

    /**
     * Adds a player to the game with the specified name.
     * For the unicity of the id, if a player join with a name,
     * is not possible for others players to join with the same name,
     * also if the player has left the game in the waiting phase.
     * @param name name of the player to add.
     * @return created `Player` object.
     * @throws GameFullException             if the game is already full.
     * @throws NameAlreadyExistentException  if the player name already exists.
     */
    public Player addPlayer(String name) throws GameFullException, NameAlreadyExistentException {
        // exception handling
        if(!playerHandler.checkName(name))
            throw new NameAlreadyExistentException();

        // creation and setting of the player
        Player player = new Player(name,gameID);
        player.setEventKeeper(eventKeeper);
        player.getLibrary().setEventKeeper(eventKeeper);
        playerHandler.addPlayer(player);

        // insert the eventList of the player in the EventKeeper
        eventKeeper.addPersonalList(player.getID());

        notifyEvent(new JoinMessage(name));
        return player;
    }


    /**
     * Starts the game. No checks if the correct number of player has been reached.
     */
    public void startGame(){

        // creation of the 2 common goal cards if the game mode is Expert
        if (gameMode.equals(GameMode.EXPERT)){
            commonGoalCard1 = bagCommon.drawCommonGoalCard();
            commonGoalCard1.setEventKeeper(eventKeeper);
            commonGoalCard1.setTokenList(createTokenList());
            commonGoalCard2 = bagCommon.drawCommonGoalCard();
            commonGoalCard2.setEventKeeper(eventKeeper);
            commonGoalCard2.setTokenList(createTokenList());
        }

        // creation of the personal cards, one for player
        for(Player p: playerHandler.getPlayerList()) {
            PersonalGoalCard personalCard = bagPersonal.drawPersonalGoalCard();
            personalCard.setEventKeeper(eventKeeper);
            p.setPersonalGoalCard(personalCard);
        }

        // init the game
        board.drawBoardItems();
        playerHandler.choseFirstPlayer();
        gameState = GameState.GAME;

        // notify the begin of the game and of the first turn
        notifyEvent(new StartGameMessage());
        notifyEvent(new NewTurnMessage(playerHandler.current().getName()));
    }

    /**
     * Returns the token list for a common goal card, according with the number of player.
     *
     * @return created list of tokens.
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

    // action of the players

    /**
     * Checks if is the turn of the player with the passed ID.
     *
     * @param id       the ID of the player
     * @throws NotInGameException if the player is not currently in a game
     * @throws WrongTurnException if it is not the player's turn
     */
    public void validateAction(String id) throws NotInGameException, WrongTurnException {
        if(!(gameState==GameState.GAME))
            throw new NotInGameException();
        if(!playerHandler.current().getID().equals(id))
            throw new WrongTurnException();
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
            notifyEvent(new PickMessage(coordinates,playerHandler.current().getName(),item));
    }

    /**
     * Releases the hand by putting back the picked items in the board.
     * If the hand is empty, nothing happen.
     */
    public void releaseHand() {
        board.releaseHand();
        notifyEvent(new UndoMessage(playerHandler.current().getName()));
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
        notifyEvent(new OrderMessage(list,playerHandler.current().getName()));
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

        // put
        Player currentPlayer = playerHandler.current();
        Bookshelf library = currentPlayer.getLibrary();
        library.putItemList(hand.getHand(),column);
        board.endTurn();

        notifyEvent(new PutMessage(column,playerHandler.current().getName()));

        // update points
        if(gameMode.equals(GameMode.EXPERT))
            checkFullfillCommonGoalCard(currentPlayer);
        if(library.isFull()) {
            currentPlayer.setEndGameToken();
            playerHandler.notifyLastRound();
            notifyEvent(new LastRoundMessage(playerHandler.current().getName()));
        }
        playerHandler.current().updatePoints(false);

        endTurn();
    }

    /**
     * Ends the current player's turn and switches to the next player.
     * If the game is finished or if there are less than two players avaiable,
     * no turn is set.
     */
    private void endTurn() {
        playerHandler.next();
        if (playerHandler.hasNext()){
            notifyEvent(new NewTurnMessage(playerHandler.current().getName()));
        }
    }

    /**
     * Ends the game.
     */
    public void endGame(){
        gameState = GameState.END;
        // determines the winner
        String name = playerHandler.makeFinalClassification();

        // notification
        if(playerHandler.getNumPlayersAvaiable()<2)
            notifyEvent(new EndGameMessage(name,EndCause.NOT_ENOUGH_ACTIVE_PLAYER));
        else if(playerHandler.getNumPlayersConnected()<2)
            notifyEvent(new EndGameMessage(name,EndCause.DISCONNECTED_PLAYERS_NOT_RECONNECT_ON_TIME));
        else
            notifyEvent(new EndGameMessage(name,EndCause.LAST_ROUND_FINISHED));
    }

    /** If the player fullfill the condition of one of the common goal card,
    *  it gives the token to the player.
    *
    *  @param currentPlayer player to check
    */
    private void checkFullfillCommonGoalCard(Player currentPlayer){
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
     * Adds a message to the chat:
     * if the message is private, it will send only to the sender and to the receiver,
     * if the message is global, it will be send to everyone.
     *
     * @param chatMessage The chat message to be added.
     * @throws InvalidNameException If the receiver name is invalid.
     */
    public void addChatMessage(ChatMessage chatMessage) throws InvalidNameException {
        //global message
        if(chatMessage.all) {
            notifyEvent(chatMessage);
            return;
        }

        //private message
        ArrayList<Player> playerList = playerHandler.getPlayerList();
        if(!chatMessage.receiver.equals(chatMessage.sender) && playerList.stream().map(Player::getName).anyMatch(s -> s.equals(chatMessage.receiver)))
        {
            for(Player p:playerList){
                if(p.getName().equals(chatMessage.receiver))
                    notifyEventToID(p.getID(),chatMessage);
                if(p.getName().equals(chatMessage.sender))
                    notifyEventToID(p.getID(),chatMessage);
            }
            return;
        }
        throw new InvalidNameException();
    }

    // leaving, disconnection and reconnection

    /**
     * Removes a player from the game. If the removed player owned the turn and if there are enough player, the turn is switched.
     * You should check if the game have to be finished after this invocation.
     *
     * @param id The ID of the player to be removed.
     */
    public void leave(String id) {
        // leave
        String name = getPlayerNameByID(id);
        playerHandler.playerLeave(id,gameState);
        notifyEvent(new LeaveMessage(name));

        // end turn ?
        if(gameState.equals(GameState.GAME) && playerHandler.getNumPlayersAvaiable()>=2 && playerHandler.current().getID().equals(id)) {
            board.releaseHand();
            endTurn();
        }

    }

    /**
     * Disconnects a player from the game. If the removed player owned the turn and if there are enough connected player, the turn is switched.
     * You should check if the game have to be finished after this invocation.
     *
     * @param id The ID of the player to be disconnected.
     */
    public void disconnect(String id) throws GameFinishedException {
        // for the timer thread
        numDisconnection++;

        // disconnection
        playerHandler.playerDisconnect(id,gameState);
        notifyEvent(new DisconnectMessage(getPlayerNameByID(id)));

        // end turn ?
        if(gameState.equals(GameState.GAME) && playerHandler.current().getID().equals(id) && playerHandler.getNumPlayersConnected()>=2)
        {
            board.releaseHand();
            endTurn();
        }
    }

    /**
     * Reconnects a player to the game. If the current player isn't connected and there are more than one players, the turn is switched.
     * You should check if the game have to be finished after this invocation.
     *
     * @param id The ID of the player to be reconnected.
     * @return The name of the player that reconnected.
     * @throws AlreadyConnectedException  If the player is already connected.
     * @throws GameLeftException          If the player has left the game.
     * @throws InvalidIdException       If the ID is not valid.
     */
    public String reconnect(String id) throws AlreadyConnectedException, GameLeftException, InvalidIdException {
        // check
        if(getPlayerByID(id)==null)
            throw new InvalidIdException();
        if(getPlayerByID(id).isConnected())
            throw new AlreadyConnectedException();
        if(getPlayerByID(id).isInactive())
            throw new GameLeftException();

        // reconnection
        playerHandler.reconnect(id);
        notifyEvent(new ReconnectMessage(getPlayerNameByID(id)));

        // end turn ?
        if(playerHandler.getNumPlayersConnected()>=2 && !playerHandler.current().isConnected()) {
            board.releaseHand();
            endTurn();
        }

        return playerHandler.getPlayerByID(id).getName();
    }

    // timer

    /**
     * Checks if the time for a player to reconnect has expired.
     *
     * @param time The time at which the timer started.
     * @return true if the timer has expired and the conditions are met, false otherwise.
     */
    public boolean checkTimer(Long time, int numDisconnection){
        long n = time + Settings.timeout_timer - System.currentTimeMillis();
        boolean b = (!isFinished() && getNumDisconnection() == numDisconnection && getNumPlayersConnected() < 2 && n>0);
        if(b)
            notifyEvent(new TimerMessage((int)n));
        return b;
    }

    /**
     * Returns the number of player disconnections that have occurred during the game.
     *
     * @return The number of player disconnections.
     */
    public int getNumDisconnection(){
        return numDisconnection;
    }

    // localgame and eventkeeper

    /**
     * Returns a LocalGame object representing the current state of the game.
     *
     * @return A LocalGame object representing the current state of the game.
     */
    public LocalGame getLocal(){
        return new LocalGame(gameMode,gameID,numPlayers,playerHandler.getNumPlayer(),gameState);
    }

    /**
     * Returns the event keeper object responsible for managing game events.
     *
     * @return The event keeper object.
     */
    public EventKeeper getEventKeeper(){
        return eventKeeper;
    }

    // utils

    /**
     * Checks if the game is ready to start.
     * This means that the request number of players has been reached.
     * @return `true` if the game is ready, `false` otherwise.
     */
    public boolean isReady(){
        return playerHandler.getNumPlayer()==numPlayers;
    }

    /**
     * Checks if the game has finished.
     * This happens if is finished the last turn or if less than two players are left.
     * @return `true` if the game has finished, `false` otherwise.
     */
    public boolean isFinished(){
        return !playerHandler.hasNext() || getNumPlayersAvaiable()<2;
    }

    /**
     * Returns the total number of players request to start the game.
     *
     * @return The total number of players request to start the game.
     */
    public int getNumPlayers() {
        return numPlayers;
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
     * Returns the ID of the game.
     *
     * @return The ID of the game.
     */
    public int getGameID() {
        return gameID;
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
     * Returns the list of players in the game.
     *
     * @return The list of players in the game.
     */
    public ArrayList<Player> getPlayerList(){
        return playerHandler.getPlayerList();
    }

    /**
     * Returns the number of players currently connected.
     *
     * @return The number of players connected.
     */
    public int getNumPlayersConnected(){
        return playerHandler.getNumPlayersConnected();
    }

    /**
     * Returns the number of players currently in the game, connected or not.
     *
     * @return The number of players currently in the game.
     */
    public int getNumPlayersAvaiable(){
        return playerHandler.getNumPlayersAvaiable();
    }

}



