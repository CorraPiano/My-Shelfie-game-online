package it.polimi.ingsw.client;

import it.polimi.ingsw.client.commands.BroadcastCommand;
import it.polimi.ingsw.client.commands.SendCommand;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.GUI.GUIMain;
import it.polimi.ingsw.client.view.TUI.TUI;
import it.polimi.ingsw.controller.Main;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

/**
 * The MyShelfie class serves as the entry point for launching different components of the application,
 * such as the server, TUI (Text-based User Interface), and GUI (Graphical User Interface).
 */
public class MyShelfie {
    // 0 server
    // 1 cli
    // 2 gui

    /**
     * The main method of the application.
     *
     * @param args Command-line arguments.
     * @throws AlreadyBoundException If the server is already bound.
     * @throws IOException           If an I/O error occurs.
     */
    public static void main( String[] args ) throws AlreadyBoundException, IOException {
        MyShelfie app = new MyShelfie();
        if (args.length > 0) {
            String startOption = args[0];

            if (startOption.equalsIgnoreCase("server") || startOption.equals("0"))
                app.startServer();
            else if (startOption.equalsIgnoreCase("tui") || startOption.equals("1"))
                app.startTui();
            else if (startOption.equalsIgnoreCase("gui") || startOption.equals("2"))
                app.startGui();
            else {
                app.choseOption();
            }
        } else {
            app.choseOption();
        }
    }

    /**
     * Starts the server component of the application.
     *
     * @throws AlreadyBoundException If the server is already bound.
     * @throws IOException           If an I/O error occurs.
     */
    public void startServer() throws AlreadyBoundException, IOException {
        Main.main(null);
    }

    /**
     * Starts the TUI (Text-based User Interface) component of the application.
     *
     * @throws RemoteException If a remote communication error occurs.
     */
    public void startTui() throws RemoteException { TUI.main(null);
    }

    /**
     * Starts the GUI (Graphical User Interface) component of the application.
     */
    public void startGui(){
        GUIMain.main(null);
    }

    private void choseOption() throws AlreadyBoundException, IOException {
        System.out.println("Select the app you want to launch ");
        System.out.println("  0 - SERVER");
        System.out.println("  1 - TUI");
        System.out.println("  2 - GUI");
        while(true) {
            try {
                Scanner in = new Scanner(System.in);
                String line = in.next();
                if (line.equalsIgnoreCase("server") || line.equals("0")) {
                    startServer();
                    break;
                }
                if (line.equalsIgnoreCase("tui") || line.equals("1")) {
                    startTui();
                    break;
                }
                if (line.equalsIgnoreCase("gui") || line.equals("2")) {
                    startGui();
                    break;
                } else
                    System.out.println("not valid option");
            } catch (Exception e){
            }
        }
    }


}



