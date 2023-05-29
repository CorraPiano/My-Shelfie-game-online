package it.polimi.ingsw.client.connection;

import com.google.gson.Gson;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.connection.TCPMessage;
import it.polimi.ingsw.connection.message.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection implements Runnable{
    private final Scanner in;
    private final PrintWriter out;
    private final Gson gson;
    private final TCPReceiver TCPreceiver;

    public ClientConnection(Socket socket, TCPReceiver TCPreceiver) throws IOException {
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream());
        gson = new Gson();
        this.TCPreceiver=TCPreceiver;
    }

    public void run() {
        // gestire la chiusura della connesione
        while(true){
            String line = in.nextLine();
            try{
                TCPMessage message = gson.fromJson(line, TCPMessage.class);
                try {
                    TCPreceiver.receive(message);
                }catch(Exception e){

                }
            } catch(Exception e){

            }
        }
    }

    public void send(Sendable message){
        String json = gson.toJson(message);
        TCPMessage TCPmessage = new TCPMessage(message.getHeader(),json);
        String str = gson.toJson(TCPmessage);
        out.println(str);
        out.flush();
    }

}

