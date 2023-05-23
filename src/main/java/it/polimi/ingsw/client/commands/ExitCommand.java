package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.connection.Sender;
import it.polimi.ingsw.client.*;
import java.util.Scanner;

public class ExitCommand implements Command {

    public void execute(Sender sender, Scanner stdin, Client client) {
        sender.leaveGame();
    }
}