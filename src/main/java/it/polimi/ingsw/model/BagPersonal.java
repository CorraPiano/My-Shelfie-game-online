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
    public PersonalGoalCard drawPersonalGoalCard() throws Exception{
        Collections.shuffle(bagPersonal);
        if (! bagPersonal.isEmpty()) {
            return bagPersonal.remove(0);
        }
        throw new Exception("No more items left to be drawn !");
    };
    public int getBagCommonSize() {return bagPersonal.size();}
}
