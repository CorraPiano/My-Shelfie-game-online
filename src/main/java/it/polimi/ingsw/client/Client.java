package it.polimi.ingsw.client;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.view.ViewHandler;
import it.polimi.ingsw.connection.TCPMessage;
import it.polimi.ingsw.controller.ClientSkeleton;
import it.polimi.ingsw.model.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Client extends UnicastRemoteObject implements ClientSkeleton {

    //Riferimenti al miniModel che saranno da togliere e da gestire in un'altra classe
    /*
    private localBoard board;
    private HashMap<String, localBookshelf> bookshelfmap;
    private localHand hand; //devo fare un HashMap?
    private ArrayList<localPlayer> localPlayerList;
    */

    private ModelView modelView;
    private ViewHandler viewHandler;
    private String ID;
    private boolean state;

    public Client() throws RemoteException {
        state = false;
        viewHandler = new ViewHandler();
        modelView = new ModelView();
        /*
        board = new localBoard(null);
        bookshelfmap = new HashMap<>();
        hand = new localHand(null, 0);
        localPlayerList = new ArrayList<>();
        */
    }

    //vedere se fare public o private le classi del localModel
    //attualmente sono public, se vanno rese private vanno inseriti i getter
    @Override
    public void newChatMessage(String name, String message) throws RemoteException {
        System.out.println(">> "+name+": "+message);
    }

    public void getID(String ID){
        System.out.println("CLIENT: partita creata e giocatore connesso");
        setId(ID);
        setState(true);
    }

    public void playerJoin(String name) throws RemoteException {
        System.out.println(">> "+name+" si è unito alla partita");
    }

    public void playerLeave(String name) throws RemoteException {
        System.out.println(">> "+name+": ha lasciato la partita");
    }

    public void startGame(String name) throws RemoteException {
        System.out.println(">> partitia iniziata!");
        viewHandler.showGame(modelView.getLocalBoard(), modelView.getLocalBookshelfs(), modelView.getCommonCards(), modelView.getDataCard(), modelView.getLocalPlayerList());
        System.out.println(">> e' turno di "+name);
    }

    public void newTurn(String name) throws RemoteException {
        viewHandler.showNewTurn(modelView.getLocalBoard(), modelView.getLocalBookshelfs(), modelView.getCommonCards(), modelView.getDataCard(), modelView.getLocalPlayerList());
        System.out.println(">> e' il turno di "+name);
    }

    public void lastRound(String name) throws RemoteException {
        System.out.println(">> "+name+" ha completato la sua libreria!");
    }

    public void endGame(String name) throws RemoteException {
        System.out.println(">> partitia terminata!");
        System.out.println(">> il vincitore e' "+name);
    }

    public void leaveGame(String name) throws RemoteException{
        //da implementare
    }

    public void notify(TCPMessage TCPMessage) throws RemoteException {
        //attualmente non serve, per eventuali modifiche future
    }

    public void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException{
        viewHandler.showBoad(modelView.getLocalBoard());
        viewHandler.showHand(modelView.getLocalHand());
        System.out.println(">> "+name+": PICK "+item.getType().getValue()+" in coords "+coordinates.toString());
    }
    public void notifyUndo(String name) throws RemoteException{
        System.out.println(">> "+name+": UNDO ");
    }
    public void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException{
        System.out.println(">> "+name+": ORDER with "+list.toString());
    }
    public void notifyPut(String name, int column) throws RemoteException{
        viewHandler.showBookshelf(modelView.getLocalBookshelfs().get(name));
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
    public void updateBoard(LocalBoard board) throws RemoteException {
        //viewHandler.showBoad(board);
        modelView.setLocalBoard(board);
    }

    public void updateBookshelf(LocalBookshelf bookshelf) throws RemoteException {
        //viewHandler.showBookshelf(bookshelf);
        modelView.setLocalBookshelf(bookshelf);
    }

    public void updateHand(LocalHand hand) throws RemoteException {
        //viewHandler.showHand(hand);
        modelView.setLocalHand(hand);
    }

    public void updateGame(LocalGame localGame) throws RemoteException {
        //viewHandler.showPlayers(playerList);
        modelView.setLocalPlayer(localGame.playerList);
    }

    //non usata, da rimuovere
    public void updatePlayerList(ArrayList<LocalPlayer> playerList) throws RemoteException {
        //viewHandler.showPlayers(playerList);
        modelView.setLocalPlayer(playerList);
    }

    public void updateCommonGoalCard(LocalCommonCard commonGoalCard) throws RemoteException{
        // TODO: ad updateCommonGoalCard verrà passata una LocalCommonCard al posto di una commonGoalCard
        //viewHandler.showCommonGoalCards(commonGoalCard);
        LocalCommonCard localCommonCard = new LocalCommonCard(commonGoalCard.getType(), commonGoalCard.showToken());
        modelView.setLocalCommonCard(localCommonCard);
    }
    public void updatePersonalGoalCard(DataCard dataCard) throws RemoteException{
        //viewHandler.showPersonalGoalCard(dataCard);
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

    public void ping(int ping) throws RemoteException{

    }
}
