package it.polimi.ingsw.client;

import it.polimi.ingsw.client.commands.CommonCardCommand;
import it.polimi.ingsw.client.commands.HelpCommand;
import it.polimi.ingsw.client.connection.Sender;
import it.polimi.ingsw.client.commands.*;

import java.util.Scanner;

public class GraphicsInterface {
    private final Client client;
    private final Sender sender;
    private final Scanner stdin;

    public GraphicsInterface(Sender sender, Client client){
        this.sender=sender;
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
                        new CreateCommand().execute(sender,stdin,client);
                        break;
                    case "JOIN":
                        new JoinCommand().execute(sender,stdin,client);
                        break;
                    case "LIST":
                        new ListCommand().execute(sender,stdin,client);
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
                    case "PICK" -> new PickCommand().execute(sender, stdin, client);
                    case "UNDO" -> new UndoCommand().execute(sender, stdin, client);
                    case "ORDER" -> new OrderCommand().execute(sender, stdin, client);
                    case "PUT" -> new PutCommand().execute(sender, stdin, client);
                    case "SEND" -> new SendCommand().execute(sender, stdin, client);
                    case "EXIT" -> new ExitCommand().execute(sender, stdin, client);
                    case "HELP" -> new HelpCommand().execute(sender, stdin, client);
                    case "SHOWCOMMONCARD" -> new CommonCardCommand().execute(sender, stdin, client);
                    default -> {
                        System.out.println("CLIENT:: comando sconosciuto");
                        line = stdin.nextLine();
                    }
                }
            }
        }
        joinMatch();
    }

}