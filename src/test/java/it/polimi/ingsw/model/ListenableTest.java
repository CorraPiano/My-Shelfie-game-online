package it.polimi.ingsw.model;

import it.polimi.ingsw.connection.message.Sendable;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ListenableTest {
    private Sendable localRepresentation;
    private EventKeeper eventKeeper;

    @Test
    void notifyUpdate_withEventKeeper_notifiesAllListeners() {
        // Create a mock EventKeeper
        EventKeeper eventKeeper = mock(EventKeeper.class);

        // Create a mock Sendable instance
        Sendable sendable = mock(Sendable.class);

        // Create a TestListenable instance and set the mock EventKeeper
        TestListenable listenable = new TestListenable();
        listenable.setEventKeeper(eventKeeper);

        // Call the notifyUpdate method
        listenable.notifyUpdate();

        // Verify that the eventKeeper's notifyAll method was called with the expected argument
        verify(eventKeeper).notifyAll(same(listenable.getLocal()));
    }

    @Test
    void notifyUpdate_withoutEventKeeper_doesNotThrowException() {
        // Create a TestListenable instance without setting the eventKeeper

        TestListenable listenable = new TestListenable();

        // Call the notifyUpdate method
        assertDoesNotThrow(() -> listenable.notifyUpdate());
    }

    private static class TestListenable extends Listenable {
        @Override
        protected Sendable getLocal() {
            return null; // Replace with a mock or actual implementation for testing purposes
        }
    }

    @Test
    public void testNotifyEventToID() {
        // Create a mock EventKeeper instance
        EventKeeper eventKeeper = mock(EventKeeper.class);

        // Create a Listenable object and set the mock EventKeeper
        Listenable listenable = new Listenable() {
            @Override
            public Sendable getLocal() {
                return null; // Return some object
            }
        };
        listenable.setEventKeeper(eventKeeper);

        // Create a mock Sendable event
        Sendable sendable = mock(Sendable.class);

        // Create a mock listener ID
        String listenerId = "123";

        // Call the notifyEventToID method
        listenable.notifyEventToID(listenerId, sendable);

        // Verify that the eventKeeper.notifyToID method was called with the correct arguments
        verify(eventKeeper).notifyToID(listenerId, sendable);
    }
}