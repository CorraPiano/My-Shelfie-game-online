package it.polimi.ingsw.controller;

import java.util.HashMap;

public class ConnectionChecker {

    private final HashMap<String,Long> lastPing;
    private final Controller controller;

    public ConnectionChecker(Controller controller) {
        this.lastPing = new HashMap<>();
        this.controller = controller;
    }

    public synchronized boolean setPing(String id) {
        if (lastPing.containsKey(id)){
            lastPing.put(id, System.currentTimeMillis());
            return true;
        }
        return false;
        //this.notifyAll();
    }
    public synchronized long getPing(String id) {
        return lastPing.getOrDefault(id, null);
    }

    public synchronized void removeID(String id) {
        lastPing.remove(id);
    }


    public void run(String id) {
        lastPing.put(id, System.currentTimeMillis());
        System.out.println("connection controller di "+id+" started");
        // siamo sicure che id non può essere null, altrimenti il thread si è gia fermato
            while (System.currentTimeMillis() - getPing(id) <= Settings.timeout_server) {
                synchronized (this) {
                    try {
                        this.wait(Settings.timeout_server);
                    } catch (Exception ignored) {}
                }
            }
        try {
            removeID(id);
            controller.disconnect(id);
        } catch(Exception ignored){}
        System.out.println("connection controllee di "+id+" stopped");

    }

}
