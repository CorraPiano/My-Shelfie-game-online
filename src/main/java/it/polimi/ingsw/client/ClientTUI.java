package it.polimi.ingsw.client;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.TUI.OutputHandler;
import it.polimi.ingsw.connection.message.EndCause;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Item;
import java.rmi.RemoteException;
import java.util.ArrayList;
import static it.polimi.ingsw.util.Constants.*;

/**
 * The ClientTUI class represents a text-based user interface for the client.
 */
public class ClientTUI extends Client {
    private final OutputHandler outputHandler;

    /**
     * Constructs a new ClientTUI instance.
     *
     * @throws RemoteException if there is a communication-related issue.
     */
    public ClientTUI() throws RemoteException {
        super();
        modelView = new ModelView();
        outputHandler = new OutputHandler(modelView);

    }

    /**
     * Retrieves the OutputHandler associated with the client.
     *
     * @return The OutputHandler instance.
     */
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

    /**
     * Receives the list of available games from the server.
     *
     * @param gameslist The list of available games.
     */
    public void receiveGamesList(ArrayList<LocalGame> gameslist){
        // fix grafico
        outputHandler.showList(gameslist);
        //gameslist.forEach(System.out::println);
        setState(ClientState.READY);
    }

    /**
     * Receives the client's ID from the server.
     *
     * @param ID The client's ID.
     */
    public void receiveID(String ID){
        setID(ID);
        if(getPhase().equals(ClientPhase.HOME))
            setPhase(ClientPhase.LOBBY);
        setState(ClientState.READY);
    }

    /**
     * Receives the client's name from the server.
     *
     * @param name The client's name.
     */
    public void receiveName(String name){
        setName(name);
        setPhase(ClientPhase.GAME);
        setState(ClientState.READY);
    }

