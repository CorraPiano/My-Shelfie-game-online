package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The `LocalPlayerList` class represents a list of players in the client's game model.
 * It stores a list of `LocalPlayer` objects.
 */
public class LocalPlayerList implements Sendable, Serializable {
    public final ArrayList<LocalPlayer> playerList;

    /**
     * Constructs a `LocalPlayerList` object with the specified list of players.
     *
     * @param playerList The list of players represented by an `ArrayList` of `LocalPlayer` objects.
     */
    public LocalPlayerList(ArrayList<LocalPlayer> playerList) {
        this.playerList = playerList;
    }
    public LocalPlayerList() {
        this.playerList = new ArrayList<>();
    }

    /**
     * Returns the message header associated with the `LocalPlayerList` object.
     *
     * @return The message header.
     */
    @Override
    public MessageHeader getHeader() {
        return MessageHeader.PLAYERLIST;
    }
    public Boolean isRecurrentUpdate(){
        return true;
    }
}