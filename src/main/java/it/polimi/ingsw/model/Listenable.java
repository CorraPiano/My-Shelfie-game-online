package it.polimi.ingsw.model;

import it.polimi.ingsw.connection.message.Sendable;

/**
 * The Listenable class is an abstract base class for objects that can be listened to for updates.
 * It provides methods to notify listeners and propagate events.
 */
abstract class Listenable {

    EventKeeper eventKeeper;

    /**
     * Sets the event keeper for the listenable object.
     *
     * @param eventKeeper The EventKeeper instance to set.
     */
    public void setEventKeeper(EventKeeper eventKeeper){
        this.eventKeeper = eventKeeper;
    }

    /**
     * Notifies all listeners about the update of the listenable object.
     */
    public void notifyUpdate(){
        try {
            eventKeeper.notifyAll(this.getLocal());
        } catch (Exception e){
            // to let test work properly without setting the eventKeeper
            e.printStackTrace();
        }
    }

    /**
     * Notifies a specific listener with the given ID about the update of the listenable object.
     *
     * @param id The ID of the listener to notify.
     */
    public void notifyUpdateToID(String id){
        try {
            eventKeeper.notifyToID(id,this.getLocal());
        } catch (Exception e){
            // to let test work properly without setting the eventKeeper
            e.printStackTrace();
        }
    }

    /**
     * Notifies all listeners with the given sendable event.
     *
     * @param sendable The sendable event to notify.
     */
    public void notifyEvent(Sendable sendable){
            eventKeeper.notifyAll(sendable);
    }

    /**
     * Notifies a specific listener with the given ID about the sendable event.
     *
     * @param id       The ID of the listener to notify.
     * @param sendable The sendable event to notify.
     */
    public void notifyEventToID(String id, Sendable sendable){
            eventKeeper.notifyToID(id,sendable);
            // to let test work properly without setting the eventKeeper
    }

    /**
     * Returns the local representation of the listenable object.
     *
     * @return The local representation of the listenable object as a Sendable instance.
     */
    protected abstract Sendable getLocal();

}
