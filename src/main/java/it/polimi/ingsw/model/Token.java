package it.polimi.ingsw.model;

import com.sun.jdi.Value;

public class Token {
    //ATTRIBUTES
    private int value;

    //METHODS
    public Token(int value) {
        this.value = value;
    }

    public int getValue() { return value; }
}
