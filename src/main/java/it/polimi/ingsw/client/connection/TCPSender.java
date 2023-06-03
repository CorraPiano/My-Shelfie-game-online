package it.polimi.ingsw.client.connection;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.connection.message.*;

import java.util.ArrayList;

public class TCPSender extends Sender {
    private final ClientConnection connection;
    private final Client client;

    public TCPSender(ClientConnection connection, Client client){
        this.connection = connection;
        this.client = client;
    }
    public void getGameList() {
        connection.send(new ListMessage());
        client.putInWait();
    }
    public void addFirstPlayer(String name, GameMode gameMode, int numPlayer) {
        client.setName(name);
        connection.send(new CreateMessage(name,gameMode,numPlayer));
        client.putInWait();
    }
    public void addPlayer(String name, int gameID)  {
        client.setName(name);
        connection.send(new JoinMessage(name,gameID));
        client.putInWait();
    }
    public void pickItem(Coordinates coordinates)  {
        connection.send(new PickMessage(coordinates));
        client.putInWait();
    }
    public void undoPick() {
        connection.send(new UndoMessage());
        client.putInWait();
    }
    public void putItemList(int column)  {
        connection.send(new PutMessage(column));
        client.putInWait();
    }
    public void selectInsertOrder(ArrayList<Integer> order) {
        connection.send(new OrderMessage(order));
        client.putInWait();
    }
    public void addChatMessage(String message, String receiver) {
        connection.send(new ChatMessage(client.getName(), message, receiver));
        client.putInWait();
    }
    public void addChatMessage(String message){
        connection.send(new ChatMessage(client.getName(), message));
        client.putInWait();
    }
    public void leaveGame() {
        connection.send(new LeaveMessage());
        client.putInWait();
    }
    public void reconnectGame(String name,int gameID) {
        String id = name+"_"+gameID;
        connection.send(new ReconnectMessage(id));
        client.setName(name);
        client.setId(id);
        client.putInWait();
    }

}

