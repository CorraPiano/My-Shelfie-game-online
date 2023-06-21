package it.polimi.ingsw.client.view.TUI;

import it.polimi.ingsw.client.ClientPhase;
import it.polimi.ingsw.client.ClientState;
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
    private Scanner stdin;

    public InputHandler(Sender sender, ClientTUI client){
        this.sender=sender;
        this.client=client;
        stdin = new Scanner(System.in);
    }



    public void inputReader() {
        client.getOutputHandler().presentation();
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
                            case "LIST" -> new ListCommand().execute(sender, stdin, client);
                            case "HELP" -> new HelpCommand().execute(sender, stdin, client);
                            case "EXIT" -> new ExitCommand().execute(sender, stdin, client);
                            default -> System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Unknown command");
                        }
                    }
                    /*case LOBBY -> {
                        switch (line.toUpperCase()) {
                            case "LEAVE" -> new LeaveCommand().execute(sender, stdin, client);
                            case "HELP" -> new HelpCommand().execute(sender, stdin, client);
                            case "CHAT" -> new ChatCommand().execute(sender, stdin, client);
                        }
                    }*/
                    case GAME -> {
                        switch (line.toUpperCase()) {
                            case "HELP" -> new HelpCommand().execute(sender, stdin, client);
                            case "LEAVE" -> new LeaveCommand().execute(sender, stdin, client);
                            case "PICK" -> new PickCommand().execute(sender, stdin, client);
                            case "UNDO" -> new UndoCommand().execute(sender, stdin, client);
                            case "ORDER" -> new OrderCommand().execute(sender, stdin, client);
                            case "PUT" -> new PutCommand().execute(sender, stdin, client);
                            case "SHOW" -> new CommonCardCommand().execute(sender, stdin, client);
                            case "CHAT" -> new ChatCommand().execute(sender, stdin, client);
                            default -> System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Unknown command");
                        }
                    }
                    case CHAT -> {
                        switch (line.toUpperCase()) {
                            case "/SEND", "@" -> new SendCommand().execute(sender, stdin, client);
                            case "/BROADCAST" -> new BroadcastCommand().execute(sender, stdin, client);
                            case "/HELP" -> new HelpCommand().execute(sender, stdin, client);
                            case "/CLOSE" -> client.closeChat();
                            default -> new BroadcastCommand().execute(sender, stdin, line);
                        }
                    }
                    case HOME_RECONNECTION, MATCH_RECONNECTION -> {
                        switch (line.toUpperCase()) {
                            case "HELP" -> new HelpCommand().execute(sender, stdin, client);
                            case "EXIT" -> new ExitCommand().execute(sender, stdin, client);
                            default -> System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Unknown command");
                        }
                    }
                    /*case MATCH_RECONNECTION -> {
                        switch (line.toUpperCase()) {
                            case "HELP" -> new HelpCommand().execute(sender, stdin, client);
                            case "LEAVE" -> new ExitCommand().execute(sender, stdin, client);
                            default -> System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Unknown command");
                        }
                    }*/
                }
            } catch (Exception e){
                e.printStackTrace();
                System.out.println(e);
            }
            while (client.getState().equals(ClientState.WAIT)) {
                try {
                    synchronized (client) {
                        client.wait();
                    }
                } catch (Exception e){}
            }
            stdin = new Scanner(System.in);
        }
    }




/*

    public void joinMatch(){

        presentation();

        String line;
        while(client.getPhase().equals(ClientPhase.JOIN)) {
            line = stdin.next();
            if(client.getPhase().equals(ClientPhase.JOIN)) {
                try {
                    switch (line.toUpperCase()) {
                        case "CREATE" -> new CreateCommand().execute(sender, stdin, client);
                        case "RECONNECT" -> new ReconnectCommand().execute(sender, stdin, client);
                        case "JOIN" -> new JoinCommand().execute(sender, stdin, client);
                        case "LIST" -> new ListCommand().execute(sender, stdin, client);
                        case "HELP" -> new HelpCommand().execute(sender, stdin, client);
                        //case "EXIT" -> new HelpCommand().execute(sender, stdin, client);
                        default ->  System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Unknown command");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    System.out.println(e);
                }
            }
            stdin = new Scanner(System.in);
            while (client.getState().equals(ClientState.WAIT)) {
                try {
                    synchronized (client) {
                        client.wait();
                    }
                } catch (Exception e){}
            }
            System.out.println();
        }
        if(client.getPhase().equals(ClientPhase.GAME))
            playMatch();
    }


    public void playMatch(){
        String line;
        while(client.getPhase().equals(ClientPhase.GAME)) {
            line = stdin.next();
            if(client.getPhase().equals(ClientPhase.GAME)) {
                try {
                    switch (line.toUpperCase()) {
                        case "PICK" -> new PickCommand().execute(sender, stdin, client);
                        case "UNDO" -> new UndoCommand().execute(sender, stdin, client);
                        case "ORDER" -> new OrderCommand().execute(sender, stdin, client);
                        case "PUT" -> new PutCommand().execute(sender, stdin, client);
                        case "LEAVE" -> new LeaveCommand().execute(sender, stdin, client);
                        case "HELP" -> new HelpCommand().execute(sender, stdin, client);
                        case "SHOWCOMMONCARD" -> new CommonCardCommand().execute(sender, stdin, client);
                        case "CHAT" -> exeChat();
                        default -> System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Unknown command");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            stdin = new Scanner(System.in);
            while (client.getState().equals(ClientState.WAIT)) {
                try {
                    synchronized (client) {
                        client.wait();
                    }
                } catch (Exception e){}
            }
        }
        joinMatch();
    }

    private void exeChat(){
        String line;
        client.OpenChat();
        while(client.getPhase().equals(ClientPhase.CHAT)) {
            line = stdin.next();
            //per bloccare il caso in qui la partita termini in attesa di un messaggio
            if(client.getPhase().equals(ClientPhase.CHAT)) {
                try {
                    switch (line.toUpperCase()) {
                        case "/SEND" -> new SendCommand().execute(sender, stdin, client);
                        case "/BROADCAST" -> new BroadcastCommand().execute(sender, stdin, client);
                        case "@" -> new SendCommand().execute(sender, stdin, client);
                        case "/HELP" -> new HelpCommand().execute(sender, stdin, client);
                        case "/CLOSE" -> client.closeChat();
                        default -> new BroadcastCommand().execute(sender, stdin, line);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            stdin = new Scanner(System.in);
            while (client.getState().equals(ClientState.WAIT)) {
                 try {
                    synchronized (client) {
                        client.wait();
                    }
                } catch (Exception e){}
            }
        }
    }
*/
}