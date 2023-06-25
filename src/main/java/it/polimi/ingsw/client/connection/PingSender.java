package it.polimi.ingsw.client.connection;

public class PingSender implements Runnable{

    private final Sender sender;
    private int curr;
    private final ConnectionChecker connectionChecker;
    public PingSender (Sender sender, ConnectionChecker connectionChecker){
        this.sender = sender;
        this.connectionChecker = connectionChecker;
        this.curr = 0;
    }

    public synchronized void stopCurrentThread(){
        curr++;
        this.notifyAll();
    }

    public synchronized int getCurrNum(){
        return curr;
    }

    public synchronized void startNewThread(){
        new Thread(this).start();
    }

    public void run(){
        int n = getCurrNum();
        while(n==getCurrNum()){
            try {
                sender.ping(0);
                //System.out.println("ricevuto un ping");
                synchronized (this) {
                    this.wait(3000);
                }
            } catch (Exception ignored) {};
        }
    }
}
