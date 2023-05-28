package it.polimi.ingsw.client.view.TUI;

import it.polimi.ingsw.client.ClientTUI;
import it.polimi.ingsw.client.commands.CommonCardCommand;
import it.polimi.ingsw.client.commands.HelpCommand;
import it.polimi.ingsw.client.connection.Sender;
import it.polimi.ingsw.client.commands.*;

import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.*;

public class InputHandler {
    private final ClientTUI client;
    private final Sender sender;
    private final Scanner stdin;

    public InputHandler(Sender sender, ClientTUI client){
        this.sender=sender;
        this.client=client;
        stdin = new Scanner(System.in);
    }

    public void joinMatch(){

        presentation();

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
                        System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Unknown command");
                        line = stdin.nextLine();
                }
            }
        }
        playMatch();
    }

    private void presentation() {
        System.out.println(BROWN_FOREGROUND + "\n───────────────────────────────── ❮❰♦❱❯ ─────────────────────────────────" + ANSI_RESET);
        System.out.println(BROWN_FOREGROUND + MYSHELFIE_LOBBY + ANSI_RESET + "\n");
        System.out.println(
                ANSI_YELLOW + "❮INSTRUCTION❯ " + ANSI_RESET + "Here you can the use the following commands with their format:\n" +
                "              ➤ CREATE <username> <gamemode> <number_of_players>: to create a new game\n" +
                "                       (EASY -> gamemode = 0, EXPERT -> gamemode = 1)\n" +
                "              ➤ JOIN <username> <gameID>: to join an existing game\n" +
                "              ➤ LIST: to have the list of the current free games");
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
                        System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Unknown command");
                        line = stdin.nextLine();
                    }
                }
            }
        }
        joinMatch();
    }

}