package it.polimi.ingsw.clientTest.commands;

import it.polimi.ingsw.clientTest.Client;
import it.polimi.ingsw.clientTest.ConnectionType;
import it.polimi.ingsw.clientTest.Sender;
import it.polimi.ingsw.model.GameMode;

import java.util.Objects;
import java.util.Scanner;

public class CreateCommand implements Command{
    @Override
    public void execute(Sender sender, Scanner stdin, Client client) {
        String name = stdin.next();
        String gameModeString = stdin.next();
        int num = stdin.nextInt();
        GameMode gamemode;

        if(Objects.equals(name, "")) {
            System.out.println("CLIENT:: nome non valido");
            return;
        }

        if(gameModeString.equalsIgnoreCase("EASY") || gameModeString.equals("0"))
            gamemode = GameMode.EASY;
        else if(gameModeString.equalsIgnoreCase("EXPERT") || gameModeString.equals("1"))
            gamemode = GameMode.EXPERT;
        else{
            System.out.println("CLIENT:: gamemode non valida");
            return;
        }

        if(num<2 || num>4) {
            System.out.println("CLIENT:: nome giocatori non valido");
            return;
        }

        sender.addFirstPlayer(name, gamemode, num);
    }
}
