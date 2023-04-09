package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Skeleton extends Remote {
    String addFirstPlayer(String name, GameMode gameMode, int numPlayer) throws Exception;
    String addPlayer(String name) throws Exception;
    void pickItem(int n1, int n2, String id)  throws Exception;

    void undoPick(String id) throws Exception;

    void putItemList(int column, String id) throws Exception;

    void addChatMessage(String chatMessage,String id) throws Exception;

    void selectInsertOrder(ArrayList<Integer> order, String id) throws Exception;

}