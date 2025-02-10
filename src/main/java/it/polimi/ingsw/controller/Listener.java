package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.ReconnectType;
import it.polimi.ingsw.connection.message.*;
import it.polimi.ingsw.exception.GameFinishedException;
import it.polimi.ingsw.exception.InvalidIdException;
import it.polimi.ingsw.model.EventKeeper;
import it.polimi.ingsw.model.Gameplay;

import java.rmi.RemoteException;

/**
 * The abstract Listener class represents a thread that listens on the eventKeeper, handling the communication
 * towards client.
 * Specifically it recover Sendable objects from eventKeeper and send them to the client.
 */
public abstract class Listener implements Runnable {
    private final EventKeeper eventKeeper;
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
        this.eventKeeper = eventKeeper;
        this.name = name;
        this.id=id;
        this.controller = controller;
        gson = new Gson();
    }

    public synchronized void fixOffset(ReconnectType reconnectType){
        synchronized (eventKeeper) {
            eventKeeper.fixOffset(id,reconnectType);
        }
    }

    /**
     * Runs the listener thread.
     * It listens for incoming messages and handles them accordingly.
     */
    public void run() {
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
        }

        System.out.println("thread di "+ id +" terminato");
    }

    /**
     * Handles Sendable object, sending it to the client.
     *
     * @param sendable the Sendable object representing the received message
     */
    abstract void handleSendable(Sendable sendable) throws Exception;
}
