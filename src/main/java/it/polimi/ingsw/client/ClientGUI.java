package it.polimi.ingsw.client;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.GUI.Command;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.DataCard;
import it.polimi.ingsw.model.Item;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientGUI extends Client{

    private final ModelView modelView;
    private GUI gui;
    private String ID;
    private boolean state;
    private final boolean GRAPHIC = true;

    public ClientGUI() throws RemoteException {
        state = false;
        modelView = new ModelView();
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void ping(int ping) throws RemoteException{}

    public void reveiceOK(){
        // da sistemare per evitare errore nel comando dopo join
    }

    public void receiveID(String ID){
        this.ID=ID;
        this.state=true;
    }

    public void receiveException(String e){
        System.out.println(e);
    }

    public void receiveGamesList(ArrayList<LocalGame> gameslist) throws RemoteException {
        gui.refreshGameList(gameslist);
    }

    public void createGame(int gameID) throws RemoteException {

    }

    public void playerJoin(String name) throws RemoteException {
        //System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + " joined the game");
    }

    public void playerLeave(String name) throws RemoteException {
        //System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + ": left the game");
    }

    public void startGame(String name) throws RemoteException {
        gui.switchStage(Command.START_GAME);

    }

    public void newTurn(String name) throws RemoteException {
//        if(GRAPHIC)
//            viewHandler.showNewTurn(modelView.getLocalBoard(), modelView.getLocalBookshelfs(), modelView.getCommonCards(), modelView.getDataCard(), modelView.getLocalPlayerList(), modelView.getGameMode());
//        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + "'s turn");
        gui.setTurn(name);
    }

    public void lastRound(String name) throws RemoteException {
        //System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_CYAN + name + ANSI_RESET + " has finished his bookshelf!");
    }

    public void endGame(String name) throws RemoteException {
        gui.updateEnd(name, modelView.getLocalPlayerList());
        gui.updateStatistics(name, modelView.getLocalBookshelfs(), modelView.getLocalPlayerList());
        //System.out.println(ANSI_YELLOW + "❮INFORMATION❯" + ANSI_RESET + " game over!");
        //System.out.println(ANSI_YELLOW + "❮INFORMATION❯" + ANSI_RESET + " the winner is " + ANSI_CYAN + name);
    }

    public void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException{
        gui.updateBoard();
        gui.updateHand();
        //System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": PICK, coordinates " + coordinates.toString());
    }
    public void notifyUndo(String name) throws RemoteException{
        gui.updateBoard();
        gui.updateHand();
        //System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": UNDO ");
    }
    public void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException{
        gui.updateHand();
        //System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": ORDER with " + list.toString());
    }
    public void notifyPut(String name, int column) throws RemoteException{
        gui.updateHand();
        gui.updateBookShelf();
        //System.out.println(ANSI_YELLOW + "❮ACTION❯ " + ANSI_CYAN + name + ANSI_RESET + ": PUT, column " + column);
    }


    public void updateBoard(LocalBoard board) throws RemoteException {
        if(GRAPHIC)
            modelView.setLocalBoard(board);
        else
            System.out.println("--> board received");
    }

    public void updateBookshelf(LocalBookshelf bookshelf) throws RemoteException {
        if(GRAPHIC)
            modelView.setLocalBookshelf(bookshelf);
        else
            System.out.println("--> bookshelf received");
    }

    public void updateHand(LocalHand hand) throws RemoteException {
        if(GRAPHIC)
             modelView.setLocalHand(hand);
        else
            System.out.println("--> hand received");
    }

    public void updateGame(LocalGame localGame) throws RemoteException {
        if(GRAPHIC) {
            modelView.setLocalPlayer(localGame.playerList);
            modelView.setGameMode(localGame.gameMode);
        }
        else
            System.out.println("--> game received");
    }

    public void updateCommonGoalCard(LocalCommonCard localCommonCard) throws RemoteException{
        if(GRAPHIC)
            modelView.setLocalCommonCard(localCommonCard);
        else
            System.out.println("--> commonCard received");
    }
    public void updatePersonalGoalCard(LocalPersonalCard personalCard) throws RemoteException{
        // deve riceve una personal, non datacard
        if(GRAPHIC) {
            modelView.setPersonalCard(new DataCard(personalCard.num));
            modelView.setLocalPersonalCard(personalCard);
        }
        else
            System.out.println("--> personalCard received");
    }

    public void updateChat(ChatMessage chatMessage) throws RemoteException {
        if(GRAPHIC) {
            gui.updateChat(chatMessage, name);
        }
        else
            System.out.println("--> personalCard received");
    }


    public String getID() {
        return ID;
    }

    /*public boolean getState() {
        return state;
    }
*/
    public GUI getGui() {
        return gui;
    }
    public ModelView getModelView() {
        return modelView;
    }

}
