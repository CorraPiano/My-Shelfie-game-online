package it.polimi.ingsw.exception;

public class WrongTurnException extends Exception{

    public String toString(){
        return "is not you turn! you will be able to play when your turn start!";
    }
}
