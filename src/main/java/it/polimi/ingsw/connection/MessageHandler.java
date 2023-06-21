package it.polimi.ingsw.connection;

import com.google.gson.Gson;
import it.polimi.ingsw.connection.message.*;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exception.*;

import java.rmi.RemoteException;

public class MessageHandler {

    private final SocketMap socketMap;
    private final Controller controller;
    //private final SenderTCP senderTCP;
    private final Gson gson;
    public MessageHandler(Controller controller, SocketMap socketMap){
        this.controller=controller;
        this.socketMap=socketMap;
        //this.senderTCP=senderTCP;
        this.gson=new Gson();
    }
    public void receive(TCPMessage TCPmessage, Connection connection) throws RemoteException, InvalidIdException, GameFullException, NameAlreadyExistentException, InvalidGameIdException, UnavaiableCommandException, GameModeException, NumPlayersException, EmptyHandException, NotInGameException, WrongTurnException, InvalidColumnPutException, NotEnoughSpacePutException, WrongLengthOrderException, WrongContentOrderException, NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, OutOfBoardPickException, InvalidNameException, GameFinishedException {

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
                String name = controller.reconnect(reconnectMessage.id,connection);
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
            }
            default -> {
                throw new UnavaiableCommandException();
            }
        }
    }

    private void send(Sendable sendable, Connection conn){
        String json =  gson.toJson(sendable);
        TCPMessage TCPmessage = new TCPMessage(sendable.getHeader(),json);
        try {
            conn.send(TCPmessage);
        } catch(Exception e){}
    }

    private void sendID(String id, Connection conn){
        TCPMessage TCPmessage = new TCPMessage(MessageHeader.ID,id);
        try {
            conn.send(TCPmessage);
        } catch(Exception e){}
    }
    private void sendName(String name, Connection conn){
        TCPMessage TCPmessage = new TCPMessage(MessageHeader.NAME,name);
        try {
            conn.send(TCPmessage);
        } catch(Exception e){}
    }
    private void sendNothing(Connection conn){
        TCPMessage TCPmessage = new TCPMessage(MessageHeader.NOTHING,"");
        try {
            conn.send(TCPmessage);
        } catch(Exception e){}
    }


}
