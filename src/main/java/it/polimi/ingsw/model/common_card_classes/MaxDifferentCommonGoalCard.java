package it.polimi.ingsw.model.common_card_classes;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The MaxDifferentCommonGoalCard class represents a common goal card that requires maximizing the number of different colors in specific patterns on a bookshelf.
 * It extends the CommonGoalCard class and implements the checkFullFil() method to check if the goal is fulfilled.
 */
public class MaxDifferentCommonGoalCard extends CommonGoalCard {
    //private final int type;
    private List<Token> token;

    /**
     * Constructs a MaxDifferentCommonGoalCard object with the specified type.
     *
     * @param type the type of the goal card
     */
    public MaxDifferentCommonGoalCard(int type) {
        super(type);
    }

    /**
     * Checks if the goal card is fulfilled by maximizing the number of different colors in specific patterns on the bookshelf.
     *
     * @param library the bookshelf to check for the specific color patterns
     * @return true if the goal is fulfilled, false otherwise
     */
    @Override
    public boolean checkFullFil(Bookshelf library) {
        Set<Integer> colorSet = new HashSet<>();
        int colorGroupCounter = 0;
        Item item = null;
        if(type == 4){

            for (int i = 0; i<5;i++){
                for(int j=0;j<6;j++){
                    item = library.getItem(new Coordinates(j,i)).orElse(null);
                    if(item == null) break;
                    colorSet.add(item.getType().getValue());
                }
                if(item != null && colorSet.size() <= 3) colorGroupCounter++;
                if(colorGroupCounter>=3) return true;
                colorSet = new HashSet<>();
            }
            return false;
        }
        else if(type == 7){
            for (int i = 0; i<6; i++){
                for(int j = 0; j<5; j++){

                    item = library.getItem(new Coordinates(i, j)).orElse(null);
                    if(item == null) break;
                    colorSet.add(item.getType().getValue());
                }
                if(item != null && colorSet.size() <=3) colorGroupCounter++;
                if(colorGroupCounter>=4) return true;
                colorSet = new HashSet<>();
            }
            return false;
        }
        return false;
    }

}
