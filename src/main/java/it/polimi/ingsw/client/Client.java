package it.polimi.ingsw.client;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.TUI.OutputHandler;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.controller.ClientSkeleton;
import it.polimi.ingsw.model.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

public class Client extends UnicastRemoteObject implements ClientSkeleton {
    protected ModelView modelView;
    //private View view;
    protected  String ID;
    protected String name;
    protected  ClientPhase phase;
    protected  ClientState state;
    protected final Chat chat;
    private final boolean DEBUG = false;

    public Client() throws RemoteException {
        phase = ClientPhase.HOME;
        state = ClientState.READY;
        chat = new Chat(this);
    }

    public synchronized ModelView getModelView() { return modelView; }
    public synchronized Chat getChat() { return chat; }

    // getter
    public synchronized String getName() { return name; }
    public synchronized String getID() { return ID; }
    public synchronized ClientPhase getPhase() { return phase; }
    public synchronized ClientState getState() { return state; }

    // setter
    public synchronized void setName(String name){
        this.name=name;
    }
    public synchronized void setID(String id){
        this.ID=id;
        LocalSave.storeID(ID);
    }
    public synchronized void setPhase(ClientPhase phase){
        this.phase = phase;
    }
    public synchronized void setState(ClientState state){
        this.state = state;
        this.notifyAll();
    }

    //FROM TCP (to create similarity with RMI)
    public void receiveGamesList(ArrayList<LocalGame> gameslist) throws RemoteException {
        setState(ClientState.READY);
    }
    public void receiveID(String ID){
        setID(ID);
        setState(ClientState.READY);
        setPhase(ClientPhase.GAME);
    }
    public void receiveName(String name){
        setName(name);
        setState(ClientState.READY);
        setPhase(ClientPhase.GAME);
    }
    public void receiveException(String e){
        if(getPhase().equals(ClientPhase.MATCH_RECONNECTION)) {
            System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "reconnection to game failed!");
            setPhase(ClientPhase.HOME);
        }
        System.out.println(e);
        setState(ClientState.READY);
    }
    public void receiveNothing(){
        setState(ClientState.READY);
    }

    //gestione della chat
    //per ora solo in clientTUI

    // RMI: aggiornamenti
    public void ping(int ping) throws RemoteException{}
    public void createGame(int gameID, GameMode gameMode, int numPlayers) throws RemoteException {
        setPhase(ClientPhase.GAME);
    }
    public void playerJoin(String name) throws RemoteException {}
    public void playerLeave(String name) throws RemoteException {}
    public void playerDisconnect(String name) throws RemoteException {}
    public void playerReconnect(String name) throws RemoteException {
        setPhase(ClientPhase.GAME);
    }
    public void startGame() throws RemoteException {}
    public void newTurn(String name) throws RemoteException {}
    public void lastRound(String name) throws RemoteException {}
    public void endGame(String name) throws RemoteException {
        setPhase(ClientPhase.HOME);
    }

    // RMI: notifiche
    public void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException{}
    public void notifyUndo(String name) throws RemoteException{}
    public void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException{}
    public void notifyPut(String name, int column) throws RemoteException{}

    // RMI: aggiornamenti local model
    public void updateBoard(LocalBoard board) throws RemoteException {
        modelView.setLocalBoard(board);
    }
    public void updateBookshelf(LocalBookshelf bookshelf) throws RemoteException {
        modelView.setLocalBookshelf(bookshelf);
    }
    public void updateHand(LocalHand hand) throws RemoteException {
        modelView.setLocalHand(hand);
    }
    /*public void updateGame(LocalGame localGame) throws RemoteException {
        modelView.setLocalGame(localGame);
        //modelView.setLocalPlayer(localGame.playerList);
        //modelView.setGameMode(localGame.gameMode);
    }*/
    public void updatePlayerList(LocalPlayerList localPlayerList) throws RemoteException {
        modelView.setLocalPlayerList(localPlayerList);
    }
    public void updateCommonGoalCard(LocalCommonCard localCommonCard) throws RemoteException{
        modelView.setLocalCommonCard(localCommonCard);
    }
    public void updatePersonalGoalCard(LocalPersonalCard personalCard) throws RemoteException{
        // deve riceve una personal, non datacard !!!
        //modelView.setPersonalCard(new DataCard(personalCard.num));
        modelView.setLocalPersonalCard(personalCard);
    }
    public void updateChat(ChatMessage chatMessage) throws RemoteException {
        //System.out.println("--> chat message received");
        chat.addChatMessage(chatMessage);
    }

    // reconnection
    public void lostConnection(){
        if(getPhase().equals(ClientPhase.HOME))
            setPhase(ClientPhase.HOME_RECONNECTION);
        else
            setPhase(ClientPhase.MATCH_RECONNECTION);
        System.out.println();
        System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "connection lost!");
        System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "attempting to reconnect...");
    }
    public void homeReconnection(){
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "reconnection to server done!");
        setPhase(ClientPhase.HOME);
    }
    public void gameReconnection(){
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "reconnection to server done!");
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "attempting to reconnect to the game...");
    }
    public void leaveGame() {
        setPhase(ClientPhase.HOME);
    }
}
