package it.polimi.ingsw.exception;

public class OutOfBoardPickException extends Exception {
    public String toString(){
        return "The coordinates you selected are not valid";
    }
}
