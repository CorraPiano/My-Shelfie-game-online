package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Represents the coordinates of a position.
 */
public class Coordinates implements Serializable {
    private int column;
    private int row;

    /**
     * Constructs an instance of Coordinates.
     */
    public Coordinates() {
    }

    /**
     * Constructs an instance of Coordinates with the specified row and column.
     *
     * @param row    The row coordinate.
     * @param column The column coordinate.
     */
    public Coordinates(int row, int column) {
        this.column = column;
        this.row = row;
    }

    /**
     * Sets the column coordinate.
     *
     * @param column the column coordinate.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Sets the row coordinate.
     *
     * @param row the row coordinate.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Returns the column coordinate.
     *
     * @return The column coordinate.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Returns the row coordinate.
     *
     * @return the row coordinate.
     */
    public int getRow() {
        return row;
    }

    /**
     * Compares this Coordinates object to the specified object.
     *
     * @param obj The object to compare.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return obj.toString().equals(this.toString());
    }

    /**
     * Returns the hash code for this Coordinates object.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * Returns a string representation of the Coordinates object.
     *
     * @return The string representation of the Coordinates object.
     */
    @Override
    public String toString() {
        return Integer.toString(row) + "," + Integer.toString(column);
    }
}