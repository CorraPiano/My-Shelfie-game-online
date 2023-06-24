package it.polimi.ingsw.client.connection;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientPhase;
import it.polimi.ingsw.client.ClientState;

/**
 * The `ConnectionChecker` class is responsible for periodically checking the connection status between the client and the server.
 * It runs in a separate thread and uses the `Sender` object to send ping requests to the server and handle reconnection attempts.
 */
public class ConnectionChecker implements Runnable {

    private final Sender sender;
    private final Client client;
    private Boolean state;

    /**
     * Constructs a `ConnectionChecker` object with the specified `Sender` and `Client` objects.
     *
     * @param sender The `Sender` object used for sending ping requests and reconnection attempts.
     * @param client The `Client` object associated with this connection checker.
     */
    public ConnectionChecker(Sender sender, Client client) {
        this.sender = sender;
        this.client = client;
    }

    /**
     * Runs the connection checker in a separate thread.
     * Periodically sends ping requests to the server and handles reconnection attempts in case of lost connection.
     */
    @Override
    public void run() {
        while (!client.getPhase().equals(ClientPhase.CLOSE)) {
            synchronized (sender) {
                // Ping
                try {
                    sender.ping(0);
                } catch (Exception e) {
                    client.lostConnection();
                    tryReconnection();
                }
                // Wait
                try {
                    sender.wait(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Tries to reconnect to the server in case of a lost connection.
     * Keeps attempting reconnection until successful or until the client is closed.
     */
    public synchronized void tryReconnection() {
        while (true) {
            try {
                sender.connect();
                if (client.getPhase().equals(ClientPhase.MATCH_RECONNECTION)) {
                    client.setState(ClientState.WAIT);
                    client.gameReconnection();
                    sender.reconnectGame(client.getID(), false);
                } else {
                    client.homeReconnection();
                }
                break;
            } catch (Exception e) {
                try {
                    this.wait(1000);
                } catch (Exception ignored) {
                }
            }
        }
    }
}