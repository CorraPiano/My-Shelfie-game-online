package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Board {

    private Item [][] LivingRoom; // state of the living room
    private BagItem bag;
    private int Row;
    private int Columns;
    private int [][] masks;


    public Board() {};
    public void createBoard() {};
    public void refillBoard() {};
    public ArrayList<Item> getItems (Coordinates[] coordinates) {};
    public boolean checkRefill() {};

}
