package it.polimi.ingsw.model.common_card_classes;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.CommonGoalCard;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Token;

import java.util.ArrayList;
import java.util.List;

public class CornerCommonGoalCard extends CommonGoalCard {

    private List<Token> token;
    private int type;

    public CornerCommonGoalCard(int type) {
        this.type = type;
    }

    @Override
    public boolean checkFullFil(Bookshelf library) {

        boolean someItemIsEmpty = library.getItem(new Coordinates(0,0)).isEmpty()
                                    || library.getItem(new Coordinates(0,4)).isEmpty()
                                    || library.getItem(new Coordinates(5,4)).isEmpty()
                                    || library.getItem(new Coordinates(5,0)).isEmpty();

        if(someItemIsEmpty) return false;

        int color = library.getItem(new Coordinates(0,0)).get().getType().getValue();

        boolean someItemHasDifferentColor = library.getItem(new Coordinates(0,0)).get().getType().getValue() != color
                                            || library.getItem(new Coordinates(0,4)).get().getType().getValue() != color
                                            || library.getItem(new Coordinates(5,4)).get().getType().getValue() != color
                                            || library.getItem(new Coordinates(5,0)).get().getType().getValue() != color;

        return !someItemHasDifferentColor;
    }


}
