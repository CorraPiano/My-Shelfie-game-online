package it.polimi.ingsw.model.common_card_classes;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The GroupCommonGoalCard class represents a common goal card that requires specific grouping conditions on a bookshelf.
 * It extends the CommonGoalCard class and implements the checkFullFil() method to check if the goal is fulfilled.
 */
public class GroupCommonGoalCard extends CommonGoalCard {
    //private final int type;
    private List<Token> token;
    private boolean[][] mask;
    private final int nRows;
    private final int nColumns;

    /**
     * Constructs a GroupCommonGoalCard object with the specified type.
     *
     * @param type the type of the goal card
     */
    public GroupCommonGoalCard(int type) {
        super(type);
        this.nRows = 6;
        this.nColumns = 5;
        this.mask = new boolean[6][5];
    }

    /**
     * Checks if the goal card is fulfilled by checking for specific grouping conditions on the bookshelf.
     *
     * @param library the bookshelf to check for grouping conditions
     * @return true if the goal is fulfilled, false otherwise
     */
    @Override
    public boolean checkFullFil(Bookshelf library) {
        boolean someIsEmpty = false, allWithSameColor = false;
        int color, colorGroup=0;


        if(type == 3){
             // Square
            Item item1, item2, item3;
            Item pre1, pre2;
            for(int i = 0 ; i < 5; i++){
                for(int j=0; j<4; j++){
                    Item item0 = library.getItem(new Coordinates(i, j)).orElse(null);
                    if(item0!= null)
                        color = item0.getType().getValue();
                    else
                        break;

                    item1 = library.getItem(new Coordinates(i+1, j)).orElse(null);
                    item2 = library.getItem(new Coordinates(i+1, j+1)).orElse(null);
                    item3 = library.getItem(new Coordinates(i, j+1)).orElse(null);

                    someIsEmpty = item1==null || item2==null || item3==null;

                    if (!someIsEmpty){
                        // lato sx
                        if (i<5 && j > 0 ){
                            pre1 = library.getItem(new Coordinates(i, j-1)).orElse(null);
                            pre2 = library.getItem(new Coordinates(i+1, j-1)).orElse(null);
                            if (pre1 != null && pre2 != null){
                                if(pre1.getType().getValue()== color || pre2.getType() .getValue() == color) break;
                            }
                        }
                        // lato dx
                        if (i < 5 && j < 3 ){
                            pre1 = library.getItem(new Coordinates(i, j+2)).orElse(null);
                            pre2 = library.getItem(new Coordinates(i+1, j+2)).orElse(null);
                            if (pre1 != null && pre2 != null){
                                if(pre1.getType().getValue()== color || pre2.getType() .getValue() == color)
                                    break;
                            }
                        }
                        // lato in alto
                        if (i > 0 && j < 4 ){
                            pre1 = library.getItem(new Coordinates(i -1, j)).orElse(null);
                            pre2 = library.getItem(new Coordinates(i -1, j+1)).orElse(null);
                            if (pre1 != null && pre2 != null){
                                if(pre1.getType().getValue()== color || pre2.getType() .getValue() == color) break;
                            }
                        }
                        // lato in basso
                        if (i < 4 && j < 4 ){
                            pre1 = library.getItem(new Coordinates(i+2, j)).orElse(null);
                            pre2 = library.getItem(new Coordinates(i+2, j+1)).orElse(null);
                            if (pre1 != null && pre2 != null){
                                if(pre1.getType().getValue()== color || pre2.getType() .getValue() == color) break;
                            }
                        }


                        allWithSameColor = item1.getType().getValue()==color &&
                                item2.getType().getValue()==color &&
                                item3.getType().getValue()==color;
                    }
                    if(allWithSameColor) {
                        colorGroup++;
                        allWithSameColor = false;
                    }
                }
            }
            return colorGroup >= 2;
        }
        else if(type == 1 || type == 0){
            // Adiacency
            int nGroup = 0;
            int counter = 0;
            Item[][] mat = new Item[nRows][nColumns];

            for(int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) mask[i][j] = false;
            }

            for(int i = 0; i < nRows; i++) {
                for(int j = 0; j < nColumns; j++) {
                    mat[i][j] = library.getItem(new Coordinates(i, j)).orElse(null);
                    //System.out.println(mat[i][j].getType().getValue());
                }
            }


            // Creo library

            for(int i = 0; i < nRows; i++) {
                for(int j = 0; j < nColumns; j++) {
                    if(!mask[i][j] && mat[i][j] != null) {
                        counter = 1;
                        mask[i][j] = true;
                        counter = countNearbyItems(i, j, counter, mat[i][j].getType(), mat);

                        nGroup += counter / ((type == 1)? 4: 2);
                    }

                }
            }
            return nGroup >= ((type == 1) ? 4 : 6);
        }
        return false;
    }

    private int countNearbyItems(int i, int j, int counter, ItemType type, Item[][] library) {
        if((j+1) < nColumns && library[i][j+1] != null) {
            if(!mask[i][j+1] && library[i][j+1].getType() == type) {
                mask[i][j+1] = true;
                counter = 1 + countNearbyItems(i, j+1, counter, type, library);
            }
        }
        if((j-1) >= 0 && library[i][j-1] != null) {
            if(!mask[i][j-1] && library[i][j-1].getType() == type) {
                mask[i][j-1] = true;
                counter = 1 + countNearbyItems(i, j-1, counter, type, library);
            }
        }
        if((i+1) < nRows && library[i+1][j] != null) {
            if(!mask[i+1][j] && library[i+1][j].getType() == type) {
                mask[i+1][j] = true;
                counter = 1 + countNearbyItems(i+1, j, counter, type, library);
            }
        }
        if((i-1) >= 0 && library[i-1][j] != null) {
            if(!mask[i-1][j] &&  library[i-1][j].getType() == type) {
                mask[i-1][j] = true;
                counter = 1 + countNearbyItems(i-1, j, counter, type, library);
            }
        }
        return counter;
    }

}

