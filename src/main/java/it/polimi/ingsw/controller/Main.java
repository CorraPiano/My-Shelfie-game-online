package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.connection.ClientConnection;
import it.polimi.ingsw.connection.MessageHandler;
import it.polimi.ingsw.connection.SocketMap;
import it.polimi.ingsw.connection.TCPServer;
import it.polimi.ingsw.connection.message.NewTurnMessage;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.util.IPLoader;

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
    public static void main(String[] args) throws IOException, AlreadyBoundException, RemoteException {

        String IP = IPLoader.getLocalIp();
        SocketMap socketMap = new SocketMap();
        //SenderTCP senderTCP = new SenderTCP(socketMap);
        Controller controller = new Controller();
        MessageHandler messageHandler = new MessageHandler(controller,socketMap);

        //String IP = insertIP();
        startTCP(controller,messageHandler);
        startRMI(controller,IP);
    }


    private static void startRMI(Controller controller,String IP) throws RemoteException {
        try{
            System.setProperty("java.rmi.server.hostname", IP);
            LocateRegistry.createRegistry(Settings.RMIPORT);
            Registry registry = LocateRegistry.getRegistry();

            registry.bind(Settings.remoteObjectName, UnicastRemoteObject.exportObject(controller, Settings.RMIPORT));
            System.out.println("RMI attivo");
        }
        catch(Exception e) {
        }
    }

    private static void startTCP(Controller controller, MessageHandler messageHandler){
        try{
            ServerSocket serverSocket = new ServerSocket(Settings.TCPPORT);
            TCPServer TCPserver = new TCPServer(serverSocket, messageHandler);
            new Thread(TCPserver).start();
            System.out.println("TCP attivo");
        } catch (Exception e){
        }
    }
}