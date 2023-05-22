package it.polimi.ingsw.client;

import it.polimi.ingsw.client.commands.*;
import it.polimi.ingsw.controller.ControllerSkeleton;

import java.util.Scanner;

public class GraphicsInterface {
    private final it.polimi.ingsw.client.Client client;
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
                    case "CREATE" -> {
                        new CreateCommand().execute(controller, stdin, client);
                    }
                    case "JOIN" -> {
                        new JoinCommand().execute(controller, stdin, client);
                    }
                    case "LIST" -> {
                        new ListCommand().execute(controller, stdin, client);
                    }
                    default -> {
                        System.out.println("CLIENT:: comando sconosciuto");
                        line = stdin.nextLine();
                    }
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
                    case "PICK" -> new PickCommand().execute(controller, stdin, client);
                    case "UNDO" -> new UndoCommand().execute(controller, stdin, client);
                    case "ORDER" -> new OrderCommand().execute(controller, stdin, client);
                    case "PUT" -> new PutCommand().execute(controller, stdin, client);
                    case "SEND" -> new SendCommand().execute(controller, stdin, client);
                    case "EXIT" -> new ExitCommand().execute(controller, stdin, client);
                    case "HELP" -> new HelpCommand().execute(controller, stdin, client);
                    case "SHOWCOMMONCARD" -> new CommonCardCommand().execute(controller, stdin, client);
                    default -> {
                        System.out.println("CLIENT:: comando sconosciutoooooo");
                        line = stdin.nextLine();
                    }
                }
            }
        }
        joinMatch();
    }

}