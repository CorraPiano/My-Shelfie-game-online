package it.polimi.ingsw.exception;

public class GameModeException extends Exception {
    public String toString(){
        return "The gamemode you selected is not valid";
    }
}
