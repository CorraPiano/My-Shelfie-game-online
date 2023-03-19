package it.polimi.ingsw.model;

import java.util.Random;

public class PersonalGoalCard {
    private final DataCard card;
    public PersonalGoalCard() {
        this.card = new DataCard(new Random().nextInt(11));
    }
    public int calculatePoints(){
        return 0;
    }

    public DataCard getCard() {
        return card;
    }
}
