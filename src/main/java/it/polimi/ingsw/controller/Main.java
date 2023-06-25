package it.polimi.ingsw.controller;

import it.polimi.ingsw.connection.MessageHandler;
import it.polimi.ingsw.connection.SocketMap;
import it.polimi.ingsw.connection.TCPServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.registry.Registry;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) throws IOException, AlreadyBoundException, RemoteException{

        SocketMap socketMap = new SocketMap();
        //SenderTCP senderTCP = new SenderTCP(socketMap);
        Controller controller = new Controller();
        MessageHandler messageHandler = new MessageHandler(controller,socketMap);

        startTCP(controller,messageHandler);
        startRMI(controller);
    }
    private static void startRMI(Controller controller) throws RemoteException {
        //String ipAddress = new String();
        //System.setProperty("java.rmi.server.hostname", ipAddress);
        try{
            System.setProperty("java.rmi.server.hostname", "192.168.1.6");
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
            ServerSocket serverSocket =new ServerSocket(Settings.TCPPORT);
            TCPServer TCPserver = new TCPServer(serverSocket, messageHandler);
            new Thread(TCPserver).start();
            System.out.println("TCP attivo");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}