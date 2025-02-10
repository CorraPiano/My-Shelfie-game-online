package it.polimi.ingsw.client.connection;

import it.polimi.ingsw.controller.Settings;

/**
 * The PingSender class is responsible for sending ping messages at regular intervals.
 */
public class PingSender implements Runnable{

    private final Sender sender;
    private int curr;
    private final ConnectionChecker connectionChecker;

    /**
     * Constructs a new PingSender object with the specified Sender and ConnectionChecker.
     *
     * @param sender             The Sender object to use for sending ping messages.
     * @param connectionChecker  The ConnectionChecker object to check the connection status.
     */
    public PingSender (Sender sender, ConnectionChecker connectionChecker){
        this.sender = sender;
        this.connectionChecker = connectionChecker;
        this.curr = 0;
    }

    /**
     * Stops the current thread by incrementing the current number and notifying all waiting threads.
     */
    public synchronized void stopCurrentThread(){
        curr++;
        this.notifyAll();
    }

    /**
     * Retrieves the current thread number.
     *
     * @return The current thread number.
     */
    public synchronized int getCurrNum(){
        return curr;
    }

    /**
     * Starts a new thread for the PingSender.
     */
    public synchronized void startNewThread(){
        new Thread(this).start();
    }

    /**
     * The run method of the PingSender thread. Sends ping messages at regular intervals until the current number changes.
     */
    public void run(){
        int n = getCurrNum();
        while(n==getCurrNum()){
            try {
                sender.ping(0);
                //System.out.println("ricevuto un ping");
                synchronized (this) {
                    this.wait(Settings.clock_pingSender);
                }
            } catch (Exception ignored) {};
        }
    }
}
