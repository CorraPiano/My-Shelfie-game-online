package it.polimi.ingsw.clientTest.commands;

import it.polimi.ingsw.clientTest.Client;
import it.polimi.ingsw.controller.ControllerSkeleton;

import java.util.Scanner;

public interface Command {
   void execute(ControllerSkeleton controller, Scanner stdin,Client client);
}
