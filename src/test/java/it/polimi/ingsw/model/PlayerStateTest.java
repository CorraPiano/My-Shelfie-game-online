package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStateTest {
    @Test
    public void testEnumValues() {
        PlayerState[] states = PlayerState.values();

        assertEquals(3, states.length);
        assertEquals(PlayerState.ACTIVE, states[0]);
        assertEquals(PlayerState.DISCONNECTED, states[1]);
        assertEquals(PlayerState.INACTIVE, states[2]);
    }
    @Test
    public void testEnumToString() {
        assertEquals("ACTIVE", PlayerState.ACTIVE.toString());
        assertEquals("DISCONNECTED", PlayerState.DISCONNECTED.toString());
        assertEquals("INACTIVE", PlayerState.INACTIVE.toString());
    }
}
