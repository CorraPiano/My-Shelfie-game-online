package it.polimi.ingsw.model.common_card_classes;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

public class GroupCommonGoalCard extends CommonGoalCard {
    private final int type;
    private List<Token> token;
    public GroupCommonGoalCard(int type) {
        this.type = type;

    }

    @Override
    public boolean checkFullFil(Bookshelf library) {
        boolean someIsEmpty = false, allWithSameColor = false;
        int color, colorGroup=0;

        if(type == 2){
            for(int i = 0 ; i < 6; i=i+2){
                for(int j=0; j<3; j++){
                    Item item0 = library.getItem(new Coordinates(i, j)).orElse(null);
                    if(item0!= null)
                        color = item0.getType().getValue();
                    else
                        break;

                    Item item1 = library.getItem(new Coordinates(i-1, j)).orElse(null);
                    Item item2 = library.getItem(new Coordinates(i-1, j+1)).orElse(null);
                    Item item3 = library.getItem(new Coordinates(i, j+1)).orElse(null);

                    someIsEmpty = item1==null || item2==null || item3==null;
                    if (!someIsEmpty){

                        allWithSameColor = item1.getType().getValue()==color &&
                                item2.getType().getValue()==color &&
                                item3.getType().getValue()==color;
                    }
                    if(allWithSameColor) colorGroup++;
                }
            }
            return colorGroup >= 4;
        }
        else if(type == 3){
            for (int i=3;i<6;i++){
                for(int j=0; j<5; j++){
                    Item item0 = library.getItem(new Coordinates(i, j)).orElse(null);
                    if(item0!= null)
                        color = item0.getType().getValue();
                    else
                        break;

                    Item item1 = library.getItem(new Coordinates(i-1, j)).orElse(null);
                    Item item2 = library.getItem(new Coordinates(i-2, j)).orElse(null);
                    Item item3 = library.getItem(new Coordinates(i-3, j)).orElse(null);

                    someIsEmpty = item1==null || item2==null || item3==null;
                    if (!someIsEmpty){

                        allWithSameColor = item1.getType().getValue()==color &&
                                item2.getType().getValue()==color &&
                                item3.getType().getValue()==color;
                    }
                    if(allWithSameColor) colorGroup++;
                }
            }
            return colorGroup >= 4;
        }
        else if (type == 4){
            for (int i=1;i<6;i+=2){
                for(int j=0; j<4; j++){
                    Item item0 = library.getItem(new Coordinates(i, j)).orElse(null);
                    if(item0!= null)
                        color = item0.getType().getValue();
                    else
                        break;

                    Item item1 = library.getItem(new Coordinates(i-1, j)).orElse(null);
                    someIsEmpty = item1==null;
                    if (!someIsEmpty){
                        allWithSameColor = item1.getType().getValue()==color;
                    }
                    if(allWithSameColor) colorGroup++;
                }
            }
            return colorGroup >= 6;
        }
        return false;
    }

}

