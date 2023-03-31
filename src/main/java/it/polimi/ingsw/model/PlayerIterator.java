package it.polimi.ingsw.model;
import java.util.ArrayList;

public class PlayerIterator {
    private int size;
    private int curr;
    private boolean lastRound;
    private int remains;
    private final ArrayList<Player> playerList;

    public PlayerIterator(ArrayList<Player> playerList){
        this.playerList=playerList;
        size = playerList.size();
        curr = (int)(Math.random()*(size+1));
    }

    public Player current(){
        return playerList.get(curr);
    }

    public void next() throws Exception{
        if(lastRound) {
            if (remains <= 0)
                throw new Exception();
            remains = remains - 1;
        }

        if(curr<size-1)
            curr=curr+1;
        else
            curr=0;
    }

    // non ritorna eccezioni, ma ignora la notifica se è stata gia inviata
    public void notifyLastRound(){
        if(!lastRound) {
            lastRound = true;
            remains = size - 1;
        }
    }

    public boolean isEnd(){
        return lastRound && remains<=0;
    }
}
