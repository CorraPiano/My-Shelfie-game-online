package it.polimi.ingsw.exception;

public class MalformedMessageException extends Exception{
    public String toString(){
        return "sorry, the message you send is malformed";
    }
}
