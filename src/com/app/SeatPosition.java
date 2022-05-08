package com.app;

/**
 * Represents the position of an airline seat
 * @author Jason Smit
 *
 */
public class SeatPosition {

    private final int row;
    private final char column;

    public SeatPosition(int row, char column) {
            this.row = row;
            this.column = column;

    }

    public int getRow() {return this.row;}
    public char getColumn() {return this.column;}
}
