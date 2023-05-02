package it.polimi.ingsw.model;
import it.polimi.ingsw.exception.*;

import java.util.ArrayList;

public class Gameplay {

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

    public Gameplay(GameMode gameMode, int numPlayers, int gameID) throws NumPlayersException, GameModeException {
        if(!gameMode.equals(GameMode.EASY) && !gameMode.equals(GameMode.EXPERT))
            throw new GameModeException();
        if(numPlayers<=1 || numPlayers>4)
            throw new NumPlayersException();
        this.gameID = gameID;
        this.gameMode = gameMode;
        this.numPlayers = numPlayers;
        this.playerList = new ArrayList<Player>();
        hand = new Hand();
        board = new Board(numPlayers,hand);
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
        if(playerList.size() == numPlayers)
            startGame();
        return player;
    }

    private boolean checkName(String name){
        for(Player p: playerList)
            if(p.getName().equals(name))
                return false;
        return true;
    }

    private void startGame(){
        gameState = GameState.GAME;
        if (gameMode.equals(GameMode.EXPERT)){
            // capire se mettere le token presso la common o presso gameplay
            commonGoalCard1 = bagCommon.drawCommonGoalCard();
            commonGoalCard1.setTokenList(createTokenList());
            commonGoalCard2 = bagCommon.drawCommonGoalCard();
            commonGoalCard1.setTokenList(createTokenList());
        }
        for(Player p: playerList) {
            p.setPersonalGoalCard(bagPersonal.drawPersonalGoalCard());
        }
       board.drawBoardItems();
        playerHandler = new PlayerHandler(playerList);
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
            board.getItem(coordinates);
    }

    public void releaseHand() {
        board.releaseHand();
    }

    public void putItemList(int column) throws EmptyHandException, InvalidColumnPutException, NotEnoughSpacePutException {
        if(hand.getSize()==0)
            throw new EmptyHandException();
        Player currentPlayer = playerHandler.current();
        Bookshelf library = currentPlayer.getLibrary();
        library.putItemList(hand.getHand(),column);
        board.endTurn();

        if(gameMode.equals(GameMode.EXPERT))
            checkFullfillCommonGoalCard(currentPlayer );
        if(library.isFull()) {
            currentPlayer.setEndGameToken();
            playerHandler.notifyLastRound();
        }
        if(playerHandler.next())
            currentPlayer.updatePoints(false);
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



