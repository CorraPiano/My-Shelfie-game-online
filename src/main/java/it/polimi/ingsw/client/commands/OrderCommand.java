package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import java.util.ArrayList;
import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

public class OrderCommand  implements Command {

    public void execute(Sender sender, Scanner stdin, Client client) {
        String line = "";
        ArrayList<Integer> list = new ArrayList<>();
        int counter =0;
        while(!line.equals(".") && counter<3){
            if(stdin.hasNext()) {
                line = stdin.next();
                try {
                    int n = Integer.parseInt(line);
                    if (n >= 0) {
                        list.add(n);
                        counter++;
                    }
                    else {
                        System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Is required a list of integers");
                        return;
                    }
                } catch (Exception e){
                    break;
                }
            }
        }

        if(counter<2){
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Is required a list of at least 2 integers");
            return;
        }
        if(counter>3){
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Is required a list of at most 3 integers");
            return;
        }

        sender.selectInsertOrder(list);
    }
}
