package it.polimi.ingsw.connection;

import com.google.gson.Gson;
import it.polimi.ingsw.connection.message.*;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exception.*;

import java.rmi.RemoteException;

/**
 * Handles incoming messages and invokes the appropriate methods in the controller based on the message type.
 */
public class MessageHandler {

    private final Controller controller;
    private final SocketMap socketMap;
    //private final SenderTCP senderTCP;
    private final Gson gson;


    /**
     * Constructs a new MessageHandler object.
     *
     * @param controller The controller responsible for handling game logic.
     * @param socketMap  The map of sockets and their corresponding identifiers.
     */
    public MessageHandler(Controller controller, SocketMap socketMap){
        this.controller=controller;
        this.socketMap=socketMap;
        //this.senderTCP=senderTCP;
        this.gson=new Gson();
    }

    /**
     * Processes the received TCP message and invokes the corresponding controller methods based on the message type.
     *
     * @param TCPmessage  The TCP message received.
     * @param connection  The connection from which the message was received.
     * @throws RemoteException                 If a remote communication error occurs.
     * @throws InvalidIdException               If an invalid player identifier is encountered.
     * @throws GameFullException                If the game is already full.
     * @throws NameAlreadyExistentException     If a player with the same name already exists.
     * @throws InvalidGameIdException           If an invalid game identifier is encountered.
     * @throws UnavaiableCommandException       If an unavailable command is encountered.
     * @throws GameModeException                If an invalid game mode is encountered.
     * @throws NumPlayersException              If an invalid number of players is encountered.
     * @throws EmptyHandException               If a player's hand is empty.
     * @throws NotInGameException               If a player is not in the game.
     * @throws WrongTurnException               If it is not the player's turn.
     * @throws InvalidColumnPutException        If an invalid column for placing an item is encountered.
     * @throws NotEnoughSpacePutException       If there is not enough space in the selected column to place the item.
     * @throws WrongLengthOrderException        If an order with an invalid length is encountered.
     * @throws WrongContentOrderException       If an order with invalid content is encountered.
     * @throws NotLinearPickException           If a non-linear pick is encountered in a linear pick phase.
     * @throws LimitReachedPickException        If the pick limit has been reached.
     * @throws NotCatchablePickException        If a non-catchable item is encountered.
     * @throws EmptySlotPickException           If an empty slot is encountered during picking.
     * @throws OutOfBoardPickException          If an out-of-board slot is encountered during picking.
     * @throws InvalidNameException             If an invalid player name is encountered.
     * @throws GameFinishedException            If the game has already finished.
     * @throws AlreadyConnectedException        If a player is already connected.
     * @throws GameLeftException                If a player has already left the game.
     */
    public void receive(TCPMessage TCPmessage, Connection connection) throws RemoteException, InvalidIdException, GameFullException, NameAlreadyExistentException, InvalidGameIdException, UnavaiableCommandException, GameModeException, NumPlayersException, EmptyHandException, NotInGameException, WrongTurnException, InvalidColumnPutException, NotEnoughSpacePutException, WrongLengthOrderException, WrongContentOrderException, NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, OutOfBoardPickException, InvalidNameException, GameFinishedException, AlreadyConnectedException, GameLeftException {

        switch (TCPmessage.getHeader()) {
            case LIST -> {
                ListMessage list = new ListMessage(controller.getGameList());
                send(list,connection);
            }
            case PICK -> {
                PickMessage pickMessage = gson.fromJson(TCPmessage.getBody(), PickMessage.class);
                controller.pickItem(pickMessage.coordinates, socketMap.getIdByConnection(connection));
                sendNothing(connection);
            }
            case UNDO -> {
                controller.undoPick(socketMap.getIdByConnection(connection));
                sendNothing(connection);
            }
            case ORDER -> {
                OrderMessage orderMessage = gson.fromJson(TCPmessage.getBody(), OrderMessage.class);
                controller.selectInsertOrder(orderMessage.orderlist, socketMap.getIdByConnection(connection));
                sendNothing(connection);
            }
            case PUT -> {
                PutMessage putMessage = gson.fromJson(TCPmessage.getBody(), PutMessage.class);
                controller.putItemList(putMessage.column, socketMap.getIdByConnection(connection));
                sendNothing(connection);
            }
            case JOIN -> {
                JoinMessage joinMessage = gson.fromJson(TCPmessage.getBody(), JoinMessage.class);
                String id = controller.addPlayer(joinMessage.name, joinMessage.gameID,connection);
                socketMap.bind(id, connection);
                sendID(id,connection);
            }
            case CREATE -> {
                CreateMessage createMessage = gson.fromJson(TCPmessage.getBody(), CreateMessage.class);
                String id = controller.addFirstPlayer(createMessage.name, createMessage.gameMode, createMessage.numPlayers,connection);
                socketMap.bind(id, connection);
                sendID(id,connection);
            }
            case RECONNECTION -> {
                ReconnectMessage reconnectMessage = gson.fromJson(TCPmessage.getBody(), ReconnectMessage.class);
                String name = controller.reconnect(reconnectMessage.id,connection,reconnectMessage.reset);
                socketMap.bind(reconnectMessage.id, connection);
                sendName(name,connection);
            }
            case LEAVE -> {
                controller.leaveGame(socketMap.getIdByConnection(connection));
                sendNothing(connection);
            }
            case CHAT -> {
                ChatMessage chatMessage = gson.fromJson(TCPmessage.getBody(), ChatMessage.class);
                controller.addChatMessage(chatMessage,socketMap.getIdByConnection(connection) );
                sendNothing(connection);
            }
            case PING -> {
                PingMessage pingMessage = gson.fromJson(TCPmessage.getBody(), PingMessage.class);
                try {
                    controller.ping(pingMessage.n, socketMap.getIdByConnection(connection));
                } catch(Exception e){};
                sendPing(pingMessage.n,connection);
            }
            default -> {
                throw new UnavaiableCommandException();
            }
        }
    }

    private void sendPing(int n, Connection conn){
        try {
            conn.send(new TCPMessage(MessageHeader.PING, gson.toJson(new PingMessage(n))));
        } catch(Exception ignored){};
    }


    /**
     * Sends a TCP message to the specified connection containing the serialized Sendable object.
     *
     * @param sendable The Sendable object to be sent.
     * @param conn     The connection to send the message to.
     */
    private void send(Sendable sendable, Connection conn){
        String json =  gson.toJson(sendable);
        TCPMessage TCPmessage = new TCPMessage(sendable.getHeader(),json);
        try {
            conn.send(TCPmessage);
        } catch(Exception e){}
    }

    /**
     * Sends an ID message to the specified connection containing the player identifier.
     *
     * @param id   The player identifier.
     * @param conn The connection to send the message to.
     */
    private void sendID(String id, Connection conn){
        TCPMessage TCPmessage = new TCPMessage(MessageHeader.ID,id);
        try {
            conn.send(TCPmessage);
        } catch(Exception e){}
    }

    /**
     * Sends a name message to the specified connection containing the player name.
     *
     * @param name The player name.
     * @param conn The connection to send the message to.
     */
    private void sendName(String name, Connection conn){
        TCPMessage TCPmessage = new TCPMessage(MessageHeader.NAME,name);
        try {
            conn.send(TCPmessage);
        } catch(Exception e){}
    }

    /**
     * Sends an empty message to the specified connection.
     *
     * @param conn The connection to send the message to.
     */
    private void sendNothing(Connection conn){
        TCPMessage TCPmessage = new TCPMessage(MessageHeader.NOTHING,"");
        try {
            conn.send(TCPmessage);
        } catch(Exception e){}
    }


}
