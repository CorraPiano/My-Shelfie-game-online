package it.polimi.ingsw.model;

import it.polimi.ingsw.model.common_card_classes.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BagPersonal{
    private List<PersonalGoalCard> bagPersonal;

    public BagPersonal() {
        bagPersonal = new ArrayList<PersonalGoalCard>();
        for(int n=0; n<12;n++)
            bagPersonal.add(new PersonalGoalCard(n));
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
