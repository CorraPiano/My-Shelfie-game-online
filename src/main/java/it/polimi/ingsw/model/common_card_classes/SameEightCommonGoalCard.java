package it.polimi.ingsw.model.common_card_classes;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The SameEightCommonGoalCard class represents a common goal card that requires having exactly eight items of specific colors on the bookshelf.
 * It extends the CommonGoalCard class and implements the checkFullFil() method to check if the goal is fulfilled.
 */
public class SameEightCommonGoalCard extends CommonGoalCard {
    //private final int type;
    private List<Token> token;

    /**
     * Constructs a SameEightCommonGoalCard object with the specified type.
     *
     * @param type the type of the goal card
     */
    public SameEightCommonGoalCard(int type) {
        super(type);
    }

    /**
     * Checks if the goal card is fulfilled by having exactly eight items of specific colors on the bookshelf.
     *
     * @param library the bookshelf to check for the specific colors
     * @return true if the goal is fulfilled, false otherwise
     */
    @Override
    public boolean checkFullFil(Bookshelf library) {

        Set<ItemType> colorsSet = new HashSet<>();
        colorsSet.add(ItemType.GREEN);
        colorsSet.add(ItemType.YELLOW);
        colorsSet.add(ItemType.PINK);
        colorsSet.add(ItemType.CYAN);
        colorsSet.add(ItemType.WHITE);

        Coordinates c = new Coordinates();
        int colorsCounter = 0;

        for(ItemType t: colorsSet){
            for(int i=0; i<6; i++){
                for(int j=0; j<5; j++){
                    c.setRow(i);
                    c.setColumn(j);
                    if(library.getItem(c).isPresent()){

                        if(library.getItem(c).get().getType() == t) colorsCounter++;
                    }
                }
            }
            if(colorsCounter >= 8) return true;
            colorsCounter = 0;
        }
        return false;
    }

}
