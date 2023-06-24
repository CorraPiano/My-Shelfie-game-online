package it.polimi.ingsw.controller;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ControllerSkeleton extends Remote {
    ArrayList<LocalGame> getGameList() throws RemoteException;

    String addFirstPlayer(String name, GameMode gameMode, int numPlayer, String signature) throws RemoteException, GameModeException, GameFullException, NumPlayersException, NameAlreadyExistentException, NotBoundException;
    //String addFirstPlayer(String name, GameMode gameMode, int numPlayer, ClientSkeleton cc) throws RemoteException, GameModeException, GameFullException, NumPlayersException, NameAlreadyExistentException;

    String addPlayer(String name, int gameID, String signature) throws RemoteException, GameFullException, NameAlreadyExistentException, InvalidGameIdException, NotBoundException;
    //String addPlayer(String name, int gameID, ClientSkeleton cc) throws RemoteException, GameFullException, NameAlreadyExistentException, InvalidGameIdException;
    void pickItem(Coordinates coordinates, String id)  throws RemoteException, NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, NotInGameException, WrongTurnException, OutOfBoardPickException, InvalidIdException;

    void undoPick(String id) throws RemoteException, NotInGameException, WrongTurnException, InvalidIdException;

    void putItemList(int column, String id) throws RemoteException, EmptyHandException, NotInGameException, WrongTurnException, InvalidColumnPutException, InvalidIdException, NotEnoughSpacePutException;

    void selectInsertOrder(ArrayList<Integer> order, String id) throws RemoteException, WrongLengthOrderException, WrongContentOrderException, NotInGameException, WrongTurnException, InvalidIdException;

    void addChatMessage(ChatMessage chatMessage, String id) throws RemoteException, InvalidIdException, InvalidNameException;

    void leaveGame(String id) throws RemoteException, InvalidIdException;

    String reconnect(String id, ClientSkeleton cc, boolean reset) throws InvalidIdException, RemoteException, GameFinishedException, AlreadyConnectedException, GameLeftException;

    void ping(int n) throws RemoteException;
}