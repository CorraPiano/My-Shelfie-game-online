package it.polimi.ingsw.model;

import it.polimi.ingsw.client.localModel.LocalPersonalCard;
import it.polimi.ingsw.connection.message.Sendable;

import java.util.Set;

public class PersonalGoalCard extends Listenable {
    private final DataCard card;
    private Bookshelf library;
    private Integer points;
    private String ID;
    //mettere numero a enum
    private int num;
    public PersonalGoalCard(int n) {
        this.card = new DataCard(n);
        this.num = n;
    }

    public void setBookshelf(Bookshelf library){
        this.library = library;
    }
    public void setID(String ID){
        this.ID = ID;
        notifyUpdateToID(ID);
    }
    public String getID(){
        return ID;
    }
    public int calculatePoints() {
        points = 0;
        if(library == null) return 0;

        Set<Coordinates> coordinate = card.getCoordinate();
        int[] pointVet = {0, 1, 2, 4, 6, 9, 12};

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

    @Override
    public LocalPersonalCard getLocal() {
        //return new LocalPersonalCard(card.getCardMatrix(),num);
        return new LocalPersonalCard(num);
    }

    public int getNum(){
        return this.num;
    }
}
