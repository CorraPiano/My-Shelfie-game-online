package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.Connection;
import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.ReconnectType;

/**
 * The ReconnectMessage class represents a message used to request or confirm a player's reconnection to a game.
 * It contains the player's ID, name, and a flag indicating whether the game should be reset.
 */
public class ReconnectMessage implements Sendable {

    /**
     * The player's ID used for reconnection.
     */
    public final String id;

    /**
     * The player's name.
     */
    public final String name;

    /**
     * A flag indicating whether the game should be reset upon reconnection.
     */
    public final ReconnectType reconnectType;

    /**
     * Constructs a ReconnectMessage object for requesting reconnection with the specified player ID and reset flag.
     *
     * @param id    the player's ID used for reconnection
     * @param reconnectType a flag indicating the type of reconnection
     */
    public ReconnectMessage(String id, ReconnectType reconnectType) {
        this.id = id;
        this.reconnectType = reconnectType;
        this.name = null;
    }

    /**
     * Constructs a ReconnectMessage object for confirming reconnection with the specified player name.
     *
     * @param name the player's name
     */
    public ReconnectMessage(String name) {
        this.id = null;
        this.reconnectType = null;
        this.name = name;
    }

    /**
     * Retrieves the message header associated with the ReconnectMessage.
     *
     * @return the message header
     */
    @Override
    public MessageHeader getHeader() {
        return MessageHeader.RECONNECTION;
    }
    public Boolean isRecurrentUpdate(){
        return false;
    }
}
