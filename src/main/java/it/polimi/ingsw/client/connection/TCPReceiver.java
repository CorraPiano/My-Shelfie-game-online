package it.polimi.ingsw.client.connection;

import com.google.gson.Gson;
import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.client.*;
import it.polimi.ingsw.connection.TCPMessage;
import it.polimi.ingsw.connection.message.*;
import it.polimi.ingsw.controller.Settings;

import java.net.Socket;

/**
 * The `TCPReceiver` class is responsible for receiving and handling TCP messages from the server in the client-side application.
 * It deserializes the received messages and forwards them to the appropriate methods in the `Client` class for further processing.
 * The class uses the Gson library to deserialize JSON messages into their corresponding Java objects.
 */
public class TCPReceiver {
    private final Gson gson;
    private final Client client;
    private String name;
    private final boolean DEBUG_MESSAGE = false;


    /**
     * Constructs a new TCPReceiver instance with the specified client.
     *
     * @param client The client instance.
     */
    public TCPReceiver(Client client) {
        this.gson = new Gson();
        this.client = client;
    }

    /**
     * Receives and handles the TCP message received from the server.
     *
     * @param TCPmessage The TCP message received from the server.
     * @throws Exception if an error occurs while handling the message.
     */
    public void receive(TCPMessage TCPmessage) throws Exception {
        if (DEBUG_MESSAGE)
            System.out.println(TCPmessage.getBody());
        switch (TCPmessage.getHeader()) {
            case NOTHING -> {
                client.receiveNothing();
            }
            case ID -> {
                client.receiveID(TCPmessage.getBody());
            }
            case NAME -> {
                client.receiveName(TCPmessage.getBody());
            }
            case EXCEPTION -> {
                client.receiveException(TCPmessage.getBody());
            }
            case CREATE -> {
                CreateMessage event = gson.fromJson(TCPmessage.getBody(), CreateMessage.class);
                client.createGame(event.gameID, event.gameMode, event.numPlayers);
            }
            case ENDGAME -> {
                EndGameMessage event = gson.fromJson(TCPmessage.getBody(), EndGameMessage.class);
                client.endGame(event.name, event.cause);
            }
            case JOIN -> {
                JoinMessage event = gson.fromJson(TCPmessage.getBody(), JoinMessage.class);
                client.playerJoin(event.name);
            }
            case LASTROUND -> {
                LastRoundMessage event = gson.fromJson(TCPmessage.getBody(), LastRoundMessage.class);
                client.lastRound(event.name);
            }
            case LEAVE -> {
                LeaveMessage event = gson.fromJson(TCPmessage.getBody(), LeaveMessage.class);
                client.playerLeave(event.name);
            }
            case DISCONNECTION -> {
                DisconnectMessage event = gson.fromJson(TCPmessage.getBody(), DisconnectMessage.class);
                client.playerDisconnect(event.name);
            }
            case RECONNECTION -> {
                ReconnectMessage event = gson.fromJson(TCPmessage.getBody(), ReconnectMessage.class);
                client.playerReconnect(event.name);
            }
            case LIST -> {
                ListMessage list = gson.fromJson(TCPmessage.getBody(), ListMessage.class);
                client.receiveGamesList(list.gamesList);
            }
            case NEWTURN -> {
                NewTurnMessage event = gson.fromJson(TCPmessage.getBody(), NewTurnMessage.class);
                client.newTurn(event.name);
            }
            case ORDER -> {
                OrderMessage message = gson.fromJson(TCPmessage.getBody(), OrderMessage.class);
                client.notifyOrder(message.name, message.orderlist);
            }
            case PICK -> {
                PickMessage message = gson.fromJson(TCPmessage.getBody(), PickMessage.class);
                client.notifyPick(message.name, message.coordinates, message.item);
            }
            case PUT -> {
                PutMessage message = gson.fromJson(TCPmessage.getBody(), PutMessage.class);
                client.notifyPut(message.name, message.column);
            }
            case STARTGAME -> {
                StartGameMessage event = gson.fromJson(TCPmessage.getBody(), StartGameMessage.class);
                client.startGame();
            }
            case UNDO -> {
                UndoMessage message = gson.fromJson(TCPmessage.getBody(), UndoMessage.class);
                client.notifyUndo(message.name);
            }
            case BOARD -> {
                LocalBoard board = gson.fromJson(TCPmessage.getBody(), LocalBoard.class);
                client.updateBoard(board);
            }
            case BOOKSHELF -> {
                LocalBookshelf bookshelf = gson.fromJson(TCPmessage.getBody(), LocalBookshelf.class);
                client.updateBookshelf(bookshelf);
            }
            case COMMONGOALCARD -> {
                LocalCommonCard commonGoalCard = gson.fromJson(TCPmessage.getBody(), LocalCommonCard.class);
                client.updateCommonGoalCard(commonGoalCard);
            }
            /*case GAME -> {
                LocalGame localGame = gson.fromJson(TCPmessage.getBody(),LocalGame.class);
                client.updateGame(localGame);
            }*/
            case PLAYERLIST -> {
                LocalPlayerList localPlayerList = gson.fromJson(TCPmessage.getBody(), LocalPlayerList.class);
                client.updatePlayerList(localPlayerList);
            }
            case HAND -> {
                LocalHand hand = gson.fromJson(TCPmessage.getBody(), LocalHand.class);
                client.updateHand(hand);
            }
            case PERSONALGOALCARD -> {
                LocalPersonalCard personalGoalCard = gson.fromJson(TCPmessage.getBody(), LocalPersonalCard.class);
                client.updatePersonalGoalCard(personalGoalCard);
            }
            case PLAYER -> {
                System.out.println("player");
            }
            case CHAT -> {
                ChatMessage message = gson.fromJson(TCPmessage.getBody(), ChatMessage.class);
                client.updateChat(message);
            }
            case PING -> {
                //System.out.println("...ricevuto un ping");
            }
            case TIMER -> {
                TimerMessage message = gson.fromJson(TCPmessage.getBody(), TimerMessage.class);
                client.timer(message.seconds);
            }
            default -> {
                throw new Exception();
            }
        }
    }

    /**
     * Notifies the client about a lost connection.
     */
    public void lostConnection() {
        //client.lostConnection();
    }
}
