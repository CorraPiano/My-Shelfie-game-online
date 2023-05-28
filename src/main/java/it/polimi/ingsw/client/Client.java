package it.polimi.ingsw.client;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.TUI.OutputHandler;
import it.polimi.ingsw.controller.ClientSkeleton;
import it.polimi.ingsw.model.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Client extends UnicastRemoteObject implements ClientSkeleton {

    private final ModelView modelView;
    private final OutputHandler outputHandler;
    //private View view;
    private String ID;
    private boolean state;

    private final boolean GRAPHIC = true;

    public Client() throws RemoteException {
        state = false;
        outputHandler = new OutputHandler();
        modelView = new ModelView();
        //this.view = view;
    }

    public void ping(int ping) throws RemoteException{}

    public void reveiceOK(){}
    public void receiveID(String ID){}
    public void receiveException(String e){
        System.out.println(e);
    }
    public void receiveGamesList(ArrayList<LocalGame> gameslist) throws RemoteException {}

    public void updateChat(String name, String message) throws RemoteException {}

    public void createGame(int gameID) throws RemoteException {}
    public void playerJoin(String name) throws RemoteException {}
    public void playerLeave(String name) throws RemoteException {}
    public void startGame(String name) throws RemoteException {}
    public void newTurn(String name) throws RemoteException {}
    public void lastRound(String name) throws RemoteException {}
    public void endGame(String name) throws RemoteException {}

    public void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException{}
    public void notifyUndo(String name) throws RemoteException{}
    public void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException{}
    public void notifyPut(String name, int column) throws RemoteException{}

    public void updateBoard(LocalBoard board) throws RemoteException {}
    public void updateBookshelf(LocalBookshelf bookshelf) throws RemoteException {}
    public void updateHand(LocalHand hand) throws RemoteException {}
    public void updateGame(LocalGame localGame) throws RemoteException {}
    public void updateCommonGoalCard(LocalCommonCard localCommonCard) throws RemoteException{}
    public void updatePersonalGoalCard(LocalPersonalCard personalCard) throws RemoteException{}


    public String getID() {
        return ID;
    }
    public boolean getState() {
        return state;
    }
    public OutputHandler getViewhandler() {
        return outputHandler;
    }
    public ModelView getModelView() {
        return modelView;
    }
}
