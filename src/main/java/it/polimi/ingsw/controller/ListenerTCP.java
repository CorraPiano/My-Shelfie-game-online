package it.polimi.ingsw.controller;

import it.polimi.ingsw.connection.Connection;
import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.TCPMessage;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.EventKeeper;
import it.polimi.ingsw.model.Gameplay;

public class ListenerTCP extends Listener{

    Connection connection;

    public ListenerTCP(Connection connection,  Controller controller, EventKeeper eventKeeper, String id, String name){
        super(controller, eventKeeper, id, name);
        this.connection = connection;
    }

    public void handleSendable(Sendable sendable){
        try {
            String json = gson.toJson(sendable);
            TCPMessage TCPmessage = new TCPMessage(sendable.getHeader(), json);
            connection.send(TCPmessage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ping() throws Exception {
        connection.send(new TCPMessage(MessageHeader.PING, ""));
    }
}
