package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.connection.message.*;
import it.polimi.ingsw.model.EventKeeper;

public abstract class Listener implements Runnable {

    private Boolean active;
    private final EventKeeper eventKeeper;
    private int cursor;
    private int personalCursor;
    protected Gson gson;
    private String id;

    public Listener(EventKeeper eventKeeper, String id) {
        this.eventKeeper = eventKeeper;
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
        try {
            while (true) {
                synchronized (this) {
                    if (!active)
                        break;
                }
                synchronized (eventKeeper) {
                    if (eventKeeper.isPresentPersonal(id, personalCursor)) {
                        System.out.println("eeeeeeeeeeeeeeeeee");
                        this.handleSendable(eventKeeper.getListenablePersonal(id, personalCursor));
                        personalCursor++;
                    }
                    else if (eventKeeper.isPresent(cursor)) {
                        this.handleSendable(eventKeeper.getListenable(cursor));
                        cursor++;
                    } else
                        eventKeeper.wait();
                }
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    abstract void handleSendable(Sendable sendable);


}
