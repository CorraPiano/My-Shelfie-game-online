package it.polimi.ingsw.client;

import it.polimi.ingsw.client.commands.BroadcastCommand;
import it.polimi.ingsw.client.commands.SendCommand;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.TUI.TUI;
import it.polimi.ingsw.controller.Main;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

public class MyShelfie {
    // 0 server
    // 1 cli
    // 2 gui
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

    public void startServer() throws AlreadyBoundException, IOException {
        Main.main(null);
    }
    public void startTui() throws RemoteException { TUI.main(null);
    }
    public void startGui(){
        GUI.main(null);
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
                e.printStackTrace();
            }
        }
    }


}



