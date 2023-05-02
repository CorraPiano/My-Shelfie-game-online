package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidGameIdException;
import it.polimi.ingsw.exception.InvalidIdException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Gameplay;

import java.util.ArrayList;
import java.util.HashMap;

public class GameplaysHandler {

    private ArrayList<Gameplay> gameplayList = null;
    private HashMap<String, Integer> mapID;
    private HashMap<Integer, ArrayList<String>> mapGame;
    private int gameNum;

    public GameplaysHandler(){
        gameNum=0;
        mapID = new HashMap<>();
        mapGame = new HashMap<>();
        gameplayList = new ArrayList<Gameplay>();
    }

    public int nextID() {
        return gameNum;
    }

    public void addGameplay(Gameplay gameplay){
        gameplayList.add(gameNum,gameplay);
        mapGame.put(gameNum,new ArrayList<>());
        gameNum++;
    }

    public void bind(String id, int gameID){
        mapID.put(id,gameID);
        mapGame.get(gameID).add(id);
    }

    public Gameplay getGameplay(int gameID) throws InvalidGameIdException {
        if(gameID<gameNum)
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
}
