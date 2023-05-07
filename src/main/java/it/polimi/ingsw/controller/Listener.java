package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;

public class Listener implements PropertyChangeListener {
    //private final HashMap<> connectionType;
    private final  BroadcasterRMI broadcasterRMI;
    private final HashMap<String, BiFunction<Object, Object, Integer>> functionHandlerRMI;
    private final int gameID;
    //SocketHanlerOut handlerSocket;

    public Listener(int gameID, BroadcasterRMI broadCastRMI) {
        this.gameID = gameID;
        this.broadcasterRMI = broadCastRMI;
        functionHandlerRMI = new HashMap<>();
        // x meaning that the parameter is not used in the function
        this.functionHandlerRMI.put("BOARD", (board, x)->{
            this.broadcasterRMI.updateBoard(this.gameID, (Board) board);
            return 1;
        });
        this.functionHandlerRMI.put("BOOKSHELF", (bookshelf, name)->{
            this.broadcasterRMI.updateBookshelf(this.gameID, (String) name, (Bookshelf) bookshelf);
            return 1;
        });
        this.functionHandlerRMI.put("COMMON", (commonList, x)->{
            this.broadcasterRMI.updateCommonGoalCard(this.gameID, (ArrayList<CommonGoalCard>) commonList);
            return 1;
        });
        this.functionHandlerRMI.put("PERSONAL", (personal, x)->{
            this.broadcasterRMI.sendPersonalGoalCard(this.gameID, (PersonalGoalCard) personal);
            return 1;
        });
        this.functionHandlerRMI.put("PLAYERLIST", (playerList, x)->{
            this.broadcasterRMI.updatePlayerList(this.gameID, (ArrayList<Player>) playerList);
            return 1;
        });
        this.functionHandlerRMI.put("HAND", (hand, x)->{
            this.broadcasterRMI.updateHand(this.gameID, (Hand) hand);
            return 1;
        });
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        // HashMap Connection
        String name = null;
        functionHandlerRMI.get(evt.getPropertyName()).apply(evt.getNewValue(), name);
    }

}
