package it.polimi.ingsw.model;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Gameplay {

    private int gameId=0;
    private GameMode gameMode;
    private int numPlayers;
    private ArrayList<Player> playerList = new ArrayList<>();
    private Board board;
    private CommonGoalCard commonGoalCard1;
    private CommonGoalCard commonGoalCard2;
    private BagPersonal bagPersonal;
    private BagCommon bagCommon;
    private PlayerIterator playerIterator;
    private Player currentPlayer;
    private Hand hand;

    // inserire gameid

    public Gameplay(GameMode gameMode, int numPlayers){
        this.gameMode=gameMode;
        this.numPlayers=numPlayers;
        hand = new Hand();
        board=new Board(numPlayers);
        bagPersonal=new BagPersonal();
        if (gameMode.equals(GameMode.EXPERT)) {
            bagCommon=new BagCommon();
        }
    }

    public Player addPlayer(String name) throws Exception {
        if(playerList.size()==numPlayers)
            throw new Exception();
        Player player = new Player(name);
        playerList.add(player);
        return player;
    }

    public PlayerIterator startGame(){
        if (gameMode.equals(GameMode.EXPERT)){
            // capire se mettere le token presso la common o presso gameplay
            commonGoalCard1 = bagCommon.drawCommonGoalCard();
            commonGoalCard1.setTokenList(createTokenList(numPlayers));
            commonGoalCard2 = bagCommon.drawCommonGoalCard();
            commonGoalCard1.setTokenList(createTokenList(numPlayers));
        }
        for(Player p: playerList) {
            p.setPersonalGoalCard(bagPersonal.drawPersonalGoalCard());
        }
        board.drawBoardItems();
        playerIterator = new PlayerIterator(playerList);
        return playerIterator;
    }

    // #4 sistemare le eccezioni
    public void pickItemList(ArrayList<Coordinates> list) throws Exception{
        currentPlayer = playerIterator.current();
        // controllo che le coordinata sia adiacente alla precedente
        board.getItemList(list);
    }

    public void releaseHand() {
        // rinominare putItems in putItemList
        // board.releaseHand();
        //hand.clear();
    }

    public void putItemList(int column) throws Exception {
        Bookshelf library = currentPlayer.getLibrary();
        library.putItemList(hand.getHand(),column);
        hand.clear();
        if(library.isFull()) {
            currentPlayer.setEndGameToken();
            playerIterator.notifyLastRound();
        }
    }

    public int calcPoints(){
        if(!currentPlayer.haveToken1()){
            if(commonGoalCard1.checkFullFil(currentPlayer.getLibrary()))
                currentPlayer.setToken1(commonGoalCard1.popToken());
        }
        if(!currentPlayer.haveToken2()){
            if(commonGoalCard2.checkFullFil(currentPlayer.getLibrary()))
                currentPlayer.setToken2(commonGoalCard2.popToken());
        }
        currentPlayer.updatePoints(false);
        return currentPlayer.getPoints();
    }

    public void endGame(){

        // verificare sorted
        for(Player p: playerList) {
            p.updatePoints(true);
        }
        playerList=playerList.stream().sorted((x,y)->{
            if(x.getPoints()<y.getPoints())
                return 1;
            if(x.getPoints()==y.getPoints() && x.getFirstPlayerSeat())
                return 1;
            if(x.getPoints()==y.getPoints() && y.getFirstPlayerSeat())
                return -1;
            if(x.getPoints()>y.getPoints())
                return -1;
            return 0;
        }).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public void selectOrderHand(ArrayList<Integer> list){
        hand.selectOrderHand(list);
    }

    private ArrayList<Token> createTokenList(int numPlayers){
       ArrayList<Token> list = new ArrayList<>();
       list.add(new Token(8));
       list.add(new Token(6));
       if(numPlayers>2)
           list.add(new Token(4));
       if(numPlayers>3)
           list.add(new Token(2));
       return list;
    }

    public ArrayList<Player> TestGetPlayerList(){
        return playerList;
    }
}



