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
    private final HashMap<String,Integer> listenableNums;


    //local model

    private LocalBoard localBoard;
    private LocalHand localHand;
    private LocalPlayerList localPlayerList;
    private final Map<String, LocalBookshelf> localBookshelfMap;


    /**
     * Constructs an EventKeeper object with the given gameplay instance.
     */
    public EventKeeper(){
        listenableList=new ArrayList<>();
        idList = new ArrayList<>();
        localBookshelfMap = new HashMap<>();

        listenableNums = new HashMap<>();
        personalList=new HashMap<>();
        personalListGui = new HashMap<>();
        offsets = new HashMap<>();
        offsetsGui = new HashMap<>();
    }


    /**
     * Adds a personal event list for the specified player ID.
     *
     * @param id the player ID
     */
    public synchronized void addPersonalList(String id){
        listenableNums.put(id,0);
        idList.add(id);
        personalList.put(id,new ArrayList<>(listenableList));
        personalListGui.put(id,new ArrayList<>());
        offsets.put(id,-1);
        offsetsGui.put(id,-1);
    }

    public synchronized void removePersonalList(String id){
        idList.remove(id);
        listenableNums.remove(id);
        personalList.remove(id);
        personalListGui.remove(id);
        offsets.remove(id);
        offsetsGui.remove(id);
        //fix
        this.notifyAll();
    }

    /** Updates the listenableNum of a player, used to stop the current associated listener.
     *
     * @param id the ID of the selected player
     */
    public synchronized void updateListenableNum(String id){
        if(idList.contains(id)) {
            listenableNums.put(id, listenableNums.get(id)+1);
            this.notifyAll();
        }
    }

    /** Checks if a listener should stop. This happen if the passed integer is not the same of integer
     * locally associated to the player..
    *
     * @param id the ID associated with the listener
     * @param n the number recorded in the listener
     * @return 'false' if the listener has to stop, false otherwise
     */
    public synchronized boolean checkActivity(String id, int n){
        if(!idList.contains(id))
            return false;
        return listenableNums.get(id)==n;
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
        switch (reconnectType) {
            case GUI -> {
                // prepare the two list
                ArrayList<Sendable> list = new ArrayList<>();
                for (Sendable sendable : personalList.get(id)) {
                    if (!sendable.isRecurrentUpdate()) {
                        if (sendable.getHeader().equals(MessageHeader.STARTGAME)) {
                            list.add(localBoard);
                            list.add(localHand);
                            list.add(localPlayerList);
                            for (String s : localBookshelfMap.keySet()) {
                                System.out.println(s);
                                list.add(localBookshelfMap.get(s));
                            }
                        }
                        list.add(sendable);
                    }
                }

                // set offsetGui and the personalListGui
                if (list.size() <= personalList.get(id).size()) {
                    personalListGui.put(id, list);
                    offsetsGui.put(id, personalList.get(id).size());
                } else {
                    personalListGui.put(id, new ArrayList<>());
                    offsetsGui.put(id, -1);
                }
                // reset the offset
                offsets.put(id, -1);
            }
            case SENDNEW -> {
                // reset the offsetGui and the personalListGui
                offsetsGui.put(id, -1);
                personalListGui.put(id, new ArrayList<>());
            }
            case SENDALL -> {
                // reset the offset
                offsets.put(id, -1);
                // reset the offsetGui and the personalListGui
                offsetsGui.put(id, -1);
                personalListGui.put(id, new ArrayList<>());
            }
        }
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


    public LocalBoard getLocalBoard() {
        return localBoard;
    }

    public LocalHand getLocalHand() {
        return localHand;
    }

    public LocalPlayerList getLocalPlayerList() {
        return localPlayerList;
    }

    public Map<String, LocalBookshelf> getLocalBookshelfMap() {
        return localBookshelfMap;
    }


}
