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

/**
 * The `RMISender` class is responsible for sending messages from the client to the server using RMI (Remote Method Invocation).
 * It extends the abstract `Sender` class and implements the necessary methods for communication.
 */
public class RMISender extends Sender {

    private ControllerSkeleton controller;
    ConnectionChecker connectionChecker;
    private final Client client;
    private final String IP;

    /**
     * Constructs an `RMISender` object with the specified IP address and client.
     *
     * @param IP     The IP address of the server.
     * @param client The client associated with this sender.
     * @throws Exception If an error occurs while establishing the RMI connection.
     */
    public RMISender(String IP, Client client) throws Exception {
        this.IP = IP;
        this.client = client;
        connectionChecker = new ConnectionChecker(this,client);
        connect();
        new Thread(connectionChecker).start();
    }

    /**
     * Retrieves the list of available games from the server.
     */
    @Override
    public synchronized void getGameList() {
        try {
            ArrayList<LocalGame> list = controller.getGameList();
            client.receiveGamesList(list);
        } catch (Exception e) {
            client.receiveException(e.toString());
        }
    }

    /**
     * Adds the first player to a new game on the server.
     *
     * @param name      The name of the player.
     * @param gameMode  The game mode chosen by the player.
     * @param numPlayer The number of players in the game.
     */
    @Override
    public synchronized void addFirstPlayer(String name, GameMode gameMode, int numPlayer) {
        try {
            client.setName(name);
            String ID = controller.addFirstPlayer(name, gameMode, numPlayer, client);
            client.receiveID(ID);
        } catch (Exception e) {
            client.receiveException(e.toString());
        }
    }

    /**
     * Adds a player to an existing game on the server.
     *
     * @param name    The name of the player.
     * @param gameID  The ID of the game.
     */
    @Override
    public synchronized void addPlayer(String name, int gameID) {
        try {
            client.setName(name);
            String ID = controller.addPlayer(name, gameID, client);
            client.receiveID(ID);
        } catch (Exception e) {
            client.receiveException(e.toString());
        }
    }

    /**
     * Picks an item at the specified coordinates on the game board.
     *
     * @param coordinates The coordinates of the item.
     */
    @Override
    public synchronized void pickItem(Coordinates coordinates) {
        try {
            controller.pickItem(coordinates, client.getID());
            client.receiveNothing();
        } catch (Exception e) {
            client.receiveException(e.toString());
        }
    }

    /**
     * Undoes the last item picked by the player.
     */
    @Override
    public synchronized void undoPick() {
        try {
            controller.undoPick(client.getID());
            client.receiveNothing();
        } catch (Exception e) {
            client.receiveException(e.toString());
        }
    }

    /**
     * Puts the picked item into the specified column of the game board.
     *
     * @param column The column index.
     */
    @Override
    public synchronized void putItemList(int column) {
        try {
            controller.putItemList(column, client.getID());
            client.receiveNothing();
        } catch (Exception e) {
            client.receiveException(e.toString());
        }
    }

    /**
     * Selects the order for inserting the items into the game board.
     *
     * @param order The list of column indices representing the order of insertion.
     */
    @Override
    public synchronized void selectInsertOrder(ArrayList<Integer> order) {
        try {
            controller.selectInsertOrder(order, client.getID());
            client.receiveNothing();
        } catch (Exception e) {
            client.receiveException(e.toString());
        }
    }

    /**
     * Adds a chat message to the game chat with the specified receiver.
     *
     * @param message  The message content.
     * @param receiver The receiver of the message.
     */
    @Override
    public synchronized void addChatMessage(String message, String receiver) {
        try {
            controller.addChatMessage(new ChatMessage(client.getName(), message, receiver), client.getID());
            client.receiveNothing();
        } catch (Exception e) {
            client.receiveException(e.toString());
        }
    }

    /**
     * Adds a chat message to the game chat without a specific receiver.
     *
     * @param message The message content.
     */
    @Override
    public synchronized void addChatMessage(String message) {
        try {
            controller.addChatMessage(new ChatMessage(client.getName(), message), client.getID());
            client.receiveNothing();
        } catch (Exception e) {
            client.receiveException(e.toString());
        }
    }

    /**
     * Leaves the current game.
     */
    @Override
    public synchronized void leaveGame() {
        try {
            controller.leaveGame(client.getID());
            client.receiveNothing();
        } catch (Exception e) {
            client.receiveException(e.toString());
        }
        client.leaveGame();
    }

    /**
     * Reconnects to a previously joined game with the specified ID.
     *
     * @param id    The ID of the game to reconnect to.
     * @param reset Indicates whether the game should be reset or resumed.
     */
    @Override
    public synchronized void reconnectGame(String id, boolean reset) {
        try {
            client.setID(id);
            String name = controller.reconnect(id, client, reset);
            client.receiveName(name);
        } catch (Exception e) {
            client.receiveException(e.toString());
        }
    }

    /**
     * Connects to the server using RMI based on the specified IP address.
     *
     * @throws Exception If an error occurs while establishing the RMI connection.
     */
    @Override
    public synchronized void connect() throws Exception {
        Registry registry = LocateRegistry.getRegistry(IP, Settings.RMIPORT);
        this.controller = (ControllerSkeleton) registry.lookup(Settings.remoteObjectName);
    }
     public synchronized boolean reconnect() {
         client.forceCloseApp();
         return false;
     }

    /**
     * Sends a ping request to the server with the specified number.
     *
     * @param n The ping number.
     * @throws Exception If an error occurs while sending the ping.
     */
    @Override
    public synchronized void ping(int n) throws Exception {
        int  nn = controller.ping(n,client.getID());
        //System.out.println("_____>>>>>>>>>>>>");
        connectionChecker.setLastPing();
    }
}