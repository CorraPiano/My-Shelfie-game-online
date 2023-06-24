package it.polimi.ingsw.client.connection;

import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.client.*;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.controller.ControllerSkeleton;
import it.polimi.ingsw.controller.Settings;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.UUID;

public class RMISender extends Sender {
    private ControllerSkeleton controller;
    private final Client client;
    private final String IP;

    private String signature;

    public RMISender(String IP, Client client) throws Exception {
        this.IP = IP;
        this.client = client;
        connect();
        ConnectionChecker connectionChecker = new ConnectionChecker(this,client);
        new Thread(connectionChecker).start();
    }

    //da eliminare
    public RMISender(ControllerSkeleton controller, Client client) {
        this.controller = controller;
        this.client = client;
        this.IP = null;
    }

    public synchronized void getGameList() {
        try {
            ArrayList<LocalGame> list = controller.getGameList();
            client.receiveGamesList(list);
        }
        catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public synchronized void addFirstPlayer(String name, GameMode gameMode, int numPlayer) {
        try {
            client.setName(name);
            //String ID = controller.addFirstPlayer(name, gameMode, numPlayer, client);
            String ID = controller.addFirstPlayer(name, gameMode, numPlayer, signature);
            client.receiveID(ID);
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public synchronized void addPlayer(String name, int gameID) {
        try {
            client.setName(name);
            //String ID = controller.addPlayer(name,gameID,client);
            String ID = controller.addPlayer(name, gameID, signature);
            client.receiveID(ID);
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public synchronized void pickItem(Coordinates coordinates) {
        try {
            controller.pickItem(coordinates,client.getID());
            client.receiveNothing();
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public synchronized void undoPick() {
        try {
            controller.undoPick(client.getID());
            client.receiveNothing();
        }catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public synchronized void putItemList(int column) {
        try {
            controller.putItemList(column,client.getID());
            client.receiveNothing();
        }catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public synchronized void selectInsertOrder(ArrayList<Integer> order)  {
        try {
            controller.selectInsertOrder(order,client.getID());
            client.receiveNothing();
        }catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public synchronized void addChatMessage(String message, String receiver) {
        try{
            controller.addChatMessage(new ChatMessage(client.getName(), message, receiver), client.getID());
            client.receiveNothing();
        }catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public synchronized void addChatMessage(String message) {
        try{
            controller.addChatMessage(new ChatMessage(client.getName(), message), client.getID());
            client.receiveNothing();
        }catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public synchronized void leaveGame() {
        try {
            controller.leaveGame(client.getID());
            client.receiveNothing();
        }catch (Exception e){
            client.receiveException(e.toString());
        }
        client.leaveGame();
    }

    public synchronized void reconnectGame(String id) {
        try {
            client.setID(id);
            //far ritornare il name dal controller
            String name = controller.reconnect(id,client,true);
            client.receiveName(name);
        }catch (Exception e){
            client.receiveException(e.toString());
        }
    }

    public synchronized void connect() throws Exception {
        Registry registry = LocateRegistry.getRegistry(IP, Settings.RMIPORT);
        signature = UUID.randomUUID().toString();
        registry.rebind(signature, client);
        this.controller = (ControllerSkeleton) registry.lookup(Settings.remoteObjectName);
    }
    public synchronized void ping(int n) throws Exception {
        controller.ping(n);
    }
}
