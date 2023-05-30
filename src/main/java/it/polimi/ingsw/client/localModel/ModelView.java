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
    private GameMode gameMode;
    private LocalBoard localBoard;
    private Map<String, LocalBookshelf> localBookshelfMap;
    private LocalHand localHand;
    private ArrayList<LocalPlayer> localPlayerList;
    private ArrayList<LocalCommonCard> localCommonCardList;
    private DataCard dataCard;
    private LocalPersonalCard localPersonalCard;



    //CONSTRUCTOR
    public ModelView() {
        this.localBoard = new LocalBoard();
        this.localBookshelfMap = new HashMap<>();
        this.localPlayerList = new ArrayList<>();
        this.localCommonCardList = new ArrayList<>();
    }

    //SETTERS
    public void setGameMode(GameMode gameMode){
        this.gameMode = gameMode;
    }
    public void setLocalBoard(LocalBoard localBoard) {
        this.localBoard = localBoard;
    }
    public void setLocalBookshelf(LocalBookshelf localBookshelf) {
        this.localBookshelfMap.put(localBookshelf.name, localBookshelf);
    }
    public void setLocalHand(LocalHand localHand) {
        this.localHand = new LocalHand(localHand.hand, localHand.size);
    }
    public void setLocalPlayer(ArrayList<LocalPlayer> localPlayerList) {
        this.localPlayerList = localPlayerList;
    }
    public void setLocalCommonCard(LocalCommonCard localCommonCard) {
        this.localCommonCardList.add(this.localPlayerList.size(), localCommonCard);
    }
    public void setPersonalCard(DataCard dataCard) {
        this.dataCard = dataCard;
    }
    public void setLocalPersonalCard (LocalPersonalCard localPersonalCard){
        this.localPersonalCard = localPersonalCard;
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
        return dataCard;
    }
    public GameMode getGameMode() {
        return gameMode;
    }
    public LocalPersonalCard getLocalPersonalCard(){
        return localPersonalCard;
    }
}
