package it.polimi.ingsw.connection.message;

import it.polimi.ingsw.connection.MessageHeader;

public class UndoMessage implements Sendable{
        // -> cmd: undo
        // <- notify: undo

        public final String name;

        public UndoMessage(){
                this.name=null;
        }

        public UndoMessage(String name){
                this.name=name;
        }

        @Override
        public MessageHeader getHeader(){
                return MessageHeader.UNDO;
        }
}
