package it.polimi.ingsw.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class represents a TCP server that listens for incoming client connections.
 */
public class TCPServer implements Runnable{

    private int port;
    private ArrayList<Connection> connectionList = new ArrayList<>();
    private ServerSocket serverSocket;
    private MessageHandler messageHandler;

    /**
     * Constructs a new TCPServer object with the specified server socket and message handler.
     *
     * @param serverSocket   The server socket used for accepting client connections.
     * @param messageHandler The message handler responsible for processing received messages.
     */
    public TCPServer(ServerSocket serverSocket, MessageHandler messageHandler){
        this.serverSocket=serverSocket;
        this.messageHandler=messageHandler;
    }

    /**
     * Starts the TCP server and listens for incoming client connections.
     * Once a connection is accepted, a new thread is created to handle the connection.
     */
    public void run() {
        int num=0;
        while(true){
            try{
                //the method blocks the thread
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket,num,messageHandler);
                System.out.println(socket.getLocalAddress());
                // vedere se serve connectionList
                // connectionList.add(connection);
                new Thread(connection).start();
                num++;
             }
             catch(IOException e){}
         }
    }

}
