package it.polimi.ingsw.client.connection;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientPhase;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.controller.Settings;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.connection.message.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class TCPSender extends Sender {
    private ClientConnection connection;
    private final Client client;

    private final String IP;

    public TCPSender(String IP, Client client) throws Exception {
        this.IP = IP;
        this.client = client;
        connect();
        ConnectionChecker connectionChecker = new ConnectionChecker(this,client);
        new Thread(connectionChecker).start();
    }
    public TCPSender(ClientConnection connection, Client client){
        this.connection = connection;
        this.client = client;
        this.IP = null;
    }
    public void getGameList() {
        try {
            connection.send(new ListMessage());
        } catch(Exception e){

        }
        putInWait();
    }
    public void addFirstPlayer(String name, GameMode gameMode, int numPlayer) {
        client.setName(name);
        try {
            connection.send(new CreateMessage(name, gameMode, numPlayer));
        } catch(Exception e){

        }
        putInWait();
    }
    public void addPlayer(String name, int gameID)  {
        client.setName(name);
        try {
            connection.send(new JoinMessage(name,gameID));
        } catch(Exception e){

        }
        putInWait();
    }
    public void pickItem(Coordinates coordinates)  {
        try {
            connection.send(new PickMessage(coordinates));
        } catch(Exception e){

        }
        putInWait();
    }
    public void undoPick() {
        try{
            connection.send(new UndoMessage());
        } catch(Exception e){

        }
        putInWait();
    }
    public void putItemList(int column)  {
        try{
            connection.send(new PutMessage(column));
        } catch(Exception e){

        }
        putInWait();
    }
    public void selectInsertOrder(ArrayList<Integer> order) {
        try{
            connection.send(new OrderMessage(order));
        } catch(Exception e){

        }
        putInWait();
    }
    public void addChatMessage(String message, String receiver) {
        try{
            connection.send(new ChatMessage(client.getName(), message, receiver));
        } catch(Exception e){

        }
        putInWait();
    }
    public void addChatMessage(String message){
        try{
            connection.send(new ChatMessage(client.getName(), message));
        } catch(Exception e){

        }
        putInWait();
    }
    public void leaveGame() {
        try{
            connection.send(new LeaveMessage());
        } catch(Exception e){

        }
        putInWait();
        client.leaveGame();
    }
    public void reconnectGame(String id) {
        client.setID(id);
        try {
            connection.send(new ReconnectMessage(id));
        } catch(Exception e){

        }
        putInWait();
    }

    public void ping(int n) throws Exception{
        connection.send(new PingMessage(n));
    }

    public void putInWait(){
        client.setState(ClientState.WAIT);
    }

    public void connect() throws IOException {
        TCPReceiver TCPreceiver = new TCPReceiver(client);
        Socket socket = new Socket(IP, Settings.TCPPORT);
        this.connection = new ClientConnection(socket,TCPreceiver);
        new Thread(connection).start();
    }

}

