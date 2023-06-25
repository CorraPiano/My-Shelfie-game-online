package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;

import java.util.Scanner;

/**
 * The `Command` interface represents a command that can be executed by the client application.
 * Classes implementing this interface should provide the `execute` method to perform the command's functionality.
 */
public interface Command {

   /**
    * Executes the command.
    *
    * @param controller The controller or sender object responsible for executing the command.
    * @param stdin      The `Scanner` object used for reading user input.
    * @param client     The `Client` object representing the client application.
    */
   void execute(Sender controller, Scanner stdin, Client client);

}
