package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

/**
 * The TimerMessage class represents a message indicating the timer value during a game.
 * It is a notification sent from the server to the client.
 */
public class TimerMessage implements Sendable {

    /**
     * The number of seconds remaining on the timer.
     */
    public final int seconds;

    /**
     * Constructs a TimerMessage object with the specified number of seconds.
     *
     * @param seconds the number of seconds remaining on the timer
     */
    public TimerMessage(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Retrieves the message header associated with the TimerMessage.
     *
     * @return the message header
     */
    @Override
    public MessageHeader getHeader() {
        return MessageHeader.TIMER;
    }
    public Boolean isRecurrentUpdate(){
        return true;
    }
}