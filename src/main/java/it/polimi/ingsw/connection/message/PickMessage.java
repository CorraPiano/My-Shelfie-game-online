package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Item;

/**
 * The PickMessage class represents a message used to notify the server about a player's pick action.
 * It contains the coordinates of the picked item, the name of the player who picked it, and the item itself.
 */
public class PickMessage implements Sendable {

    /**
     * The coordinates of the picked item.
     */
    public Coordinates coordinates;

    /**
     * The name of the player who picked the item.
     */
    public String name;

    /**
     * The picked item.
     */
    public Item item;

    /**
     * Constructs a PickMessage object with the specified coordinates of the picked item.
     *
     * @param coordinates the coordinates of the picked item
     */
    public PickMessage(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.name = null;
        this.item = null;
    }

    /**
     * Constructs a PickMessage object with the specified coordinates, player name, and picked item.
     *
     * @param coordinates the coordinates of the picked item
     * @param name        the name of the player who picked the item
     * @param item        the picked item
     */
    public PickMessage(Coordinates coordinates, String name, Item item) {
        this.coordinates = coordinates;
        this.name = name;
        this.item = item;
    }

    /**
     * Retrieves the message header associated with the PickMessage.
     *
     * @return the message header
     */
    @Override
    public MessageHeader getHeader() {
        return MessageHeader.PICK;
    }
}
