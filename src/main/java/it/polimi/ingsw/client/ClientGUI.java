package it.polimi.ingsw.client;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.GUI.Command;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.utils.NotificationsType;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.connection.message.EndCause;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Item;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static it.polimi.ingsw.util.Constants.*;

/**
 * The ClientGUI class extends the Client class and represents a client with a graphical user interface.
 */
public class ClientGUI extends Client{

    private GUI gui;
    //private boolean state;
    private final boolean GRAPHIC = true;

    /**
     * Constructs a new ClientGUI object.
     *
     * @throws RemoteException if a remote error occurs.
     */
    public ClientGUI() throws RemoteException {
        //state = false;
        modelView = new ModelView();
    }

    /**
     * Sets the GUI instance associated with the client.
     *
     * @param gui The GUI instance to set.
     */
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * Notifies the client about a ping.
     *
     * @param ping The ping value.
     * @throws RemoteException if a remote error occurs.
     */
    public void ping(int ping) throws RemoteException{}

    public void reveiceOK(){
        // da sistemare per evitare errore nel comando dopo join
    }

    /**
     * Receives the ID assigned to the client by the server.
     *
     * @param ID The client ID.
     */
    public void receiveID(String ID){
        //this.ID=ID;
        //this.state=true;
        setID(ID);
        if(getPhase().equals(ClientPhase.HOME))
            setPhase(ClientPhase.LOBBY);
        setState(ClientState.READY);
    }

    /**
     * Receives the name assigned to the client by the server.
     *
     * @param name The client name.
     */
    public void receiveName(String name){
        setName(name);
        setPhase(ClientPhase.GAME);
        setState(ClientState.READY);
    }

    /**
     * Receives an exception from the server.
     *
     * @param e The exception message.
     */
    public void receiveException(String e){
        if(getPhase().equals(ClientPhase.MATCH_RECONNECTION)) {
            //System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "reconnection to game failed!");
            gui.updateExceptionNotification("reconnection to game failed");
            setPhase(ClientPhase.HOME);
        }
        else{
            System.out.println(e);
            gui.updateExceptionNotification(e);
        }
        setState(ClientState.READY);
    }

    /**
     * Receives a notification of no data from the server.
     */
    public void receiveNothing(){
        setState(ClientState.READY);
    }

    /**
     * Receives the list of games from the server.
     *
     * @param gameslist The list of games.
     * @throws RemoteException if a remote error occurs.
     */
    public void receiveGamesList(ArrayList<LocalGame> gameslist) throws RemoteException {
        gui.refreshGameList(gameslist);
    }

    /**
     * Creates a new game.
     *
     * @param gameID     The ID of the game.
     * @param gameMode   The game mode.
     * @param numPlayers The number of players.
     * @throws RemoteException if a remote error occurs.
     */
    public void createGame(int gameID, GameMode gameMode, int numPlayers) throws RemoteException {
        setPhase(ClientPhase.LOBBY);
        modelView.init(gameID,gameMode,numPlayers,getName());
        gui.joinLobby();
    }

    /**
     * Notifies the GUI that a player has joined the game lobby.
     *
     * @param name The name of the player who joined the game lobby.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void playerJoin(String name) throws RemoteException {
        this.gui.updatePlayerList(this.modelView.getLocalPlayerList(), Command.JOIN_GAME, name);
    }

    /**
     * Notifies the GUI that a player has left the game lobby.
     *
     * @param name The name of the player who left the game lobby.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void playerLeave(String name) throws RemoteException {
        this.gui.updatePlayerList(this.modelView.getLocalPlayerList(), Command.QUIT, name);
    }

    /**
     * Notifies the GUI that a player has disconnected from the game.
     *
     * @param name The name of the player who disconnected.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void playerDisconnect(String name) throws RemoteException {
        gui.updateTableView();
    }

    /**
     * Notifies the GUI that a player has reconnected to the game.
     *
     * @param name The name of the player who reconnected.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void playerReconnect(String name) throws RemoteException {
        // se arriva prima il name, la fase sara gia a GAME
        setPhase(ClientPhase.GAME);
        gui.updateTableView();
    }

    /**
     * Notifies the GUI that the game has started.
     *
     * @throws RemoteException if there is a communication-related issue.
     */
    public void startGame() throws RemoteException {
        setPhase(ClientPhase.GAME);
        modelView.loadPlayers();
        chat = new Chat(this);
        gui.switchStage(Command.START_GAME);
    }

