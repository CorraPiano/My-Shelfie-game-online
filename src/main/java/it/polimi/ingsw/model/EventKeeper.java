package it.polimi.ingsw.model;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.controller.Settings;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The EventKeeper class is responsible for managing events and notifications in the game.
 */
public class EventKeeper {
    private final ArrayList<Sendable> listenableList;
    private final HashMap<String,ArrayList<Sendable>> personalList;
    private final HashMap<String,Long> lastPing;
    private final ArrayList<String> idList;
    private final HashMap<String,Integer> offsets;
    private final Gameplay gameplay;


    /**
     * Constructs an EventKeeper object with the given gameplay instance.
     *
     * @param gameplay the Gameplay instance
     */
    public EventKeeper(Gameplay gameplay){
        this.listenableList=new ArrayList<>();
        this.personalList=new HashMap<>();
        this.lastPing = new HashMap<>();
        this.idList = new ArrayList<>();
        this.gameplay = gameplay;
        this.offsets = new HashMap<>();
    }

//if (System.currentTimeMillis() - playerPing.get(p.getID()) > 10000) {

    /**
     * Adds a personal event list for the specified player ID.
     *
     * @param id the player ID
     */
    public synchronized void addPersonalList(String id){
        ArrayList<Sendable> l = new ArrayList<>(listenableList);
        this.idList.add(id);
        this.personalList.put(id,l);
        this.offsets.put(id,0);
        this.lastPing.put(id,System.currentTimeMillis());
    }

    /**
     * Checks if an event with the given index is present in the global event list.
     *
     * @param n the index of the event
     * @return true if the event is present, false otherwise
     */
    public synchronized boolean isPresent(int n) {
        return n<listenableList.size();
    }

    /**
     * Retrieves the event at the specified index from the global event list.
     *
     * @param n the index of the event
     * @return the Sendable event object, or null if the index is out of bounds
     */
    public synchronized  Sendable getListenable(int n) {
        if(n < listenableList.size() && n>=0)
            return listenableList.get(n);
        else
            return null;
    }

    /**
     * Checks if an event with the given index is present in the personal event list for the specified player ID.
     *
     * @param id the player ID
     * @param s  the index of the event
     * @return true if the event is present, false otherwise
     */
    public synchronized boolean isPresentPersonal(String id,int s) {
        int n =  offsets.get(id);
        if(personalList.containsKey(id)) {
            return n < personalList.get(id).size();
        }
        return false;
    }

    /**
     * Sets the offset for the specified player ID in the personal event list.
     *
     * @param id the player ID
     * @param n  the offset value
     */
    public synchronized void setOffset(String id, int n){
        offsets.put(id,n);
    }

    /**
     * Retrieves the event at the specified index from the personal event list for the specified player ID.
     *
     * @param id the player ID
     * @param s  the index of the event
     * @return the Sendable event object, or null if the index is out of bounds or the player ID is invalid
     */
    public synchronized Sendable getListenablePersonal(String id,int s) {
        int n =  offsets.get(id);
        if(personalList.containsKey(id)) {
            ArrayList<Sendable> l = personalList.get(id);
            if (n < l.size() && n >= 0) {
                Sendable sendable = l.get(n);
                if(sendable.getHeader().equals(MessageHeader.TIMER))
                    l.remove(n);
                else
                    offsets.put(id,n+1);;
                return sendable;
            }
        }
        return null;
    }

    /**
     * Notifies all players in the game with the specified Sendable event object.
     *
     * @param sendable the Sendable event object to notify
     */
    public synchronized void notifyAll(Sendable sendable){
        //System.out.println(sendable);
        listenableList.add(sendable);
        for(String id: idList) {
            if(!sendable.getHeader().equals(MessageHeader.TIMER) || gameplay.isConnected(id))
                personalList.get(id).add(sendable);
        }
        this.notifyAll();
    }

    /**
     * Notifies the specified player with the Sendable event object.
     *
     * @param id       the player ID to notify
     * @param sendable the Sendable event object to notify
     */
    public synchronized void notifyToID(String id, Sendable sendable){
        if(personalList.containsKey(id)) {
            personalList.get(id).add(sendable);
            this.notifyAll();
        }
    }

    /**
     * Checks the connection status of the specified player.
     *
     * @param id the player ID
     * @return true if the player is still connected, false if the player has timed out
     */
    public synchronized boolean checkConnection(String id){
        return System.currentTimeMillis() - lastPing.get(id) <= Settings.timeout;
    }
}
