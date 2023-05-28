package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.TUI.TUI;
import it.polimi.ingsw.controller.Main;

import java.io.IOException;
import java.rmi.AlreadyBoundException;

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
                // Terminal help
            }
        } else {
            // Terminal help
        }
    }

    public void startServer() throws AlreadyBoundException, IOException {
        Main.main(null);
    }
    public void startTui(){ TUI.main(null);
    }
    public void startGui(){
        GUI.main(null);
    }



}



