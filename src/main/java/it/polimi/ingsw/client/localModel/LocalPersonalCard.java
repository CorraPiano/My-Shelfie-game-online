package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.DataCard;
import it.polimi.ingsw.model.Item;
import javafx.scene.chart.PieChart;

import java.io.Serializable;
import java.util.HashMap;

/**
 * The `LocalPersonalCard` class represents a personal goal card in the client's game model.
 * It stores the number of the personal goal card and its associated data card.
 */
public class LocalPersonalCard implements Sendable, Serializable {

    public final int num;
    public final DataCard dataCard;

    /**
     * Constructs a `LocalPersonalCard` object with the specified personal goal card number.
     * This constructor is used when the associated data card is not available.
     *
     * @param num The number of the personal goal card.
     */
    public LocalPersonalCard(int num) {
        this.num = num;
        this.dataCard = null;
    }

    /**
     * Constructs a `LocalPersonalCard` object with the specified personal goal card number and data card.
     *
     * @param num       The number of the personal goal card.
     * @param dataCard  The associated data card represented by a `DataCard` object.
     */
    public LocalPersonalCard(int num, DataCard dataCard) {
        this.num = num;
        this.dataCard = dataCard;
    }

    /**
     * Returns the message header associated with the `LocalPersonalCard` object.
     *
     * @return The message header.
     */
    public MessageHeader getHeader() {
        return MessageHeader.PERSONALGOALCARD;
    }
    public Boolean isRecurrentUpdate(){
        return false;
    }
}