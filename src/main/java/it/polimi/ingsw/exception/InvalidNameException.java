package it.polimi.ingsw.exception;

public class InvalidNameException extends Exception{
    public String toString(){
        return "There aren't players with this name";
    }
}
