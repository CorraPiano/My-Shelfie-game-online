package it.polimi.ingsw.model.common_card_classes;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The DiagonalCommonGoalCard class represents a common goal card that requires specific item placement patterns on the bookshelf.
 * Description: Five tiles of the same type forming a diagonal.
 * It extends the CommonGoalCard class and implements the checkFullFil() method to check if the goal is fulfilled.
 */

public class DiagonalCommonGoalCard extends CommonGoalCard {
    //private final int type;
    private List<Token> token;

    /**
     * Constructs a DiagonalCommonGoalCard object with the specified type.
     *
     * @param type the type of the goal card
     */
    public DiagonalCommonGoalCard(int type) {
        super(type);
    }

    /**
     * Checks if the goal card is fulfilled by checking the item placement patterns on the bookshelf.
     *
     * @param library the bookshelf to check for item placement
     * @return true if the goal is fulfilled, false otherwise
     */
    @Override
    public boolean checkFullFil(Bookshelf library) {

        if (type == 6) {

            // Diagonale con colore uguale

            boolean principalDiagonal = true;
            boolean secondDiagonal = true;
            int color00 = -1;

            // Diagonale principale 1
            Coordinates coordinates = new Coordinates(0, 0);
            if(library.getItem(coordinates).isPresent()) {
                color00 = library.getItem(coordinates).get().getType().getValue();
            }else{
                principalDiagonal = false;
            }

            for(int i = 1; i < 5 && principalDiagonal; i++){
                coordinates.setRow(i);
                coordinates.setColumn(i);

                if(library.getItem(coordinates).isEmpty() || library.getItem(coordinates).get().getType().getValue() != color00){
                     principalDiagonal = false;
                    break;
                }
            }
            if(principalDiagonal) return true;

            // Diagonale principale 2

            principalDiagonal = true;
            coordinates = new Coordinates(1, 0);
            if(library.getItem(coordinates).isPresent()) {
                color00 = library.getItem(coordinates).get().getType().getValue();
            }else{
                principalDiagonal = false;
            }

            for(int i = 2; i < 6 && principalDiagonal; i++){
                    coordinates.setRow(i);
                    coordinates.setColumn(i-1);

                    if(library.getItem(coordinates).isEmpty() || library.getItem(coordinates).get().getType().getValue() != color00){
                        principalDiagonal = false;
                        break;
                    }
                }

            if(principalDiagonal) return true;


            // Diagonale secondaria 1
            coordinates.setRow(4);
            coordinates.setColumn(0);
            if(library.getItem(coordinates).isPresent()) {
                color00 = library.getItem(coordinates).get().getType().getValue();
            }else{
                secondDiagonal = false;
            }
            int j = 0;
            for(int i = 4; i >= 0 && secondDiagonal; i--){
                coordinates.setRow(i);
                coordinates.setColumn(j);

                if(library.getItem(coordinates).isEmpty() || library.getItem(coordinates).get().getType().getValue() != color00){
                    secondDiagonal = false;
                    break;
                }
                j++;
            }
            if (secondDiagonal) return true;

            // Diagonale secondaria 2
            secondDiagonal = true;
            coordinates.setRow(5);
            coordinates.setColumn(0);
            if(library.getItem(coordinates).isPresent()) {
                color00 = library.getItem(coordinates).get().getType().getValue();
            }
            else{
                secondDiagonal = false;
            }
            j = 1;
            for(int i = 4; i >= 1 && secondDiagonal; i--){
                coordinates.setRow(i);
                coordinates.setColumn(j);

                if(library.getItem(coordinates).isEmpty() || library.getItem(coordinates).get().getType().getValue() != color00){
                    secondDiagonal = false;
                    break;
                }
                j++;
            }

            return secondDiagonal;
        }
        else{
            // Matrice triangolare

            Coordinates coordinates = new Coordinates();
            //---- DIAGONALE PRINCIPALE -------
            // Diagonale partendo da (0,0) non vuoto
            boolean triangularMatrix = true;
            for(int i = 0; i < 6; i++){
                for(int j = 0; j < 5; j++){
                    coordinates.setRow(i);
                    coordinates.setColumn(j);

                    if(j <= i && library.getItem(coordinates).isEmpty()) {
                        triangularMatrix = false;
                        break;
                    }
                    if(j > i && library.getItem(coordinates).isPresent()) {
                        triangularMatrix = false;
                        break;
                    }

                }
                if (!triangularMatrix) break;
            }
            if(triangularMatrix) return true;
            // Diagonale partendo da (0,0) vuoto
            triangularMatrix = true;
            for(int i = 0; i < 6; i++){
                for(int j = 0; j < 5; j++){
                    coordinates.setRow(i);
                    coordinates.setColumn(j);

                    if(j < i && library.getItem(coordinates).isEmpty()) {
                        triangularMatrix = false;
                        break;
                    }
                    if(j >= i && library.getItem(coordinates).isPresent()) {
                        triangularMatrix = false;
                        break;
                    }
                }
                if (!triangularMatrix) break;
            }
            if(triangularMatrix) return true;



            //---- DIAGONALE SECONDARIA -------

            // Diagonale partendo da (0,4) vuoto
            int k = 5;
            triangularMatrix = true;
            for(int i = 0; i < 6; i++){
                for(int j = 0; j < 5 ; j++){
                    coordinates.setRow(i);
                    coordinates.setColumn(j);

                    if(j >= k && library.getItem(coordinates).isEmpty()) {
                        triangularMatrix = false;
                        break;
                    }
                    if(j < k && library.getItem(coordinates).isPresent()) {
                        triangularMatrix = false;
                        break;
                    }

                }
                if (!triangularMatrix) break;
                if (k>0) k--;
            }
            if(triangularMatrix) return true;
            // Diagonale partendo da (0,4) non vuoto
            k = 4;
            triangularMatrix = true;
            for(int i = 0; i < 6; i++){
                for(int j = 0; j < 5 ; j++){
                    coordinates.setRow(i);
                    coordinates.setColumn(j);

                    if(j >= k && library.getItem(coordinates).isEmpty()) {
                        triangularMatrix = false;
                        break;
                    }
                    if(j < k && library.getItem(coordinates).isPresent()) {
                        triangularMatrix = false;
                        break;
                    }

                }
                if (!triangularMatrix) break;
                if (k>0) k--;
            }
            if(triangularMatrix) return true;


            return triangularMatrix;
        }

    }
}
