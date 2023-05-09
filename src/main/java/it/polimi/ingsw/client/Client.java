package it.polimi.ingsw.client;

import it.polimi.ingsw.client.localModel.localBoard;
import it.polimi.ingsw.client.localModel.localBookshelf;
import it.polimi.ingsw.client.localModel.localHand;
import it.polimi.ingsw.client.localModel.localPlayer;
import it.polimi.ingsw.client.view.ViewHandler;
import it.polimi.ingsw.controller.ClientSkeleton;
import it.polimi.ingsw.model.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client extends UnicastRemoteObject implements ClientSkeleton {

    //Riferimenti al miniModel che saranno da togliere e da gestire in un'altra classe
    /*
    private localBoard board;
    private HashMap<String, localBookshelf> bookshelfmap;
    private localHand hand; //devo fare un HashMap?
    private ArrayList<localPlayer> localPlayerList;
    */

    private ViewHandler viewHandler;
    private String ID;
    private boolean state;

    public Client() throws RemoteException {
        state = false;
        viewHandler = new ViewHandler();
        /*
        board = new localBoard(null);
        bookshelfmap = new HashMap<>();
        hand = new localHand(null, 0);
        localPlayerList = new ArrayList<>();
        */
    }
    @Override
    public void newChatMessage(String name, String message) throws RemoteException {
        System.out.println(">> "+name+": "+message);
    }

    public void playerJoin(String name) throws RemoteException {
        System.out.println(">> "+name+" si è unito alla partita");
    }

    public void playerLeave(String name) throws RemoteException {
        System.out.println(">> "+name+": ha lasciato la partita");
    }

    public void startGame(String name) throws RemoteException {
        System.out.println(">> partitia iniziata!");
        System.out.println(">> e' turno di "+name);
    }

    public void newTurn(String name) throws RemoteException {
        System.out.println(">> e' il turno di "+name);
    }

    public void lastRound(String name) throws RemoteException {
        System.out.println(">> "+name+" ha completato la sua libreria!");
    }

    public void endGame(String name) throws RemoteException {
        System.out.println(">> partitia terminata!");
        System.out.println(">> il vincitore e' "+name);
    }

    public void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException{
        System.out.println(">> "+name+": PICK "+item.getType().getValue()+" in coords "+coordinates.toString());
    }
    public void notifyUndo(String name) throws RemoteException{
        System.out.println(">> "+name+": UNDO ");
    }
    public void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException{
        System.out.println(">> "+name+": ORDER with "+list.toString());
    }
    public void notifyPut(String name, int column) throws RemoteException{
        System.out.println(">> "+name+": PUT in column "+column);
    }
    /*
    ================================================================================================
    IDEA: stampare tutto ogni volta che arriva una notifica e quindi non nei metodi "update"
    IDEA: è stata cambiata la board, allora chiamo il metodo notify presente nella classe
    gestoreCLI oppure gestoreGUI che sceglie se e che cosa stampare in base al fatto che la
    board è stata cambia
    ================================================================================================
     */
    public void updateBoard(localBoard board) throws RemoteException {
        //this.board = board;
        //viewHandler.showGame(board, bookshelfmap, hand, localPlayerList);
        viewHandler.showBoad(board);
    }

    public void updateBookshelf(localBookshelf bookshelf) throws RemoteException {
        //bookshelfmap.put(bookshelf.name, bookshelf);
        //viewHandler.showGame(board, bookshelfmap, hand, localPlayerList);
        viewHandler.showBookshelf(bookshelf);
    }

    public void updateHand(localHand hand) throws RemoteException {
        //this.hand = hand;
        //viewHandler.showGame(board, bookshelfmap, hand, localPlayerList);
        viewHandler.showHand(hand);
    }

    public void updatePlayerList(ArrayList<localPlayer> playerList) throws RemoteException {
        System.out.print("\n");
        System.out.println("ecco i giocatori: ");
        for(localPlayer p:playerList) {
            //this.localPlayerList.add(p); //aggiungo i player alla lista di player
            //bookshelfmap.put(p.name, new localBookshelf(p.name, new Item[6][5])); //creo per ogni giocatore la sua bookshelf vuota
            System.out.print(p.name + " ");
            if(p.firstPlayerSeat)
                System.out.print("*\n");
            else
                System.out.print("\n");
        }
        System.out.print("\n");
    }

    public void updateCommonGoalCard(ArrayList<CommonGoalCard> commonGoalCardslist) throws RemoteException{
        System.out.println("... due common goal card con i relativi token ... ");
    }
    public void sendPersonalGoalCard(PersonalGoalCard personalGoalCard) throws RemoteException{
        System.out.println("... la tua personal goal card ... ");
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
}
