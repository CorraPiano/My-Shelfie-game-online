package it.polimi.ingsw.exception;

public class OutOfBoardPickException extends Exception {
    public String toString(){
        return "the coordinates you selected are not valid";
    }
}
