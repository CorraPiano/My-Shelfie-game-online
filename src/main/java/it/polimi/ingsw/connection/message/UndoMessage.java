package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

/**
 * The UndoMessage class represents a message indicating a request to undo a move during a game.
 * It is a command sent from the client to the server.
 */
public class UndoMessage implements Sendable {

        /**
         * The name of the player requesting the undo.
         */
        public final String name;

        /**
         * Constructs an UndoMessage object without specifying the player name.
         */
        public UndoMessage() {
                this.name = null;
        }

        /**
         * Constructs an UndoMessage object with the specified player name.
         *
         * @param name the name of the player requesting the undo
         */
        public UndoMessage(String name) {
                this.name = name;
        }

        /**
         * Retrieves the message header associated with the UndoMessage.
         *
         * @return the message header
         */
        @Override
        public MessageHeader getHeader() {
                return MessageHeader.UNDO;
        }
        public Boolean isRecurrentUpdate(){
                return false;
        }
}