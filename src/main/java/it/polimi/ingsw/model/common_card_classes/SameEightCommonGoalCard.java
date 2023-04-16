package it.polimi.ingsw.model.common_card_classes;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SameEightCommonGoalCard extends CommonGoalCard {
    private final int type;
    private List<Token> token;

    public SameEightCommonGoalCard(int type) {
        this.type = type;
    }

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
            if(colorsCounter == 8) return true;
        }

        return false;
    }

}
