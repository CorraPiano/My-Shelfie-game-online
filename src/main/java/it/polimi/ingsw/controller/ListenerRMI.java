package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.*;
import it.polimi.ingsw.model.EventKeeper;
import it.polimi.ingsw.model.Gameplay;

import java.rmi.RemoteException;

/**
 * The ListenerRMI class represents a listener for RMI-based communication between the server and the clients.
 * It handles incoming messages and delegates the processing to the appropriate methods in the client.
 */
public class ListenerRMI extends Listener{
    ClientSkeleton client;

    /**
 * Constructs a new ListenerRMI instance.
 *
 * @param client        the ClientSkeleton object representing the RMI client
 * @param controller    the Controller object managing the game
 * @param eventKeeper   the EventKeeper object storing the events
 * @param id            the unique identifier of the player
 * @param name          the name of the player
 */
    public ListenerRMI(ClientSkeleton client, Controller controller, EventKeeper eventKeeper, String id, String name){
                super(controller, eventKeeper, id, name);
                this.client = client;
        }

    /**
         * Handles the received sendable object by delegating the processing to the appropriate methods in the client.
         *
         * @param sendable the Sendable object representing the received message
         */
    public void handleSendable(Sendable sendable){
                MessageHeader header = sendable.getHeader();
                try {
                        switch(header){
                                case TIMER -> {
                                        TimerMessage message = (TimerMessage) sendable;
                                        client.timer(message.seconds);
                                }
                                case CHAT -> {
                                        ChatMessage message = (ChatMessage) sendable;
                                        client.updateChat(message);
                                }
                                case CREATE -> {
                                        CreateMessage message = (CreateMessage) sendable;
                                        client.createGame(message.gameID,message.gameMode,message.numPlayers);
                                }
                                case JOIN ->{
                                        JoinMessage message = (JoinMessage) sendable;
                                        System.out.println("TEST ----------------------------------------");
                                        client.playerJoin(message.name);
                                        System.out.println("TEST join------------------------------------");
                                }
                                case LEAVE -> {
                                        LeaveMessage message = (LeaveMessage) sendable;
                                        client.playerLeave(message.name);
                                }
                                case DISCONNECTION -> {
                                        DisconnectMessage message = (DisconnectMessage) sendable;
                                        client.playerDisconnect(message.name);
                                }
                                case RECONNECTION -> {
                                        ReconnectMessage message = (ReconnectMessage) sendable;
                                        client.playerReconnect(message.name);
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
                                        client.startGame();
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
                                        client.endGame(message.name,message.cause);
                                }
                                case BOOKSHELF -> {
                                        client.updateBookshelf((LocalBookshelf)sendable);
                                }
                                case BOARD -> {
                                        client.updateBoard((LocalBoard)sendable);
                                }
                                /*case GAME -> {
                                        client.updateGame((LocalGame)sendable);
                                }*/
                                case PLAYERLIST -> {
                                        client.updatePlayerList((LocalPlayerList)sendable);
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

    /**
         * Sends a ping message to the client to check the connection.
         *
         * @throws Exception if an error occurs while sending the ping message
         */
    public void ping() throws Exception {
        client.ping(0);
    }
}
