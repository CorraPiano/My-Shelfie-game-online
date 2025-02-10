package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.localModel.*;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.connection.message.EndCause;
import it.polimi.ingsw.model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * The ClientSkeleton interface represents the remote interface for the client in the game controller.
 * It defines the methods that can be called by the server to communicate with the client.
 */
public interface ClientSkeleton extends Remote{

    /**
     * Updates the chat with a new message.
     *
     * @param message the chat message to be updated
     * @throws RemoteException if a remote communication error occurs
     */
    void updateChat(ChatMessage message) throws RemoteException;

    /**
     * Notifies the client that a new game has been created.
     *
     * @param gameID    the ID of the game
     * @param gameMode  the game mode
     * @param numPlayers the number of players in the game
     * @throws RemoteException if a remote communication error occurs
     */
    void createGame(int gameID, GameMode gameMode, int numPlayers) throws RemoteException;

    /**
     * Notifies the client that a player has joined the game.
     *
     * @param name the name of the player who joined
     * @throws RemoteException if a remote communication error occurs
     */
    void playerJoin(String name) throws RemoteException;

    /**
     * Notifies the client that a player has left the game.
     *
     * @param name the name of the player who left
     * @throws RemoteException if a remote communication error occurs
     */
    void playerLeave(String name) throws RemoteException;

    /**
     * Notifies the client that a player has disconnected from the game.
     *
     * @param name the name of the player who disconnected
     * @throws RemoteException if a remote communication error occurs
     */
    void playerDisconnect(String name) throws RemoteException;

    /**
     * Notifies the client that a player has reconnected to the game.
     *
     * @param name the name of the player who reconnected
     * @throws RemoteException if a remote communication error occurs
     */
    void playerReconnect(String name) throws RemoteException;

    /**
     * Notifies the client to start the game.
     *
     * @throws RemoteException if a remote communication error occurs
     */
    void startGame() throws RemoteException;

    /**
     * Notifies the client that it is a new turn for a player.
     *
     * @param name the name of the player
     * @throws RemoteException if a remote communication error occurs
     */
    void newTurn(String name) throws RemoteException;

    /**
     * Notifies the client that it is the last round of the game.
     *
     * @param name the name of the player
     * @throws RemoteException if a remote communication error occurs
     */
    void lastRound(String name) throws RemoteException;

    /**
     * Notifies the client that the game has ended.
     *
     * @param name  the name of the player
     * @param cause the cause of the game ending
     * @throws RemoteException if a remote communication error occurs
     */
    void endGame(String name, EndCause cause) throws RemoteException;

    /**
     * Notifies the client about a player's pick action.
     *
     * @param name       the name of the player
     * @param coordinates the coordinates of the picked item
     * @param item       the picked item
     * @throws RemoteException if a remote communication error occurs
     */
    void notifyPick(String name, Coordinates coordinates, Item item) throws RemoteException;

    /**
     * Notifies the client about a player's undo action.
     *
     * @param name the name of the player
     * @throws RemoteException if a remote communication error occurs
     */
    void notifyUndo(String name) throws RemoteException;

    /**
     * Notifies the client about the order of the players.
     *
     * @param name the name of the player
     * @param list the ordered list of player IDs
     * @throws RemoteException if a remote communication error occurs
     */
    void notifyOrder(String name, ArrayList<Integer> list) throws RemoteException;

    /**
     * Notifies the client about a player's put action.
     *
     * @param name   the name of the player
     * @param column the column where the item is placed
     * @throws RemoteException if a remote communication error occurs
     */
    void notifyPut(String name, int column) throws RemoteException;

    /**
     * Updates the local game board in the client.
     *
     * @param board the updated local game board
     * @throws RemoteException if a remote communication error occurs
     */
    void updateBoard(LocalBoard board) throws RemoteException;

    /**
     * Updates the local bookshelf in the client.
     *
     * @param bookshelf the updated local bookshelf
     * @throws RemoteException if a remote communication error occurs
     */
    void updateBookshelf(LocalBookshelf bookshelf) throws RemoteException;

    /**
     * Updates the local hand in the client.
     *
     * @param hand the updated local hand
     * @throws RemoteException if a remote communication error occurs
     */
    void updateHand(LocalHand hand) throws RemoteException;

    //void updateGame(LocalGame localGame) throws RemoteException;

    /**
     * Updates the local player list in the client.
     *
     * @param localPlayerList the updated local player list
     * @throws RemoteException if a remote communication error occurs
     */
    void updatePlayerList(LocalPlayerList localPlayerList) throws RemoteException;

    /**
     * Updates the local common goal card in the client.
     *
     * @param commonGoalCard the updated local common goal card
     * @throws RemoteException if a remote communication error occurs
     */
    void updateCommonGoalCard(LocalCommonCard commonGoalCard) throws RemoteException;

    /**
     * Updates the local personal goal card in the client.
     *
     * @param personalGoalCard the updated local personal goal card
     * @throws RemoteException if a remote communication error occurs
     */
    void updatePersonalGoalCard(LocalPersonalCard personalGoalCard) throws RemoteException;

    /**
     * Sends a ping message to the client for checking the connection status.
     *
     * @param ping the ping value
     * @throws RemoteException if a remote communication error occurs
     */
    void ping(int ping) throws RemoteException;

    /**
     * Notifies the client about the remaining time in seconds for a player's turn.
     *
     * @param seconds the remaining time in seconds
     * @throws RemoteException if a remote communication error occurs
     */
    void timer(int seconds) throws RemoteException;
}