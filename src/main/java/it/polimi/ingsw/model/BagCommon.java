package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the bag where common goal cards get drawn
 */
public class BagCommon {
    private final List<CommonGoalCard> bagCommon;

    /**
     * Default constructor. Sets the bag of common goal cards ready to be drawn.
     */
    public BagCommon() {
        bagCommon = new ArrayList<CommonGoalCard>();
        CommonGoalCardFactory cardFactory = new CommonGoalCardFactory();
        for (int i = 0; i < 12; i++){
            bagCommon.add(cardFactory.getCommonGoalCard(i));
        }
    }

    /**
     * Draws a CommonGoalCard from the bag.
     *
     * @return CommonGoalCard drawn.
     */
    public CommonGoalCard drawCommonGoalCard() {
        Collections.shuffle(bagCommon);
        if (!bagCommon.isEmpty()) {
            return bagCommon.remove(0);
        }
        return null;
    }

    /**
     * Returns the size of the CommonGoalCards bag.
     *
     * @return an int representing the size of the Common bag.
     */
    public int getBagCommonSize() { return bagCommon.size(); }
}

