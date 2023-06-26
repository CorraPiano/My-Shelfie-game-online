package it.polimi.ingsw.exception;

public class NotLinearPickException extends Exception{
    public String toString(){
        return "you must pick the items close to each other";
    }
}
