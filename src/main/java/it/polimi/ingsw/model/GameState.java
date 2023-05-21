package it.polimi.ingsw.model;

import it.polimi.ingsw.model.GameMode;

import java.io.Serializable;

public enum GameState implements Serializable {
    WAIT,
    GAME,
    END,
}