    /**
     * Receives an exception message from the server.
     *
     * @param e The exception message.
     */
    public void receiveException(String e){
        if(getPhase().equals(ClientPhase.MATCH_RECONNECTION)) {
            System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "reconnection to game failed!");
            setPhase(ClientPhase.HOME);
            outputHandler.showHomeIntro();
        }
        else
            System.out.println(e);
        setState(ClientState.READY);
    }

    /**
     * Receives a notification of no data from the server.
     */
    public void receiveNothing(){
        setState(ClientState.READY);
    }

    //gestione della chat

    /**
     * Opens the chat interface.
     * Sets the client phase to "CHAT" and starts a new thread for the chat.
     */
    public synchronized void openChat(){
        setPhase(ClientPhase.CHAT);
        new Thread(chat).start();
    }

    /**
     * Closes the chat interface.
     * Sets the client phase back to "GAME" and stops the chat thread.
     */
    public synchronized void closeChat(){
        setPhase(ClientPhase.GAME);
        chat.stopThread();
        outputHandler.showGame();
    }

    // RMI: aggiornamenti

    /**
     * Ping method to check the client's connection to the server.
     *
     * @param ping The ping value sent to the server.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void ping(int ping) throws RemoteException{}

    /**
     * Creates a new game with the specified game ID, game mode, and number of players.
     *
     * @param gameID     The ID of the game.
     * @param gameMode   The game mode.
     * @param numPlayers The number of players in the game.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void createGame(int gameID, GameMode gameMode, int numPlayers) throws RemoteException {
        // se arriva prima l'ID, la fase sara gia a GAME
        setPhase(ClientPhase.LOBBY);
        modelView.init(gameID,gameMode,numPlayers,getName());
        //modelView.init();
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "A game is created with gameID " + gameID );
    }

    /**
     * Notifies the client about a player joining the game.
     *
     * @param name The name of the joining player.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void playerJoin(String name) throws RemoteException {
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + "you" + ANSI_RESET + " joined the game");
        else
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + " joined the game");
    }

    /**
     * Notifies the client about a player leaving the game.
     *
     * @param name The name of the leaving player.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void playerLeave(String name) throws RemoteException {
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + "you" + ANSI_RESET + " left the game");
        else
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + " left the game");
    }

    /**
     * Notifies the client about a player losing the connection.
     *
     * @param name The name of the player who lost the connection.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void playerDisconnect(String name) throws RemoteException {
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + "you" + ANSI_RESET + " has lost the connection");
        else
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + " has lost the connectionn");
    }

    /**
     * Notifies the client about a player reconnecting to the game.
     *
     * @param name The name of the player who reconnected.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void playerReconnect(String name) throws RemoteException {
        // se arriva prima il name, la fase sara gia a GAME
        setPhase(ClientPhase.GAME);
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + "you" + ANSI_RESET + " have reconnected");
        else
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + " has reconnected");
    }

    /**
     * Notifies the client that the game has started.
     *
     * @throws RemoteException if there is a communication-related issue.
     */
    public void startGame() throws RemoteException {
        setPhase(ClientPhase.GAME);
        modelView.loadPlayers();
        chat = new Chat(this);
        outputHandler.showGameIntro();
        //outputHandler.showGame(modelView.getLocalBoard(), modelView.getLocalBookshelfs(), modelView.getCommonCards(), modelView.getDataCard(), modelView.getLocalPlayerList(), modelView.getGameMode());
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET +  "The game has begun!");
        /*if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + "your" + ANSI_RESET + "'s turn!");
        else
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + "'s turn");*/
    }

    /**
     * Notifies the client about the start of a new turn.
     *
     * @param name The name of the player whose turn it is.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void newTurn(String name) throws RemoteException {
        modelView.setCurrentPlayer(name);
        if(getPhase().equals(ClientPhase.GAME))
            outputHandler.showGame();
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "your" + ANSI_RESET + "'s turn!");
        else
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + "'s turn");
    }

    /**
     * Notifies the client that the current round is the last round.
     *
     * @param name The name of the player who finished their bookshelf.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void lastRound(String name) throws RemoteException {
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + "you" + ANSI_RESET + " have finished his bookshelf!");
        else
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + " has finished his bookshelf!");
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + " one round left!");
    }

    /**
     * Notifies the client that the game has ended.
     *
     * @param name  The name of the player who triggered the end of the game.
     * @param cause The cause of the game ending.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void endGame(String name, EndCause cause) throws RemoteException {
        if(getPhase().equals(ClientPhase.CHAT))
            chat.stopThread();
        setPhase(ClientPhase.HOME);
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET +  "The game is finished!");
        outputHandler.showEndGame();
        //outputHandler.showStatistics();
        //outputHandler.showPodium();
        outputHandler.showHomeIntro();
    }

    // RMI: notifiche

    /**
     * Notifies the client about a player picking an item from the library.
     *
     * @param name       The name of the player who picked the item.
     * @param coordinates The coordinates of the item in the library.
     * @param item       The item that was picked.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException{
        if(getPhase().equals(ClientPhase.GAME))
            outputHandler.showCurrentAction(name);
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + "you" + ANSI_RESET + ": PICK, coordinates " + coordinates.toString());
        else
            System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": PICK, coordinates " + coordinates.toString());
    }

    /**
     * Notifies the client about a player requesting an undo action.
     *
     * @param name The name of the player who requested the undo action.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void notifyUndo(String name) throws RemoteException{
        if(getPhase().equals(ClientPhase.GAME))
            outputHandler.showCurrentAction(name);
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + "you" + ANSI_RESET + ": UNDO ");
        else
            System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": UNDO ");
    }

    /**
     * Notifies the client about a player ordering a set of items.
     *
     * @param name The name of the player who ordered the items.
     * @param list The list of item indices in the order they were chosen.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException{
        if(getPhase().equals(ClientPhase.GAME))
            outputHandler.showCurrentAction(name);
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + "you" + ANSI_RESET + ": ORDER with " + list.toString());
        else
            System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": ORDER with " + list.toString());
    }

    /**
     * Notifies the client about a player placing an item on their bookshelf.
     *
     * @param name   The name of the player who placed the item.
     * @param column The column where the item was placed.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void notifyPut(String name, int column) throws RemoteException{
        if(getPhase().equals(ClientPhase.GAME))
            outputHandler.showBookshelf(name);
        if(name.equals(getName()))
            System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + "you" + ANSI_RESET + ": PUT, column " + column);
        else
            System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": PUT, column " + column);
    }

    // RMI: aggiornamenti al local model
    // in client

    // reconnection

    /**
     * Notifies the client that the connection to the server has been lost.
     * Handles the reconnection process.
     */
    public void lostConnection(){
        if(getPhase().equals(ClientPhase.CHAT))
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

    /**
     * Handles the reconnection process for the client in the home phase.
     */
    public void homeReconnection(){
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "reconnection to server done!");
        setPhase(ClientPhase.HOME);
        outputHandler.showHomeIntro();
    }

    /**
     * Handles the reconnection process for the client in the game phase.
     */
    public void gameReconnection(){
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "reconnection to server done!");
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "attempting to reconnect...");
    }

    /**
     * Sets the client's phase to HOME and notifies the client that they have left the game.
     */
    public void leaveGame() {
        setPhase(ClientPhase.HOME);
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_CYAN + "you "+ ANSI_RESET + "left the game!");
        outputHandler.showHomeIntro();
    }

    /**
     * Notifies the client about the remaining time in the game.
     *
     * @param seconds The number of seconds remaining in the game.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void timer(int seconds) throws RemoteException{
        seconds = seconds/1000;
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_CYAN + seconds  +" seconds "+ ANSI_RESET + "left before the end of the game!");
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "the timer will stop if someone joins the game!");
    }

}
