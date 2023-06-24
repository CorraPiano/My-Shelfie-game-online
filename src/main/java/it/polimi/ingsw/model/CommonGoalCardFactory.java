package it.polimi.ingsw.model;

import it.polimi.ingsw.model.common_card_classes.*;

/**
 * Factory class for creating common goal cards.
 */
public class CommonGoalCardFactory {

    /**
     * Returns a common goal card based on the specified type.
     *
     * @param type The type of the common goal card.
     * @return A common goal card of the specified type, or {@code null} if the type is invalid.
     */
    public CommonGoalCard getCommonGoalCard(int type) {
        if (type == 6 || type == 11)
            return new DiagonalCommonGoalCard(type);
        else if (type == 3 || type == 1 || type == 0)
            return new GroupCommonGoalCard(type);
        else if (type == 2)
            return new CornerCommonGoalCard(type);
        else if (type == 10)
            return new XCommonGoalCard(type);
        else if (type == 8 || type == 9)
            return new DifferentCommonGoalCard(type);
        else if (type == 4 || type == 7)
            return new MaxDifferentCommonGoalCard(type);
        else if (type == 5)
            return new SameEightCommonGoalCard(type);
        return null;
    }
}
