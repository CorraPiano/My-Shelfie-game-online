package it.polimi.ingsw.model;
import it.polimi.ingsw.exception.GameFullException;

import java.util.ArrayList;
import java.util.Iterator;

//da sistemare
public class PlayerHandler{
    private int size;
    private int curr;
    private boolean lastRound;

    private ArrayList<Player> playerList;


    public PlayerHandler(ArrayList<Player> playerList){
        this.playerList = playerList;
        size=playerList.size();
        curr = 0; //(int)(Math.random()*(size+1));
    }

    //se la partita non è inviata ritona NULL
    public Player current(){
        if(curr>=0 && curr<size)
            return playerList.get(curr);
        else
            return null;
    }

    //ritorna false se la partitia è finita
    public boolean next() {
        if(curr<size-1)
            curr=curr+1;
        else{
            if(lastRound)
                return false;
            else
                curr=0;
        }
        return true;
    }

    // non ritorna eccezioni, ma ignora la notifica se è stata gia inviata
    public void notifyLastRound(){
        if(!lastRound) {
            lastRound = true;
        }
    }

    public boolean isEnd(){
        return lastRound && curr==size-1;
    }

}
