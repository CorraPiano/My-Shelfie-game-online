package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.*;
import it.polimi.ingsw.model.EventKeeper;
import it.polimi.ingsw.model.Gameplay;

import java.rmi.RemoteException;

/**
 * The abstract Listener class represents a thread that listens for incoming messages from the clients.
 * It handles the communication between the clients and the server, processing the received messages.
 */
public abstract class Listener implements Runnable {

    private Boolean active;
    private final EventKeeper eventKeeper;
    private int cursor;
    private int personalCursor;
    protected final Gson gson;
    private final String id;
    //private final Gameplay gameplay;
    private final Controller controller;

    private final String name;

    /**
     * Constructs a new Listener instance.
     *
     * @param controller   the Controller object managing the game
     * @param eventKeeper  the EventKeeper object storing the events
     * @param id           the unique identifier of the player
     * @param name         the name of the player
     */
    public Listener(Controller controller, EventKeeper eventKeeper, String id, String name) {
        //this.eventKeeper = gameplay.getEventKeeper();
        //this.gameplay = gameplay;
        this.controller = controller;
        this.eventKeeper = eventKeeper;
        this.name=name;
        this.id=id;
        cursor=0;
        personalCursor=0;
        gson = new Gson();
    }

    /**
     * Disconnects the listener from the server.
     */
    public synchronized void disconnect(){
        //active=false;
    }

    public synchronized void reset(){
        eventKeeper.resetOffset(id);
    }

    /**
     * Runs the listener thread.
     * It listens for incoming messages and handles them accordingly.
     */
    public void run() {
        //eventKeeper.resetOffset(id);
        System.out.println("thread di " + id + " avviato");
        try {
            while (true) {
                synchronized (eventKeeper) {
                    if (eventKeeper.isPresentPersonal(id, personalCursor)) {
                        Sendable sendable = eventKeeper.getListenablePersonal(id, personalCursor);
                        if(sendable.getHeader().equals(MessageHeader.LEAVE) && ((LeaveMessage)sendable).name.equals(name))
                            break;
                        this.handleSendable(sendable);
                        eventKeeper.nextpos(id);
                        if(sendable.getHeader().equals(MessageHeader.ENDGAME))
                            break;
                        personalCursor++;
                    } else {
                        ping();
                        eventKeeper.wait(5000);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            try {
                controller.disconnect(id);
            } catch (Exception ee){

            }
        }
        System.out.println("thread di "+ id +" terminato");
    }

    /**
     * Handles the received sendable object.
     *
     * @param sendable the Sendable object representing the received message
     */
    abstract void handleSendable(Sendable sendable);

    /**
     * Sends a ping message to the client to check the connection.
     *
     * @throws Exception if an error occurs while sending the ping message
     */
    abstract void ping() throws Exception;

}
