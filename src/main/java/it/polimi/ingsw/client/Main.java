package it.polimi.ingsw.client;

import it.polimi.ingsw.client.connection.*;
import it.polimi.ingsw.controller.Settings;
import it.polimi.ingsw.controller.ControllerSkeleton;

import java.io.IOException;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, AlreadyBoundException, NotBoundException {
        System.out.println("seleziona il protocollo di comunicazione:");
        System.out.println("0) RMI");
        System.out.println("1) TCP");
        Scanner stdin = new Scanner(System.in);
        Sender sender = null;
        Client client = new Client();
        while(true) {
            if(stdin.hasNext()){
                try{
                    int x = stdin.nextInt();
                    if(x==0) {
                        sender = new RMISender(startRMI(), client);
                    }
                    else if(x==1) {
                        sender = createTCPConnection(client);
                    }
                    else
                        System.out.println("input non valido");
                    break;
                } catch(Exception e){
                    stdin.nextLine();
                }
            }
        }
        GraphicsInterface graphicsInterface = new GraphicsInterface(sender, client);
        System.out.println("client avviato");
        graphicsInterface.joinMatch();
    }

    private static Sender createTCPConnection(Client client) throws IOException {
        TCPReceiver TCPreceiver = new TCPReceiver(client);
        ClientConnection connection = new ClientConnection(startTCP(),TCPreceiver);
        new Thread(connection).start();
        return new TCPSender(connection);
    }

    private static ControllerSkeleton startRMI() throws RemoteException, NotBoundException {
        Registry registry;
        ControllerSkeleton controller;
        registry = LocateRegistry.getRegistry();
        controller = (ControllerSkeleton) registry.lookup(Settings.remoteObjectName);
        return controller;
    }

    private static Socket startTCP() throws IOException {
        return new Socket("localhost", 8081);
    }
}
