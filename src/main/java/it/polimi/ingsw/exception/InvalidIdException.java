package it.polimi.ingsw.exception;

public class InvalidIdException extends Exception{
    public String toString(){
        return "Sorry, there is a problem with your ID.\nPlease check your input!";
    }
}
