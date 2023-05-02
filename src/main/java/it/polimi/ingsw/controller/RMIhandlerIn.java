package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class RMIhandlerIn extends UnicastRemoteObject implements Skeleton{

    //usata per salvare lo skeleton del client
    Controller controller;
    public RMIhandlerIn(Controller controller) throws RemoteException {
        this.controller=controller;
    }
    public ArrayList<String> getGameList(){
        return controller.getGameList();
    }
    public synchronized String addFirstPlayer(String name, GameMode gameMode, int numPlayer) throws RemoteException, GameModeException, GameFullException, NumPlayersException, NameAlreadyExistentException {
        return controller.addFirstPlayer(name,gameMode,numPlayer);
    }
    public synchronized String addPlayer(String name, int gameID) throws RemoteException, GameFullException, NameAlreadyExistentException, InvalidGameIdException {
        return controller.addPlayer(name,gameID);
    }
    public synchronized void pickItem(Coordinates coordinates, String id) throws RemoteException, NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, NotInGameException, WrongTurnException, OutOfBoardPickException, InvalidIdException {
        controller.pickItem(coordinates,id);
    }

    public synchronized void undoPick(String id) throws RemoteException, NotInGameException, WrongTurnException, InvalidIdException {
        controller.undoPick(id);
    }

    public synchronized void putItemList(int column, String id) throws RemoteException, EmptyHandException, NotInGameException, WrongTurnException, InvalidColumnPutException, InvalidIdException, NotEnoughSpacePutException {
        controller.putItemList(column,id);
    }

    public synchronized void selectInsertOrder(ArrayList<Integer> order, String id) throws WrongLengthOrderException, WrongContentOrderException, NotInGameException, WrongTurnException, InvalidIdException {
        controller.selectInsertOrder(order,id);
    }

    public void addChatMessage(String chatMessage,String id) throws RemoteException, InvalidIdException {
        controller.addChatMessage(chatMessage,id);
    }

    public void leaveGame(String id) throws InvalidIdException {
        controller.leaveGame(id);
    }

}
