package it.polimi.ingsw.connection;

import it.polimi.ingsw.connection.message.Sendable;

/**
 * This class represents a TCP message containing a header and a body.
 */
public class TCPMessage {
    private MessageHeader header;
    private String body;

    /**
     * Constructs a new TCPMessage object with the specified header and body.
     *
     * @param header The header of the TCP message.
     * @param body   The body of the TCP message.
     */
    public TCPMessage(MessageHeader header, String body){
        this.header=header;
        this.body=body;
    }

    /**
     * Returns the header of the TCP message.
     *
     * @return The header of the TCP message.
     */
    public MessageHeader getHeader(){return header;}

    /**
     * Returns the body of the TCP message.
     *
     * @return The body of the TCP message.
     */
    public String getBody(){return body;}
}

