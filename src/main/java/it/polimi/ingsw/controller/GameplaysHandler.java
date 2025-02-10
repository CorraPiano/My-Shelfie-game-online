package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.exception.InvalidGameIdException;
import it.polimi.ingsw.exception.InvalidIdException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Gameplay;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The GameplaysHandler class manages the collection of gameplay instances.
 */
public class GameplaysHandler {

    private ArrayList<Gameplay> gameplayList = null;
    private final HashMap<String, Integer> mapID;

    /**
     * Constructs a new GameplaysHandler object.
     * Initializes the gameplay list and ID map.
     */
    public GameplaysHandler(){
        mapID = new HashMap<>();
        gameplayList = new ArrayList<>();
    }

    /**
     * Retrieves the next available ID for a gameplay.
     *
     * @return The next gameplay ID.
     */
    public int nextID() {
        return gameplayList.size();
    }

    /**
     * Adds a gameplay to the handler with the specified game ID.
     *
     * @param gameplay The gameplay to add.
     * @param gameID   The game ID associated with the gameplay.
     */
    public void addGameplay(Gameplay gameplay, int gameID){
        gameplayList.add(gameID,gameplay);
        //mapGame.put(gameID,new ArrayList<>());
    }

    /**
     * Binds a player ID to a game ID.
     *
     * @param id     The player ID to bind.
     * @param gameID The game ID to bind the player to.
     */
    public void bind(String id, int gameID){
        //clean();
        mapID.put(id,gameID);
        //mapListener.put(id,listener);
        //mapGame.get(gameID).add(id);
    }

    //public void rebind(String id, Listener  listener){
    //    mapListener.put(id,listener);
    //}

    /**
     * Removes a player ID from the handler.
     *
     * @param id The player ID to remove.
     */
    public void remove(String id){
        //clean();
        mapID.remove(id);
        //mapListener.get(id).disconnect();
        //mapListener.remove(id);
    }

    /**
     * Retrieves the gameplay with the specified game ID.
     *
     * @param gameID The game ID of the desired gameplay.
     * @return The gameplay instance.
     * @throws InvalidGameIdException If the game ID is invalid.
     */
    public Gameplay getGameplay(int gameID) throws InvalidGameIdException {
        System.out.println(gameplayList.size()+" "+gameID);
        if(gameID>=0 && gameID<gameplayList.size() && gameplayList.get(gameID)!=null)
            return gameplayList.get(gameID);
        throw new InvalidGameIdException();
    }

    /**
     * Retrieves the gameplay associated with the specified player ID.
     *
     * @param id The player ID.
     * @return The gameplay instance.
     * @throws InvalidIdException If the player ID is invalid.
     */
    public Gameplay getHisGameplay(String id) throws InvalidIdException {
        if(mapID.containsKey(id) && gameplayList.get(mapID.get(id))!=null)
            return gameplayList.get(mapID.get(id));
        throw new InvalidIdException();
    }

    /**
     * Retrieves a list of available local games.
     *
     * @return The list of local games.
     */
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

    /**
     * Removes a game from the handler with the specified game ID.
     *
     * @param gameID The game ID to remove.
     */
    public void removeGame(int gameID){
        gameplayList.set(gameID,null);
        ArrayList<String> list = new ArrayList<>();
        for(String s: mapID.keySet())
            if(mapID.get(s)==gameID)
                list.add(s);
        for(String s: list)
                mapID.remove(s);
        System.out.println("gameplay "+gameID+" rimosso dalla mappa");
    }


}
