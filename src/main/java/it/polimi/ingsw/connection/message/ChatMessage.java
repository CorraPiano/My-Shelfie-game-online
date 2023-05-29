package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

public class ChatMessage implements Sendable, Serializable {

    public final String sender;
    public final String receiver;

    public final String message;

    public boolean all;

    public ChatMessage(String sender, String message, String  receiver){
        this.sender = sender;
        this.message = message;
        this.receiver = receiver;
        all = false;
    }

    public ChatMessage(String sender, String message){
        this.sender = sender;
        this.message = message;
        this.receiver = null;
        all = true;
    }

    @Override
    public MessageHeader getHeader() {
        return MessageHeader.CHAT;
    }
}
