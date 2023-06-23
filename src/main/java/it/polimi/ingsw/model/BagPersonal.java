package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BagPersonal{
    private List<PersonalGoalCard> bagPersonal;

    /**
     * Default constructor. Fills the bag personal.
     *
     */
    public BagPersonal() {
        bagPersonal = new ArrayList<PersonalGoalCard>();
        for(int n=0; n<12;n++)
            bagPersonal.add(new PersonalGoalCard(n));
    }

    /**
     * Draws a PersonalGoalCard
     *
     * @return the PersonalGoalCard drawn.
     */
    public PersonalGoalCard drawPersonalGoalCard() {
        Collections.shuffle(bagPersonal);
        if (!bagPersonal.isEmpty()) {
            return bagPersonal.remove(0);
        }
        return null;
    }

    /**
     * Getter for the bag personal size.
     *
     * @return int representing the personal cards left.
     */
    public int getBagPersonalSize() {return bagPersonal.size();}
}
