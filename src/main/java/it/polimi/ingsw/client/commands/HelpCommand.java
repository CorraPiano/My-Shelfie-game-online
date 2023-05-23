package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.*;

public class HelpCommand implements Command {

    public void execute(Sender sender, Scanner scanner, Client client) {
        System.out.println(BROWN_FOREGROUND + "Here is the list of the commands:\n" +
                " ➤ " + ANSI_GREEN + "PICK [row] [column]" + BROWN_FOREGROUND + ": use this command in order to take an Item from the board\n" +
                " ➤ " + ANSI_GREEN + "PUT [column]" + BROWN_FOREGROUND + ": if you have some Items in your hand you can put them in the bookshelf\n" +
                " ➤ " + ANSI_GREEN + "UNDO" + BROWN_FOREGROUND + ": if you realized that you made a mistake in taking the item you can redo the action\n" +
                " ➤ " + ANSI_GREEN + "SHOWCOMMONCARD" + BROWN_FOREGROUND + ": use this command tu see the description of the current common cards\n" +
                " ➤ " + ANSI_GREEN + "SEND [message]" + BROWN_FOREGROUND + ": with this command you can chat with the other players\n" + ANSI_GREEN);
    }
}
