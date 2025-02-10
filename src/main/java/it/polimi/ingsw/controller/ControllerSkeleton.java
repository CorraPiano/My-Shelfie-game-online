package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.connection.ReconnectType;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * The remote interface for the controller of the game.
 * This interface defines the methods that can be called remotely by clients.
 */
public interface ControllerSkeleton extends Remote {

    /**
     * Retrieves the list of active local games.
     *
     * @return an ArrayList of LocalGame instances representing the active local games.
     * @throws RemoteException if a remote communication error occurs.
     */
    ArrayList<LocalGame> getGameList() throws RemoteException;

    /**
     * Adds the first player to a new game.
     *
     * @param name      the name of the player.
     * @param gameMode  the game mode.
     * @param numPlayer the number of players in the game.
     * @param cc        the client callback object.
     * @return a String representing the unique identifier of the game.
     * @throws RemoteException              if a remote communication error occurs.
     * @throws GameModeException            if the specified game mode is invalid.
     * @throws GameFullException            if the game is already full and cannot accept new players.
     * @throws NumPlayersException          if the specified number of players is invalid for the game mode.
     * @throws NameAlreadyExistentException if the specified name is already used by another player.
     * @throws NotBoundException            if the client callback object is not bound.
     */
    String addFirstPlayer(String name, GameMode gameMode, int numPlayer, ClientSkeleton cc) throws RemoteException, GameModeException, GameFullException, NumPlayersException, NameAlreadyExistentException, NotBoundException;

    /**
     * Adds a player to an existing game.
     *
     * @param name    the name of the player.
     * @param gameID  the unique identifier of the game.
     * @param cc      the client callback object.
     * @return a String representing the unique identifier of the game.
     * @throws RemoteException              if a remote communication error occurs.
     * @throws GameFullException            if the game is already full and cannot accept new players.
     * @throws NameAlreadyExistentException if the specified name is already used by another player.
     * @throws InvalidGameIdException       if the specified game identifier is invalid.
     * @throws NotBoundException            if the client callback object is not bound.
     */
    String addPlayer(String name, int gameID, ClientSkeleton cc) throws RemoteException, GameFullException, NameAlreadyExistentException, InvalidGameIdException, NotBoundException;

    /**
     * Picks an item from the board.
     *
     * @param coordinates the coordinates of the item on the board.
     * @param id          the unique identifier of the player.
     * @throws RemoteException           if a remote communication error occurs.
     * @throws NotLinearPickException     if the item cannot be picked linearly.
     * @throws LimitReachedPickException if the pick limit has been reached.
     * @throws NotCatchablePickException  if the item cannot be caught.
     * @throws EmptySlotPickException     if the slot is empty and does not contain an item.
     * @throws NotInGameException         if the player is not part of the game.
     * @throws WrongTurnException         if it's not the player's turn.
     * @throws OutOfBoardPickException    if the specified coordinates are outside the board.
     * @throws InvalidIdException         if the specified player identifier is invalid.
     */
    void pickItem(Coordinates coordinates, String id) throws RemoteException, NotLinearPickException, LimitReachedPickException, NotCatchablePickException, EmptySlotPickException, NotInGameException, WrongTurnException, OutOfBoardPickException, InvalidIdException;

    /**
     * Undoes the last pick action of the player.
     *
     * @param id the unique identifier of the player.
     * @throws RemoteException     if a remote communication error occurs.
     * @throws NotInGameException  if the player is not part of the game.
     * @throws WrongTurnException  if it's not the player's turn.
     * @throws InvalidIdException  if the specified player identifier is invalid.
     */
    void undoPick(String id) throws RemoteException, NotInGameException, WrongTurnException, InvalidIdException;

    /**
     * Puts an item in the player's item list.
     *
     * @param column the column where the item should be placed.
     * @param id     the unique identifier of the player.
     * @throws RemoteException          if a remote communication error occurs.
     * @throws EmptyHandException       if the player's hand is empty and does not contain any items.
     * @throws NotInGameException       if the player is not part of the game.
     * @throws WrongTurnException       if it's not the player's turn.
     * @throws InvalidColumnPutException if the specified column is invalid for the item placement.
     * @throws InvalidIdException       if the specified player identifier is invalid.
     * @throws NotEnoughSpacePutException if there is not enough space to put the item in the specified column.
     */
    void putItemList(int column, String id) throws RemoteException, EmptyHandException, NotInGameException, WrongTurnException, InvalidColumnPutException, InvalidIdException, NotEnoughSpacePutException;

    /**
     * Selects the order for inserting items into the player's item list.
     *
     * @param order the order of the columns for item insertion.
     * @param id    the unique identifier of the player.
     * @throws RemoteException           if a remote communication error occurs.
     * @throws WrongLengthOrderException  if the length of the order list is incorrect.
     * @throws WrongContentOrderException if the content of the order list is incorrect.
     * @throws NotInGameException         if the player is not part of the game.
     * @throws WrongTurnException         if it's not the player's turn.
     * @throws InvalidIdException         if the specified player identifier is invalid.
     */
    void selectInsertOrder(ArrayList<Integer> order, String id) throws RemoteException, WrongLengthOrderException, WrongContentOrderException, NotInGameException, WrongTurnException, InvalidIdException;

    /**
     * Adds a chat message to the game.
     *
     * @param chatMessage the chat message to be added.
     * @param id          the unique identifier of the player.
     * @throws RemoteException   if a remote communication error occurs.
     * @throws InvalidIdException if the specified player identifier is invalid.
     * @throws InvalidNameException if the name of the chat message is invalid.
     */
    void addChatMessage(ChatMessage chatMessage, String id) throws RemoteException, InvalidIdException, InvalidNameException, NotInGameException;

    /**
     * Leaves the game.
     *
     * @param id the unique identifier of the player.
     * @throws RemoteException  if a remote communication error occurs.
     * @throws InvalidIdException if the specified player identifier is invalid.
     */
    void leaveGame(String id) throws RemoteException, InvalidIdException, GameFinishedException;

    /**
     * Reconnects a player to a game.
     *
     * @param id    the unique identifier of the player.
     * @param cc    the client callback object.
     * @param reconnectType indicates the type of reconnection.
     * @return a String representing the unique identifier of the game.
     * @throws InvalidIdException        if the specified player identifier is invalid.
     * @throws RemoteException          if a remote communication error occurs.
     * @throws GameFinishedException     if the game has already finished.
     * @throws AlreadyConnectedException if the player is already connected to a game.
     * @throws GameLeftException         if the player has left the game.
     */
    String reconnect(String id, ClientSkeleton cc, ReconnectType reconnectType) throws InvalidIdException, RemoteException, GameFinishedException, AlreadyConnectedException, GameLeftException;

    /**
     * Sends a ping to the server.
     *
     * @param n the ping number.
     * @throws RemoteException if a remote communication error occurs.
     */
   int ping(int n, String id) throws RemoteException, InvalidIdException;
}