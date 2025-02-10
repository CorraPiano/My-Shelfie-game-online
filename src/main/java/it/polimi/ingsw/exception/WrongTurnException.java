package it.polimi.ingsw.exception;

public class WrongTurnException extends Exception{

    public String toString(){
        return "It's not you turn! You'll be able to play when your turn starts!";
    }
}
