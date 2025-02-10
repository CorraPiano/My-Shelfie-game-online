package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientGUI;
import it.polimi.ingsw.client.connection.*;

/**
 * The View interface defines the contract for a client-side view in a client-server application.
 * It provides methods to set and retrieve the sender object and the client object associated with the view.
 */
public interface View {

    /**
     * Sets the sender object for the view.
     * The sender object is responsible for sending messages from the client to the server.
     *
     * @param sender The sender object to set.
     */
    public void setSender(Sender sender);

    /**
     * Returns the sender object associated with the view.
     *
     * @return The sender object.
     */
    public Sender getSender();

    /**
     * Sets the client object for the view.
     * The client object represents the client and provides access to client-related functionality.
     */
    public void setClient();

    /**
     * Returns the client object associated with the view.
     *
     * @return The client object.
     */
    public Client getClient();
}