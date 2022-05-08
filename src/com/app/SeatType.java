package com.app;

/**
 * An enumeration of all possible airline seat types
 * @author Jason Smit
 *
 */
public enum SeatType {
    WINDOW("Window", 'w'), AISLE("Aisle", 'a'), MIDDLE("Middle", 'm');
	
    private final String seatName;
    private final char shortHand;

    private SeatType(String seatName, char shortHand) {
        this.seatName = seatName;
        this.shortHand = shortHand;
    }

    public char getShortHand() {return this.shortHand;}
    public String getSeatName() {return this.seatName;}

    @Override
    public String toString() {
        return this.seatName;
    }

}
