package it.polimi.ingsw.model;

import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.controller.Settings;

import java.util.ArrayList;
import java.util.HashMap;

public class EventKeeper {
    private final ArrayList<Sendable> listenableList;
    private final HashMap<String,ArrayList<Sendable>> personalList;
    private final HashMap<String,Long> lastPing;

    private final ArrayList<String> idList;

    public EventKeeper(){
        this.listenableList=new ArrayList<>();
        this.personalList=new HashMap<>();
        this.lastPing = new HashMap<>();
        this.idList = new ArrayList<>();
    }

//if (System.currentTimeMillis() - playerPing.get(p.getID()) > 10000) {
    public synchronized void addPersonalList(String id){
        ArrayList<Sendable> l = new ArrayList<>(listenableList);
        this.idList.add(id);
        this.personalList.put(id,l);
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

    public synchronized boolean isPresentPersonal(String id,int n) {
        if(personalList.containsKey(id)) {
            return n < personalList.get(id).size();
        }
        return false;
    }

    public synchronized Sendable getListenablePersonal(String id, int n) {
        if(personalList.containsKey(id)) {
            ArrayList<Sendable> l = personalList.get(id);
            if (n < l.size() && n >= 0)
                return l.get(n);
        }
        return null;
    }

    public synchronized void notifyAll(Sendable sendable){
        //System.out.println(sendable);
        listenableList.add(sendable);
        for(String id: idList)
            personalList.get(id).add(sendable);
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
