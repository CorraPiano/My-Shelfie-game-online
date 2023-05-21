package it.polimi.ingsw.connection;

import com.google.gson.Gson;
import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.connection.message.*;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.Coordinates;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class MessageHandler {

    private final SocketMap socketMap;
    private final Controller controller;
    private final SenderTCP senderTCP;
    private final Gson gson;
    public MessageHandler(Controller controller, SocketMap socketMap, SenderTCP senderTCP){
        this.controller=controller;
        this.socketMap=socketMap;
        this.senderTCP=senderTCP;
        this.gson=new Gson();
    }
    public void receive(TCPMessage TCPmessage, Connection connection) throws RemoteException, InvalidIdException, GameFullException, NameAlreadyExistentException, InvalidGameIdException, UnavaiableCommandException, GameModeException, NumPlayersException, EmptyHandException, NotInGameException, WrongTurnException, InvalidColumnPutException, NotEnoughSpacePutException, WrongLengthOrderException, WrongContentOrderException, NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, OutOfBoardPickException {

        switch (TCPmessage.getHeader()) {
            case LIST -> {
                GamesList gamesList = new GamesList(controller.getGameList());
                senderTCP.sendToConnection(gamesList, connection);
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
                String id = controller.addPlayer(joinMessage.name, joinMessage.gameID);
                socketMap.bind(id, connection);
                controller.tryStartGame(joinMessage.gameID);
                senderTCP.send(MessageHeader.ID, id, id);
            }
            case CREATE -> {
                CreateMessage createMessage = gson.fromJson(TCPmessage.getBody(), CreateMessage.class);
                String id = controller.addFirstPlayer(createMessage.name, createMessage.gameMode, createMessage.numPlayers);
                socketMap.bind(id, connection);
                senderTCP.send(MessageHeader.ID, id, id);
            }
            default -> {
                throw new UnavaiableCommandException();
            }
        }
    }

}
