package it.polimi.ingsw.controller;

import it.polimi.ingsw.connection.MessageHandler;
import it.polimi.ingsw.connection.SocketMap;
import it.polimi.ingsw.connection.TCPServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.registry.Registry;
import java.rmi.*;
import java.rmi.registry.*;

public class Main {
    public static void main(String[] args) throws IOException, AlreadyBoundException, RemoteException{
        SocketMap socketMap = new SocketMap();
        //SenderTCP senderTCP = new SenderTCP(socketMap);
        Controller controller = new Controller();
        MessageHandler messageHandler = new MessageHandler(controller,socketMap);

        startTCP(controller,messageHandler);
        startRMI(controller);


    }

    /**
     * Starts the RMI server and binds the Controller object to the registry.
     *
     * @param controller the Controller object managing the game
     * @throws RemoteException if a remote communication error occurs
     */
    private static void startRMI(Controller controller) throws RemoteException {
        //String ipAddress = new String();
        //System.setProperty("java.rmi.server.hostname", ipAddress);
        try{
            LocateRegistry.createRegistry(Settings.RMIPORT);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind(Settings.remoteObjectName, controller);
            System.out.println("RMI attivo");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the TCP server and listens for client connections.
     *
     * @param controller     the Controller object managing the game
     * @param messageHandler the MessageHandler object for handling incoming messages
     */
    private static void startTCP(Controller controller, MessageHandler messageHandler){
        try{
            // interfacce di scambio
            ServerSocket serverSocket =new ServerSocket(Settings.TCPPORT);
            TCPServer TCPserver = new TCPServer(serverSocket, messageHandler);
            new Thread(TCPserver).start();
            System.out.println("TCP attivo");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}