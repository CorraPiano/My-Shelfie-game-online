package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.localModel.localBoard;
import it.polimi.ingsw.client.localModel.localBookshelf;
import it.polimi.ingsw.client.localModel.localHand;
import it.polimi.ingsw.client.localModel.localPlayer;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class BroadcasterRMI {

    private final HashMap<String,ClientSkeleton> clientControllerMap;
    private final GameplaysHandler gameplaysHandler;

    public BroadcasterRMI(GameplaysHandler gameplaysHandler){
        clientControllerMap = new HashMap<>();
        this.gameplaysHandler = gameplaysHandler;
    }
    public void addClientController(String id, ClientSkeleton cc){
        clientControllerMap.put(id,cc);
    }
    public void newChatMessage(int gameID, String name, String chatMessage)  {
        try {
             for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).newChatMessage(name, chatMessage);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //eventi di gioco
    public void playerJoin(int gameID, String name) {
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).playerJoin(name);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void playerLeave(int gameID, String name)  {
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).playerLeave(name);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void startGame(int gameID,String name)  {
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).startGame(name);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void newTurn(int gameID,String name) {
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).newTurn(name);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void lastRound(int gameID,String name) {
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).lastRound(name);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void endGame(int gameID,String name) {
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).endGame(name);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //notifica dei comandi di altri giocatori
    public void notifyPick(int gameID,String name, Coordinates coordinates, Item item){
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).notifyPick(name,coordinates, item);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void notifyUndo(int gameID,String name) {
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).notifyUndo(name);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void notifyOrder(int gameID,String name, ArrayList<Integer> list) {
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).notifyOrder(name,list);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void notifyPut(int gameID,String name, int column){
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).notifyPut(name,column);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //weight listener
    public void updateBoard(int gameID, Board board){
        localBoard localBoard = new localBoard(board.getLivingRoom());

        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).updateBoard(localBoard);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void updateBookshelf(int gameID,Bookshelf bookshelf) {
        Item[][] matrix = bookshelf.getLibrary();
        String name = bookshelf.getName();
        localBookshelf localBookshelf = new localBookshelf(name,matrix);

        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).updateBookshelf(localBookshelf);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void updateHand(int gameID,Hand hand)  {
        Item[] array = new Item[hand.getSize()];
        for(int i=0;i<hand.getSize();i++)
            array[i]=hand.getHand().get(i);
        localHand localHand = new localHand(array,hand.getSize());

        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
            clientControllerMap.get(s).updateHand(localHand);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void updatePlayerList(int gameID,ArrayList<Player> playerList) {
        ArrayList<localPlayer> localPlayerList = new ArrayList<localPlayer>();
        for(Player p: playerList){
            localPlayerList.add(new localPlayer(p.getName(),p.getFirstPlayerSeat(),p.getPoints()));
        }

        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).updatePlayerList(localPlayerList);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void updateCommonGoalCard(int gameID,ArrayList<CommonGoalCard> commonGoalCardslist) {
        try {
            for (String s : gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).updateCommonGoalCard(commonGoalCardslist);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendPersonalGoalCard(int id,PersonalGoalCard personalGoalCard) {
        try {
            clientControllerMap.get(id).sendPersonalGoalCard(personalGoalCard);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
