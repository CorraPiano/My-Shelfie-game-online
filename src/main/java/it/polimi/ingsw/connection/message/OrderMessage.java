package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

import java.util.ArrayList;

/**
 * The OrderMessage class represents a message used to set the player order during the game setup.
 * It contains the ordered list of player IDs and an optional name of the player who sent the message.
 */
public class OrderMessage implements Sendable {

    /**
     * The ordered list of player IDs.
     */
    public final ArrayList<Integer> orderlist;

    /**
     * The name of the player who sent the message (optional).
     */
    public final String name;

    /**
     * Constructs an OrderMessage object with the specified ordered list of player IDs and no sender name.
     *
     * @param list the ordered list of player IDs
     */
    public OrderMessage(ArrayList<Integer> list) {
        this.orderlist = list;
        this.name = null;
    }

    /**
     * Constructs an OrderMessage object with the specified ordered list of player IDs and sender name.
     *
     * @param list the ordered list of player IDs
     * @param name the name of the player who sent the message
     */
    public OrderMessage(ArrayList<Integer> list, String name) {
        this.orderlist = list;
        this.name = name;
    }

    /**
     * Retrieves the message header associated with the OrderMessage.
     *
     * @return the message header
     */
    @Override
    public MessageHeader getHeader() {
        return MessageHeader.ORDER;
    }
}
