package it.polimi.ingsw.model.common_card_classes;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.CommonGoalCard;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * The CornerCommonGoalCard class represents a common goal card that requires specific items to be placed in the corners of the bookshelf.
 * Description: Four tiles of the same type in the four  corners of the bookshelf.
 * It extends the CommonGoalCard class and implements the checkFullFil() method to check if the goal is fulfilled.
 */
public class CornerCommonGoalCard extends CommonGoalCard {

    private List<Token> token;
    //private int type;

    /**
     * Constructs a CornerCommonGoalCard object with the specified type.
     *
     * @param type the type of the goal card
     */
    public CornerCommonGoalCard(int type) {
        super(type);
    }

    /**
     * Checks if the goal card is fulfilled by checking if the required items are placed in the corners of the bookshelf.
     *
     * @param library the bookshelf to check for item placement
     * @return true if the goal is fulfilled, false otherwise
     */
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
