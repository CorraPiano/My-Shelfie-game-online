package it.polimi.ingsw.client.view.TUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientTUI;
import it.polimi.ingsw.client.ConnectionType;
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

    public static String IP;
    public static int port;
    //private static Sender sender;
    //private ClientTUI client;

    public static void main(String[] args) throws RemoteException {
        System.out.println(BROWN_FOREGROUND + MYSHELFIE_LOGIN + ANSI_RESET + "\n");
        ClientTUI client = new ClientTUI();
        Sender sender = setupSender(client);
        InputHandler inputHandler = new InputHandler(sender, client);
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "Client started");
        inputHandler.inputReader();

        //setupAddressAndPort(client);
        //setupCommunicationMethod(client);
    }

    public static Sender setupSender(ClientTUI client){
        Scanner stdin = new Scanner(System.in);
        Sender sender = null;
        ConnectionType connectionType;
        while(true) {
            try {
                String IP = setupIP();
                System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "The client is trying to connect to " + ANSI_CYAN + IP + ANSI_RESET  +"\n");
                connectionType = setupConnectionType();
                if (connectionType.equals(ConnectionType.RMI))
                    sender = new RMISender(IP, client);
                if (connectionType.equals(ConnectionType.TCP))
                    sender = new TCPSender(IP, client);
                break;
            } catch(Exception e){
                sender = null;
                System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "impossible to connect");
            }
        }
        return sender;
    }

    private static String setupIP() {
        Scanner in = new Scanner(System.in);
        String IP;
        System.out.println(ANSI_YELLOW + "❮INSTRUCTION❯ " + ANSI_RESET + "Please insert the IP address of the server (format: x.y.z.w)");
        System.out.println(ANSI_YELLOW + "❮INSTRUCTION❯ " + ANSI_RESET + "Insted press ENTER for default address");
        try {
            String input = in.nextLine();
            if(input.isEmpty()){
                IP = Settings.IP;
                System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "The IP address will be set to the default: " + Settings.IP + "\n");
                return IP ;
            }
            if (input.matches(Constants.IPV4_PATTERN)) {
                IP = input;
                return IP ;
            }
        } catch(Exception e) {
        };
        System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Invalid IP address!");
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "The IP address will be set to the default: " + Settings.IP + "\n");
        IP = Settings.IP;
        return IP ;
    }

    private static ConnectionType setupConnectionType() {
        System.out.println(ANSI_YELLOW + "❮INSTRUCTION❯ " + ANSI_RESET + "Select the communication protocol you are going to use:");
        System.out.println("               0 - RMI (Remote Method Invocation)");
        System.out.println("               1 - TCP (Transmission Control Protocol)");
        System.out.println("              Type the number of the desired option");

        while(true) {
            Scanner in = new Scanner(System.in);
            String line = in.next();
            try {
                if (line.equalsIgnoreCase("RMI") || line.equals("0")) {
                    return ConnectionType.RMI;
                }
                if (line.equalsIgnoreCase("TCP") || line.equals("1")) {
                    return ConnectionType.TCP;
                }
                System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Invalid input");
            } catch (Exception e) {
                System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Invalid input");
            }
        }
    }


    // da eliminare sostituite dalle nuove linee sopra in favore della riconnessione
    // da eliminare sostituite dalle nuove linee sopra in favore della riconnessione
    // da eliminare sostituite dalle nuove linee sopra in favore della riconnessione
    // da eliminare sostituite dalle nuove linee sopra in favore della riconnessione
    // da eliminare sostituite dalle nuove linee sopra in favore della riconnessione
    // da eliminare sostituite dalle nuove linee sopra in favore della riconnessione


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
            Scanner in = new Scanner(System.in);
            String line = in.next();
            try {
                if (line.equalsIgnoreCase("RMI") || line.equals("0")) {
                    //sender = new RMISender(startRMI(), client);
                    break;
                }
                if (line.equalsIgnoreCase("TCP") || line.equals("1")) {
                    sender = createTCPConnection(client);
                    break;
                }
                System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Invalid input");
            } catch(Exception e){
                System.out.println("impossible to connect");
            }
        }

        /*while(true) {
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
        }*/
        InputHandler inputHandler = new InputHandler(sender, client);
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "Client started");
        //inputHandler.joinMatch();
    }
    private static Sender createTCPConnection(Client client) throws IOException {
        TCPReceiver TCPreceiver = new TCPReceiver(client);
        ClientConnection connection = new ClientConnection(startTCP(),TCPreceiver);
        new Thread(connection).start();
        return new TCPSender(connection,client);
    }
    private static ControllerSkeleton startRMI() throws RemoteException, NotBoundException {
        Registry registry;
        ControllerSkeleton controller;
        registry = LocateRegistry.getRegistry(IP,Settings.RMIPORT);
        controller = (ControllerSkeleton) registry.lookup(Settings.remoteObjectName);
        return controller;
    }
    private static Socket startTCP() throws IOException {
        return new Socket(IP, Settings.TCPPORT);
    }
    private static void setupAddressAndPort(ClientTUI client){
        System.out.println(ANSI_YELLOW + "❮INSTRUCTION❯ " + ANSI_RESET + "Please insert the IP address of the server (format: x.y.z.w)");
        System.out.println(ANSI_YELLOW + "❮INSTRUCTION❯ " + ANSI_RESET + "Insted press ENTER for default address");
        setIPAddress();

        //System.out.println(ANSI_YELLOW + "❮INSTRUCTION❯ " + ANSI_RESET + "Please insert the port that the server is listening on");
        //setPort();

    }
    private static void setPort() {
        Scanner in = new Scanner(System.in);
        try {
            int input = in.nextInt();
            if (1024 < input && input < 8090) {
                port = input;
                return;
            }
        } catch(Exception e) {};
        System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Invalid port\n");
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "The server will be listening on the default port: " + Constants.DEFAULT_PORT + "\n");
        port = Constants.DEFAULT_PORT;
    }

    private static void setIPAddress() {
        Scanner in = new Scanner(System.in);
        try {
            String input = in.nextLine();
            if(input.isEmpty()){
                IP = Settings.IP;
                System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "The IP address will be set to the default: " + Settings.IP + "\n");
                return;
            }
            if (input.matches(Constants.IPV4_PATTERN)) {
                IP = input;
                return;
            }
        } catch(Exception e) {};
        System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Invalid IP address!");
        System.out.println(ANSI_YELLOW + "❮INFORMATION❯ " + ANSI_RESET + "The IP address will be set to the default: " + Settings.IP + "\n");
        IP = Settings.IP;
    }

    @Override
    public void setSender(Sender sender) {

    }

    @Override
    public Sender getSender() {
        return null;
    }

    @Override
    public void setClient() {

    }

    @Override
    public Client getClient() {
        return null;
    }
}
