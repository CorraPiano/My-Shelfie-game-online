package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Item;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The `LocalHand` class represents the local player's hand in the client's game model.
 * It stores the items in the hand, their coordinates, and the size of the hand.
 */
public class LocalHand implements Serializable, Sendable {

    public final Item[] hand;
    public final ArrayList<Coordinates> coordinatesList;
    public final int size;

    /**
     * Constructs a `LocalHand` object with the specified hand, size, and coordinates.
     *
     * @param hand             The items in the hand represented by an array of `Item` objects.
     * @param size             The size of the hand.
     * @param coordinatesList  The coordinates of the items in the hand.
     */
    public LocalHand(Item[] hand, int size, ArrayList<Coordinates> coordinatesList) {
        this.hand = hand;
        this.size = size;
        this.coordinatesList = coordinatesList;
    }

    /**
     * Constructs an empty `LocalHand` object.
     * This constructor is used when the hand is initially empty.
     */
    public LocalHand() {
        this.hand = new Item[0];
        this.size = 0;
        this.coordinatesList = new ArrayList<>();
    }

    /**
     * Returns the message header associated with the `LocalHand` object.
     *
     * @return The message header.
     */
    public MessageHeader getHeader() {
        return MessageHeader.HAND;
    }
}