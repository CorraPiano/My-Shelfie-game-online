package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commoncard.CommonGoalCard;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BagPersonal{
    private List<PersonalGoalCard> bagPersonal;
    public BagPersonal() {
        bagPersonal = new ArrayList<PersonalGoalCard>();
        //aggiungi tutte le common goal cards: ancora da definire quante sono!
    }
    public PersonalGoalCard drawPersonalGoalCard() {
        Collections.shuffle(bagPersonal);
        if (!bagPersonal.isEmpty()) {
            return bagPersonal.remove(0);
        }
        return null;
    }
    public int getBagCommonSize() {return bagPersonal.size();}
}
