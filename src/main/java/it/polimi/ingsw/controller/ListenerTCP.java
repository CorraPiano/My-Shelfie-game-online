package it.polimi.ingsw.controller;

import it.polimi.ingsw.connection.Connection;
import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.TCPMessage;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.EventKeeper;
import it.polimi.ingsw.model.Gameplay;

/**
 * The ListenerTCP class represents a listener for TCP-based communication between the server and the clients.
 * It handles incoming messages by sending them over the TCP connection to the client.
 */
public class ListenerTCP extends Listener{

    Connection connection;

    /**
     * Constructs a new ListenerTCP instance.
     *
     * @param connection    the Connection object representing the TCP connection
     * @param eventKeeper   the EventKeeper object storing the events
     * @param id            the unique identifier of the player
     * @param listenableNum  the number associated with the current connection of the player
     */
    public ListenerTCP(Connection connection, EventKeeper eventKeeper, String id, int listenableNum){
        super(eventKeeper, id, listenableNum);
        this.connection = connection;
    }

    /**
     * Handles the received sendable object by converting it to JSON and sending it over the TCP connection to the client.
     *
     * @param sendable the Sendable object representing the received message
     */
    public void handleSendable(Sendable sendable){
        try {
            String json = gson.toJson(sendable);
            TCPMessage TCPmessage = new TCPMessage(sendable.getHeader(), json);
            connection.send(TCPmessage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Sends a ping message over the TCP connection to the client to check the connection.
     *
     * @throws Exception if an error occurs while sending the ping message
     */
    public void ping() throws Exception {
        connection.send(new TCPMessage(MessageHeader.PING, ""));
    }
}
