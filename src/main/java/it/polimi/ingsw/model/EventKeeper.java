package it.polimi.ingsw.model;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.controller.Settings;

import java.util.ArrayList;
import java.util.HashMap;

public class EventKeeper {
    private final ArrayList<Sendable> listenableList;
    private final HashMap<String,ArrayList<Sendable>> personalList;
    private final HashMap<String,Long> lastPing;

    private final ArrayList<String> idList;
    private final HashMap<String,Integer> offsets;

    private final Gameplay gameplay;

    public EventKeeper(Gameplay gameplay){
        this.listenableList=new ArrayList<>();
        this.personalList=new HashMap<>();
        this.lastPing = new HashMap<>();
        this.idList = new ArrayList<>();
        this.gameplay = gameplay;
        this.offsets = new HashMap<>();
    }

//if (System.currentTimeMillis() - playerPing.get(p.getID()) > 10000) {
    public synchronized void addPersonalList(String id){
        ArrayList<Sendable> l = new ArrayList<>(listenableList);
        this.idList.add(id);
        this.personalList.put(id,l);
        this.offsets.put(id,0);
        this.lastPing.put(id,System.currentTimeMillis());
    }

    public synchronized boolean isPresent(int n) {
        return n<listenableList.size();
    }

    public synchronized  Sendable getListenable(int n) {
        if(n < listenableList.size() && n>=0)
            return listenableList.get(n);
        else
            return null;
    }

    public synchronized boolean isPresentPersonal(String id,int s) {
        int n =  offsets.get(id);
        if(personalList.containsKey(id)) {
            return n < personalList.get(id).size();
        }
        return false;
    }

    public synchronized void setOffset(String id, int n){
        offsets.put(id,n);
    }

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

    public synchronized void notifyAll(Sendable sendable){
        //System.out.println(sendable);
        listenableList.add(sendable);
        for(String id: idList) {
            if(!sendable.getHeader().equals(MessageHeader.TIMER) || gameplay.isConnected(id))
                personalList.get(id).add(sendable);
        }
        this.notifyAll();
    }

    public synchronized void notifyToID(String id, Sendable sendable){
        if(personalList.containsKey(id)) {
            personalList.get(id).add(sendable);
            this.notifyAll();
        }
    }

    public synchronized boolean checkConnection(String id){
        return System.currentTimeMillis() - lastPing.get(id) <= Settings.timeout;
    }
}
