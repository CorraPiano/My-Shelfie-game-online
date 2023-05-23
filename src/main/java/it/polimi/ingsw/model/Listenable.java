package it.polimi.ingsw.model;

import it.polimi.ingsw.connection.message.Sendable;

abstract class Listenable {

    EventKeeper eventKeeper;

    public void setEventKeeper(EventKeeper eventKeeper){
        this.eventKeeper = eventKeeper;
    }

    public void notifyUpdate(){
        try {
            eventKeeper.notifyAll(this.getLocal());
        } catch (Exception e){
            // to let test work properly without setting the eventKeeper
            e.printStackTrace();
        }
    }

    public void notifyUpdateToID(String id){
        try {
            eventKeeper.notifyToID(id,this.getLocal());
        } catch (Exception e){
            // to let test work properly without setting the eventKeeper
            e.printStackTrace();
        }
    }

    public void notifyEvent(Sendable sendable){
        try {
            eventKeeper.notifyAll(sendable);
        } catch (Exception e){
            // to let test work properly without setting the eventKeeper
            e.printStackTrace();
        }
    }

    public void notifyEventToID(String id, Sendable sendable){
        try {
            eventKeeper.notifyToID(id,sendable);
        } catch (Exception e){
            // to let test work properly without setting the eventKeeper
            e.printStackTrace();
        }
    }

    protected abstract Sendable getLocal();

}
