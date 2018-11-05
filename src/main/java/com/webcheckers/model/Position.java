package com.webcheckers.model;

public class Position {

    // Attributes
    // TODO: limit possible values to 0 - 7 inclusive

    private int row;
    private int cell;

    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    /**
     * Returns row for given position
     * @return row
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Returns cell for given position
     * @return cell
     */
    public int getCell() {
        return this.cell;
   }
}
