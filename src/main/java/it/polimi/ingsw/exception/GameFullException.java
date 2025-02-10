package it.polimi.ingsw.exception;

public class GameFullException extends Exception{
    public String toString(){
        return "You can't join the game because it's full";
    }
}
