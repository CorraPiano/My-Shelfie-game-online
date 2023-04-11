package it.polimi.ingsw.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection implements Runnable{
    private final Socket socket;
    private final TCPServer server;
    private final int num;
    private Scanner in;
    private PrintWriter out;

    public Connection(Socket socket, int num, TCPServer server) throws IOException {
        this.socket = socket;
        this.num = num;
        this.server = server;
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream());
    }

    public void run() {
        System.out.println("nuova connessione");
        // gestire la chiusura della connesione
        while(true){
            String line = in.nextLine();
            out.println("ok");
            out.flush();
            System.out.println(num + " : " + line);
            //server.send(new Message(line));
        }
    }
}
