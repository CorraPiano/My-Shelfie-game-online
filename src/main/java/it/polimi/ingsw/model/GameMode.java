package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * The GameMode enum represents the different game modes available.
 */
public enum GameMode implements Serializable {
    /**
     * Easy game mode. No CommonGoalCards in this mode.
     */
    EASY,

    /**
     * Expert game mode. Game with CommonGoalCards.
     */
    EXPERT,
}