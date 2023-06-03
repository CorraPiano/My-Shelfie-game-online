package it.polimi.ingsw.client;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.TUI.OutputHandler;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.controller.ClientSkeleton;
import it.polimi.ingsw.model.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Client extends UnicastRemoteObject implements ClientSkeleton {
    protected final ModelView modelView;
    //private View view;
    protected String ID;
    protected String name;
    protected ClientPhase phase;
    protected ClientState state;
    protected final Chat chat;
    private final boolean DEBUG = false;

    public Client() throws RemoteException {
        modelView = new ModelView();
        phase = ClientPhase.JOIN;
        state = ClientState.READY;
        chat = new Chat(this);
    }

    // funzioni non visibili al server (invocate da sender/receiver)
    // corrispondono a return di RMI
    public synchronized void receiveNothing(){
        state = ClientState.READY;
        this.notifyAll();
    }
    public synchronized void receiveID(String ID){
        this.ID=ID;
        phase = ClientPhase.GAME;
        state = ClientState.READY;
        this.notifyAll();
    }
    public synchronized void setName(String name){ this.name=name; }
    public synchronized void setId(String id){ this.ID=id; }
    public synchronized void receiveException(String e){
        state = ClientState.READY;
        this.notifyAll();
    }
    public synchronized void receiveGamesList(ArrayList<LocalGame> gameslist) throws RemoteException {
        state = ClientState.READY;
        this.notifyAll();
    }
    public synchronized void putInWait(){ state = ClientState.WAIT; }

    public synchronized void OpenChat(){
        phase=ClientPhase.CHAT;
        new Thread(chat).start();
    }
    public synchronized void closeChat(){
        chat.stopThread();
        phase=ClientPhase.GAME;
    }

    public synchronized String getID() {
        return ID;
    }
    public synchronized String getName() { return name; }
    public synchronized ClientPhase getPhase() { return phase; }
    public synchronized ClientState getState() { return state; }
    public synchronized ModelView getModelView() {
        return modelView;
    }
    public synchronized Chat getChat() { return chat; }

    // !!! vedere dove serve sincronized


    // funzioni visibili al server
    public void ping(int ping) throws RemoteException{}

    public void createGame(int gameID) throws RemoteException {}
    public void playerJoin(String name) throws RemoteException {}
    public void playerLeave(String name) throws RemoteException {}
    public void playerDisconnect(String name) throws RemoteException {}
    public synchronized void playerReconnect(String name) throws RemoteException {
        //phase = ClientPhase.GAME;
        state = ClientState.READY;
        this.notifyAll();
    }
    public void startGame(String name) throws RemoteException {}
    public void newTurn(String name) throws RemoteException {}
    public void lastRound(String name) throws RemoteException {}
    public synchronized void endGame(String name) throws RemoteException {
        if(phase.equals(ClientPhase.CHAT))
            chat.stopThread();
        phase=ClientPhase.JOIN;
    }

    public void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException{}
    public void notifyUndo(String name) throws RemoteException{}
    public void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException{}
    public void notifyPut(String name, int column) throws RemoteException{}

    public void updateBoard(LocalBoard board) throws RemoteException {
        if(DEBUG)
            System.out.println("--> board received");
        modelView.setLocalBoard(board);
    }

    public void updateBookshelf(LocalBookshelf bookshelf) throws RemoteException {
        if(DEBUG)
            System.out.println("--> bookshelf received");
        modelView.setLocalBookshelf(bookshelf);
    }

    public void updateHand(LocalHand hand) throws RemoteException {
        if(DEBUG)
            System.out.println("--> hand received");
        modelView.setLocalHand(hand);
    }

    public void updateGame(LocalGame localGame) throws RemoteException {
        if(DEBUG)
            System.out.println("--> game received");
        modelView.setLocalPlayer(localGame.playerList);
        modelView.setGameMode(localGame.gameMode);
    }

    public void updateCommonGoalCard(LocalCommonCard localCommonCard) throws RemoteException{
        if(DEBUG)
            System.out.println("--> commonCard received");
        modelView.setLocalCommonCard(localCommonCard);
    }

    public void updatePersonalGoalCard(LocalPersonalCard personalCard) throws RemoteException{
        if(DEBUG)
            System.out.println("--> personalCard received");
        // deve riceve una personal, non datacard !!!
        modelView.setPersonalCard(new DataCard(personalCard.num));
        modelView.setLocalPersonalCard(personalCard);
    }

    public void updateChat(ChatMessage chatMessage) throws RemoteException {
        if(DEBUG)
            System.out.println("--> chat message received");
        chat.addChatMessage(chatMessage);
    }

    public OutputHandler getOutputHandler() {
        return null;
    }

    public void lostConnection(){
        phase = ClientPhase.CLOSE;
    }
}
