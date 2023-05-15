package it.polimi.ingsw.model;

import java.util.Set;

public class PersonalGoalCard extends Listenable{
    private final DataCard card;
    private Bookshelf library;
    private Integer points;
    private String ID;
    public PersonalGoalCard(int n) {
        this.card = new DataCard(n);
    }

    public void setBookshelf(Bookshelf library){
        this.library = library;
    }
    public void setID(String ID){
        this.ID = ID;
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

    public void send(){
        notifyListener("PERSONAL");
    }

    public DataCard getCard() {
        return card;
    }


}
