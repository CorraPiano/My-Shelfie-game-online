package it.polimi.ingsw.clientTest;

import it.polimi.ingsw.clientTest.commands.*;
import it.polimi.ingsw.controller.ControllerSkeleton;

import java.util.Scanner;

public class GraphicsInterface {
    private final Client client;
    private final ControllerSkeleton controller;
    private final Scanner stdin;

    public GraphicsInterface(ControllerSkeleton controller, Client client){
        this.controller=controller;
        this.client=client;
        stdin = new Scanner(System.in);
    }

    public void joinMatch(){
        String line;
        while(!client.getState()) {
            if (stdin.hasNext()) {
                line = stdin.next();
                switch (line.toUpperCase()) {
                    case "CREATE":
                        new CreateCommand().execute(controller,stdin,client);
                        break;
                    case "JOIN":
                        new JoinCommand().execute(controller,stdin,client);
                        break;
                    case "LIST":
                        new ListCommand().execute(controller,stdin,client);
                        break;
                    default:
                        System.out.println("CLIENT:: comando sconosciuto");
                        line = stdin.nextLine();
                }
            }
        }
        playMatch();
    }

    public void playMatch(){
        String line;
        while(client.getState()) {
            if (stdin.hasNext()) {
                line = stdin.next();
                switch (line.toUpperCase()) {
                    case "PICK":
                        new PickCommand().execute(controller,stdin,client);
                        break;
                    case "UNDO":
                        new UndoCommand().execute(controller,stdin,client);
                        break;
                    case "ORDER":
                        new OrderCommand().execute(controller,stdin,client);
                        break;
                    case "PUT":
                        new PutCommand().execute(controller,stdin,client);
                        break;
                    case "SEND":
                        new SendCommand().execute(controller,stdin,client);
                        break;
                    case"EXIT":
                        new ExitCommand().execute(controller,stdin,client);
                        break;
                    default:
                        System.out.println("CLIENT:: comando sconosciuto");
                        line = stdin.nextLine();
                }
            }
        }
        joinMatch();
    }

}
