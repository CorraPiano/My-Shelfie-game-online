package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.*;
import it.polimi.ingsw.model.EventKeeper;

public class ListenerRMI extends Listener{
        ClientSkeleton client;

        public ListenerRMI(ClientSkeleton client, EventKeeper eventKeeper, String id ){
                super(eventKeeper,id);
                this.client = client;
        }

        public void handleSendable(Sendable sendable){
                MessageHeader header = sendable.getHeader();
                try {
                        switch(header){
                                case CHAT -> {
                                        ChatMessage message = (ChatMessage) sendable;
                                        client.updateChat(message);
                                }
                                case CREATE -> {
                                        CreateMessage message = (CreateMessage) sendable;
                                        client.createGame(message.gameID);
                                }
                                case JOIN ->{
                                        JoinMessage message = (JoinMessage) sendable;
                                        client.playerJoin(message.name);
                                }
                                case LEAVE -> {
                                        LeaveMessage message = (LeaveMessage) sendable;
                                        client.playerLeave(message.name);
                                }
                                case PICK -> {
                                        PickMessage message = (PickMessage) sendable;
                                        client.notifyPick(message.name,message.coordinates,message.item);
                                }
                                case UNDO -> {
                                        UndoMessage message = (UndoMessage) sendable;
                                        client.notifyUndo(message.name);
                                }
                                case ORDER -> {
                                        OrderMessage message = (OrderMessage) sendable;
                                        client.notifyOrder(message.name,message.orderlist);
                                }
                                case PUT -> {
                                        PutMessage message = (PutMessage) sendable;
                                        client.notifyPut(message.name,message.column);
                                }
                                case STARTGAME -> {
                                        StartGameMessage message = (StartGameMessage) sendable;
                                        client.startGame(message.name);
                                }
                                case NEWTURN -> {
                                        NewTurnMessage message = (NewTurnMessage) sendable;
                                        client.newTurn(message.name);
                                }
                                case LASTROUND -> {
                                        LastRoundMessage message = (LastRoundMessage) sendable;
                                        client.lastRound(message.name);
                                }
                                case ENDGAME -> {
                                        EndGameMessage message = (EndGameMessage) sendable;
                                        client.endGame(message.name);
                                }
                                case BOOKSHELF -> {
                                        client.updateBookshelf((LocalBookshelf)sendable);
                                }
                                case BOARD -> {
                                        client.updateBoard((LocalBoard)sendable);
                                }
                                case GAME -> {
                                        client.updateGame((LocalGame)sendable);
                                }
                                case COMMONGOALCARD -> {
                                        client.updateCommonGoalCard((LocalCommonCard)sendable);
                                }
                                case PERSONALGOALCARD -> {
                                        client.updatePersonalGoalCard((LocalPersonalCard)sendable);
                                }
                                case HAND -> {
                                        client.updateHand((LocalHand)sendable);
                                }

                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}
