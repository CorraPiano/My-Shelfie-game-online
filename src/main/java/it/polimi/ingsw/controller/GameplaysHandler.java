package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidGameIdException;
import it.polimi.ingsw.exception.InvalidIdException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Gameplay;

import java.util.ArrayList;
import java.util.HashMap;

public class GameplaysHandler {

    //mappa ogni gameplay al gameID
    private ArrayList<Gameplay> gameplayList = null;
    //mappa ogni giocatore al gameID
    private final HashMap<String, Integer> mapID;
    //mappa ogni gameID ai giocatori
    private final HashMap<Integer, ArrayList<String>> mapGame;

    public GameplaysHandler(){
        mapID = new HashMap<>();
        mapGame = new HashMap<>();
        gameplayList = new ArrayList<Gameplay>();
    }

    public int nextID() {
        return gameplayList.size();
    }

    public void addGameplay(Gameplay gameplay, int gameID){
        gameplayList.add(gameID,gameplay);
        mapGame.put(gameID,new ArrayList<>());
    }

    public void bind(String id, int gameID){
        mapID.put(id,gameID);
        mapGame.get(gameID).add(id);
    }

    public Gameplay getGameplay(int gameID) throws InvalidGameIdException {
        System.out.println(gameplayList.size()+" "+gameID);
        if(gameID>=0 && gameID<gameplayList.size())
            return gameplayList.get(gameID);
        throw new InvalidGameIdException();
    }

    public Gameplay getHisGameplay(String id) throws InvalidIdException {
        if(mapID.containsKey(id))
            return gameplayList.get(mapID.get(id));
        throw new InvalidIdException();
    }

    public ArrayList<String> getGameplayList(){
        ArrayList<String> list = new ArrayList<>();
        for(Gameplay g: gameplayList){
            if(g.getGameState().equals(GameState.WAIT))
                list.add("ID: " + g.getGameID() + ", N° GIOCATORI: " + g.getNumPlayers() + ", GIOCATORI ATTUALI: "+g.getCurrentPlayers() +", MODE: " + g.getGameMode());
        }
        list.add("");
        return list;
    }

    public ArrayList<String> getPlayersFromGameplay(int gameID){
        return mapGame.get(gameID);
    }
}
