package it.polimi.ingsw.client.connection;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientPhase;
import it.polimi.ingsw.client.ClientState;

public class ConnectionChecker implements Runnable{

    private final Sender sender;
    private final Client client;
    private Boolean state;

    public ConnectionChecker (Sender sender, Client client){
        this.sender = sender;
        this.client = client;
    }

    public void run(){
        while(!client.getPhase().equals(ClientPhase.CLOSE)){
            synchronized (sender) {
                //ping
                try {
                    sender.ping(0);
                } catch(Exception e){
                    client.lostConnection();
                    tryReconnection();
                }
                //metti in wait
                try {
                    sender.wait(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public synchronized void tryReconnection(){
        while(true){
            try{
                sender.connect();
                if(client.getPhase().equals(ClientPhase.MATCH_RECONNECTION)) {
                    client.setState(ClientState.WAIT);
                    client.gameReconnection();
                    sender.reconnectGame(client.getID(),false);
                }
                else
                    client.homeReconnection();
                break;
            }
            catch(Exception e) {
                try{
                    this.wait(1000);
                } catch(Exception ee){}
            }
        }
    }
}
