package it.polimi.ingsw.exception;

public class AlreadyConnectedException extends Exception {

    public String toString(){
        return "you are already connected to the game";
    }
}