    /**
     * Notifies the GUI that a new turn has started.
     *
     * @param name The name of the player whose turn it is.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void newTurn(String name) throws RemoteException {
        modelView.setCurrentPlayer(name);
        gui.setTurn(name);
        gui.updateBoard();
        gui.updateTableView();
        gui.updateTokens();
    }

    /**
     * Notifies the GUI that the last round of the game has started.
     *
     * @param name The name of the player who triggered the last round.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void lastRound(String name) throws RemoteException {
        gui.updateGlobalNotifications(NotificationsType.LASTROUND, name, new Coordinates(), new ArrayList<>(), 0);
        gui.updateEndGameToken();
    }

    /**
     * Notifies the GUI that the game has ended.
     *
     * @param name  The name of the player who caused the game to end.
     * @param cause The cause of the game ending.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void endGame(String name, EndCause cause) throws RemoteException {
        setPhase(ClientPhase.HOME);
        gui.switchStage(Command.END);
        //gui.updateEnd();
        System.out.println("--> updateEnd() viene fatta e finisce");
        //gui.updateStatistics(name, modelView.getLocalBookshelfs(), modelView.getLocalPlayerList());
    }

    /**
     * Notifies the GUI that a player has picked an item from the board.
     *
     * @param name       The name of the player who picked the item.
     * @param coordinates The coordinates of the picked item.
     * @param item       The item that was picked.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException{
        gui.updateBoard();
        //if(Objects.equals(name, this.name)){}
        gui.updateHand();
        gui.updateGlobalNotifications(NotificationsType.PICK, name, coordinates, new ArrayList<>(), 0);
    }

    /**
     * Notifies the GUI that a player has undone their previous action.
     *
     * @param name The name of the player who undid their action.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void notifyUndo(String name) throws RemoteException{
        gui.updateBoard();
        //if(Objects.equals(name, this.name)){  }
        gui.updateHand();
        gui.updateGlobalNotifications(NotificationsType.UNDO, name, new Coordinates(), new ArrayList<>(), 0);
    }

    /**
     * Notifies the GUI about the order of items chosen by a player.
     *
     * @param name The name of the player who chose the order.
     * @param list The list representing the order of items chosen.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException{
        //if(Objects.equals(name, this.name)){  }
        gui.updateHand();
        gui.updateGlobalNotifications(NotificationsType.ORDER, name, new Coordinates(), list, 0);
    }

    /**
     * Notifies the GUI that a player has placed an item on their bookshelf.
     *
     * @param name   The name of the player who placed the item.
     * @param column The column index where the item was placed.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void notifyPut(String name, int column) throws RemoteException{
        //if(Objects.equals(name, this.name)){  }
        gui.updateHand();
        gui.updateBookShelf();
        gui.updatePlayersBookshelfs(modelView.getLocalBookshelfs(), name);
        gui.updateGlobalNotifications(NotificationsType.PUT, name, new Coordinates(), new ArrayList<>(), column);
    }

    /**
     * Updates the local board in the model with the provided board.
     *
     * @param board The updated board.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void updateBoard(LocalBoard board) throws RemoteException {
        modelView.setLocalBoard(board);
        System.out.println("--> board received");
    }

    /**
     * Updates the local bookshelf in the model with the provided bookshelf.
     *
     * @param bookshelf The updated bookshelf.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void updateBookshelf(LocalBookshelf bookshelf) throws RemoteException {
        modelView.setLocalBookshelf(bookshelf);
        System.out.println("--> bookshelf received");
    }

    /**
     * Updates the local hand in the model with the provided hand.
     *
     * @param hand The updated hand.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void updateHand(LocalHand hand) throws RemoteException {
        modelView.setLocalHand(hand);
        System.out.println("--> hand received");
    }

    /*public void updateGame(LocalGame localGame) throws RemoteException {
        modelView.setLocalPlayer(localGame.playerList);
        modelView.setGameMode(localGame.gameMode);
        }
        else
            System.out.println("--> game received");
    }*/

    /**
     * Updates the local player list in the model with the provided player list.
     *
     * @param localPlayerList The updated player list.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void updatePlayerList(LocalPlayerList localPlayerList) throws RemoteException {
        modelView.setLocalPlayerList(localPlayerList);
        System.out.println("--> player list received");
    }

    /**
     * Updates the local common goal card in the model with the provided common card.
     *
     * @param localCommonCard The updated common goal card.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void updateCommonGoalCard(LocalCommonCard localCommonCard) throws RemoteException{
        modelView.setLocalCommonCard(localCommonCard);
        System.out.println("--> commonCard received");
    }

    /**
     * Updates the local personal goal card in the model with the provided personal card.
     *
     * @param personalCard The updated personal goal card.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void updatePersonalGoalCard(LocalPersonalCard personalCard) throws RemoteException{
        // deve riceve una personal, non datacard
        modelView.setLocalPersonalCard(personalCard);
        System.out.println("--> personalCard received");
    }

    /**
     * Updates the chat display in the GUI with the received chat message.
     *
     * @param chatMessage The chat message to be displayed.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void updateChat(ChatMessage chatMessage) throws RemoteException {
        gui.updateChat(chatMessage, name);
    }

    /**
     * Notifies the client about the remaining time in the game.
     *
     * @param seconds The number of seconds remaining in the game.
     * @throws RemoteException if there is a communication-related issue.
     */
    public void timer(int seconds) throws RemoteException{
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_CYAN + seconds  +" seconds "+ ANSI_RESET + "left before the end of the game!");
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "the timer will stop if someone joins the game!");
    }

    /*
    public boolean getState() {
        return state;
    } */

    /**
     * Retrieves the ID of the client.
     *
     * @return The ID of the client.
     */
    public String getID() { return ID; }

    /**
     * Retrieves the GUI associated with the client.
     *
     * @return The GUI instance.
     */
    public GUI getGui() { return gui; }

    /**
     * Retrieves the model view associated with the client.
     *
     * @return The model view instance.
     */
    public ModelView getModelView() { return modelView; }

}
