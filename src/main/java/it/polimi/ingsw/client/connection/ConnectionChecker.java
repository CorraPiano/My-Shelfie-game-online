package it.polimi.ingsw.client.connection;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientPhase;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.controller.Settings;

import static java.lang.Thread.sleep;

/**
 * The `ConnectionChecker` class is responsible for periodically checking the connection status between the client and the server.
 * It runs in a separate thread and uses the `Sender` object to send ping requests to the server and handle reconnection attempts.
 */
public class ConnectionChecker implements Runnable {

    private final Sender sender;
    private final Client client;
    private Boolean connectionState;
    private final PingSender pingSender;
    private long lastPing;

    /**
     * Constructs a `ConnectionChecker` object with the specified `Sender` and `Client` objects.
     *
     * @param sender The `Sender` object used for sending ping requests and reconnection attempts.
     * @param client The `Client` object associated with this connection checker.
     */
    public ConnectionChecker (Sender sender, Client client){
        this.client = client;
        this.sender = sender;
        pingSender = new PingSender(sender,this);
    }

    public synchronized void setLastPing(){
        lastPing=System.currentTimeMillis();
    }
    public synchronized long getLastPing(){
        return lastPing;
    }

    public void run(){
        setLastPing();
        pingSender.startNewThread();
        while(!client.getPhase().equals(ClientPhase.CLOSE)){
            //System.out.println(System.currentTimeMillis()-getLastPing());
            if(System.currentTimeMillis()-getLastPing()> 11000) {
                pingSender.stopCurrentThread();
                client.lostConnection();
                tryReconnection();
                setLastPing();
            }
            try {
                synchronized (this) {
                    this.wait(5000);
                }
            } catch (InterruptedException ignored) {}
        }
        pingSender.stopCurrentThread();
    }

    /**
     * Tries to reconnect to the server in case of a lost connection.
     * Keeps attempting reconnection until successful or until the client is closed.
     */
    public synchronized void tryReconnection() {
        while (true) {
            try {
                sender.connect();
                if(client.getPhase().equals(ClientPhase.MATCH_RECONNECTION)) {
                    client.setState(ClientState.WAIT);
                    client.gameReconnection();
                    sender.reconnectGame(client.getID(),false);
                }
                else
                    client.homeReconnection();
                pingSender.startNewThread();
                break;
            }
            catch(Exception ignored1) {
                synchronized (this) {
                    try {
                        this.wait(1000);
                    } catch (Exception ignored2) {}
                }
            }
        }
    }
}
