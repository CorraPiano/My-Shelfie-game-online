package it.polimi.ingsw.controller;

import java.util.HashMap;

/**
 * The ConnectionChecker class is responsible for checking the connection status of clients.
 */
public class ConnectionChecker {

    private final HashMap<String,Long> lastPing;
    private final Controller controller;

    /**
     * Constructs a new ConnectionChecker object.
     *
     * @param controller The controller instance.
     */
    public ConnectionChecker(Controller controller) {
        this.lastPing = new HashMap<>();
        this.controller = controller;
    }

    /**
     * Sets the ping timestamp for the specified client ID.
     *
     * @param id The client ID.
     * @return {@code true} if the ping timestamp was set successfully, {@code false} otherwise.
     */
    public synchronized boolean setPing(String id) {
        if (lastPing.containsKey(id)){
            lastPing.put(id, System.currentTimeMillis());
            return true;
        }
        return false;
        //this.notifyAll();
    }

    /**
     * Retrieves the ping timestamp for the specified client ID.
     *
     * @param id The client ID.
     * @return The ping timestamp, or {@code null} if the client ID is not found.
     */
    public synchronized long getPing(String id) {
        return lastPing.getOrDefault(id, null);
    }

    /**
     * Removes the specified client ID from the connection checker.
     *
     * @param id The client ID to remove.
     */
    public synchronized void removeID(String id) {
        lastPing.remove(id);
    }

    /**
     * Runs the connection checker for the specified client ID.
     * The connection checker periodically updates the ping timestamp for the client
     * and checks if the connection is still active. If the connection is lost,
     * it notifies the controller to disconnect the client.
     *
     * @param id The client ID.
     */
    public void run(String id) {
        lastPing.put(id, System.currentTimeMillis());
        System.out.println("connection controller di "+id+" started");
        // siamo sicure che id non può essere null, altrimenti il thread si è gia fermato
            while (System.currentTimeMillis() - getPing(id) <= Settings.timeout_server) {
                synchronized (this) {
                    try {
                        this.wait(Settings.clock_connectionCheck);
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
