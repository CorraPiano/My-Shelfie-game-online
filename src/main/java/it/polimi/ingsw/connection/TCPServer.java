package it.polimi.ingsw.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer implements Runnable{
    private int port;
    private ArrayList<Connection> connectionList = new ArrayList<>();

    private ServerSocket serverSocket;

    public TCPServer(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }

    public void run() {
        int num=0;
        while(true){
            try{
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket,num,this);
                // vedere se serve connectionList
                // connectionList.add(connection);
                new Thread(connection).start();
                num++;
             }
             catch(IOException e){}
         }
    }

}
