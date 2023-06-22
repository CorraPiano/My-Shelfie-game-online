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
    //private final Gameplay gameplay;
    private final Controller controller;

    private final String name;

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

    public synchronized void disconnect(){
        //active=false;
    }


    public void run() {
        System.out.println("thread di " + id + " avviato");
        eventKeeper.setOffset(id,0);
        try {
            while (true) {
                synchronized (eventKeeper) {
                    if (eventKeeper.isPresentPersonal(id, personalCursor)) {
                        Sendable sendable = eventKeeper.getListenablePersonal(id, personalCursor);
                        if(sendable.getHeader().equals(MessageHeader.LEAVE) && ((LeaveMessage)sendable).name.equals(name))
                            break;
                        this.handleSendable(sendable);
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

    abstract void handleSendable(Sendable sendable);

    abstract void ping() throws Exception;


}
