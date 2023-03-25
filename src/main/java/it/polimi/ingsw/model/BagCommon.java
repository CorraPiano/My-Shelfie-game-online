package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commoncard.CommonGoalCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BagCommon {

    private List<CommonGoalCard> bagCommon;

    public BagCommon() {
        bagCommon = new ArrayList<CommonGoalCard>();
        //aggiungi tutte le commongoalcards: ancora da definire quante sono!
    }
    public CommonGoalCard drawCommonGoalCard() {
        Collections.shuffle(bagCommon);
        if (! bagCommon.isEmpty()) {
            return bagCommon.remove(0);
        }
        return null;
    }
    public int getBagCommonSize() { return bagCommon.size(); }
}

