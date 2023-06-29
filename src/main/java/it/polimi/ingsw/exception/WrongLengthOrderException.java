package it.polimi.ingsw.exception;

public class WrongLengthOrderException extends Exception {
    public String toString(){
        return "The order you selected is not valid";
    }
}
