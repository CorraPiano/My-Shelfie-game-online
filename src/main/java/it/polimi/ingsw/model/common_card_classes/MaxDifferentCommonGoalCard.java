package it.polimi.ingsw.model.common_card_classes;

import it.polimi.ingsw.model.*;

import java.util.HashSet;
import java.util.Set;

public class MaxDifferentCommonGoalCard implements CommonGoalCard {
    private final int type;
    private final Token token;

    public MaxDifferentCommonGoalCard(int type, Token token) {
        this.type = type;
        this.token = token;
    }

    @Override
    public boolean checkFullFil(Bookshelf library) {
        Set<Integer> colorSet = new HashSet<>();
        int colorGroupCounter = 0;
        Item item;
        if(type == 9){

            for (int i = 0; i<4;i++){
                for(int j=0;j<5;j++){
                    item = library.getItem(new Coordinates(j,i)).orElse(null);
                    if(item == null) break;
                    colorSet.add(item.getType().getValue());
                }
                if(colorSet.size() <= 3) colorGroupCounter++;
                if(colorGroupCounter>=2) return true;
                colorSet = new HashSet<>();
            }
            return false;
        }
        else if(type == 10){
            for (int i = 0; i<5; i++){
                for(int j = 0; j<4; j++){

                    item = library.getItem(new Coordinates(i, j)).orElse(null);
                    if(item == null) break;
                    colorSet.add(item.getType().getValue());
                }
                if(colorSet.size() <=3) colorGroupCounter++;
                if(colorGroupCounter>=2) return true;
                colorSet = new HashSet<>();
            }
            return false;
        }
        return false;
    }

    @Override
    public void setTokenList() {

    }

    @Override
    public Token showToken() {
        return null;
    }

    @Override
    public Token popToken() {
        return null;
    }
}
