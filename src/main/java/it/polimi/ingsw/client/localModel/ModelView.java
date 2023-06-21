package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.model.DataCard;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.PersonalGoalCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModelView {
    /*
    =================================================================================
    IDEA: avere tutte le cose che servono a CLI e GUI per stampare la roba
    Verranno passati tramite file JSON i dati (per esempio una matrice di Item per
    la board) e qua ci saranno tutti i vari get e set.
    =================================================================================
     */

    //ATTRIBUTES

    //private LocalGame localGame;

    private GameMode gameMode;
    private int numPlayers;
    private int gameID;
    private LocalBoard localBoard;
    private ArrayList<LocalPlayer> localPlayerList;
    private Map<String, LocalBookshelf> localBookshelfMap;
    private LocalHand localHand;
    private ArrayList<LocalCommonCard> localCommonCardList;
    private LocalPersonalCard localPersonalCard;

    //per end game
    private Map<String, LocalPersonalCard> personalCardMap;


    //CONSTRUCTOR
    public ModelView(int gameID ,GameMode gameMode, int numPlayers) {
        this.gameID=gameID;
        this.gameMode=gameMode;
        this.numPlayers=numPlayers;
        this.localBookshelfMap = new HashMap<>();
        this.localPlayerList = new ArrayList<>();
        this.localHand = new LocalHand();
        this.localCommonCardList = new ArrayList<>();
    }

    public void init(){
        for (LocalPlayer p: localPlayerList) {
            System.out.println(p);
            this.localBookshelfMap.put(p.name, new LocalBookshelf(p.name));
        }
    }

    //SETTERS
    //public void setLocalGame(LocalGame localGame){
    //this.localGame = localGame;
    //}
    public void setLocalBoard(LocalBoard localBoard) {
        this.localBoard = localBoard;
    }
    public void setLocalPlayerList(LocalPlayerList localPlayerList){
        this.localPlayerList = localPlayerList.playerList;
        for(LocalPlayer lp: this.localPlayerList){
            if(lp.numPersonalCard!=-1)
                personalCardMap.put(lp.name,new LocalPersonalCard(lp.numPersonalCard,new DataCard(lp.numPersonalCard)));
        }
    }
    public void setLocalBookshelf(LocalBookshelf localBookshelf) {
        this.localBookshelfMap.put(localBookshelf.name, localBookshelf);
    }
    public void setLocalHand(LocalHand localHand) {
        this.localHand = localHand;
        //this.localHand = new LocalHand(localHand.hand, localHand.size);
    }

    public void setLocalCommonCard(LocalCommonCard localCommonCard) {
        //this.localCommonCardList.add(this.localPlayerList.size(), localCommonCard);
        if(this.localCommonCardList.size()<2)
            this.localCommonCardList.add(localCommonCard);
        else if(this.localCommonCardList.get(0).type==localCommonCard.type)
            this.localCommonCardList.set(0, localCommonCard);
        else
            this.localCommonCardList.set(1, localCommonCard);
    }

    public void setLocalPersonalCard (LocalPersonalCard localPersonalCard){
        this.localPersonalCard = new LocalPersonalCard(localPersonalCard.num,new DataCard(localPersonalCard.num));
    }

    //GETTERS
    public LocalBoard getLocalBoard() {
        return localBoard;
    }
    public Map<String, LocalBookshelf> getLocalBookshelfs() {
        return localBookshelfMap;
    }
    public LocalHand getLocalHand() {
        return localHand;
    }
    public ArrayList<LocalPlayer> getLocalPlayerList() {
        return localPlayerList;
    }
    public ArrayList<LocalCommonCard> getCommonCards() {
        return localCommonCardList;
    }
    public DataCard getDataCard() {
        return localPersonalCard.dataCard;
    }
    public GameMode getGameMode() {
        return gameMode;
    }
    public int getNumPlayers() {
        return numPlayers;
    }
    public int getGameID() {
        return gameID;
    }

    public int getCurrentPlayer(){
        return localPlayerList.size();
    }
    public LocalPersonalCard getLocalPersonalCard(){
        return localPersonalCard;
    }
}
