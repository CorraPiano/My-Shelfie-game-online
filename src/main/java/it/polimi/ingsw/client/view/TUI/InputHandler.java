package it.polimi.ingsw.client.view.TUI;

import it.polimi.ingsw.client.ClientPhase;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.ClientTUI;;
import it.polimi.ingsw.client.LocalSave;
import it.polimi.ingsw.client.connection.Sender;
import it.polimi.ingsw.client.commands.*;
import java.util.Scanner;
import static it.polimi.ingsw.util.Constants.*;

public class InputHandler {
    private final ClientTUI client;
    private final Sender sender;
    private Scanner stdin;

    public InputHandler(Sender sender, ClientTUI client){
        this.sender=sender;
        this.client=client;
        stdin = new Scanner(System.in);
    }

    /** <p> </p>keep waiting and handling the input of the user, according to the phase of the client</p>
     *  <p> In the input is valid, an action is performed </p>
     */
    public void readInput() {
        client.getOutputHandler().showHomeIntro();
        String line;
        while(!client.getPhase().equals(ClientPhase.CLOSE)){
            line = stdin.next();
            try {
                switch (client.getPhase()) {
                    case HOME -> {
                        switch (line.toUpperCase()) {
                            case "CREATE" -> new CreateCommand().execute(sender, stdin, client);
                            case "RECONNECT" -> new ReconnectCommand().execute(sender, stdin, client);
                            case "JOIN" -> new JoinCommand().execute(sender, stdin, client);
                            case "LIST" -> sender.getGameList();
                            case "HELP" -> help();
                            case "EXIT" -> closeApp();
                            default -> client.getOutputHandler().showError("Unknown command");
                        }
                    }
                    case LOBBY -> {
                        switch (line.toUpperCase()) {
                            case "LEAVE" -> leave();
                            case "HELP" -> help();
                        }
                    }
                    case GAME -> {
                        switch (line.toUpperCase()) {
                            case "HELP" -> help();
                            case "LEAVE" -> leave();
                            case "PICK" -> new PickCommand().execute(sender, stdin, client);
                            case "UNDO" -> sender.undoPick();
                            case "ORDER" -> new OrderCommand().execute(sender, stdin, client);
                            case "PUT" -> new PutCommand().execute(sender, stdin, client);
                            case "SHOW_COMMON" -> client.getOutputHandler().showCommonCards();
                            case "CHAT" -> client.openChat();
                            default -> client.getOutputHandler().showError("Unknown command");
                        }
                    }
                    case CHAT -> {
                        switch (line.toUpperCase()) {
                            case "/SEND", "@" -> new SendCommand().execute(sender, stdin, client);
                            case "/BROADCAST" -> new BroadcastCommand().execute(sender, stdin, client);
                            case "/HELP" -> help();
                            case "/CLOSE" -> client.closeChat();
                            default -> new BroadcastCommand().execute(sender, stdin, line,client);
                        }
                    }
                    case HOME_RECONNECTION, MATCH_RECONNECTION -> {
                        switch (line.toUpperCase()) {
                            case "HELP" -> help();
                            case "EXIT" -> closeApp();
                            default -> client.getOutputHandler().showError("Unknown command");
                        }
                    }
                }
            } catch (Exception e){
                client.getOutputHandler().showError("Invalid input");
                //System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Invalid input");
            }
            while (client.getState().equals(ClientState.WAIT)) {
                try {
                    synchronized (client) {
                        client.wait();
                    }
                } catch (Exception ignored){}
            }
            // create a new scanner to get rid of the unread input in the buffer
            stdin = new Scanner(System.in);
        }
    }

    private void closeApp(){
        client.closeApp();
    }

    private void help(){
        client.getOutputHandler().showHelp(client.getPhase());
    }

    private void leave(){
        LocalSave.clear();
        sender.leaveGame();
    }
}