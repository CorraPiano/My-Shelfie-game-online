package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.*;

public class HelpCommand implements Command {

    public void execute(Sender sender, Scanner scanner, Client client) {
        switch (client.getPhase()) {
            case HOME -> {
                System.out.println(BROWN_FOREGROUND + "\nYou can start a new game or join an existing one" + ANSI_RESET);
                System.out.println(BROWN_FOREGROUND + "Here is the list of the commands:\n" +
                        " ➤ " + ANSI_GREEN + "LIST" + BROWN_FOREGROUND + ": ... \n" +
                        " ➤ " + ANSI_GREEN + "CREATE" + BROWN_FOREGROUND + ": ... \n" +
                        " ➤ " + ANSI_GREEN + "JOIN" + BROWN_FOREGROUND + ": ... \n" +
                        " ➤ " + ANSI_GREEN + "RECONNECT" + BROWN_FOREGROUND + ": ... \n" +
                        " ➤ " + ANSI_GREEN + "EXIT" + BROWN_FOREGROUND + ":  ... \n" +
                        " ➤ " + ANSI_GREEN + "HELP" + BROWN_FOREGROUND + ":  ...  \n" + ANSI_RESET);
            }
            case LOBBY -> {
                System.out.println(BROWN_FOREGROUND + "\nYou are waiting for the other players" + ANSI_RESET);
                System.out.println(BROWN_FOREGROUND + "Here is the list of the commands:\n" +
                        " ➤ " + ANSI_GREEN + "LEAVE" + BROWN_FOREGROUND + ": with this command you will end the game FOR ALL THE PLAYERS \n" +
                        " ➤ " + ANSI_GREEN + "HELP" + BROWN_FOREGROUND + ": use this command for a description of the avaiable commands \n" + ANSI_RESET);
            }
            case GAME -> {
                System.out.println(BROWN_FOREGROUND + "\nYou are playing the game" + ANSI_RESET);
                System.out.println(BROWN_FOREGROUND + "Here is the list of the commands:\n" +
                    " ➤ " + ANSI_GREEN + "PICK [row] [column]" + BROWN_FOREGROUND + ": use this command in order to take an Item from the board\n" +
                    " ➤ " + ANSI_GREEN + "PUT [column]" + BROWN_FOREGROUND + ": if you have some Items in your hand you can put them in the bookshelf\n" +
                    " ➤ " + ANSI_GREEN + "ORDER [int] {int} {int} " + BROWN_FOREGROUND + ": the items in the hand will be put according with their position in the list\n" +
                    " ➤ " + ANSI_GREEN + "UNDO" + BROWN_FOREGROUND + ": if you realized that you made a mistake in taking the item you can redo the action\n" +
                    " ➤ " + ANSI_GREEN + "SHOW" + BROWN_FOREGROUND + ": use this command tu see the description of the current common cards\n" +
                    " ➤ " + ANSI_GREEN + "CHAT" + BROWN_FOREGROUND + ": with this command you can open the chat\n" +
                    " ➤ " + ANSI_GREEN + "LEAVE" + BROWN_FOREGROUND + ": with this command you will leave the game \n" +
                    " ➤ " + ANSI_GREEN + "HELP" + BROWN_FOREGROUND + ": use this command for a description of the avaiable commands \n" + ANSI_RESET);
            }
            case MATCH_RECONNECTION -> {
                System.out.println(BROWN_FOREGROUND + "\nYou have lost the connection! We are trying to reconnect you to the game" + ANSI_RESET);
                System.out.println(BROWN_FOREGROUND + "Here is the list of the commands:\n" +
                        " ➤ " + ANSI_GREEN + "EXIT" + BROWN_FOREGROUND + ": with this command you will stop the attempt to reconnect and close the app\n" +
                        " ➤ " + ANSI_GREEN + "HELP" + BROWN_FOREGROUND + ": use this command for a description of the avaiable commands \n" + ANSI_RESET);
            }
            case HOME_RECONNECTION -> {
                System.out.println(BROWN_FOREGROUND + "\nYou have lost the connection! We are trying to reconnect you to the server" + ANSI_RESET);
                System.out.println(BROWN_FOREGROUND + "Here is the list of the commands:\n" +
                        " ➤ " + ANSI_GREEN + "EXIT" + BROWN_FOREGROUND + ": with this command you will stop the attempt to reconnect and close the app \n" +
                        " ➤ " + ANSI_GREEN + "HELP" + BROWN_FOREGROUND + ": use this command for a description of the avaiable commands \n" + ANSI_RESET);
            }
            case CHAT -> {
                System.out.println(BROWN_FOREGROUND + "\nHere you can write to the other players" + ANSI_RESET);
                System.out.println(BROWN_FOREGROUND + "Everything you write will be sent to all the player after pushing ENTER\n" +
                        "Here is the list of the commands, which have to begin with '/':\n" +
                        " ➤ " + ANSI_GREEN + "/SEND [player name] [message]" + BROWN_FOREGROUND + ": send a message to a specific player\n" +
                        " ➤ " + ANSI_GREEN + "/BROADCAST  [message]" + BROWN_FOREGROUND + ": send a message to everyone \n" +
                        " ➤ " + ANSI_GREEN + "/CLOSE " + BROWN_FOREGROUND + ": use this command to close the chat \n" +
                        " ➤ " + ANSI_GREEN + "/HELP" + BROWN_FOREGROUND + ": use this command for a description of the avaiable commands \n" + ANSI_RESET);
            }
        }
    }
}
