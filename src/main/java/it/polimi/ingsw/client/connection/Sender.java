package it.polimi.ingsw.client.connection;

import it.polimi.ingsw.client.localModel.LocalGame;
import it.polimi.ingsw.connection.message.ChatMessage;
import it.polimi.ingsw.controller.ClientSkeleton;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameMode;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * The `Sender` abstract class defines the interface for sending messages from the client to the server.
 * Concrete implementations of this class handle the actual sending of messages over a specific network protocol.
 */
public abstract class Sender {

    /**
     * Sends a request to the server to retrieve the list of available games.
     */
    public abstract void getGameList();

    /**
     * Sends a request to the server to add the first player to a new game.
     *
     * @param name     The name of the player.
     * @param gameMode The game mode chosen by the player.
     * @param numPlayer The number of players in the game.
     */
    public abstract void addFirstPlayer(String name, GameMode gameMode, int numPlayer);

    /**
     * Sends a request to the server to add a player to an existing game.
     *
     * @param name    The name of the player.
     * @param gameID  The ID of the game to join.
     */
    public abstract void addPlayer(String name, int gameID);

    /**
     * Sends a request to the server to pick an item at the specified coordinates.
     *
     * @param coordinates The coordinates of the item to pick.
     */
    public abstract void pickItem(Coordinates coordinates);

    /**
     * Sends a request to the server to undo the last item pick.
     */
    public abstract void undoPick();

    /**
     * Sends a request to the server to put an item in the specified column.
     *
     * @param column The column where the item should be put.
     */
    public abstract void putItemList(int column);

    /**
     * Sends a request to the server to select the insert order for items.
     *
     * @param order The list of integers representing the desired insert order.
     */
    public abstract void selectInsertOrder(ArrayList<Integer> order);

    /**
     * Sends a chat message to the specified receiver.
     *
     * @param message  The chat message to send.
     * @param receiver The name of the message receiver.
     */
    public abstract void addChatMessage(String message, String receiver);

    /**
     * Sends a chat message to all players in the game.
     *
     * @param message The chat message to send.
     */
    public abstract void addChatMessage(String message);

    /**
     * Sends a request to the server to leave the current game.
     */
    public abstract void leaveGame();

    /**
     * Sends a request to the server to reconnect to a game with the specified ID.
     *
     * @param id     The ID of the game to reconnect to.
     * @param reset  Indicates whether the game should be reset upon reconnection.
     */
    public abstract void reconnectGame(String id, boolean reset);

    /**
     * Sends a ping request to the server with the specified number.
     *
     * @param n The ping number.
     * @throws Exception If an error occurs while sending the ping.
     */
    public abstract void ping(int n) throws Exception;

    /**
     * Establishes a connection with the server.
     *
     * @throws Exception If an error occurs while establishing the connection.
     */
    public abstract void connect() throws Exception;
    public abstract boolean reconnect() throws Exception;
}