package it.polimi.ingsw.exception;

public class GameModeException extends Exception {
    public String toString(){
        return "the gamemode you selected is not valid";
    }
}
