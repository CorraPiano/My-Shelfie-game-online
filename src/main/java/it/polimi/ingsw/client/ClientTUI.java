package it.polimi.ingsw.client;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.TUI.OutputHandler;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Item;
import java.rmi.RemoteException;
import java.util.ArrayList;
import static it.polimi.ingsw.util.Constants.*;

public class ClientTUI extends Client {
    private final OutputHandler outputHandler;

    public ClientTUI() throws RemoteException {
        super();
        outputHandler = new OutputHandler();
    }

    public OutputHandler getOutputHandler() { return outputHandler; }
    //public synchronized Chat getChat() { return chat; }

    // getter
    //public synchronized String getName() { return name; }
    //public synchronized String getID() { return ID; }
    //public synchronized ClientPhase getPhase() { return phase; }
    //public synchronized ClientState getState() { return state; }

    // setter
    /*public synchronized void setName(String name){
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
    }*/

    //FROM TCP (to create similarity with RMI)
    public void receiveGamesList(ArrayList<LocalGame> gameslist){
        // fix grafico
        gameslist.forEach(System.out::println);
        setState(ClientState.READY);
    }
    public void receiveID(String ID){
        setID(ID);
        if(getPhase().equals(ClientPhase.HOME))
            setPhase(ClientPhase.LOBBY);
        setState(ClientState.READY);
    }
    public void receiveName(String name){
        setName(name);
        setPhase(ClientPhase.GAME);
        setState(ClientState.READY);
    }
    public void receiveException(String e){
        if(getPhase().equals(ClientPhase.MATCH_RECONNECTION)) {
            System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "reconnection to game failed!");
            setPhase(ClientPhase.HOME);
            outputHandler.presentation();
        }
        else
            System.out.println(e);
        setState(ClientState.READY);
    }
    public void receiveNothing(){
        setState(ClientState.READY);
    }

    //gestione della chat
    public synchronized void openChat(){
        setPhase(ClientPhase.CHAT);
        new Thread(chat).start();
    }
    public synchronized void closeChat(){
        setPhase(ClientPhase.GAME);
        chat.stopThread();
        outputHandler.showNewTurn(modelView.getLocalBoard(), modelView.getLocalBookshelfs(), modelView.getCommonCards(), modelView.getDataCard(), modelView.getLocalPlayerList(), modelView.getGameMode());
    }

    // RMI: aggiornamenti
    public void ping(int ping) throws RemoteException{}
    public void createGame(int gameID, GameMode gameMode, int numPlayers) throws RemoteException {
        // se arriva prima l'ID, la fase sara gia a GAME
        setPhase(ClientPhase.LOBBY);
        modelView = new ModelView(gameID,gameMode,numPlayers);
        //modelView.init();
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "A game is created with gameID " + gameID );
    }
    public void playerJoin(String name) throws RemoteException {
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + "you" + ANSI_RESET + " joined the game");
        else
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + " joined the game");
    }
    public void playerLeave(String name) throws RemoteException {
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + "you" + ANSI_RESET + " left the game");
        else
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + " left the game");
    }
    public void playerDisconnect(String name) throws RemoteException {
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + "you" + ANSI_RESET + " has lost the connection");
        else
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + " has lost the connectionn");
    }
    public void playerReconnect(String name) throws RemoteException {
        // se arriva prima il name, la fase sara gia a GAME
        setPhase(ClientPhase.GAME);
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + "you" + ANSI_RESET + " have reconnected");
        else
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + " has reconnected");
    }
    public void startGame() throws RemoteException {
        setPhase(ClientPhase.GAME);
        modelView.init();
        outputHandler.showIntro();
        //outputHandler.showGame(modelView.getLocalBoard(), modelView.getLocalBookshelfs(), modelView.getCommonCards(), modelView.getDataCard(), modelView.getLocalPlayerList(), modelView.getGameMode());
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET +  "The game has begun!");
        /*if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + "your" + ANSI_RESET + "'s turn!");
        else
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + "'s turn");*/
    }
    public void newTurn(String name) throws RemoteException {
        if(getPhase().equals(ClientPhase.GAME))
            outputHandler.showNewTurn(modelView.getLocalBoard(), modelView.getLocalBookshelfs(), modelView.getCommonCards(), modelView.getDataCard(), modelView.getLocalPlayerList(), modelView.getGameMode());
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "your" + ANSI_RESET + "'s turn!");
        else
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + "'s turn");
    }
    public void lastRound(String name) throws RemoteException {
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + "you" + ANSI_RESET + " have finished his bookshelf!");
        else
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + " has finished his bookshelf!");
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + " one round left!");
    }
    public void endGame(String name) throws RemoteException {
        if(getPhase().equals(ClientPhase.CHAT))
            chat.stopThread();
        setPhase(ClientPhase.HOME);
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET +  "The game is finished!");
        outputHandler.showStatistics(modelView.getLocalBookshelfs(), modelView.getLocalPlayerList());
        outputHandler.showPodium(modelView.getLocalPlayerList());
        outputHandler.presentation();
    }

    // RMI: notifiche
    public void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException{
        if(getPhase().equals(ClientPhase.GAME))
            outputHandler.showBoardBookshelfHand(modelView.getLocalBoard(), modelView.getLocalBookshelfs().get(name), modelView.getLocalHand());
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + "you" + ANSI_RESET + ": PICK, coordinates " + coordinates.toString());
        else
            System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": PICK, coordinates " + coordinates.toString());
    }
    public void notifyUndo(String name) throws RemoteException{
        if(getPhase().equals(ClientPhase.GAME))
            outputHandler.showBoardBookshelfHand(modelView.getLocalBoard(), modelView.getLocalBookshelfs().get(name), modelView.getLocalHand());
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + "you" + ANSI_RESET + ": UNDO ");
        else
            System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": UNDO ");
    }
    public void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException{
        if(getPhase().equals(ClientPhase.GAME))
            outputHandler.showBoardBookshelfHand(modelView.getLocalBoard(), modelView.getLocalBookshelfs().get(name), modelView.getLocalHand());
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + "you" + ANSI_RESET + ": ORDER with " + list.toString());
        else
            System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": ORDER with " + list.toString());
    }
    public void notifyPut(String name, int column) throws RemoteException{
        if(getPhase().equals(ClientPhase.GAME))
            outputHandler.showBookshelf(modelView.getLocalBookshelfs().get(name));
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + "you" + ANSI_RESET + ": PUT, column " + column);
        else
            System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": PUT, column " + column);
    }

    // RMI: aggiornamenti al local model
    // in client

    // reconnection
    public void lostConnection(){
        chat.stopThread();
        System.out.println();
        System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "connection lost!");
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "you can close the application with EXIT command");
        if(getPhase().equals(ClientPhase.HOME))
            setPhase(ClientPhase.HOME_RECONNECTION);
        else
            setPhase(ClientPhase.MATCH_RECONNECTION);
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "attempting to reconnect...");
    }
    public void homeReconnection(){
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "reconnection to server done!");
        setPhase(ClientPhase.HOME);
        outputHandler.presentation();
    }
    public void gameReconnection(){
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "reconnection to server done!");
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "attempting to reconnect...");
    }
    public void leaveGame() {
        setPhase(ClientPhase.HOME);
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_CYAN + "you "+ ANSI_RESET + "left the game!");
        outputHandler.presentation();
    }

}
