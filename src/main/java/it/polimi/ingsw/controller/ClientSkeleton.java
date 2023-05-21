package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.connection.TCPMessage;
import it.polimi.ingsw.model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientSkeleton extends Remote {
    public void getID(String ID) throws RemoteException;
    void newChatMessage(String name, String message) throws RemoteException;
    void playerJoin(String name) throws RemoteException;
    void playerLeave(String name) throws RemoteException;
    void startGame(String name) throws RemoteException;
    void newTurn(String name) throws RemoteException;
    void lastRound(String name) throws RemoteException;
    void endGame(String name) throws RemoteException;
    void notify(TCPMessage TCPMessage) throws RemoteException;
    void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException;
    void notifyUndo(String name) throws RemoteException;
    void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException;
    void notifyPut(String name, int column) throws RemoteException;
    void updateBoard(LocalBoard board) throws RemoteException;
    void updateBookshelf(LocalBookshelf bookshelf) throws RemoteException;
    void updateHand(LocalHand hand) throws RemoteException;
    void updateGame(LocalGame localGame) throws RemoteException;
    void updateCommonGoalCard(LocalCommonCard commonGoalCard) throws RemoteException;
    void updatePersonalGoalCard(DataCard dataCard) throws RemoteException;

    void ping(int ping) throws RemoteException;
}
