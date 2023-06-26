package it.polimi.ingsw.model.common_card_classes;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The DifferentCommonGoalCard class represents a common goal card that requires different color groups on a bookshelf.
 * Description: this class groups more than one common goal cards:
 * - card 8 -> Two columns each formed by 6 different types of tiles.
 * - card 9 -> Two lines each formed by 5 different types of tiles. One line can show the same or a different combination
 * of the other line.
 * It extends the CommonGoalCard class and implements the checkFullFil() method to check if the goal is fulfilled.
 */
public class DifferentCommonGoalCard extends CommonGoalCard {
    //private final int type;
    private List<Token> token;

    /**
     * Constructs a DifferentCommonGoalCard object with the specified type.
     *
     * @param type the type of the goal card
     */
    public DifferentCommonGoalCard(int type) {
        super(type);

    }

    /**
     * Checks if the goal card is fulfilled by checking for different color groups on the bookshelf.
     *
     * @param library the bookshelf to check for color groups
     * @return true if the goal is fulfilled, false otherwise
     */
    @Override
    public boolean checkFullFil(Bookshelf library) {
        Set<Integer> colorSet = new HashSet<>();
        int colorGroupCounter = 0;

        if(type == 8){
            Item item;
            for (int i = 0; i<5;i++){
                for(int j=0;j<6;j++){
                    item = library.getItem(new Coordinates(j,i)).orElse(null);
                    if(item == null) break;
                    if(colorSet.contains(item.getType().getValue())) break;
                    colorSet.add(item.getType().getValue());
                }
                if(colorSet.size() == 6) colorGroupCounter++;
                if(colorGroupCounter>=2) return true;
                colorSet = new HashSet<>();
            }
            return false;
        }
        else if(type == 9){
            Item item;
            for (int i = 0; i<6;i++){
                for(int j=0;j<5;j++){
                    item = library.getItem(new Coordinates(i, j)).orElse(null);
                    if(item == null) break;
                    if(colorSet.contains(item.getType().getValue())) break;
                    colorSet.add(item.getType().getValue());
                }
                if(colorSet.size() == 5) colorGroupCounter++;
                if(colorGroupCounter>=2) return true;
                colorSet = new HashSet<>();
            }
            return false;
        }
        return false;
    }


}
