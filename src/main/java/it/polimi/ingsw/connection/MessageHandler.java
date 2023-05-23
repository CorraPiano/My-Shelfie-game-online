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
    public void receive(TCPMessage TCPmessage, Connection connection) throws RemoteException, InvalidIdException, GameFullException, NameAlreadyExistentException, InvalidGameIdException, UnavaiableCommandException, GameModeException, NumPlayersException, EmptyHandException, NotInGameException, WrongTurnException, InvalidColumnPutException, NotEnoughSpacePutException, WrongLengthOrderException, WrongContentOrderException, NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, OutOfBoardPickException {

        switch (TCPmessage.getHeader()) {
            case LIST -> {
                ListMessage list = new ListMessage(controller.getGameList());
                send(list,connection);

                //senderTCP.sendToConnection(list, connection);
            }
            case PICK -> {
                PickMessage pickMessage = gson.fromJson(TCPmessage.getBody(), PickMessage.class);
                controller.pickItem(pickMessage.coordinates, socketMap.getIdByConnection(connection));
            }
            case UNDO -> {
                controller.undoPick(socketMap.getIdByConnection(connection));
            }
            case ORDER -> {
                OrderMessage orderMessage = gson.fromJson(TCPmessage.getBody(), OrderMessage.class);
                controller.selectInsertOrder(orderMessage.orderlist, socketMap.getIdByConnection(connection));
            }
            case PUT -> {
                PutMessage putMessage = gson.fromJson(TCPmessage.getBody(), PutMessage.class);
                controller.putItemList(putMessage.column, socketMap.getIdByConnection(connection));
            }
            case JOIN -> {
                JoinMessage joinMessage = gson.fromJson(TCPmessage.getBody(), JoinMessage.class);
                String id = controller.addPlayer(joinMessage.name, joinMessage.gameID,connection);
                socketMap.bind(id, connection);
                //da sistemare sta roba
                controller.tryStartGame(joinMessage.gameID);
                sendID(id,connection);
                //senderTCP.send(MessageHeader.ID, id, id);
                //TCPMessage message = new TCPMessage(MessageHeader.ID,id);
                //connection.send(TCPmessage);
            }
            case CREATE -> {
                CreateMessage createMessage = gson.fromJson(TCPmessage.getBody(), CreateMessage.class);
                String id = controller.addFirstPlayer(createMessage.name, createMessage.gameMode, createMessage.numPlayers,connection);
                socketMap.bind(id, connection);
                sendID(id,connection);
                //TCPMessage message = new TCPMessage(MessageHeader.ID,id);
                //connection.send(TCPmessage);
            }
            default -> {
                throw new UnavaiableCommandException();
            }
        }
    }

    public void send(Sendable sendable, Connection conn){
        String json =  gson.toJson(sendable);
        TCPMessage TCPmessage = new TCPMessage(sendable.getHeader(),json);
        conn.send(TCPmessage);
    }

    public void sendID(String id, Connection conn){
        TCPMessage TCPmessage = new TCPMessage(MessageHeader.ID,id);
        conn.send(TCPmessage);
    }

}
