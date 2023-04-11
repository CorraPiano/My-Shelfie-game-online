package it.polimi.ingsw.connection;

import it.polimi.ingsw.controller.MessageHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection implements Runnable{
    private final Socket socket;
    private final MessageHandler messageHandler;
    private final int num;
    private Scanner in;
    private PrintWriter out;


    public Connection(Socket socket, int num, MessageHandler messageHandler) throws IOException {
        this.socket = socket;
        this.num = num;
        this.messageHandler = messageHandler;
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream());
    }

    public void run() {
        System.out.println("nuova connessione");
        // gestire la chiusura della connesione
        while(true){
            String line = in.nextLine();
            //System.out.println(num + " : " + line);
            if(messageHandler.receive(new Message(line),num)){
                out.println("ok");
                out.flush();
            }
            else{
                out.println("ko");
                out.flush();
            }

        }
    }
}
