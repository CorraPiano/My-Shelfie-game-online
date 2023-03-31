package it.polimi.ingsw.model;

import it.polimi.ingsw.model.common_card_classes.*;

public class CommonGoalCardFactory{

    public CommonGoalCard getCommonGoalCard(int type){
        Token token = null;

        if(type == 0 || type == 1)
            return new DiagonalCommonGoalCard(type, token);
        else if(type == 2 || type == 3 || type == 4)
            return new GroupCommonGoalCard(type, token);
        else if(type == 5)
            return new CornerCommonGoalCard(type, token);
        else if(type == 6)
            return new XCommonGoalCard(type, token);
        else if(type == 7 || type == 8)
            return new DifferentCommonGoalCard(type, token);
        else if(type == 9 || type == 10)
            return new MaxDifferentCommonGoalCard(type, token);
        else if(type == 11)
            return new DiagonalCommonGoalCard(type, token);
        return null;
    }
}
