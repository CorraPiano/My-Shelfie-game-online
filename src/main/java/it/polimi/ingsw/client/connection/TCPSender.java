package it.polimi.ingsw.client.connection;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientPhase;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.connection.ReconnectType;
import it.polimi.ingsw.controller.Settings;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.connection.message.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

/**
 * The TCPSender class is responsible for sending messages to the server using TCP connection.
 */
public class TCPSender extends Sender {
    private ClientConnection connection;
    private final Client client;

    ConnectionChecker connectionChecker;
    TCPReceiver TCPreceiver;

    private final String IP;

    /**
     * Constructs a new TCPSender instance with the specified IP and client.
     *
     * @param IP     The IP address of the server.
     * @param client The client instance.
     * @throws Exception if an error occurs during connection setup.
     */
    public TCPSender(String IP, Client client) throws Exception {
        this.IP = IP;
        this.client = client;
        connectionChecker = new ConnectionChecker(this,client);
        TCPreceiver = new TCPReceiver(client,connectionChecker);
        connect();
        new Thread(connectionChecker).start();
    }

    /**
     * Constructs a new TCPSender instance with the given connection and client.
     *
     * @param connection The client connection.
     * @param client     The client instance.
     */
    public TCPSender(ClientConnection connection, Client client){
        this.connection = connection;
        this.client = client;
        this.IP = null;
    }

    /**
     * Sends a ListMessage to the server to retrieve the list of available games.
     */
    public void getGameList() {
        try {
            connection.send(new ListMessage());
        } catch(Exception e){

        }
        putInWait();
    }

    /**
     * Sends a CreateMessage to the server to add the first player to a new game.
     *
     * @param name      The player's name.
     * @param gameMode  The game mode.
     * @param numPlayer The number of players in the game.
     */
    public void addFirstPlayer(String name, GameMode gameMode, int numPlayer) {
        client.setName(name);
        try {
            connection.send(new CreateMessage(name, gameMode, numPlayer));
        } catch(Exception e){

        }
        putInWait();
    }

    /**
     * Sends a JoinMessage to the server to add a player to an existing game.
     *
     * @param name   The player's name.
     * @param gameID The ID of the game to join.
     */
    public void addPlayer(String name, int gameID)  {
        client.setName(name);
        try {
            connection.send(new JoinMessage(name,gameID));
        } catch(Exception e){

        }
        putInWait();
    }

    /**
     * Sends a PickMessage to the server to pick an item at the specified coordinates.
     *
     * @param coordinates The coordinates of the item to pick.
     */
    public void pickItem(Coordinates coordinates)  {
        try {
            connection.send(new PickMessage(coordinates));
        } catch(Exception e){

        }
        putInWait();
    }

    /**
     * Sends an UndoMessage to the server to undo the last item pick.
     */
    public void undoPick() {
        try{
            connection.send(new UndoMessage());
        } catch(Exception e){

        }
        putInWait();
    }

    /**
     * Sends a PutMessage to the server to put an item in the specified column.
     *
     * @param column The column where the item should be placed.
     */
    public void putItemList(int column)  {
        try{
            connection.send(new PutMessage(column));
        } catch(Exception e){

        }
        putInWait();
    }

    /**
     * Sends an OrderMessage to the server to select the insert order of items.
     *
     * @param order The order of item insertion.
     */
    public void selectInsertOrder(ArrayList<Integer> order) {
        try{
            connection.send(new OrderMessage(order));
        } catch(Exception e){

        }
        putInWait();
    }

    /**
     * Sends a ChatMessage to the server to add a chat message to a specific receiver.
     *
     * @param message  The chat message.
     * @param receiver The receiver of the chat message.
     */
    public void addChatMessage(String message, String receiver) {
        try{
            connection.send(new ChatMessage(client.getName(), message, receiver));
        } catch(Exception e){

        }
        putInWait();
    }

    /**
     * Sends a ChatMessage to the server to add a chat message to all players.
     *
     * @param message The chat message.
     */
    public void addChatMessage(String message){
        try{
            connection.send(new ChatMessage(client.getName(), message));
        } catch(Exception e){

        }
        putInWait();
    }

    /**
     * Sends a LeaveMessage to the server to leave the current game.
     */
    public void leaveGame() {
        try{
            connection.send(new LeaveMessage());
        } catch(Exception e){

        }
        putInWait();
        client.leaveGame();
    }

    /**
     * Sends a ReconnectMessage to the server to reconnect to a game.
     *
     * @param id    The ID of the game to reconnect.
     * @param isGui Indicates if is a GUI that is trying to reconnect.
     */
    public void reconnectGame(String id, boolean isGui) {
        client.setID(id);
        try {
            if(isGui)
                connection.send(new ReconnectMessage(id, ReconnectType.GUI));
            else
                connection.send(new ReconnectMessage(id, ReconnectType.SENDALL));
        } catch(Exception ignored){}
        putInWait();
    }

    /**
     * Sends a PingMessage to the server to ping with the specified value.
     *
     * @param n The ping value.
     * @throws Exception if an error occurs while sending the ping message.
     */
    public void ping(int n) throws Exception{
        connection.send(new PingMessage(n));
    }

    /**
     * Sets the client state to "WAIT".
     */
    public void putInWait(){
        client.setState(ClientState.WAIT);
    }

    /**
     * Establishes a TCP connection with the server.
     *
     * @throws IOException if an error occurs during the connection setup.
     */
    public void connect() throws IOException {
        Socket socket = new Socket(IP, Settings.TCPPORT);
        this.connection = new ClientConnection(socket,TCPreceiver);
        new Thread(connection).start();
    }

    public synchronized boolean reconnect() throws IOException {
        Socket socket = new Socket(IP, Settings.TCPPORT);
        this.connection = new ClientConnection(socket,TCPreceiver);
        new Thread(connection).start();
        return true;
    }

}

