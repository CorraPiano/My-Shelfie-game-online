package it.polimi.ingsw.model;
import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.client.localModel.LocalPlayer;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.connection.message.*;

import java.util.ArrayList;

public class Gameplay extends Listenable {

    private final GameMode gameMode;
    private GameState gameState;
    private int gameID;
    private final int numPlayers;
    private final Board board;
    private CommonGoalCard commonGoalCard1;
    private CommonGoalCard commonGoalCard2;
    private final BagPersonal bagPersonal;
    private final BagCommon bagCommon;
    private PlayerHandler playerHandler;
    private final Hand hand;
    private ArrayList<Player> playerList;

    private EventKeeper eventKeeper;

    //da eliminare
    //private final BroadcasterRMI broadcasterRMI;
    //private final OldListener listener;

    public Gameplay(GameMode gameMode, int numPlayers, int gameID) throws NumPlayersException, GameModeException {
        if(!gameMode.equals(GameMode.EASY) && !gameMode.equals(GameMode.EXPERT))
            throw new GameModeException();
        if(numPlayers<=1 || numPlayers>4)
            throw new NumPlayersException();
        eventKeeper = new EventKeeper();
        this.gameID = gameID;
        this.gameMode = gameMode;
        this.numPlayers = numPlayers;
        this.playerList = new ArrayList<Player>();
        //this.broadcasterRMI = broadcasterRMI;
        //this.listener= new OldListener(gameID,broadcasterRMI);
        hand = new Hand();
        hand.setEventKeeper(eventKeeper);
        board = new Board(numPlayers,hand);
        board.setEventKeeper(eventKeeper);

        bagPersonal = new BagPersonal();
        if (gameMode.equals(GameMode.EXPERT))
            bagCommon = new BagCommon();
        else
            bagCommon = null;
        gameState = GameState.WAIT;
        eventKeeper.notifyAll(new CreateMessage(gameID));
    }

    public Player addPlayer(String name) throws GameFullException, NameAlreadyExistentException {
        if(!checkName(name))
            throw new NameAlreadyExistentException();
        Player player = new Player(name);
        eventKeeper.addPersonalList(player.getID());
        player.setEventKeeper(eventKeeper);
        player.getLibrary().setEventKeeper(eventKeeper);
        playerList.add(player);
        //attenzione: al primo giocatore lancia una eccezione !!!
        //broadcasterRMI.playerJoin(gameID,name);
        eventKeeper.notifyAll(new JoinMessage(name));
        return player;
    }

    public boolean isReady(){
        return playerList.size() == numPlayers;
    }

    private boolean checkName(String name){
        for(Player p: playerList)
            if(p.getName().equals(name))
                return false;
        return true;
    }

    public void startGame(){
        gameState = GameState.GAME;
        if (gameMode.equals(GameMode.EXPERT)){
            // capire se mettere le token presso la common o presso gameplay
            commonGoalCard1 = bagCommon.drawCommonGoalCard();
            //commonGoalCard1.setListener(listener);
            commonGoalCard1.setEventKeeper(eventKeeper);
            commonGoalCard1.setTokenList(createTokenList());
            commonGoalCard2 = bagCommon.drawCommonGoalCard();
            commonGoalCard2.setEventKeeper(eventKeeper);
            commonGoalCard2.setTokenList(createTokenList());
        }
        for(Player p: playerList) {
            PersonalGoalCard personalCard = bagPersonal.drawPersonalGoalCard();
            personalCard.setEventKeeper(eventKeeper);
            p.setPersonalGoalCard(personalCard);
            //p.bindListner(listener);
        }
        board.drawBoardItems();
        playerHandler = new PlayerHandler(playerList);
        playerHandler.current().setFirstPlayerSeat(true);
        //broadcasterRMI.updateGame(gameID,this);
        //broadcasterRMI.startGame(gameID,playerHandler.current().getName());
        eventKeeper.notifyAll(getLocal());
        for(Player p:playerList)
            p.getLibrary().sendBookshelf();
        eventKeeper.notifyAll(new StartGameMessage(playerHandler.current().getName()));
        //far iniziare a caso il primo giocatore
    }

