package it.polimi.ingsw.model;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.connection.MessageHeader;
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
        offsets.remove(id);
        offsetsGui.remove(id);
        status.remove(id);
        personalListGui.remove(id);
        this.notifyAll();
    }

    public synchronized void updateStatus(String id, boolean b){
        if(idList.contains(id)) {
            status.put(id, b);
            this.notifyAll();
        }
    }

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
     * Resets the offset for the specified player ID in the personal event list.
     *
     * @param id the player ID
     */
    public synchronized void fixOffset(String id, boolean reset, boolean guiMode){
        System.out.println(",,,,,,,");
        if(!personalList.containsKey(id))
            return;
        if(guiMode){
            ArrayList<Sendable> list = new ArrayList<>();
            for(Sendable sendable: personalList.get(id)){
                if(!sendable.isRecurrentUpdate()) {
                    if (sendable.getHeader().equals(MessageHeader.STARTGAME)) {
                        list.add(localBoard);
                        list.add(localHand);
                        list.add(localPlayerList);
                        list.add(sendable);
                        for (String s : localBookshelfMap.keySet())
                            list.add(localBookshelfMap.get(s));
                    } else
                        list.add(sendable);
                }
            }

            if(list.size()<=personalList.get(id).size()) {
                personalListGui.put(id, list);
                offsetsGui.put(id, personalList.get(id).size());
            }
            else {
                personalListGui.put(id,new ArrayList<>());
                offsetsGui.put(id,-1);
            }

        }

        if(reset)
            offsets.put(id,-1);
    }

    /**
     * Retrieves the event at the specified index from the personal event list for the specified player ID.
     *
     * @param id the player ID
     * @return the Sendable event object, or null if the index is out of bounds or the player ID is invalid
     */
    public synchronized Sendable getListenablePersonal(String id) {
        int n =  offsets.get(id)+1;
        if(personalList.containsKey(id)) {
            ArrayList<Sendable> l = personalList.get(id);
            ArrayList<Sendable> lgui = personalListGui.get(id);
            Sendable sendable = null;
            if(n < lgui.size() && n >= 0) {
                sendable = lgui.get(n);
                //offsetsGui>=lgui.size()-1
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
     * Notifies all players in the game with the specified Sendable event object.
     *
     * @param sendable the Sendable event object to notify
     */
    public synchronized void notifyAll(Sendable sendable){
        if(sendable.isRecurrentUpdate())
            update(sendable);
        listenableList.add(sendable);
        for(String id: idList) {
            if(!sendable.getHeader().equals(MessageHeader.TIMER) || status.get(id))
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


    public void update (LocalBoard localBoard){
        this.localBoard = localBoard;
    }
    public void update (LocalPlayerList localPlayerList){
        this.localPlayerList = localPlayerList;
    }
    public void update (LocalHand localHand){
        this.localHand = localHand;
    }
    public void update (LocalBookshelf localBookshelf){
        this.localBookshelfMap.put(localBookshelf.name, localBookshelf);
    }


}
