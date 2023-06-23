package it.polimi.ingsw.model;

import it.polimi.ingsw.client.localModel.LocalCommonCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a common goal card.
 */
public abstract class CommonGoalCard extends Listenable implements Serializable {

    protected List<Token> token;
    protected int type;

    /**
     * Constructs a common goal card with the specified type.
     *
     * @param type The type of the common goal card.
     */
    public CommonGoalCard(int type) {
        this.type = type;
    }

    /**
     * Checks if the common goal card is fulfilled based on the given bookshelf.
     *
     * @param library The bookshelf to check against.
     * @return {@code true} if the common goal card is fulfilled, {@code false} otherwise.
     */
    public abstract boolean checkFullFil(Bookshelf library);

    /**
     * Sets the list of tokens associated with the common goal card.
     *
     * @param token The list of tokens to set.
     */
    public void setTokenList(List<Token> token) {
        this.token = token;
        notifyUpdate();
    }

    /**
     * Returns a new ArrayList containing the tokens associated with the common goal card.
     *
     * @return The list of tokens associated with the common goal card.
     */
    public ArrayList<Token> showToken() {
        return new ArrayList<>(token);
    }

    /**
     * Removes and returns the first token from the common goal card.
     *
     * @return The removed token.
     */
    public Token popToken() {
        Token t = token.get(0);
        token.remove(0);
        notifyUpdate();
        return t;
    }

    /**
     * Returns the type of the common goal card.
     *
     * @return The type of the common goal card.
     */
    public int getType() {
        return type;
    }

    /**
     * Returns a local representation of the common goal card.
     *
     * @return The local representation of the common goal card.
     */
    public LocalCommonCard getLocal() {
        return new LocalCommonCard(type, new ArrayList<>(token));
    }
}