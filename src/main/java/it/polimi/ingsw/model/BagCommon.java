package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BagCommon {

    private final List<CommonGoalCard> bagCommon;

    public BagCommon() {
        bagCommon = new ArrayList<CommonGoalCard>();
        CommonGoalCardFactory cardFactory = new CommonGoalCardFactory();
        for (int i = 0; i < 12; i++){
            bagCommon.add(cardFactory.getCommonGoalCard(i));
        }
    }
    public CommonGoalCard drawCommonGoalCard() {
        Collections.shuffle(bagCommon);
        if (!bagCommon.isEmpty()) {
            return bagCommon.remove(0);
        }
        return null;
    }
    public int getBagCommonSize() { return bagCommon.size(); }
}

