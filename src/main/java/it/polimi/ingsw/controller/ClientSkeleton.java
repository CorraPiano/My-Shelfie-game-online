package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.connection.TCPMessage;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientSkeleton extends Remote {
    void updateChat(ChatMessage message) throws RemoteException;
    void createGame(int gameID,GameMode gameMode, int numPlayers) throws RemoteException;
    void playerJoin(String name) throws RemoteException;
    void playerLeave(String name) throws RemoteException;
    void playerDisconnect(String name) throws RemoteException;
    void playerReconnect(String name) throws RemoteException;
    void startGame() throws RemoteException;
    void newTurn(String name) throws RemoteException;
    void lastRound(String name) throws RemoteException;
    void endGame(String name) throws RemoteException;
    void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException;
    void notifyUndo(String name) throws RemoteException;
    void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException;
    void notifyPut(String name, int column) throws RemoteException;
    void updateBoard(LocalBoard board) throws RemoteException;
    void updateBookshelf(LocalBookshelf bookshelf) throws RemoteException;
    void updateHand(LocalHand hand) throws RemoteException;
    //void updateGame(LocalGame localGame) throws RemoteException;
    void updatePlayerList(LocalPlayerList localPlayerList) throws RemoteException;
    void updateCommonGoalCard(LocalCommonCard commonGoalCard) throws RemoteException;
    void updatePersonalGoalCard(LocalPersonalCard personalGoalCard) throws RemoteException;
    void ping(int ping) throws RemoteException;

}
