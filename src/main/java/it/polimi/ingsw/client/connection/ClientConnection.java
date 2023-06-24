package it.polimi.ingsw.client.connection;

import com.google.gson.Gson;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.connection.TCPMessage;
import it.polimi.ingsw.connection.message.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * The `ClientConnection` class is responsible for managing the TCP connection between the client and the server.
 * It handles sending and receiving messages over the TCP socket.
 */
public class ClientConnection implements Runnable {
    private final Scanner in;
    private final PrintWriter out;
    private final Gson gson;
    private final TCPReceiver TCPreceiver;
    private boolean active;

    /**
     * Constructs a `ClientConnection` object with the specified socket and TCP receiver.
     *
     * @param socket       The socket representing the TCP connection with the server.
     * @param TCPreceiver  The `TCPReceiver` object used for receiving messages from the server.
     * @throws IOException if an I/O error occurs while opening the input or output streams.
     */
    public ClientConnection(Socket socket, TCPReceiver TCPreceiver) throws IOException {
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream());
        gson = new Gson();
        this.TCPreceiver = TCPreceiver;
        active = true;
    }

    /**
     * Runs the client connection, continuously receiving messages from the server and passing them to the TCP receiver.
     * It runs in a separate thread.
     */
    @Override
    public void run() {
        while (true) {
            try {
                String line = in.nextLine();
                TCPMessage message = gson.fromJson(line, TCPMessage.class);
                try {
                    TCPreceiver.receive(message);
                } catch (Exception ignored) {
                }
            } catch (Exception e) {
                active = false;
                break;
            }
        }
    }

    /**
     * Sends a sendable message to the server over the TCP connection.
     *
     * @param message The sendable message to be sent to the server.
     * @throws Exception if the connection is not active or an error occurs while sending the message.
     */
    public void send(Sendable message) throws Exception {
        if (!active)
            throw new Exception();
        String json = gson.toJson(message);
        TCPMessage TCPmessage = new TCPMessage(message.getHeader(), json);
        String str = gson.toJson(TCPmessage);
        out.println(str);
        out.flush();
    }
}