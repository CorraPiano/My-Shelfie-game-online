package it.polimi.ingsw.model;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class PersonalGoalCard {
    private final DataCard card;
    private final Bookshelf library;
    private Integer points;
    public PersonalGoalCard(Bookshelf library) {
        this.points = 0;
        this.library = library;
        this.card = new DataCard(new Random().nextInt(11));
    }
    public int calculatePoints() {
        if(library == null) return 0;

        Set<Coordinates> coordinate = card.getCoordinate();
        int[] pointVet = { 1, 2, 4, 6, 9, 12};



        for (Coordinates c : coordinate) {
            library.getItem(c).ifPresent((x) -> {
                if(x.getType().getValue() == card.getColor(c)) {
                    points = points + 1;
                }
            });
        }

        return pointVet[points];
    }

    public DataCard getCard() {
        return card;
    }

}
