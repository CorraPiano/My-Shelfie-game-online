package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class GameModeTest {

        @Test
        void testEnumValues() {
            // Test if all enum values are defined
            assertNotNull(GameMode.EASY);
            assertNotNull(GameMode.EXPERT);
        }

    }

