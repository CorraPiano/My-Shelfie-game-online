package it.polimi.ingsw.client;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.TUI.OutputHandler;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.connection.message.EndCause;
import it.polimi.ingsw.controller.ClientSkeleton;
import it.polimi.ingsw.model.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import static it.polimi.ingsw.util.Constants.*;

/**
 * The Client class represents a client connected to the server.
 */
public class Client extends UnicastRemoteObject implements ClientSkeleton {
    protected ModelView modelView;
    //private View view;
    protected  String ID;
    protected String name;
    protected  ClientPhase phase;
    protected  ClientState state;
    //protected  Chat chat;
    private final boolean DEBUG = false;


    /**
     * Constructs a new Client object.
     *
     * @throws RemoteException if a remote error occurs.
     */
    public Client() throws RemoteException {
        phase = ClientPhase.HOME;
        state = ClientState.READY;
    }

    // GETTERS
    public synchronized String getName() { return name; }

    /**
     * Retrieves the hand size of the client.
     *
     * @return The size of the hand.
     */
    public synchronized int getHandSize(){
        return modelView.getLocalHand().size;
    }

    /**
     * Retrieves the model view associated with the client.
     *
     * @return The model view.
     */
    public synchronized ModelView getModelView() { return modelView; }

    /**
     * Retrieves the chat instance associated with the client.
     *
     * @return The chat instance.
     */
    //public synchronized Chat getChat() { return chat; }

    /**
     * Retrieves the ID of the client.
     *
     * @return The client ID.
     */
    public synchronized String getID() { return ID; }

    /**
     * Retrieves the current phase of the client.
     *
     * @return The client phase.
     */
    public synchronized ClientPhase getPhase() { return phase; }

    /**
     * Retrieves the current state of the client.
     *
     * @return The client state.
     */
    public synchronized ClientState getState() { return state; }

    // SETTERS

    /**
     * Sets the name of the client.
     *
     * @param name The name to set.
     */
    public synchronized void setName(String name){
        this.name=name;
    }

    /**
     * Sets the ID of the client.
     *
     * @param id The ID to set.
     */
    public synchronized void setID(String id){
        this.ID=id;
        //LocalSave.storeID(ID);
    }

    /**
     * Sets the phase of the client.
     *
     * @param phase The phase to set.
     */
    public synchronized void setPhase(ClientPhase phase){
        this.phase = phase;
    }

    /**
     * Sets the state of the client.
     *
     * @param state The state to set.
     */
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
    public void endGame(String name, EndCause cause) throws RemoteException {
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
        modelView.updateChat(chatMessage);
        //chat.addChatMessage(chatMessage);
    }

    // RECONNECTION
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

    public void timer(int seconds) throws RemoteException {
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_CYAN + seconds  +" seconds "+ ANSI_RESET + "left before the end of the game!");
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "the timer will stop if someone joins the game!");
    }

    public void closeApp(){}
}
