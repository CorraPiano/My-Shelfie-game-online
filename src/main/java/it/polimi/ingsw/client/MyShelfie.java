package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.localModel.ModelView;

public class MyShelfie {
    // 0 server
    // 1 cli
    // 2 gui
    public static void main( String[] args ){
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

    public void startServer(){}
    public void startTui(){}
    public void startGui(){
        GUI.main(null);
    }



}



