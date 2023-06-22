package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.localModel.LocalGame;
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
    //private final HashMap<String, Listener > mapListener;
    //mappa ogni gameID ai giocatori
   // private final HashMap<Integer, ArrayList<String>> mapGame;

    public GameplaysHandler(){
        mapID = new HashMap<>();
        gameplayList = new ArrayList<Gameplay>();
        //mapListener = new HashMap<>();
    }

    public int nextID() {
        return gameplayList.size();
    }

    public void addGameplay(Gameplay gameplay, int gameID){
        gameplayList.add(gameID,gameplay);
        //mapGame.put(gameID,new ArrayList<>());
    }

    public void bind(String id, int gameID){
        //clean();
        mapID.put(id,gameID);
        //mapListener.put(id,listener);
        //mapGame.get(gameID).add(id);
    }

    //public void rebind(String id, Listener  listener){
    //    mapListener.put(id,listener);
    //}

    public void remove(String id){
        //clean();
        mapID.remove(id);
        //mapListener.get(id).disconnect();
        //mapListener.remove(id);
    }

    public Gameplay getGameplay(int gameID) throws InvalidGameIdException {
        System.out.println(gameplayList.size()+" "+gameID);
        if(gameID>=0 && gameID<gameplayList.size() && gameplayList.get(gameID)!=null)
            return gameplayList.get(gameID);
        throw new InvalidGameIdException();
    }

    public Gameplay getHisGameplay(String id) throws InvalidIdException {
        if(mapID.containsKey(id) && gameplayList.get(mapID.get(id))!=null)
            return gameplayList.get(mapID.get(id));
        throw new InvalidIdException();
    }

    public ArrayList<LocalGame> getGameplayList(){
        ArrayList<LocalGame> list = new ArrayList<>();
        for(Gameplay g: gameplayList){
            if(g!=null && g.getGameState().equals(GameState.WAIT)){
                LocalGame lg = new LocalGame(g.getGameMode(), g.getGameID(), g.getNumPlayers(),g.getCurrentPlayers(),g.getGameState());
                list.add(lg);
             }
        }
        return list;
    }

    public void removeGame(int gameID){
        gameplayList.set(gameID,null);
        for(String s: mapID.keySet())
            if(mapID.get(s)==gameID)
                mapID.remove(s);
        System.out.println("gameplay "+gameID+" rimosso dalla mappa");
    }

    /*private void clean(){
        for(String id: mapID.keySet()){
            if(gameplayList.get(mapID.get(id)).getGameState().equals(GameState.END)) {
                mapID.remove(id);
                mapListener.get(id).disconnect();
                mapListener.remove(id);
            }
        }
        for(int i=0; i<gameplayList.size();i++){
            if(gameplayList.get(i).getGameState().equals(GameState.END))
                gameplayList.set(i,null);
        }
    }*/
}
