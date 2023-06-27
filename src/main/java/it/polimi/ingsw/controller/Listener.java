package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.*;
import it.polimi.ingsw.exception.GameFinishedException;
import it.polimi.ingsw.exception.InvalidIdException;
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
        this.controller = controller;
        this.eventKeeper = eventKeeper;
        this.name=name;
        this.id=id;
        gson = new Gson();
    }


    public synchronized void reset(){
        synchronized (eventKeeper) {
            //eventKeeper.fixOffset(id,true,true);
            eventKeeper.fixOffset(id,true,true);
        }
    }

    /**
     * Runs the listener thread.
     * It listens for incoming messages and handles them accordingly.
     */
    public void run() {

        //eventKeeper.fixOffset(id,true,true);
        System.out.println("thread di " + id + " avviato");
        try {
            while (eventKeeper.checkActivity(id)) {
                synchronized (eventKeeper) {
                    if (eventKeeper.isPresentPersonal(id)) {
                        Sendable sendable = eventKeeper.getListenablePersonal(id);
                        this.handleSendable(sendable);
                        if(sendable.getHeader().equals(MessageHeader.ENDGAME))
                            break;
                    } else {
                        eventKeeper.wait(Settings.clock_listener);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        /*try {
            controller.disconnect(id);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
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