    public LocalGame getLocal(){
        ArrayList<LocalPlayer> list = new ArrayList<>();
        for(Player p: playerList)
            list.add(p.getLocal());
        return new LocalGame(gameMode,gameID,numPlayers,playerList.size(),gameState,list);
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
            //broadcasterRMI.notifyPick(gameID,playerHandler.current().getName() ,coordinates, item);
    }

    public void releaseHand() {
        board.releaseHand();
        //broadcasterRMI.notifyUndo(gameID,playerHandler.current().getName());
        eventKeeper.notifyAll(new UndoMessage(playerHandler.current().getName()));
    }

    public void putItemList(int column) throws EmptyHandException, InvalidColumnPutException, NotEnoughSpacePutException {
        if(hand.getSize()==0)
            throw new EmptyHandException();
        Player currentPlayer = playerHandler.current();
        Bookshelf library = currentPlayer.getLibrary();
        library.putItemList(hand.getHand(),column);
        board.endTurn();

        //broadcasterRMI.notifyPut(gameID,playerHandler.current().getName(),column);
        eventKeeper.notifyAll(new PutMessage(column,playerHandler.current().getName()));

        if(gameMode.equals(GameMode.EXPERT))
            checkFullfillCommonGoalCard(currentPlayer );
        if(library.isFull()) {
            currentPlayer.setEndGameToken();
            playerHandler.notifyLastRound();
            //broadcasterRMI.lastRound(gameID, playerHandler.current().getName());
            eventKeeper.notifyAll(new LastRoundMessage(playerHandler.current().getName()));
        }
        if(playerHandler.next()) {
            currentPlayer.updatePoints(false);
            //broadcasterRMI.newTurn(gameID, playerHandler.current().getName());
            eventKeeper.notifyAll(new NewTurnMessage(playerHandler.current().getName()));
        }
        else
            endGame();
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
            //eventKeeper.notifyToID(p.getID(),chatMessage);
            //eventKeeper.notifyToID(getPlayerIDByName(chatMessage.sender),chatMessage);

    }

    //si deve poter stoppare il gioco prima della fine
    public void endGame(){
        gameState = GameState.END;
        for(Player p: playerList) {
            p.updatePoints(true);
        }
        playerList=sort(playerList);
        eventKeeper.notifyAll(getLocal());
        eventKeeper.notifyAll(new EndGameMessage(playerList.get(0).getName()));
    }

    private ArrayList<Player> sort(ArrayList<Player>playerList){
        return playerList.stream().sorted((x,y)->{
            if(x.getPoints()<y.getPoints())
                return 1;
            if(x.getPoints()==y.getPoints() && x.getFirstPlayerSeat() && !y.getFirstPlayerSeat())
                return -1;
            if(x.getPoints()==y.getPoints() && y.getFirstPlayerSeat() && !x.getFirstPlayerSeat())
                return 1;
            if(x.getPoints()>y.getPoints())
                return -1;
            return 0;
        }).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public void selectOrderHand(ArrayList<Integer> list) throws WrongLengthOrderException, WrongContentOrderException {
        hand.selectOrder(list);
        //broadcasterRMI.notifyOrder(gameID, "", list);
        eventKeeper.notifyAll(new OrderMessage(list,playerHandler.current().getName()));
    }

    public ArrayList<Player> getPlayerList(){
        return playerList;
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

    public String getPlayerNameByID(String id){
        for(Player p: playerList){
            if(p.getID().equals(id))
                return p.getName();
        }
        return null;
    }

    public int getGameID() {
        return gameID;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public int getCurrentPlayers() {
        return playerList.size();
    }

    public EventKeeper getEventKeeper(){
        return eventKeeper;
    }

    public String getPlayerIDByName(String name){
        for(Player p:playerList){
            if(p.getName().equals(name))
                return p.getID();
        }
        return null;
    }
}



