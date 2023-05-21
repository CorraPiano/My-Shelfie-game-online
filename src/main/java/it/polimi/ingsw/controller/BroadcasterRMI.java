package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.SenderTCP;
import it.polimi.ingsw.connection.message.*;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class BroadcasterRMI {

    private final HashMap<String,ClientSkeleton> clientControllerMap;
    private final GameplaysHandler gameplaysHandler;
    private final SenderTCP senderTCP;

    public BroadcasterRMI(GameplaysHandler gameplaysHandler, SenderTCP senderTCP){
        clientControllerMap = new HashMap<>();
        this.gameplaysHandler = gameplaysHandler;
        this.senderTCP = senderTCP;
    }
    public void addClientController(String id, ClientSkeleton cc){
        clientControllerMap.put(id,cc);
    }

    public void newChatMessage(int gameID, String name, String chatMessage)  {
        try {
            System.out.println(chatMessage);
             /*for(String s: gameplaysHandler.getPlayersFromGameplay(gameID)){
                 if(clientControllerMap.containsKey(s))
                    clientControllerMap.get(s).newChatMessage(name, chatMessage);
                 else
                     senderTCP.newChatMessage(s,name,chatMessage);
             }*/
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendID(String id){
        try {
            if(clientControllerMap.containsKey(id))
                clientControllerMap.get(id).getID(id);
            else
                senderTCP.send(MessageHeader.ID,id,id);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    //eventi di gioco
    public void playerJoin(int gameID, String name) {
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                if(clientControllerMap.containsKey(s))
                    clientControllerMap.get(s).playerJoin(name);
                else {
                    //senderTCP.send(MessageHeader.PLAYERJOIN, name, s);
                    PlayerJoinEvent message = new PlayerJoinEvent(name);
                    senderTCP.send(message, s);
                }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void playerLeave(int gameID,String name) {
        try {
            //sistemare leave game
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID)) {
                if (clientControllerMap.containsKey(s))
                    clientControllerMap.get(s).playerLeave(name);
                else
                    senderTCP.send(MessageHeader.PLAYERLEAVE, name, s);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void startGame(int gameID,String name)  {
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID)) {
                if(clientControllerMap.containsKey(s))
                    clientControllerMap.get(s).startGame(name);
                else {
                    //senderTCP.send(MessageHeader.STARTGAME, name, s);
                    StartGameEvent message = new StartGameEvent(name);
                    senderTCP.send(message, s);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void newTurn(int gameID,String name) {
        try {
            for (String s : gameplaysHandler.getPlayersFromGameplay(gameID)){
                if (clientControllerMap.containsKey(s))
                    clientControllerMap.get(s).newTurn(name);
                else {
                    //senderTCP.send(MessageHeader.NEWTURN, name, s);
                    NewTurnEvent message = new NewTurnEvent(name);
                    senderTCP.send(message, s);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void lastRound(int gameID,String name) {
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID)) {
                if (clientControllerMap.containsKey(s))
                    clientControllerMap.get(s).lastRound(name);
                else {
                    //senderTCP.send(MessageHeader.LASTROUND, name, s);
                    LastRoundEvent message = new LastRoundEvent(name);
                    senderTCP.send(message, s);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void endGame(int gameID,String name) {
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID)) {
                if (clientControllerMap.containsKey(s))
                    clientControllerMap.get(s).endGame(name);
                else {
                    //senderTCP.send(MessageHeader.ENDGAME, name, s);
                    EndGameEvent message = new EndGameEvent(name);
                    senderTCP.send(message, s);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //notifica dei comandi di altri giocatori
    public void notifyPick(int gameID,String name, Coordinates coordinates, Item item){
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID)) {
                if (clientControllerMap.containsKey(s))
                    clientControllerMap.get(s).notifyPick(name, coordinates, item);
                else {
                    PickMessage message = new PickMessage(coordinates, name, item);
                    senderTCP.send(message, s);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void notifyUndo(int gameID,String name) {
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID)) {
                if (clientControllerMap.containsKey(s))
                    clientControllerMap.get(s).notifyUndo(name);
                else {
                    UndoMessage message = new UndoMessage(name);
                    senderTCP.send(message,s);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void notifyOrder(int gameID,String name, ArrayList<Integer> list) {
        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID)) {
                if (clientControllerMap.containsKey(s))
                    clientControllerMap.get(s).notifyOrder(name, list);
                else {
                    OrderMessage message = new OrderMessage(list,name);
                    senderTCP.send(message, s);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void notifyPut(int gameID,String name, int column){
        try {
            for (String s : gameplaysHandler.getPlayersFromGameplay(gameID)) {
                if (clientControllerMap.containsKey(s))
                    clientControllerMap.get(s).notifyPut(name, column);
                else {
                    PutMessage message = new PutMessage(column, name);
                    senderTCP.send(message, s);
                }
            }
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
                if(clientControllerMap.containsKey(s))
                    clientControllerMap.get(s).updateBoard(localBoard);
                else
                    senderTCP.send(localBoard,s);
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
                if(clientControllerMap.containsKey(s))
                    clientControllerMap.get(s).updateBookshelf(localBookshelf);
                else
                    senderTCP.send(localBookshelf,s);
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
                if(clientControllerMap.containsKey(s))
                    clientControllerMap.get(s).updateHand(localHand);
                else
                    senderTCP.send(localHand,s);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void updateGame(int gameID, Gameplay gameplay) {
        ArrayList<LocalPlayer> localPlayerList = new ArrayList<LocalPlayer>();
        for(Player p: gameplay.getPlayerList()){
            LocalPlayer lp = new LocalPlayer(p.getName(), p.getFirstPlayerSeat(), p.getEndGameToken(), p.getToken1(), p.getToken2(), p.getPoints());
            localPlayerList.add(lp);
        }
        LocalGame localGame = new LocalGame(gameplay.getGameMode(), gameplay.getGameID(), gameplay.getNumPlayers(),gameplay.getCurrentPlayers(),gameplay.getGameState(),localPlayerList);

        try {
            for(String s: gameplaysHandler.getPlayersFromGameplay(gameID))
                if(clientControllerMap.containsKey(s))
                    clientControllerMap.get(s).updateGame(localGame);
                else
                    senderTCP.send(localGame,s);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void updateCommonGoalCard(int gameID,CommonGoalCard commonGoalCard) {

        LocalCommonCard localCommonCard = new LocalCommonCard(commonGoalCard.getType(),commonGoalCard.showToken());
        try {
            for (String s : gameplaysHandler.getPlayersFromGameplay(gameID))
                if(clientControllerMap.containsKey(s))
                    clientControllerMap.get(s).updateCommonGoalCard(localCommonCard);
                else
                    senderTCP.send(localCommonCard,s);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendPersonalGoalCard(PersonalGoalCard personalGoalCard) {
        try {
            if(clientControllerMap.containsKey(personalGoalCard.getID()))
                clientControllerMap.get(personalGoalCard.getID()).updatePersonalGoalCard(personalGoalCard.getCard());
            else {
                //senderTCP.send(personalGoalCard.getCard(),personalGoalCard.getID());
                LocalPersonalCard message = new LocalPersonalCard(personalGoalCard.getCard().getCardMatrix());
                senderTCP.send(message, personalGoalCard.getID());
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
