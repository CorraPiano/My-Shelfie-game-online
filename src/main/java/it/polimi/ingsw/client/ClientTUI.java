package it.polimi.ingsw.client;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.TUI.OutputHandler;
import it.polimi.ingsw.model.Coordinates;
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

    public OutputHandler getOutputHandler() {
        return outputHandler;
    }

    @Override
    public synchronized void receiveException(String e){
        System.out.println(e);
        state = ClientState.READY;
        this.notifyAll();
    }

    @Override
    public synchronized void receiveGamesList(ArrayList<LocalGame> gameslist) throws RemoteException {
        // da sistemare
        gameslist.forEach(System.out::println);
        state = ClientState.READY;
        this.notifyAll();
    }

    public synchronized void closeChat(){
        chat.stopThread();
        phase=ClientPhase.GAME;
        outputHandler.showNewTurn(modelView.getLocalBoard(), modelView.getLocalBookshelfs(), modelView.getCommonCards(), modelView.getDataCard(), modelView.getLocalPlayerList(), modelView.getGameMode());
    }

    public void createGame(int gameID) throws RemoteException {
        phase = ClientPhase.GAME;
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "The game is created with gameID " + gameID );
    }

    public void playerJoin(String name) throws RemoteException {
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + " joined the game");
    }

    public void playerLeave(String name) throws RemoteException {
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + ": left the game");
    }
    public void playerDisconnect(String name) throws RemoteException {
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + ": has lost the connection");
    }

    @Override
    public synchronized void playerReconnect(String name) throws RemoteException {
        phase = ClientPhase.GAME;
        state = ClientState.READY;
        this.notifyAll();
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + ": has reconnected");
    }

    public void startGame(String name) throws RemoteException {
        if(phase.equals(ClientPhase.GAME)) {
            outputHandler.showIntro();
            outputHandler.showGame(modelView.getLocalBoard(), modelView.getLocalBookshelfs(), modelView.getCommonCards(), modelView.getDataCard(), modelView.getLocalPlayerList(), modelView.getGameMode());
        }
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET +  "The game has begun!");
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + "'s turn");
    }

    public void newTurn(String name) throws RemoteException {
        if(phase.equals(ClientPhase.GAME))
            outputHandler.showNewTurn(modelView.getLocalBoard(), modelView.getLocalBookshelfs(), modelView.getCommonCards(), modelView.getDataCard(), modelView.getLocalPlayerList(), modelView.getGameMode());
        if(name.equals(this.name))
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "your turn!");
        else
            System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + "'s turn");
    }

    public void lastRound(String name) throws RemoteException {
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + " has finished his bookshelf!");
    }

    @Override
    public synchronized void endGame(String name) throws RemoteException {
        if(phase.equals(ClientPhase.CHAT))
            chat.stopThread();
        phase=ClientPhase.JOIN;

        outputHandler.showStatistics(modelView.getLocalBookshelfs(), modelView.getLocalPlayerList());
        outputHandler.showPodium(modelView.getLocalPlayerList());
        System.out.println(ANSI_YELLOW + "❮INSTRUCTION❯ " + ANSI_RESET + "Enter any key to continue ...");

    }

    public void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException{
        if(phase.equals(ClientPhase.GAME))
            outputHandler.showBoardBookshelfHand(modelView.getLocalBoard(), modelView.getLocalBookshelfs().get(name), modelView.getLocalHand());
        System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": PICK, coordinates " + coordinates.toString());
    }
    public void notifyUndo(String name) throws RemoteException{
        System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": UNDO ");
    }
    public void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException{
        System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": ORDER with " + list.toString());
    }
    public void notifyPut(String name, int column) throws RemoteException{
        if(phase.equals(ClientPhase.GAME))
            outputHandler.showBookshelf(modelView.getLocalBookshelfs().get(name));
        System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": PUT, column " + column);
    }

    public void lostConnection(){
        chat.stopThread();
        phase = ClientPhase.CLOSE;
        state = ClientState.READY;
        System.out.println("connection lost");
        System.exit(0);
    }

}
