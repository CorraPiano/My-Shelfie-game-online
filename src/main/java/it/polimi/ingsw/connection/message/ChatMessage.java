package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

/**
 * The ChatMessage class represents a chat message that can be sent from one user to another.
 * It implements the Sendable interface and Serializable for object serialization.
 */
public class ChatMessage implements Sendable, Serializable {

    /**
     * The sender of the chat message.
     */
    public final String sender;

    /**
     * The receiver of the chat message.
     */
    public final String receiver;

    /**
     * The content of the chat message.
     */
    public final String message;

    /**
     * Indicates whether the chat message is intended for all users.
     */
    public boolean all;

    /**
     * Constructs a ChatMessage object with the specified sender, message, and receiver.
     *
     * @param sender   the sender of the chat message
     * @param message  the content of the chat message
     * @param receiver the receiver of the chat message
     */
    public ChatMessage(String sender, String message, String receiver) {
        this.sender = sender;
        this.message = message;
        this.receiver = receiver;
        all = false;
    }

    /**
     * Constructs a ChatMessage object with the specified sender and message, intended for all users.
     *
     * @param sender  the sender of the chat message
     * @param message the content of the chat message
     */
    public ChatMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
        this.receiver = null;
        all = true;
    }

    /**
     * Retrieves the message header associated with the ChatMessage.
     *
     * @return the message header
     */
    @Override
    public MessageHeader getHeader() {
        return MessageHeader.CHAT;
    }

    public Boolean isRecurrentUpdate(){
        return false;
    }
}