package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.connection.Sender;
import it.polimi.ingsw.model.GameMode;
import java.util.Scanner;
import static it.polimi.ingsw.util.Constants.*;

public class CommonCardCommand  implements Command {

    public void execute(Sender sender, Scanner stdin, Client client) {
        System.out.println(BROWN_FOREGROUND + "Here are the current common cards and their description:\n" + ANSI_RESET);
        if(client.getModelView().getGameMode().compareTo(GameMode.EASY) == 1) {
            client.getViewhandler().showCommonCards(client.getModelView().getCommonCards().get(0));
            client.getViewhandler().showCommonCards(client.getModelView().getCommonCards().get(1));
        } else {
            System.out.println("There are no CommonGoalCards in EASY game-mode");
        }
    }
}
