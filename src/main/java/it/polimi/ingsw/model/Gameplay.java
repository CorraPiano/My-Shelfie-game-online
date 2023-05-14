package it.polimi.ingsw.model;
import it.polimi.ingsw.controller.BroadcasterRMI;
import it.polimi.ingsw.controller.Listener;
import it.polimi.ingsw.exception.*;

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
    private final BroadcasterRMI broadcasterRMI;
    private final Listener listener;

    public Gameplay(GameMode gameMode, int numPlayers, int gameID,BroadcasterRMI broadcasterRMI) throws NumPlayersException, GameModeException {
        if(!gameMode.equals(GameMode.EASY) && !gameMode.equals(GameMode.EXPERT))
            throw new GameModeException();
        if(numPlayers<=1 || numPlayers>4)
            throw new NumPlayersException();
        this.gameID = gameID;
        this.gameMode = gameMode;
        this.numPlayers = numPlayers;
        this.playerList = new ArrayList<Player>();
        this.broadcasterRMI = broadcasterRMI;
        this.listener= new Listener(gameID,broadcasterRMI);
        hand = new Hand(listener);
        board = new Board(numPlayers,hand,listener);
        bagPersonal = new BagPersonal();
        if (gameMode.equals(GameMode.EXPERT))
            bagCommon = new BagCommon();
        else
            bagCommon = null;
        gameState = GameState.WAIT;
    }

    public Player addPlayer(String name) throws GameFullException, NameAlreadyExistentException {
        if(!checkName(name))
            throw new NameAlreadyExistentException();
        Player player = new Player(name);
        playerList.add(player);
        //attenzione: al primo giocatore lancia una eccezione !!!
        broadcasterRMI.playerJoin(gameID,name);
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
            commonGoalCard1.setListener(listener);
            commonGoalCard1.setTokenList(createTokenList());
            commonGoalCard2 = bagCommon.drawCommonGoalCard();
            commonGoalCard2.setListener(listener);
            commonGoalCard2.setTokenList(createTokenList());
        }
        for(Player p: playerList) {
            p.setPersonalGoalCard(bagPersonal.drawPersonalGoalCard());
            p.bindListner(listener);
        }
       board.drawBoardItems();
       playerHandler = new PlayerHandler(playerList);
       playerHandler.current().setFirstPlayerSeat(true);

       broadcasterRMI.startGame(gameID,playerHandler.current().getName());
       broadcasterRMI.updatePlayerList(gameID,playerList);
       //broadcasterRMI.updateBoard(gameID,board);
       //for(Player p: playerList)
        //    broadcasterRMI.updateBookshelf(gameID,p.getName(),p.getLibrary());
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
            broadcasterRMI.notifyPick(gameID,playerHandler.current().getName() ,coordinates, item);
            //broadcasterRMI.updateBoard(gameID,board);
            //broadcasterRMI.updateHand(gameID,hand);
    }

    public void releaseHand() {
        board.releaseHand();
        broadcasterRMI.notifyUndo(gameID,playerHandler.current().getName());
        //broadcasterRMI.updateBoard(gameID,board);
        //broadcasterRMI.updateHand(gameID,hand);
    }

    public void putItemList(int column) throws EmptyHandException, InvalidColumnPutException, NotEnoughSpacePutException {
        if(hand.getSize()==0)
            throw new EmptyHandException();
        Player currentPlayer = playerHandler.current();
        Bookshelf library = currentPlayer.getLibrary();
        library.putItemList(hand.getHand(),column);
        board.endTurn();

        broadcasterRMI.notifyPut(gameID,playerHandler.current().getName(),column);
        //broadcasterRMI.updateBookshelf(gameID,playerHandler.current().getName(),playerHandler.current().getLibrary());

        if(gameMode.equals(GameMode.EXPERT))
            checkFullfillCommonGoalCard(currentPlayer );
        if(library.isFull()) {
            currentPlayer.setEndGameToken();
            playerHandler.notifyLastRound();
            broadcasterRMI.lastRound(gameID, playerHandler.current().getName());
        }
        if(playerHandler.next()) {
            currentPlayer.updatePoints(false);
            broadcasterRMI.newTurn(gameID, playerHandler.current().getName());
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

    //si deve poter stoppare il gioco prima della fine
    public void endGame(){
        gameState = GameState.END;
        for(Player p: playerList) {
            p.updatePoints(true);
        }
        playerList=sort(playerList);
        broadcasterRMI.endGame(gameID,playerList.get(0).getName());
        broadcasterRMI.updatePlayerList(gameID,playerList);
        //broadcasterRMI.updateBoard(gameID,board);
       // for(Player p: playerList)
         //   broadcasterRMI.updateBookshelf(gameID,p.getName(),p.getLibrary());
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
}



