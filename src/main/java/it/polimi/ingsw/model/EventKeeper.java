package it.polimi.ingsw.model;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.ReconnectType;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.controller.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The EventKeeper class is responsible for managing events and notifications in the game.
 */
public class EventKeeper {
    private final ArrayList<Sendable> listenableList;
    private final HashMap<String,ArrayList<Sendable>> personalList;
    private final HashMap<String,ArrayList<Sendable>> personalListGui;
    private final ArrayList<String> idList;
    private final HashMap<String,Integer> offsets;
    private final HashMap<String,Integer> offsetsGui;
    private final HashMap<String,Boolean> status;


    //local model

    private LocalBoard localBoard;
    private LocalHand localHand;
    private LocalPlayerList localPlayerList;
    private final Map<String, LocalBookshelf> localBookshelfMap;


    /**
     * Constructs an EventKeeper object with the given gameplay instance.
     */
    public EventKeeper(){
        this.listenableList=new ArrayList<>();
        this.personalList=new HashMap<>();
        this.idList = new ArrayList<>();
        this.offsets = new HashMap<>();
        this.offsetsGui = new HashMap<>();
        this.personalListGui = new HashMap<>();
        this.status = new HashMap<>();
        this.localBookshelfMap = new HashMap<>();
    }


    /**
     * Adds a personal event list for the specified player ID.
     *
     * @param id the player ID
     */
    public synchronized void addPersonalList(String id){
        ArrayList<Sendable> l = new ArrayList<>(listenableList);
        ArrayList<Sendable> ll = new ArrayList<>();
        this.idList.add(id);
        this.personalList.put(id,l);
        this.personalListGui.put(id,ll);
        this.offsets.put(id,-1);
        this.offsetsGui.put(id,-1);
        this.status.put(id,true);
    }

    public synchronized void removePersonalList(String id){
        idList.remove(id);
        personalList.remove(id);
        personalListGui.remove(id);
        offsets.remove(id);
        offsetsGui.remove(id);
        status.remove(id);
        //fix
        this.notifyAll();
    }

    /** Updates the status of a player, used to stop the current associated listener.
     *
     * @param id the ID of the selected player
     */
    public synchronized void updateStatus(String id, boolean b){
        if(idList.contains(id)) {
            status.put(id, b);
            this.notifyAll();
        }
    }

    /** Checks if a listener should stop. This happen if the passed integer is not the same of integer
     * locally associated to the player.
    *
     * @param id the ID associated with the listener
     * @return 'false' if the listener has to stop, false otherwise
     */
    public synchronized boolean checkActivity(String id){
        if(!idList.contains(id))
            return false;
        return status.get(id);
    }

    /**
     * Checks if an event with the given index is present in the personal event list for the specified player ID.
     *
     * @param id the player ID
     * @return true if the event is present, false otherwise
     */
    public synchronized boolean isPresentPersonal(String id) {
        int n =  offsets.get(id)+1;
        if(personalList.containsKey(id)) {
            return n < personalList.get(id).size();
        }
        return false;
    }

    //to do
    /**
     * Prepares the queque of events for the specified player ID, according to the ReconnectedType.
     *
     * @param id the player ID.
     * @param reconnectType the flow of events wanted by the client.
     */
    public synchronized void fixOffset(String id, ReconnectType reconnectType){
        if(!personalList.containsKey(id))
            return;
        if(reconnectType==ReconnectType.GUI){
            ArrayList<Sendable> list = new ArrayList<>();
            for(Sendable sendable: personalList.get(id)) {
                if (!sendable.isRecurrentUpdate()) {
                    if (sendable.getHeader().equals(MessageHeader.STARTGAME)) {
                        list.add(localBoard);
                        list.add(localHand);
                        list.add(localPlayerList);
                        for (String s : localBookshelfMap.keySet()) {
                            //System.out.println(s);
                            list.add(localBookshelfMap.get(s));
                        }
                    }
                    list.add(sendable);
                }
            }
            if(list.size()<=personalList.get(id).size()) {
                personalListGui.put(id, list);
                offsetsGui.put(id, personalList.get(id).size());
                offsets.put(id,-1);
            }
            else {
                personalListGui.put(id, new ArrayList<>());
                offsetsGui.put(id,-1);
                offsets.put(id,-1);
            }
        }
        if(reconnectType==ReconnectType.SENDALL)
            offsets.put(id,-1);
    }

    /**
     * Retrieves the next event (if present) from the personal event list for the specified player ID.
     *
     * @param id the player ID
     * @return the Sendable event object, or null if object doesn't exist
     */
    public synchronized Sendable getListenablePersonal(String id) {
        int n =  offsets.get(id)+1;
        if(personalList.containsKey(id)) {
            ArrayList<Sendable> l = personalList.get(id);
            ArrayList<Sendable> lgui = personalListGui.get(id);
            Sendable sendable = null;
            if(n < lgui.size() && n >= 0) {
                sendable = lgui.get(n);
                if(n==lgui.size()-1)
                    n = offsetsGui.get(id)-1;
            }
            else if (n < l.size() && n >= 0){
                sendable = l.get(n);
                if(sendable.getHeader().equals(MessageHeader.TIMER)) {
                    l.remove(n);
                    n = n-1;
                }
            }
            offsets.put(id,n);
            return sendable;
        }
        return null;
    }

    /**
     * Adds the Sendable object to the notification lists of every player connected to the game.
     * The listeners of the players will recover the objects from their list and send them to the client.
     *
     * @param sendable the Sendable event object to notify to the clients
     */
    public synchronized void notifyAll(Sendable sendable){
        // update local model
        if(sendable.isRecurrentUpdate())
            update(sendable);
        // add object
        listenableList.add(sendable);
        for(String id: idList) {
            if(sendable.getHeader()!=MessageHeader.TIMER || status.get(id))
                personalList.get(id).add(sendable);
        }
        this.notifyAll();
    }

    /**
     * Adds the Sendable object to the notification list of the selected player
     *
     * @param id       the ID that select the player
     * @param sendable the Sendable event object to notify to the client
     */
    public synchronized void notifyToID(String id, Sendable sendable){
        if(personalList.containsKey(id)) {
            personalList.get(id).add(sendable);
            this.notifyAll();
        }
    }

    /** Adds the passed sendable to the localmodel, if the the object is suitable.
    */
    private void update(Sendable sendable){
        switch (sendable.getHeader()){
            case BOARD -> localBoard = (LocalBoard)sendable;
            case PLAYERLIST -> localPlayerList = (LocalPlayerList)sendable ;
            case HAND -> localHand = (LocalHand) sendable;
            case BOOKSHELF -> {
                LocalBookshelf lb = (LocalBookshelf)sendable;
                localBookshelfMap.put(lb.name, lb);
            }
        }
    }

    public HashMap<String, Boolean> getStatus() {
        return status;
    }

    //Testing getters
    public HashMap<String, ArrayList<Sendable>> getPersonalList() {
        return personalList;
    }

    public HashMap<String, ArrayList<Sendable>> getPersonalListGui() {
        return personalListGui;
    }


    public HashMap<String, Integer> getOffsets() {
        return offsets;
    }

    public HashMap<String, Integer> getOffsetsGui() {
        return offsetsGui;
    }




}
