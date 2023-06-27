package it.polimi.ingsw.model;

import it.polimi.ingsw.client.localModel.LocalPlayer;
import it.polimi.ingsw.client.localModel.LocalPlayerList;

import java.util.ArrayList;

/**
 * The PlayerHandler class manages the players in the game and their turns.
 */
public class PlayerHandler extends Listenable {
    private int size;
    private int curr;
    private int turn;
    private boolean lastRound;
    private ArrayList<Player> playerList;
    private ArrayList<String> nameList;
    private Gameplay gameplay;


    /**
     * Constructs a PlayerHandler object.
     * @param gamePlay the Gameplay object associated with the player handler
     */
    public PlayerHandler(Gameplay gamePlay){
        playerList = new ArrayList<>();
        this.gameplay=gamePlay;
        nameList = new ArrayList<>();
        //this.playerList = playerList;
        //size=playerList.size();
        //curr = 0; //(int)(Math.random()*(size+1));
    }

    /**
     * Chooses the first player randomly.
     */
    public void choseFirstPlayer(){
        size=playerList.size();
        //sort lista con primo player al primo posto
        curr = 0; // (int)(Math.random()*(size+1));
        turn = 0;
    }

    /**
     * Adds a player to the player list.
     * @param player the player to add
     */
    public void addPlayer(Player player){
        playerList.add(player);
        notifyUpdate();
        nameList.add(player.getName());
    }

    /**
     * Checks if a name is available for a new player.
     * @param name the name to check
     * @return true if the name is available, false otherwise
     */
    public boolean checkName(String name){
        for(String s: nameList)
            if(s.equals(name))
                return false;
        return true;
    }

    /**
     * Gets the number of players in the game.
     * @return the number of players
     */
    public int getNumPlayer(){
        return playerList.size();
    }

    /**
     * Gets the list of players.
     * @return the list of players
     */
    public ArrayList<Player> getPlayerList(){
        return playerList;
    }

    /**
     * Gets a player based on their ID.
     * @param id the ID of the player
     * @return the player object with the corresponding ID, or null if not found
     */
    public Player getPlayerByID(String id){
        for(Player p: playerList){
            if(p.getID().equals(id))
                return p;
        }
        return null;
    }

    /**
     * Gets the ID of a player based on their name.
     * @param name the name of the player
     * @return the ID of the player with the corresponding name, or null if not found
     */
    public String getPlayerIDByName(String name){
        for(Player p:playerList){
            if(p.getName().equals(name))
                return p.getID();
        }
        return null;
    }

    /**
     * Removes a player from the player list.
     * @param id the ID of the player to remove
     * @param gameState the state of gameplay
     */
    public void playerLeave(String id, GameState gameState) {
        for(int i=0;i<playerList.size();i++) {
            Player p = playerList.get(i);
            if(p.getID().equals(id)) {
                p.leave();
                if(gameState.equals(GameState.WAIT))
                    playerList.remove(i);
                try {
                    eventKeeper.removePersonalList(id);
                } catch(Exception ignored){}
                break;
            }
        }
        //if(gameState.equals(GameState.GAME) && numPlayersConnected()<2)
        notifyUpdate();
    }

    public void playerDisconnect(String id, GameState gameState) {
        Player p=null;
        for(int i=0;i<playerList.size();i++) {
            p = playerList.get(i);
            if(p.getID().equals(id)) {
                p.disconnect();
                try {
                    eventKeeper.updateStatus(p.getID(),false);
                } catch(Exception ignored){}
                if(gameState.equals(GameState.WAIT)) {
                    playerList.remove(i);
                    try {
                        eventKeeper.removePersonalList(id);
                    } catch(Exception ignored){}
                }
                break;
            }
        }
        //if(gameState.equals(GameState.GAME) && numPlayersConnected()<2)
        notifyUpdate();
    }

    public void reconnect(String id){
        getPlayerByID(id).reconnect();
        try {
            eventKeeper.updateStatus(id,true);
        } catch(Exception ignored){}
        notifyUpdate();
    }

    /**
     * Calculates the final standings of players based on their points.
     * @return the name of the player who won the game
     */
    public String makeFinalClassification(){
        for(Player p: playerList) {
            p.updatePoints(true);
        }
        playerList=sort(playerList);
        notifyUpdate();
        return playerList.get(0).getName();
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

    //se la partita non è inviata ritona NULL

    /**
     * Gets the current player in the turn.
     * @return the current player object, or null if the game is not in progress
     */
    public Player current(){
        if(curr>=0 && curr<size)
            return playerList.get(curr);
        else
            return null;
    }

    /**
     * Gets the number of players available (not inactive) in the game.
     * @return the number of players available
     */
    public int numPlayersAvaiable(){
        int numPlayersAvaiable = 0;
        for(Player p:playerList){
            if(!p.isInactive())
                numPlayersAvaiable++;
        }
        return numPlayersAvaiable;
    }

    /**
     * Gets the number of players connected to the game.
     * @return the number of players connected
     */
    public int numPlayersConnected(){
        int numPlayersConnected = 0;
        for(Player p:playerList){
            if(p.isConnected())
                numPlayersConnected ++;
        }
        return numPlayersConnected ;
    }

    public boolean hasNext(){
        return curr <= size - 1;
    }

    /**
     * Advances the turn to the next player.
     * @return true if the game can continue, false if the game has ended
     */
    public void next() {
        turn++;
        if (curr < size - 1) {
            curr = curr + 1;
        }
        else {
            if(lastRound) {
                curr++;
                return;
            }
            curr = 0;
        }

        if(current().isInactive())
            next();
        if(!current().isConnected()){
            if(numPlayersConnected()>=2)
                next();
        }
        notifyUpdate();
    }

    /**
     * Notifies that it is the last round of the game.
     */
    public void notifyLastRound(){
        lastRound = true;
    }

    /**
     * Gets the local player list representation of all players.
     * @return the LocalPlayerList object containing the local player representations
     */
    public LocalPlayerList getLocal(){
        ArrayList<LocalPlayer> list = new ArrayList<>();
        for(Player p: playerList)
            list.add(p.getLocal());
        return new LocalPlayerList(list);
    }

    public void forceNotify(){
        this.notifyUpdate();
    }

}
