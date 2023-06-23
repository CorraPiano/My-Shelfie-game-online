package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import java.util.ArrayList;
import java.util.Scanner;

import static it.polimi.ingsw.util.Constants.ANSI_RESET;
import static it.polimi.ingsw.util.Constants.ANSI_YELLOW;

public class OrderCommand  implements Command {

    public void execute(Sender sender, Scanner stdin, Client client) {

        //to be fixed
        int size = client.getHandSize();
        String line = "";
        ArrayList<Integer> list = new ArrayList<>();
        int counter =0;
        while(counter<size){
            if(stdin.hasNext()) {
                line = stdin.next();
                try {
                    int n = Integer.parseInt(line);
                    if (n >= 0) {
                        list.add(n);
                        counter++;
                    }
                    else {
                        System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Is required a list of "+ size + " positive integers");
                        return;
                    }
                } catch (Exception e){
                    System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Is required a list of "+ size +" positive integers");
                    return;
                }
            }
        }

        if(counter!=size)
            System.out.println(ANSI_YELLOW + "❮ERROR❯ " + ANSI_RESET + "Is required a list of " + size +" positive integers");
        else
            sender.selectInsertOrder(list);
    }
}
