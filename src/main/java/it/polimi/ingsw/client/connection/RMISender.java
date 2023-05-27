package it.polimi.ingsw.client.connection;

import it.polimi.ingsw.client.connection.Sender;
import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.client.*;
import it.polimi.ingsw.controller.ClientSkeleton;
import it.polimi.ingsw.controller.ControllerSkeleton;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;

import java.util.ArrayList;

public class RMISender extends Sender {
    public final ControllerSkeleton controller;
    public final Client client;
    public String ID;

    public RMISender(ControllerSkeleton controller, Client client){
        this.controller=controller;
        this.client = client;
    }
    public void getGameList() {
        try {
            ArrayList<LocalGame> list = controller.getGameList();
            client.receiveGamesList(list);
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public void addFirstPlayer(String name, GameMode gameMode, int numPlayer) {
        try {
            ID=controller.addFirstPlayer(name, gameMode, numPlayer, client);
            client.receiveID(ID);
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public void addPlayer(String name, int gameID) {
        try {
            ID=controller.addPlayer(name,gameID,client);
            client.receiveID(ID);
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public void pickItem(Coordinates coordinates) {
        try {
            controller.pickItem(coordinates,ID);
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public void undoPick() {
        try {
            controller.undoPick(ID);
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public void putItemList(int column) {
        try {
            controller.putItemList(column,ID);
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public void selectInsertOrder(ArrayList<Integer> order)  {
        try {
            controller.selectInsertOrder(order,ID);
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public void addChatMessage(String chatMessage) {
        // chat da implementare
    }
    public void leaveGame() {
        try {
            controller.leaveGame(ID);
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
}
