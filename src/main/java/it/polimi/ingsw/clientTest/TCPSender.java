package it.polimi.ingsw.clientTest;

import com.google.gson.Gson;
import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.connection.TCPMessage;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.connection.message.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class TCPSender extends Sender{
    private final ClientConnection connection;

    public TCPSender(ClientConnection connection){
        this.connection=connection;
    }
    public void getGameList() {
        connection.send(new ListMessage());
    }
    public void addFirstPlayer(String name, GameMode gameMode, int numPlayer)  {
        connection.send(new CreateMessage(name,gameMode,numPlayer));
        //si può sfruttare una blackboard per i ritorni
    }
    public void addPlayer(String name, int gameID)  {
        connection.send(new JoinMessage(name,gameID));
        //si può sfruttare una blackboard per i ritorni
    }
    public void pickItem(Coordinates coordinates)  {
        connection.send(new PickMessage(coordinates));
    }
    public void undoPick() {
        connection.send(new UndoMessage());
    }
    public void putItemList(int column)  {
        connection.send(new PutMessage(column));
    }
    public void selectInsertOrder(ArrayList<Integer> order) {
        connection.send(new OrderMessage(order));
    }
    public void addChatMessage(String chatMessage) {
        // chat da implementare
    }
    public void leaveGame() {
        connection.send(new LeaveMessage());
    }
}

