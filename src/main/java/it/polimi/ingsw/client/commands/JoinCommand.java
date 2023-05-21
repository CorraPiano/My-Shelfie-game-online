package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.ControllerSkeleton;

import java.util.Objects;
import java.util.Scanner;

public class JoinCommand implements Command {
    @Override
    public void execute(ControllerSkeleton controller, Scanner stdin, Client client) {
        String name = stdin.next();
        int num = stdin.nextInt();

        if(Objects.equals(name, "")){
            System.out.println("CLIENT:: nome non valido");
            return;
        }

        if(num<0){
            System.out.println("CLIENT:: numero partita non valida");
            return;
        }

        try {
            String ID=controller.addPlayer(name,num,client);
            client.getID(ID);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
