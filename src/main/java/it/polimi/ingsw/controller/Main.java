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
    private static void startRMI(Controller controller) throws RemoteException {
        try{
            LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind(Settings.remoteObjectName, controller);
            System.out.println("RMI attivo");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    private static void startTCP(Controller controller, MessageHandler messageHandler){
        try{
            // interfacce di scambio
            ServerSocket serverSocket =new ServerSocket(8081);
            TCPServer TCPserver = new TCPServer(serverSocket, messageHandler);
            new Thread(TCPserver).start();
            System.out.println("TCP attivo");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}