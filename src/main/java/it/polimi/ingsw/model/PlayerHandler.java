package it.polimi.ingsw.model;

import it.polimi.ingsw.client.localModel.LocalPlayer;
import it.polimi.ingsw.client.localModel.LocalPlayerList;
import it.polimi.ingsw.connection.message.Sendable;

import java.util.ArrayList;

//da sistemare
public class PlayerHandler extends Listenable {
    private int size;
    private int curr;
    private int turn;
    private boolean lastRound;

    private ArrayList<Player> playerList;

    private Gameplay gameplay;

    public PlayerHandler(Gameplay gamePlay){
        playerList = new ArrayList<>();
        this.gameplay=gamePlay;
        //this.playerList = playerList;
        //size=playerList.size();
        //curr = 0; //(int)(Math.random()*(size+1));
    }

    public void choseFirstPlayer(){
        size=playerList.size();
        //sort lista con primo player al primo posto
        curr = 0; // (int)(Math.random()*(size+1));
        turn = 0;
    }

    public void addPlayer(Player player){
        playerList.add(player);
        notifyUpdate();
    }

    public boolean checkName(String name){
        for(Player p: playerList)
            if(p.getName().equals(name))
                return false;
        return true;
    }

    public int getNumPlayer(){
        return playerList.size();
    }

    public ArrayList<Player> getPlayerList(){
        return playerList;
    }

    public Player getPlayerByID(String id){
        for(Player p: playerList){
            if(p.getID().equals(id))
                return p;
        }
        return null;
    }

    public String getPlayerIDByName(String name){
        for(Player p:playerList){
            if(p.getName().equals(name))
                return p.getID();
        }
        return null;
    }

    public void removePlayer(String id){
        for(int i=0;i<playerList.size();i++) {
            if(playerList.get(i).getID().equals(id)) {
                playerList.remove(i);
                break;
            }
        }
    }

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
    public Player current(){
        if(curr>=0 && curr<size)
            return playerList.get(curr);
        else
            return null;
    }

    private int numPlayersAvaiable(){
        int numPlayersAvaiable = 0;
        for(Player p:playerList){
            if(!p.hasLeft())
                numPlayersAvaiable++;
        }
        return numPlayersAvaiable;
    }

    private int numPlayersConnected(){
        int numPlayersConnected = 0;
        for(Player p:playerList){
            if(p.connectionState())
                numPlayersConnected ++;
        }
        return numPlayersConnected ;
    }

    //ritorna false se la partitia è finita
    public void futureCheck(){
        int check = turn;
        Long time = System.currentTimeMillis();
        while(check==turn && numPlayersConnected()<2 && System.currentTimeMillis()-time<60000) {
            try {
                synchronized (this) {
                    this.wait(5000);
                }
            } catch(Exception e){}
        }
        if(current().connectionState() || check!=turn)
            return;
        if(numPlayersConnected()>=2) {
            gameplay.endTurn();
        }
        else
            gameplay.endGame();
    }

    public boolean next() {
        turn++;
        if (curr < size - 1) {
            curr = curr + 1;
        }
        else {
            if(lastRound)
                return false;
            if(numPlayersAvaiable()<2)
                return false;
            curr = 0;
        }

        if(current().hasLeft())
            return next();
        if(!current().connectionState()){
            if(numPlayersConnected()>=2)
                return next();
            else
                new Thread(this::futureCheck).start();
        }
        notifyUpdate();
        return true;
    }

    public void notifyLastRound(){
        lastRound = true;
    }

    public LocalPlayerList getLocal(){
        ArrayList<LocalPlayer> list = new ArrayList<>();
        for(Player p: playerList)
            list.add(p.getLocal());
        return new LocalPlayerList(list);
    }

}
