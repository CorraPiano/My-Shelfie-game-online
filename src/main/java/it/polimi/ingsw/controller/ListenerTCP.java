package it.polimi.ingsw.controller;

import it.polimi.ingsw.connection.Connection;
import it.polimi.ingsw.connection.TCPMessage;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.EventKeeper;

public class ListenerTCP extends Listener{

    Connection connection;

    public ListenerTCP(Connection connection,EventKeeper eventKeeper, String id ){
        super(eventKeeper,id);
        this.connection = connection;
    }

    public void handleSendable(Sendable sendable){
        try {
            String json = gson.toJson(sendable);
            TCPMessage TCPmessage = new TCPMessage(sendable.getHeader(), json);
            System.out.println(sendable.getHeader() + ": " + json);
            connection.send(TCPmessage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
