package it.polimi.ingsw.model;

import it.polimi.ingsw.model.common_card_classes.*;

import java.util.List;

public class CommonGoalCardFactory{

    public CommonGoalCard getCommonGoalCard(int type){

        if(type == 0 || type == 1)
            return new DiagonalCommonGoalCard(type);
        else if(type == 2 || type == 3 || type == 4)
            return new GroupCommonGoalCard(type);
        else if(type == 5)
            return new CornerCommonGoalCard(type);
        else if(type == 6)
            return new XCommonGoalCard(type);
        else if(type == 7 || type == 8)
            return new DifferentCommonGoalCard(type);
        else if(type == 9 || type == 10)
            return new MaxDifferentCommonGoalCard(type);
        else if(type == 11)
            return new SameEightCommonGoalCard(type);
        return null;
    }


}



