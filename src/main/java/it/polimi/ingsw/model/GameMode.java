package it.polimi.ingsw.model;

public enum GameMode {
    EASY(0),
    EXPERT(1);
    private final int value;
    GameMode (int value) {
        this.value = value;
    }

    public boolean equals(GameMode obj){
        return obj.value == this.value;
    }
}
