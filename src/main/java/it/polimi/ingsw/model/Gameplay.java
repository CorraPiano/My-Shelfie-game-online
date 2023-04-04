package it.polimi.ingsw.model;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Gameplay {

    GameMode gameMode;
    int numPlayers;
    ArrayList<Player> playerList = new ArrayList<>();
    Board board;
    CommonGoalCard commonGoalCard1;
    CommonGoalCard commonGoalCard2;
    BagPersonal bagPersonal;
    BagCommon bagCommon;
    PlayerIterator playerIterator;
    ArrayList<Item> hand;
    Player winner;
    Player currentPlayer;

    public Gameplay(GameMode gameMode, int numPlayers){
        this.gameMode=gameMode;
        this.numPlayers=numPlayers;
        board=new Board(numPlayers);
        bagPersonal=new BagPersonal();
        if (gameMode.equals(GameMode.EXPERT)) {
            bagCommon=new BagCommon();
        }
        hand = new ArrayList<Item>();
    }

    public Player addPlayer(String name){
        Player player = new Player(name);
        playerList.add(player);
        return player;
    }

    public PlayerIterator startGame(){
        if (gameMode.equals(GameMode.EXPERT)){
            // #1 implementare getCard() in BagCommon
            // commonGoalCard1 = bagCommon.getCard(numPlayer);
            // commonGoalCard1.setTokenList(createTokenList(numPlayers));
            // commonGoalCard2 = bagCommon.getCard();
        }
        for(Player p: playerList) {
            // #2 implementare getCard() in BagPersonal
            // #3 implementare setPersonalGoalCard in Player
            // commonGoalCard1.setTokenList(createTokenList(numPlayers));
            // Player.setPersonalGoalCard(bagPersonal.getCard());
        }
        playerIterator = new PlayerIterator(playerList);
        return playerIterator;
    }

    // #4 sistemare le eccezioni
    public void pickItemList(ArrayList<Coordinates> list) throws Exception{
        hand.addAll(board.getItemList(list));
    }

    public void releaseHand() {
        // #5 implementare putItems in board
        // board.putItemList(hand);
        hand.clear();
    }

    public int putItemList(int column) throws Exception{
        Bookshelf library = currentPlayer.getLibrary();
        library.putItemList(hand,column);
        if(library.isFull())
            playerIterator.notifyLastRound();
        return calcPoints();
    }

    public int calcPoints(){
        // #6 in player mettere boolean haveToken() al posto di getToken
        /* if(!currentPlayer.haveToken1()){
            if(commonGoalCard1.checkFullfill(currentPlayer.getLibrary()))
                currentPlayer.setToken1(commonGoalCard1.popToken());
        }
        if(!currentPlayer.haveToken2()){
            if(commonGoalCard2.checkFullfill(currentPlayer.getLibrary()))
                currentPlayer.setToken2(commonGoalCard2.popToken());
        }*/
        currentPlayer.updatePoints(false);
        return currentPlayer.getPoints();
    }

    public Player endGame(){

        // #7 in player mettere boolean getFirstPlayerSeat()
        for(Player p: playerList) {
            p.updatePoints(true);
        }
        playerList=playerList.stream().sorted((x,y)->{
            if(x.getPoints()<y.getPoints())
                return 1;
            /*if(x.getPoints()==y.getPoints() && x.getFirstPlayerSeat())
                return 1;
            if(x.getPoints()==y.getPoints() && y.getFirstPlayerSeat())
                return -1;*/
            else
                return -1;
        }).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return playerList.get(0);
    }

    private ArrayList<Token> createTokenList(int numPlayers){
        //da implementare
        return null;
    }

    public void selectOrderHand(ArrayList<Integer> list){

    }

}



