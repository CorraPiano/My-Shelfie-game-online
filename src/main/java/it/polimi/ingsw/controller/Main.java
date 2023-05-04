package it.polimi.ingsw.controller;

import it.polimi.ingsw.connection.TCPServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.registry.Registry;
import java.rmi.*;
import java.rmi.registry.*;

public class Main {
    public static void main(String[] args) throws IOException, AlreadyBoundException, RemoteException{
        Controller controller = new Controller();
        startRMI(controller);
        startTCP(controller);

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
    private static void startTCP(Controller controller){
        try{
            MessageHandler messageHandler = new MessageHandler(controller);
            ServerSocket serverSocket =new ServerSocket(8080);
            TCPServer TCPserver = new TCPServer(serverSocket, messageHandler);
            new Thread(TCPserver).start();
            System.out.println("TCP attivo");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}