package it.polimi.ingsw.model.common_card_classes;

import it.polimi.ingsw.model.*;

public class XCommonGoalCard implements CommonGoalCard {

    private final Token token;
    private final int type;

    public XCommonGoalCard(int type, Token token) {
        this.token = token;
        this.type = type;
    }

    @Override
    public boolean checkFullFil(Bookshelf library) {

        Coordinates coordinates = new Coordinates();
        int color;
        boolean someIsEmpty, allWithSameColor;
        for(int i=1;i<5;i++){
            for(int j=1; j<4; j++){
                coordinates.setRow(i);
                coordinates.setColumn(j);

                if(library.getItem(coordinates).isPresent()){
                    Item item0 = library.getItem(coordinates).orElse(null);
                    color = item0.getType().getValue();

                    Item item1 = library.getItem(new Coordinates(i-1, j-1)).orElse(null);
                    Item item2 = library.getItem(new Coordinates(i+1, j+1)).orElse(null);
                    Item item3 = library.getItem(new Coordinates(i+1, j-1)).orElse(null);
                    Item item4 = library.getItem(new Coordinates(i-1, j+1)).orElse(null);

                    someIsEmpty = item1==null || item2==null || item3==null || item4==null;
                    if(!someIsEmpty){
                        allWithSameColor =  item1.getType().getValue()==color &&
                                            item2.getType().getValue()==color &&
                                            item3.getType().getValue()==color &&
                                            item4.getType().getValue()==color;
                        if(allWithSameColor) return true;
                    }

                }
            }
        }

        return false;
    }

    @Override
    public void setTokenList() {

    }

    @Override
    public Token showToken() {
        return token;
    }

    @Override
    public Token popToken() {
        return token;
    }
}
