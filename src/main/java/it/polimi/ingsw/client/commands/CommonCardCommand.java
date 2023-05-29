package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientTUI;
import it.polimi.ingsw.client.connection.Sender;
import it.polimi.ingsw.model.GameMode;
import java.util.Scanner;
import static it.polimi.ingsw.util.Constants.*;

public class CommonCardCommand  implements Command {

    public void execute(Sender sender, Scanner stdin, Client client) {
        if(client.getModelView().getGameMode().compareTo(GameMode.EASY) == 1) {
            System.out.println(BROWN_FOREGROUND + "Here are the current common cards and their description:\n" + ANSI_RESET);
            client.getOutputHandler().showCommonCards(client.getModelView().getCommonCards().get(0));
            client.getOutputHandler().showCommonCards(client.getModelView().getCommonCards().get(1));
        } else {
            System.out.println(BROWN_FOREGROUND + "There are no CommonGoalCards in EASY game-mode"+ ANSI_RESET);
        }
    }
}
