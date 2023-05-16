package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.localModel.LocalBoard;
import it.polimi.ingsw.client.localModel.LocalBookshelf;
import it.polimi.ingsw.client.localModel.LocalHand;
import it.polimi.ingsw.client.localModel.LocalPlayer;
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

    public void notifyLeave(int gameID,String name) {
        try {
            //for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
            //    clientControllerMap.get(s).notifyLeave(name);
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
        LocalBoard localBoard = new LocalBoard(board.getLivingRoom());

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
        LocalBookshelf localBookshelf = new LocalBookshelf(name,matrix);

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
        LocalHand localHand = new LocalHand(array,hand.getSize());

        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).updateHand(localHand);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void updatePlayerList(int gameID,ArrayList<Player> playerList) {
        ArrayList<LocalPlayer> localPlayerList = new ArrayList<LocalPlayer>();
        for(Player p: playerList){
            LocalPlayer lp = new LocalPlayer(p.getName(), p.getFirstPlayerSeat(), p.getEndGameToken(), p.getToken1(), p.getToken2(), p.getPoints());
            localPlayerList.add(lp);
        }

        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).updatePlayerList(localPlayerList);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void updateCommonGoalCard(int gameID,CommonGoalCard commonGoalCard) {
        try {
            for (String s : gameplaysHandler.getPlayersFromGameplay(gameID))
                clientControllerMap.get(s).updateCommonGoalCard(commonGoalCard);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendPersonalGoalCard(int gameID,PersonalGoalCard personalGoalCard) {
        try {
            clientControllerMap.get(personalGoalCard.getID()).updatePersonalGoalCard(personalGoalCard.getCard());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
