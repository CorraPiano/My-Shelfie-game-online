package it.polimi.ingsw.model;
import java.util.ArrayList;

//da sistemare
public class PlayerIterator {
    private int size;
    private int curr;
    private boolean lastRound;
    private int safe_check;

    private final ArrayList<Player> playerList;

    public PlayerIterator(ArrayList<Player> playerList){
        this.playerList=playerList;
        size = playerList.size();
        curr = (int)(Math.random()*(size+1));
        safe_check=0;
    }

    public Player current(){
        safe_check=1;
        return playerList.get(curr);
    }

    // controllare che non vengano fatti due next() di fila senza almeno un current() in mezzo ?
    public void next() throws Exception{
        if(safe_check==0)
            throw new Exception();
        if(curr<size-1)
            curr=curr+1;
        else{
            if(lastRound)
                throw new Exception();
            else
                curr=0;
        }
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
