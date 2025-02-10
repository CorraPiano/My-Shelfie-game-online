package it.polimi.ingsw.exception;

public class NotInGameException extends Exception{
    public String toString(){
        return "Sorry, the game isn't begun yet";
    }
}
