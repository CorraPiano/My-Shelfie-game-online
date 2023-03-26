package it.polimi.ingsw.model;

import com.sun.jdi.Value;

public class Token {
    //ATTRIBUTES
    private int value;

    public Token(int value) {
        this.value = value;
    }

    //METHODS
    public int getValue() { return value; }
}
