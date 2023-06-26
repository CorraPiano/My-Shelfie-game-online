package it.polimi.ingsw.exception;

public class InvalidNameException extends Exception{
    public String toString(){
        return "You weren't parte of the game you are trying to join";
    }
}
