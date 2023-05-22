package it.polimi.ingsw.client;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.ViewHandler;
import it.polimi.ingsw.connection.TCPMessage;
import it.polimi.ingsw.controller.ClientSkeleton;
import it.polimi.ingsw.model.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import static it.polimi.ingsw.util.Constants.*;

public class Client extends UnicastRemoteObject implements ClientSkeleton {

    private ModelView modelView;
    private ViewHandler viewHandler;
    private String ID;
    private boolean state;

    public Client() throws RemoteException {
        state = false;
        viewHandler = new ViewHandler();
        modelView = new ModelView();
    }

    /*
    =================================================================================================
    IDEA: vedere se fare public o private le classi del localModel
    attualmente sono public, se vanno rese private vanno inseriti i getter
    =================================================================================================
     */
    @Override
    public void newChatMessage(String name, String message) throws RemoteException {
        System.out.println(">> "+name+": "+message);
    }

    public void getID(String ID){
        //System.out.println("CLIENT: game created and player connected");
        setId(ID);
        setState(true);
    }

    public void playerJoin(String name) throws RemoteException {
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + " joined the game");
    }

    public void playerLeave(String name) throws RemoteException {
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + ": left the game");
    }

    public void startGame(String name) throws RemoteException {
        //System.out.println(ANSI_YELLOW + "❮INFORMATION❯" + ANSI_RESET +  " the game has begun!");
        viewHandler.showIntro();
        viewHandler.showGame(modelView.getLocalBoard(), modelView.getLocalBookshelfs(), modelView.getCommonCards(), modelView.getDataCard(), modelView.getLocalPlayerList(), modelView.getGameMode());
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + "'s turn");
    }

    public void newTurn(String name) throws RemoteException {
        viewHandler.showNewTurn(modelView.getLocalBoard(), modelView.getLocalBookshelfs(), modelView.getCommonCards(), modelView.getDataCard(), modelView.getLocalPlayerList(), modelView.getGameMode());
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + "'s turn");
    }

    public void lastRound(String name) throws RemoteException {
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + " has finished his bookshelf!");
    }

    public void endGame(String name) throws RemoteException {
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯" + ANSI_RESET + " game over!");
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯" + ANSI_RESET + " the winner is " + ANSI_CYAN + name);
    }

    public void leaveGame(String name) throws RemoteException{
        //da implementare
    }

    public void notify(TCPMessage TCPMessage) throws RemoteException {
        //attualmente non serve, per eventuali modifiche future
    }

    public void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException{
        viewHandler.showBoardAndHand(modelView.getLocalBoard(), modelView.getLocalHand());
        System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": PICK, coordinates " + coordinates.toString());
    }
    public void notifyUndo(String name) throws RemoteException{
        System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": UNDO ");
    }
    public void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException{
        System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": ORDER with " + list.toString());
    }
    public void notifyPut(String name, int column) throws RemoteException{
        viewHandler.showBookshelf(modelView.getLocalBookshelfs().get(name));
        System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": PUT, column " + column);
    }


    public void updateBoard(LocalBoard board) throws RemoteException {
        modelView.setLocalBoard(board);
    }

    public void updateBookshelf(LocalBookshelf bookshelf) throws RemoteException {
        modelView.setLocalBookshelf(bookshelf);
    }

    public void updateHand(LocalHand hand) throws RemoteException {
        modelView.setLocalHand(hand);
    }

    public void updateGame(LocalGame localGame) throws RemoteException {
        modelView.setLocalPlayer(localGame.playerList);
        modelView.setGameMode(localGame.gameMode);
    }

    //non usata, da rimuovere
    public void updatePlayerList(ArrayList<LocalPlayer> playerList) throws RemoteException {
        modelView.setLocalPlayer(playerList);
    }

    public void updateCommonGoalCard(LocalCommonCard localCommonCard) throws RemoteException{
        modelView.setLocalCommonCard(localCommonCard);
    }
    public void updatePersonalGoalCard(DataCard dataCard) throws RemoteException{
        modelView.setPersonalCard(dataCard);
    }

    public String getID() {
        return ID;
    }

    public void setId(String ID){
        this.ID=ID;
    }

    public boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state=state;
    }

    public ViewHandler getViewhandler() {
        return viewHandler;
    }
    public ModelView getModelView() {
        return modelView;
    }

    public void ping(int ping) throws RemoteException{}
}
