package it.polimi.ingsw.model;

public class Coordinates {
    private int column;
    private int row;

    public Coordinates() {
    }

    public Coordinates(int row, int column) {
        this.column = column;
        this.row = row;
    }

    public void setColumn(int column){
        this.column = column;
    }
    public void setRow(int row){
        this.row = row;
    }


    public int getColumn(){
        return column;
    }
    public int getRow(){
        return row;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.toString().equals(this.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        return Integer.toString(row)+","+ Integer.toString(column);
    }
}
