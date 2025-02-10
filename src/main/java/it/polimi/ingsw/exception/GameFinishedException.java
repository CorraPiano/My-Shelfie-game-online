package it.polimi.ingsw.exception;

public class GameFinishedException extends Exception{
    public String toString(){
        return "The game you selected is already finished";
    }
}
