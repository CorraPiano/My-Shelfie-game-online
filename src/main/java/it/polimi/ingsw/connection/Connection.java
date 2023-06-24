package it.polimi.ingsw.connection;

import com.google.gson.Gson;
import it.polimi.ingsw.connection.message.LeaveMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Represents a connection between a client and a server.
 */
public class Connection implements Runnable{
    private final MessageHandler messageHandler;
    private final int num;
    private final Scanner in;
    private final PrintWriter out;

    private Gson gson;

    private boolean active;

    /**
     * Constructs a new Connection object.
     *
     * @param socket          The socket representing the connection.
     * @param num             The identifier for this connection.
     * @param messageHandler  The message handler responsible for processing received messages.
     * @throws IOException    If an I/O error occurs when creating the connection.
     */
    public Connection(Socket socket, int num, MessageHandler messageHandler) throws IOException {
        this.num = num;
        this.messageHandler = messageHandler;
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream());
        gson = new Gson();
        active = true;
    }

    /**
     * Runs the connection, continuously listening for incoming messages.
     * When a message is received, it is passed to the message handler for processing.
     */
    public void run() {

        System.out.println("nuova connessione");
        // gestire la chiusura della connesione
        while(true) {
            //sistemare il thread con wait;
            try {
                //System.out.println("------------------------------------------");
                String line = in.nextLine();
                try {
                    TCPMessage message = gson.fromJson(line, TCPMessage.class);
                    if(!message.getHeader().equals(MessageHeader.PING))
                        System.out.println("<- "+message.getHeader()+" : "+message.getBody());
                    messageHandler.receive(message, this);
                } catch (Exception e) {
                    e.printStackTrace();
                    TCPMessage TCPmessage = new TCPMessage(MessageHeader.EXCEPTION, e.toString());
                    send(TCPmessage);
                }
            } catch (Exception e) {
                e.printStackTrace();
                active = false;
                break;
            }
        }
        System.out.println("connessione terminata");
    }

    /**
     * Sends a TCP message through the connection.
     *
     * @param TCPmessage The TCP message to be sent.
     * @throws Exception If the connection is not active or an error occurs during the send operation.
     */
    public synchronized void send(TCPMessage TCPmessage) throws Exception {
        if(!TCPmessage.getHeader().equals(MessageHeader.PING))
            System.out.println("-> "+TCPmessage.getHeader() + " : " + TCPmessage.getBody());
        if(!active)
            throw new Exception();
        String str = gson.toJson(TCPmessage);
        out.println(str);
        out.flush();
    }
}
