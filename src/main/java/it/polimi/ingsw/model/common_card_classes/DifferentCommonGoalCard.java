package it.polimi.ingsw.model.common_card_classes;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DifferentCommonGoalCard extends CommonGoalCard {
    private final int type;
    private List<Token> token;

    public DifferentCommonGoalCard(int type) {
        this.type = type;

    }

    @Override
    public boolean checkFullFil(Bookshelf library) {
        Set<Integer> colorSet = new HashSet<>();
        int colorGroupCounter = 0;

        if(type == 7){
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
        else if(type == 8){
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
