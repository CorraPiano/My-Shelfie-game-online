package it.polimi.ingsw.model;

import java.util.Random;
import java.util.Set;

public class PersonalGoalCard {
    private final DataCard card;
    private final Bookshelf library;
    public PersonalGoalCard(Bookshelf library) {
        this.library = library;
        this.card = new DataCard(new Random().nextInt(11));
    }
    public int calculatePoints(){
        Set<int[]> coordinate = card.getCoordinate();
        int[] pointVet = { 1, 2, 4, 6, 9, 12};
        Coordinates xy = new Coordinates();

        int point = 0;

        for (int[] c : coordinate) {
            xy.setRow(c[0]);
            xy.setColumn(c[1]);

            if(library.getItem(xy).getType().getValue() == card.getColor(c))
                point++;
        }


        return 0;
    }

    public DataCard getCard() {
        return card;
    }
}
