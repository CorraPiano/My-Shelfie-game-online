package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * The Token class represents a game token with a specific value.
 */
public class Token implements Serializable {
    private int value;

    /**
     * Constructs a new Token object with the specified value.
     *
     * @param value the value of the token
     */
    public Token(int value) {
        this.value = value;
    }

    /**
     * Returns the value of the token.
     *
     * @return the value of the token
     */
    public int getValue() {
        return value;
    }
}