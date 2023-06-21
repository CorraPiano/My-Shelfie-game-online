package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import java.util.Objects;
import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

public class JoinCommand implements Command {

    public void execute(Sender sender, Scanner stdin, Client client) {
        String name;
        int gameID;

        try{
            name = stdin.next();
            gameID = stdin.nextInt();
        } catch(Exception e){
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "\n" + "invalid command parameters");
            return;
        }

        if(Objects.equals(name, "")){
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "The name entered is not valid");
            return;
        }

        if(gameID<0){
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "The gameID entered is not valid");
            return;
        }

        //client.putInWait();
        sender.addPlayer(name, gameID);
    }
}
