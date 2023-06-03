package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.*;
import it.polimi.ingsw.model.EventKeeper;
import it.polimi.ingsw.model.Gameplay;

import java.rmi.RemoteException;

public abstract class Listener implements Runnable {

    private Boolean active;
    private final EventKeeper eventKeeper;
    private int cursor;
    private int personalCursor;
    protected final Gson gson;
    private final String id;
    private final Gameplay gameplay;

    public Listener(Gameplay gameplay, String id) {
        this.eventKeeper = gameplay.getEventKeeper();
        this.gameplay = gameplay;
        active=true;
        cursor=0;
        personalCursor=0;
        this.id=id;
        gson = new Gson();
    }

    public synchronized void disconnect(){
        active=false;
    }


    public void run() {
        System.out.println("thread avviato");
        try {
            while (true) {
                synchronized (eventKeeper) {
                    if (eventKeeper.isPresentPersonal(id, personalCursor)) {
                        this.handleSendable(eventKeeper.getListenablePersonal(id, personalCursor));
                        personalCursor++;
                    }
                    else {
                        ping();
                        eventKeeper.wait(5000);
                    }
                }
            }
        } catch (Exception e){
            gameplay.disconnect(id);
        }
        System.out.println("thread terminato");
    }

    abstract void handleSendable(Sendable sendable);

    abstract void ping() throws Exception;


}
