package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.connection.ClientConnection;
import it.polimi.ingsw.connection.MessageHandler;
import it.polimi.ingsw.connection.SocketMap;
import it.polimi.ingsw.connection.TCPServer;
import it.polimi.ingsw.connection.message.NewTurnMessage;
import it.polimi.ingsw.util.Constants;

import java.io.IOException;
import java.net.*;
import java.rmi.registry.Registry;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, AlreadyBoundException, RemoteException{

        SocketMap socketMap = new SocketMap();
        //SenderTCP senderTCP = new SenderTCP(socketMap);
        Controller controller = new Controller();
        MessageHandler messageHandler = new MessageHandler(controller,socketMap);

        String IP = insertIP();
        startTCP(controller,messageHandler);
        startRMI(controller,IP);
    }

    public static String insertIP(){
        Scanner in = new Scanner(System.in);
        String IP;
        System.out.println("insert local IP");
        try {
            String input = in.nextLine();
            if(input.isEmpty()){
                IP = Settings.IP;
                System.out.println("server ready on " + Settings.IP);
                return IP ;
            }
            if (input.matches(Constants.IPV4_PATTERN)) {
                IP = input;
                System.out.println("server ready on " + IP );
                return IP ;
            }
        } catch(Exception e) {
        }
        System.out.println("error");
        IP = Settings.IP;
        System.out.println("server ready on " + IP );
        //System.out.println(ANSI_YELLOW + "<<INFORMATION>> " + ANSI_RESET + "The IP address will be set to the default: " + Settings.IP + "\n");
        return IP;
    }

    private static void startRMI(Controller controller, String IP) throws RemoteException {
        //String ipAddress = new String();
        //System.setProperty("java.rmi.server.hostname", ipAddress);
        try{
            //da sistemare
            //System.setProperty("java.rmi.server.hostname", "192.168.1.5");
            System.setProperty("java.rmi.server.hostname", IP);
            //System.setProperty("java.rmi.server.logCalls","true");
            //System.setProperty("java.rmi.server.hostname", "192.168.1.5");
            LocateRegistry.createRegistry(Settings.RMIPORT);
            Registry registry = LocateRegistry.getRegistry();

            registry.bind(Settings.remoteObjectName, UnicastRemoteObject.exportObject(controller, Settings.RMIPORT));
            System.out.println("RMI attivo");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void startTCP(Controller controller, MessageHandler messageHandler){
        try{
            // interfacce di scambio
            ServerSocket serverSocket = new ServerSocket(Settings.TCPPORT);
            TCPServer TCPserver = new TCPServer(serverSocket, messageHandler);
            //System.out.println("----"+ serverSocket.getLocalSocketAddress());
            new Thread(TCPserver).start();
            System.out.println("TCP attivo");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}