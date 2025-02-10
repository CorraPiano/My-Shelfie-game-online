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
    private final ArrayList<String> nameList;

    /**
     * Constructs a PlayerHandler object.
     */
    public PlayerHandler(){
        playerList = new ArrayList<>();
        // for the univocity of the ID
        nameList = new ArrayList<>();
    }

    // initialization

    /**
     * Chooses the first player randomly.
     */
    public void choseFirstPlayer(){
        turn = 0;
        size=playerList.size();
        curr = (int)(Math.random()*(size));
        //security check (redundant)
        if(curr>=size)
            curr = size-1;
        if(curr<0)
            curr = 0;

        firstSort();
        current().setFirstPlayerSeat(true);

        notifyUpdate();
    }

    /* Sort playerList according to curr
     */
    private void firstSort(){
        ArrayList<Player> list = new ArrayList<>();
        int i = curr;
        int j = 0;
        while(list.size()<size){
            list.add(j,playerList.get(i));
            i++;
            if(i==size)
                i=0;
            j++;
        }
        playerList = list;
        curr = 0;
    }

    // player handling

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
     * Adds a player to the game. The player is marked as ACTIVE.
     * @param player the player to add
     */
    public void addPlayer(Player player){
        playerList.add(player);
        notifyUpdate();
        nameList.add(player.getName());
    }

    /**
     * Removes a player from the game. If the game isn't started yet, the player is remove from the player list,
     * otherwise is marked as INACTIVE.
     * @param id the ID of the player that left the game.
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
        notifyUpdate();
    }

    /**
     * Handle the disconnection of a player. The player is marked as DISCONNECTED.
     * @param id the ID of the player that disconnected.
     * @param gameState the state of gameplay
     */
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
        notifyUpdate();
    }

    /**
     * Handle the reconnection of a player. The player is marked as ACTIVE.
     * @param id the ID of the player that reconnected.
     */
    public void reconnect(String id){
        getPlayerByID(id).reconnect();
        try {
            eventKeeper.updateStatus(id,true);
        } catch(Exception ignored){}
        notifyUpdate();
    }

    // final classification

    /**
     * Calculates the final standings of players based on their points.
     * If only one player is left, he is a the top of the standings,
     * regardless of the points.
     *
     * @return the name of the player who won the game
     */
    public String makeFinalClassification(){
        for(Player p: playerList) {
            p.updatePoints(true);
        }
        finalSort();
        if(getNumPlayersConnected()<2)
            putLastConnectedOnTop();
        notifyUpdate();
        return playerList.get(0).getName();
    }

    /** Sorts the players according to their points.
     */
    private void finalSort(){
        playerList = playerList.stream().sorted((x,y)->{
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

    /** Put the first connected player on the top of the list.
     */
    private void putLastConnectedOnTop(){
        for(int i=0; i<playerList.size();i++){
            Player p = playerList.get(i);
            if(p.isConnected()){
                playerList.remove(i);
                playerList.add(0,p);
                break;
            }
        }
    }

    // turn handling

    /**
     * Gets the current player in the turn.
     * @return the current player object, or null if the current player isn't defined.
     */
    public Player current(){
        if(curr>=0 && curr<size)
            return playerList.get(curr);
        else
            return null;
    }

    /**
     * Check if a current player is defined. If a current player is not defined the game isn't started yet
     * or is already finished.
     * @return 'true' if a current player is defined, 'false' otherwise.
     */
    public boolean hasNext(){
        return curr <= size - 1;
    }

    /**
     * If possible, advances the turn to the next player.
     * If there aren't enough players avaiable, set current turn in a invalid state.
     */
    public void next() {

        turn++;

        // if there aren't enough player
        //if(getNumPlayersAvaiable()<2){
        //    curr = size;
        //    return;
        //}

        // advance
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

        // skips inactive or disconnected players
        if(current().isInactive())
            next();
        if(!current().isConnected()){
            if(getNumPlayersConnected()>=2)
                next();
        }

        notifyUpdate();
    }

    /**
     * Set that it is the last round of the game.
     */
    public void notifyLastRound(){
        lastRound = true;
    }

    // getters

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

    /**
     * Gets the number of players available (not inactive) in the game.
     * @return the number of players available
     */
    public int getNumPlayersAvaiable(){
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
    public int getNumPlayersConnected(){
        int numPlayersConnected = 0;
        for(Player p:playerList){
            if(p.isConnected())
                numPlayersConnected ++;
        }
        return numPlayersConnected ;
    }

    /**
     * Gets the list of players.
     * @return the list of players
     */
    public ArrayList<Player> getPlayerList(){
        return playerList;
    }

    /**
     * Gets the number of players in the game.
     * @return the number of players
     */
    public int getNumPlayer(){
        return playerList.size();
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


}
