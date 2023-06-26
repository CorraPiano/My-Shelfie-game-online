package it.polimi.ingsw.client.view.TUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientTUI;
import it.polimi.ingsw.client.ConnectionType;
import it.polimi.ingsw.client.connection.*;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Settings;
import it.polimi.ingsw.util.Constants;
import java.rmi.RemoteException;
import java.util.Scanner;
import static it.polimi.ingsw.util.Constants.*;

public class TUI implements View {

    public static int port;
    private static OutputHandler outputHandler;

    public static void main(String[] args) throws RemoteException {
        System.out.println(BROWN_FOREGROUND + MYSHELFIE_LOGIN + ANSI_RESET + "\n");
        outputHandler = new OutputHandler();
        ClientTUI client = new ClientTUI(outputHandler);
        Sender sender = setupSender(client);
        InputHandler inputHandler = new InputHandler(sender, client);
        outputHandler.showInformation("Client started");
        //System.out.println(ANSI_YELLOW + "<<INFORMATION>> " + ANSI_RESET + "Client started");
        inputHandler.readInput();
    }

    /** return a TCP or RMI sender, used to send data towards the server
     */
    public static Sender setupSender(ClientTUI client){
        Sender sender = null;
        ConnectionType connectionType;
        while(true) {
            try {
                String IP = setupIP();
                outputHandler.showInformation(ANSI_RESET + "The client is trying to connect to " + ANSI_CYAN + IP + ANSI_RESET  +"\n");
                //System.out.println(ANSI_YELLOW + "<<INFORMATION❯>> " + ANSI_RESET + "The client is trying to connect to " + ANSI_CYAN + IP + ANSI_RESET  +"\n");
                connectionType = setupConnectionType();
                if (connectionType.equals(ConnectionType.RMI))
                    sender = new RMISender(IP, client);
                if (connectionType.equals(ConnectionType.TCP))
                    sender = new TCPSender(IP, client);
                break;
            } catch(Exception e){
                sender = null;
                outputHandler.showError("impossible to connect");
                //System.out.println(ANSI_PINK+ "<<ERROR>> " + ANSI_RESET + "impossible to connect");
            }
        }
        return sender;
    }

    private static String setupIP() {
        Scanner in = new Scanner(System.in);
        String IP;
        outputHandler.showInstruction("Please insert the IP address of the server (format: x.y.z.w)");
        outputHandler.showInstruction("Insted press ENTER for default address");
        //System.out.println(ANSI_YELLOW + "<<INSTRUCTION>> " + ANSI_RESET + "Please insert the IP address of the server (format: x.y.z.w)");
        //System.out.println(ANSI_YELLOW + "<<INSTRUCTION>> " + ANSI_RESET + "Insted press ENTER for default address");
        try {
            String input = in.nextLine();
            if(input.isEmpty()){
                IP = Settings.IP;
                outputHandler.showInformation("The IP address will be set to the default: " + Settings.IP + "\n");
                //System.out.println(ANSI_YELLOW + "<<INFORMATION>> " + ANSI_RESET + "The IP address will be set to the default: " + Settings.IP + "\n");
                return IP ;
            }
            if (input.matches(Constants.IPV4_PATTERN)) {
                IP = input;
                return IP ;
            }
            outputHandler.showError("Invalid IP address!");
            //System.out.println(ANSI_PINK+ "<<ERROR>> " + ANSI_RESET + "Invalid IP address!");
        } catch(Exception e) {
            outputHandler.showError("Incorrect input!");
            //System.out.println(ANSI_PINK + "<<ERROR>> " + ANSI_RESET + "Incorrect input!");
        }
        outputHandler.showInformation("The IP address will be set to the default: " + Settings.IP + "\n");
        //System.out.println(ANSI_YELLOW + "<<INFORMATION>> " + ANSI_RESET + "The IP address will be set to the default: " + Settings.IP + "\n");
        IP = Settings.IP;
        return IP ;
    }

    private static ConnectionType setupConnectionType() {
        outputHandler.showSetupInstruction();
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
                outputHandler.showError("Invalid input");
                //System.out.println(ANSI_PINK + "<<ERROR>> " + ANSI_RESET + "Invalid input");
            } catch (Exception e) {
                outputHandler.showError("Invalid input");
                //System.out.println(ANSI_PINK + "<<ERROR>> " + ANSI_RESET + "Invalid input");
            }
        }
    }

    // da sistemare sotto: inutili
    public void setSender(Sender sender) {}
    public Sender getSender() {return null;}
    public void setClient() {}
    public Client getClient() {return null;}
}
