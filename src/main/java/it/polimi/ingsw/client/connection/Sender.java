package it.polimi.ingsw.client.connection;

import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.controller.ClientSkeleton;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;

import java.rmi.RemoteException;
import java.util.ArrayList;

public abstract class Sender {
    public abstract void getGameList();
    public abstract void addFirstPlayer(String name, GameMode gameMode, int numPlayer);
    public abstract void addPlayer(String name, int gameID);
    public abstract void pickItem(Coordinates coordinates);
    public abstract void undoPick();
    public abstract void putItemList(int column);
    public abstract void selectInsertOrder(ArrayList<Integer> order);
    public abstract void addChatMessage(String message, String receiver);
    public abstract void addChatMessage(String message);
    public abstract void leaveGame();
    public abstract void reconnectGame(String name, int gameID);

}
