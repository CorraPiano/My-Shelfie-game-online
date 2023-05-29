package it.polimi.ingsw.client.connection;

import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.client.*;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.controller.ControllerSkeleton;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;
import java.util.ArrayList;

public class RMISender extends Sender {
    private final ControllerSkeleton controller;
    private final Client client;

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
            client.setName(name);
            String ID = controller.addFirstPlayer(name, gameMode, numPlayer, client);
            client.receiveID(ID);
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public void addPlayer(String name, int gameID) {
        try {
            client.setName(name);
            String ID = controller.addPlayer(name,gameID,client);
            client.receiveID(ID);
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public void pickItem(Coordinates coordinates) {
        try {
            controller.pickItem(coordinates,client.getID());
            client.receiveNothing();
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public void undoPick() {
        try {
            controller.undoPick(client.getID());
            client.receiveNothing();
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public void putItemList(int column) {
        try {
            controller.putItemList(column,client.getID());
            client.receiveNothing();
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public void selectInsertOrder(ArrayList<Integer> order)  {
        try {
            controller.selectInsertOrder(order,client.getID());
            client.receiveNothing();
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public void addChatMessage(String message, String receiver) {
        try{
            controller.addChatMessage(new ChatMessage(client.getName(), message, receiver), client.getID());
            client.receiveNothing();
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public void addChatMessage(String message) {
        try{
            controller.addChatMessage(new ChatMessage(client.getName(), message), client.getID());
            client.receiveNothing();
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
    public void leaveGame() {
        try {
            controller.leaveGame(client.getID());
            client.receiveNothing();
        } catch (Exception e){
            client.receiveException(e.toString());
        }
    }
}
