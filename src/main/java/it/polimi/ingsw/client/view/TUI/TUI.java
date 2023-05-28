package it.polimi.ingsw.client.view.TUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientTUI;
import it.polimi.ingsw.client.connection.*;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.ControllerSkeleton;
import it.polimi.ingsw.controller.Settings;
import it.polimi.ingsw.util.Constants;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.*;

public class TUI implements View {

    private Sender sender;
    //private ClientTUI client;

    public static void main(String[] args) {

        System.out.println(BROWN_FOREGROUND + MYSHELFIE_LOGIN + ANSI_RESET + "\n");

        //setupConnection(ClientTUI client);
        ClientTUI client;
        try {
            client = new ClientTUI();
        } catch (RemoteException e) {
            System.out.println("Connection problem");
            throw new RuntimeException(e);
        }

        setupAddressAndPort(client);

        setupCommunicationMethod(client);

    }

    private void setupConnection(ClientTUI client) {
        try {
            client = new ClientTUI();
        } catch (RemoteException e) {
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Connection problem");
            throw new RuntimeException(e);
        }
    }
    private static void setupCommunicationMethod(ClientTUI client) {
        System.out.println(ANSI_YELLOW + "❮INSTRUCTION❯ " + ANSI_RESET + "Select the communication protocol you are going to use:");
        System.out.println("               0 - RMI (Remote Method Invocation)");
        System.out.println("               1 - TCP (Transmission Control Protocol)");
        System.out.println("              Type the number of the desired option");
        Scanner stdin = new Scanner(System.in);
        Sender sender = null;
        while(true) {
            if(stdin.hasNext()){
                try{
                    int x = stdin.nextInt();
                    switch (x) {
                        case 0 -> sender = new RMISender(startRMI(), client);
                        case 1 -> sender = createTCPConnection(client);
                        default -> System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Invalid input");
                    }
                    break;
                } catch(Exception e){ stdin.nextLine(); }
            }
        }
        InputHandler inputHandler = new InputHandler(sender, client);
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "Client started");
        inputHandler.joinMatch();
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
    private static void setupAddressAndPort(ClientTUI client){
        Scanner scanner = new Scanner(System.in);
        String IPAddress;
        int port;

        System.out.println(ANSI_YELLOW + "❮INSTRUCTION❯ " + ANSI_RESET + "Please insert the IP address of the server (format: x.y.z.w)");
        IPAddress = setIPAddress(scanner.nextLine());

        System.out.println(ANSI_YELLOW + "❮INSTRUCTION❯ " + ANSI_RESET + "Please insert the port that the server is listening on");
        port = setPort(scanner.nextInt());

        //devo ancora mettere queste porte da qualche parte
    }
    private static int setPort(int input) {
        if(1024 < input && input < 8090) {
            return input;
        }
        System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Invalid port\n");
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "The server will be listening on the default port: " + Constants.DEFAULT_PORT + "\n");
        return Constants.DEFAULT_PORT;
    }
    private static String setIPAddress(String input) {
        if(input.matches(Constants.IPV4_PATTERN)) {
            return input;
        }
        System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Invalid IP address!");
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "The IP address will be set to the default: " + Constants.DEFAULT_IP_ADDRESS + "\n");
        return Constants.DEFAULT_IP_ADDRESS;
    }


    // The other Methods
    @Override
    public void setSender(Sender sender) { }
    @Override
    public Sender getSender() { return null; }
    @Override
    public void setClient() { }
    @Override
    public Client getClient() { return null; }
}
