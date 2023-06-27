package it.polimi.ingsw.model;

import it.polimi.ingsw.client.localModel.LocalPersonalCard;
import it.polimi.ingsw.connection.message.Sendable;

import java.util.Set;

/**
 * The PersonalGoalCard class represents a personal goal card in the game.
 * It extends the Listenable class and provides methods to calculate points and retrieve information about the card.
 */
public class PersonalGoalCard extends Listenable {
    private final DataCard card;
    private Bookshelf library;
    private Integer points;
    private String ID;
    //mettere numero a enum
    private int num;


    /**
     * Constructs a PersonalGoalCard object with the specified number.
     *
     * @param n The number of the personal goal card.
     */
    public PersonalGoalCard(int n) {
        this.card = new DataCard(n);
        this.num = n;
    }

    /**
     * Calculates the points earned by the player based on the personal goal card and the bookshelf.
     *
     * @return The points earned.
     */
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

    /**
     * Sets the bookshelf for the personal goal card.
     *
     * @param library The Bookshelf instance to set.
     */
    public void setBookshelf(Bookshelf library){
        this.library = library;
    }

    public Bookshelf getLibrary() {
        return library;
    }

    /**
     * Sets the ID of the personal goal card.
     *
     * @param ID The ID to set.
     */
    public void setID(String ID){
        this.ID = ID;
        notifyUpdateToID(ID);
    }

    /**
     * Returns the ID of the personal goal card.
     *
     * @return The ID of the personal goal card.
     */
    public String getID(){
        return ID;
    }

    /**
     * Returns the data card associated with the personal goal card.
     *
     * @return The data card.
     */
    public DataCard getCard() {
        return card;
    }

    /**
     * Returns the number of the personal goal card.
     *
     * @return The number of the personal goal card.
     */
    public int getNum(){
        return this.num;
    }

    /**
     * Converts to local version of the PersonalGoalCard.
     * @return created LocalPersonalCard object.
     */
    @Override
    public LocalPersonalCard getLocal() {
        //return new LocalPersonalCard(card.getCardMatrix(),num);
        return new LocalPersonalCard(num);
    }
}
