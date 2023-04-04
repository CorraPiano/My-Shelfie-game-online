package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;

import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;

public class Controller extends UnicastRemoteObject implements Skeleton {

    private int numPlayers;
    private int maxPlayers;
    private PlayerIterator playerIterator;
    private Gameplay gameplay = null;
    public Controller() throws RemoteException{
        //UnicastRemoteObject.exportObject(this, 0);
        numPlayers=0;
        maxPlayers=0;
    }
    public Boolean addPlayer(String name, GameMode gameMode, int maxPlayers) throws Exception{
        if(playerIterator==null) {
            this.maxPlayers=maxPlayers;
            this.numPlayers++;
            System.out.println("giocatore connesso: "+name);
            if (gameplay == null)
                gameplay = new Gameplay(gameMode, numPlayers);
            Player player = gameplay.addPlayer(name);

            if (this.numPlayers == this.maxPlayers)
                playerIterator=gameplay.startGame();

            return true;
        }
        else
            return false;
    }

    public void pickItemList(ArrayList<Coordinates> coordsList, Player player)  throws Exception{
        if(playerIterator.current().equals(player))
            gameplay.pickItemList(coordsList);
    }

    public void undoPick(Player player) throws Exception{
        if(playerIterator.current().equals(player))
            gameplay.releaseHand();
    }

    public void selectInsertOrder(ArrayList<Integer> order, Player player) throws Exception{
        if(playerIterator.current().equals(player))
            gameplay.selectOrderHand(order);
    }

    public void putItemList(int column, Player player) throws Exception{
        if(playerIterator.current().equals(player))
            gameplay.putItemList(column);
    }

    public void addChatMessage(String chatMessage) throws Exception{
        System.out.println(chatMessage);
    }
}
