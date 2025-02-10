package it.polimi.ingsw.exception;

public class MalformedMessageException extends Exception{
    public String toString(){
        return "Sorry, the sent message does not have the correct syntax";
    }
}
