package it.polimi.ingsw.model;
import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.connection.message.*;

import java.util.ArrayList;

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

    public boolean isReady(){
        return playerHandler.getNumPlayer()==numPlayers;
        //return playerList.size() == numPlayers;
    }

    public boolean isFinished(){
        return gameState.equals(GameState.END);
    }


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

    public void pickItem(Coordinates coordinates) throws NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, OutOfBoardPickException {
            Item item = board.getLivingRoomItem(coordinates);
            board.getItem(coordinates);
            eventKeeper.notifyAll(new PickMessage(coordinates,playerHandler.current().getName(),item));
    }

    public void releaseHand() {
        board.releaseHand();
        eventKeeper.notifyAll(new UndoMessage(playerHandler.current().getName()));
    }

    public void selectOrderHand(ArrayList<Integer> list) throws WrongLengthOrderException, WrongContentOrderException {
        hand.selectOrder(list);
        //broadcasterRMI.notifyOrder(gameID, "", list);
        eventKeeper.notifyAll(new OrderMessage(list,playerHandler.current().getName()));
    }

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

    public void endTurn() {
        if (playerHandler.next()){
            notifyEvent(new NewTurnMessage(playerHandler.current().getName()));
        }
        else
            gameState = GameState.END;
    }

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

    public void leave(String id){
        getPlayerByID(id).leave();
        notifyEvent(new LeaveMessage(getPlayerNameByID(id)));
        playerMiss(id);
    }

    public void disconnect(String id){
        numDisconnection++;
        getPlayerByID(id).disconnect();
        notifyEvent(new DisconnectMessage(getPlayerNameByID(id)));
        //System.out.println(getPlayerNameByID(id)+" si è disconnesso");
        playerMiss(id);
    }

    public int getNumDisconnection(){
        return numDisconnection;
    }

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


    //GETTER

    public ArrayList<Player> getPlayerList(){
        return playerHandler.getPlayerList();
    }

    public GameMode getGameMode(){
        return gameMode;
    }

    public GameState getGameState(){
        return gameState;
    }

    public String getCurrentPlayerID(){
        return playerHandler.current().getID();
    }

    public Player getPlayerByID(String id){
        return playerHandler.getPlayerByID(id);
    }

    public String getPlayerNameByID(String id){
        Player player = playerHandler.getPlayerByID(id);
        if(player!=null)
            return player.getName();
        return null;
    }

    public LocalGame getLocal(){
        return new LocalGame(gameMode,gameID,numPlayers,playerHandler.getNumPlayer(),gameState);
    }

    public int getGameID() {
        return gameID;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public int getCurrentPlayers() {
        return playerHandler.getNumPlayer();
    }

    public EventKeeper getEventKeeper(){
        return eventKeeper;
    }

    public String getPlayerIDByName(String name){
        return playerHandler.getPlayerIDByName(name);
    }

    public boolean currentPlayerIsConnected(){
        return playerHandler.current().isConnected();
    }

    public int getNumPlayersConnected(){
        return playerHandler.numPlayersConnected();
    }

    public boolean isConnected(String id){
        return playerHandler.getPlayerByID(id).isConnected();
    }

    public boolean checkTimer(Long time){
        long n = time + 60000 - System.currentTimeMillis();
        boolean b = (!isFinished() && getNumDisconnection() == numDisconnection && getNumPlayersConnected() < 2 && n>0 && !currentPlayerIsConnected());
        if(b)
            notifyEvent(new TimerMessage((int)n));
        return b;
        //System.currentTimeMillis() - time < 60000
    }


}



