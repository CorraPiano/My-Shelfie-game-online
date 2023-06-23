package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import it.polimi.ingsw.client.LocalSave;

import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

public class ReconnectCommand implements Command{
    public void execute(Sender sender, Scanner stdin, Client client) {
        String id;

        /* id = LocalSave.recoverID();
        if(id==null) {
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "There isn't a game to reconnect");
            return;
        }*/

        try{
            String name = stdin.next();
            int gameID = stdin.nextInt();
            id = name +"_"+gameID;
        } catch(Exception e){
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "\n" + "invalid command parameters");
            return;
        }

        sender.reconnectGame(id);
    }
}
