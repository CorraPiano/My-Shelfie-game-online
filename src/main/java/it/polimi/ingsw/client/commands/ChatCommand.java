package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientTUI;
import it.polimi.ingsw.client.connection.Sender;

import java.util.Scanner;

public class ChatCommand implements Command {
    public void execute(Sender sender, Scanner stdin, Client client) {
        ((ClientTUI)client).openChat();
    }
}
