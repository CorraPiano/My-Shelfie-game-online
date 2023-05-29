package it.polimi.ingsw.client;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.GUI.Command;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.DataCard;
import it.polimi.ingsw.model.Item;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientGUI extends Client{
    private GUI gui;

    public ClientGUI() throws RemoteException {
        super();
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }
    public GUI getGui() {
        return gui;
    }

    @Override
    public synchronized void receiveException(String e){
        System.out.println(e);
        state = ClientState.READY;
        this.notifyAll();
    }
    @Override
    public synchronized void receiveGamesList(ArrayList<LocalGame> gameslist) throws RemoteException {
        gui.refreshGameList(gameslist);
        state = ClientState.READY;
        this.notifyAll();
    }

    public void ping(int ping) throws RemoteException{}

    public void createGame(int gameID) throws RemoteException {
        // da implementare
    }
    public void playerJoin(String name) throws RemoteException {
        // da implementare
    }
    public void playerLeave(String name) throws RemoteException {
        // da implementare
    }
    public void startGame(String name) throws RemoteException {
        gui.setCommonGoalCard(modelView.getCommonCards());
        gui.setPersonalGoalCard(modelView.getDataCard());
        gui.switchStage(Command.START_GAME);
    }
    public void newTurn(String name) throws RemoteException {
        // da implementare
    }
    public void lastRound(String name) throws RemoteException {
        // da implementare
    }
    public void endGame(String name) throws RemoteException {
        // da implementare
    }
    public void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException{
        gui.updateBoard(modelView.getLocalBoard(), modelView.getLocalHand());
    }
    public void notifyUndo(String name) throws RemoteException{
        // da implementare
    }
    public void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException{
        // da implementare
    }
    public void notifyPut(String name, int column) throws RemoteException{
        gui.updateBookShelf(modelView.getLocalBookshelfs().get(name), name);
    }

    // tutte le funzioni "update" sono in client
    // per modificarle, farne l'ovverride (mantenendo la linea di codice che aggiorna il localmodel)
}
