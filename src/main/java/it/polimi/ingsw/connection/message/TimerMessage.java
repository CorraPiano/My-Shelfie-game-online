package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class TimerMessage implements Sendable {
    public final int seconds;

    public TimerMessage(int seconds){
        this.seconds = seconds;
    }

    @Override
    public MessageHeader getHeader() {
        return MessageHeader.TIMER;
    }
}
