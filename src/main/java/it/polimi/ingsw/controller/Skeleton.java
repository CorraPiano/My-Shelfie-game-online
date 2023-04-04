package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Skeleton extends Remote {
    Boolean addPlayer(String name, GameMode gameMode, int numPlayer) throws Exception;

    void pickItemList(ArrayList<Coordinates> coordsList, Player player)  throws Exception;

    void undoPick(Player player) throws Exception;

    void putItemList(int column, Player player) throws Exception;

    void addChatMessage(String chatMessage) throws Exception;



}