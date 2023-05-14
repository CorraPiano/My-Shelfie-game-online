package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.localModel.localBoard;
import it.polimi.ingsw.client.localModel.localBookshelf;
import it.polimi.ingsw.client.localModel.localHand;
import it.polimi.ingsw.client.localModel.localPlayer;
import it.polimi.ingsw.connection.Message;
import it.polimi.ingsw.model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientSkeleton extends Remote {
    void newChatMessage(String name, String message) throws RemoteException;
    void playerJoin(String name) throws RemoteException;
    void playerLeave(String name) throws RemoteException;
    void startGame(String name) throws RemoteException;
    void newTurn(String name) throws RemoteException;
    void lastRound(String name) throws RemoteException;
    void endGame(String name) throws RemoteException;
    void leaveGame(String name) throws RemoteException;
    void notify(Message message) throws RemoteException;
    void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException;
    void notifyUndo(String name) throws RemoteException;
    void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException;
    void notifyPut(String name, int column) throws RemoteException;

    void updateBoard(localBoard board) throws RemoteException;
    void updateBookshelf(localBookshelf bookshelf) throws RemoteException;
    void updateHand(localHand hand) throws RemoteException;
    void updatePlayerList(ArrayList<localPlayer> playerList) throws RemoteException;
    void updateCommonGoalCard(CommonGoalCard commonGoalCard) throws RemoteException;
    void updatePersonalGoalCard(DataCard dataCard) throws RemoteException;

    void ping(int ping) throws RemoteException;
}
