package it.polimi.ingsw.model;

import com.sun.jdi.Value;

import java.io.Serializable;

public class Token implements Serializable {
    //ATTRIBUTES
    private int value;

    //METHODS
    public Token(int value) {
        this.value = value;
    }

    public int getValue() { return value; }
}
