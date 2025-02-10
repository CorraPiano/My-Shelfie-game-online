package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {
    @Test
    void testConstructorAndGetters() {
        // Test constructor and getters
        int row = 2;
        int column = 3;
        Coordinates coordinates = new Coordinates(row, column);
        assertEquals(row, coordinates.getRow());
        assertEquals(column, coordinates.getColumn());
    }

    @Test
    void testSetters() {
        // Test setters
        int row = 2;
        int column = 3;
        Coordinates coordinates = new Coordinates();
        coordinates.setRow(row);
        coordinates.setColumn(column);
        assertEquals(row, coordinates.getRow());
        assertEquals(column, coordinates.getColumn());
    }

    @Test
    void testEquals() {
        // Test equals method
        Coordinates coordinates1 = new Coordinates(2, 3);
        Coordinates coordinates2 = new Coordinates(2, 3);
        Coordinates coordinates3 = new Coordinates(3, 4);

        assertTrue(coordinates1.equals(coordinates2));
        assertFalse(coordinates1.equals(coordinates3));
    }

    @Test
    void testHashCode() {
        // Test hashCode method
        Coordinates coordinates1 = new Coordinates(2, 3);
        Coordinates coordinates2 = new Coordinates(2, 3);
        Coordinates coordinates3 = new Coordinates(3, 4);

        assertEquals(coordinates1.hashCode(), coordinates2.hashCode());
        assertNotEquals(coordinates1.hashCode(), coordinates3.hashCode());
    }

    @Test
    void testToString() {
        // Test toString method
        Coordinates coordinates = new Coordinates(2, 3);
        assertEquals("2,3", coordinates.toString());
    }
}