package it.polimi.ingsw.model.common_card_classes;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

public class DiagonalCommonGoalCard extends CommonGoalCard {
    private final int type;
    private List<Token> token;
    public DiagonalCommonGoalCard(int type) {
        this.type = type;
    }

    @Override
    public boolean checkFullFil(Bookshelf library) {

        if (type == 1) {

            // Diagonale con colore uguale

            boolean principalDiagonal = true;
            boolean secondDiagonal = true;
            int color00 = -1;

            // Diagonale principale
            Coordinates coordinates = new Coordinates(0, 0);
            if(library.getItem(coordinates).isPresent()) {
                color00 = library.getItem(coordinates).get().getType().getValue();
            }else{
                principalDiagonal = false;
            }

            for(int i = 1; i < 5 && principalDiagonal; i++){
                for(int j = 1; j < 5; j++){

                    coordinates.setRow(i);
                    coordinates.setColumn(j);

                    if(library.getItem(coordinates).isEmpty() || library.getItem(coordinates).get().getType().getValue() != color00){
                         principalDiagonal = false;
                        break;
                    }
                }
            }

            if(principalDiagonal) {
                return true;
            }
            // Diagonale secondaria
            coordinates.setRow(4);
            coordinates.setColumn(4);
            if(library.getItem(coordinates).isPresent()) {
                color00 = library.getItem(coordinates).get().getType().getValue();
            }else{
                secondDiagonal = false;
            }

            for(int i = 4; i >= 0 && secondDiagonal; i--){
                for(int j = 4; j>= 0; j--){

                    coordinates.setRow(i);
                    coordinates.setColumn(j);

                    if(library.getItem(coordinates).isEmpty() || library.getItem(coordinates).get().getType().getValue() != color00){
                        secondDiagonal = false;
                        break;
                    }

                }
            }

            return secondDiagonal;
        }
        else{
            // Matrice triangolare
            boolean triangularMatrix = true;
            Coordinates coordinates = new Coordinates();

            for(int i = 0; i < 5; i++){
                for(int j = 0; j < i + 1; j++){
                    coordinates.setRow(i);
                    coordinates.setColumn(j);

                    if(library.getItem(coordinates).isEmpty()) {
                        triangularMatrix = false;
                        break;
                    }
                }
            }
            if(triangularMatrix) return true;
            triangularMatrix = true;
            for(int i = 0; i < 5; i++){
                for(int j = 4; j >= 0; j--){
                    coordinates.setRow(i);
                    coordinates.setColumn(j);

                    if(library.getItem(coordinates).isEmpty()) {
                        triangularMatrix = false;
                        break;
                    }
                }
            }
            return triangularMatrix;
        }

    }
}
