package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test

    public void testConstructorAndGetters() {
        int value = 5;
        Token token = new Token(value);
        assertEquals(value, token.getValue());
        }
    }
