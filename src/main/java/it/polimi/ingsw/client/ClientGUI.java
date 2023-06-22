package it.polimi.ingsw.client;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.GUI.Command;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.utils.NotificationsType;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.connection.message.EndCause;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.DataCard;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Item;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;

import static it.polimi.ingsw.util.Constants.*;

public class ClientGUI extends Client{

    private GUI gui;
    //private boolean state;
    private final boolean GRAPHIC = true;

    public ClientGUI() throws RemoteException {
        //state = false;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void ping(int ping) throws RemoteException{}

    public void reveiceOK(){
        // da sistemare per evitare errore nel comando dopo join
    }

    public void receiveID(String ID){
        //this.ID=ID;
        //this.state=true;
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
        }
        else
            System.out.println(e);
        setState(ClientState.READY);
    }

    public void receiveNothing(){
        setState(ClientState.READY);
    }

    public void receiveGamesList(ArrayList<LocalGame> gameslist) throws RemoteException {
        gui.refreshGameList(gameslist);
    }

    public void createGame(int gameID, GameMode gameMode, int numPlayers) throws RemoteException {
        setPhase(ClientPhase.LOBBY);
        modelView = new ModelView(gameID,gameMode,numPlayers);
    }

    public void playerJoin(String name) throws RemoteException {
    }

    public void playerLeave(String name) throws RemoteException {
    }

    public void playerReconnect(String name) throws RemoteException {
        // se arriva prima il name, la fase sara gia a GAME
        setPhase(ClientPhase.GAME);
    }

    public void startGame() throws RemoteException {
        setPhase(ClientPhase.GAME);
        modelView.init();
        chat = new Chat(this);
        gui.switchStage(Command.START_GAME);
        //gui.setTurn(name);
    }

    public void newTurn(String name) throws RemoteException {
        gui.setTurn(name);
        gui.updateBoard();
    }

    public void lastRound(String name) throws RemoteException {
        gui.updateGlobalNotifications(NotificationsType.LASTROUND, name, new Coordinates(), new ArrayList<>(), 0);
    }

    public void endGame(String name, EndCause cause) throws RemoteException {
        setPhase(ClientPhase.HOME);
        gui.updateEnd(name, modelView.getLocalPlayerList());
        gui.updateStatistics(name, modelView.getLocalBookshelfs(), modelView.getLocalPlayerList());
    }

    public void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException{
        gui.updateBoard();
        //if(Objects.equals(name, this.name)){}
        gui.updateHand();
        gui.updateGlobalNotifications(NotificationsType.PICK, name, coordinates, new ArrayList<>(), 0);
    }
    public void notifyUndo(String name) throws RemoteException{
        gui.updateBoard();
        //if(Objects.equals(name, this.name)){  }
        gui.updateHand();
        gui.updateGlobalNotifications(NotificationsType.UNDO, name, new Coordinates(), new ArrayList<>(), 0);
    }
    public void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException{
        //if(Objects.equals(name, this.name)){  }
        gui.updateHand();
        gui.updateGlobalNotifications(NotificationsType.ORDER, name, new Coordinates(), list, 0);
    }
    public void notifyPut(String name, int column) throws RemoteException{
        //if(Objects.equals(name, this.name)){  }
        gui.updateHand();
        gui.updateBookShelf();
        gui.updatePlayersBookshelfs(modelView.getLocalBookshelfs(), name);
        gui.updateCurrentPoints();
        gui.updateGlobalNotifications(NotificationsType.PUT, name, new Coordinates(), new ArrayList<>(), column);
    }


    public void updateBoard(LocalBoard board) throws RemoteException {
        modelView.setLocalBoard(board);
        System.out.println("--> board received");
    }

    public void updateBookshelf(LocalBookshelf bookshelf) throws RemoteException {
        modelView.setLocalBookshelf(bookshelf);
        System.out.println("--> bookshelf received");
    }

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

    public void updatePlayerList(LocalPlayerList localPlayerList) throws RemoteException {
        modelView.setLocalPlayerList(localPlayerList);
        System.out.println("--> player list received");
    }

    public void updateCommonGoalCard(LocalCommonCard localCommonCard) throws RemoteException{
        modelView.setLocalCommonCard(localCommonCard);
        System.out.println("--> commonCard received");
    }
    public void updatePersonalGoalCard(LocalPersonalCard personalCard) throws RemoteException{
        // deve riceve una personal, non datacard
        modelView.setLocalPersonalCard(personalCard);
        System.out.println("--> personalCard received");
    }

    public void updateChat(ChatMessage chatMessage) throws RemoteException {
        gui.updateChat(chatMessage, name);
        System.out.println("--> personalCard received");
    }

    public void timer(int seconds) throws RemoteException{
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_CYAN + seconds  +" seconds "+ ANSI_RESET + "left before the end of the game!");
        System.out.println(ANSI_YELLOW + "❮INFOMATION❯ " + ANSI_RESET + "the timer will stop if someone joins the game!");
    }

    /*
    public boolean getState() {
        return state;
    } */

    public String getID() { return ID; }
    public GUI getGui() { return gui; }
    public ModelView getModelView() { return modelView; }

}
