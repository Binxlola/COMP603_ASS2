package com.app.airline;

import com.app.entities.Seat;
import com.app.SeatType;
import com.app.entities.Flight;
import com.app.entities.plane.Plane;

/**
 * Represents an airline
 * @author Jason Smit
 *
 */
public class SimpleWay extends Airline {

    public SimpleWay(String airlineName) {
            super(airlineName);
    }

    /**
     * Looks for an available first class seat to book.Preferably one of the given type.If this is not possible then any first class seat will be booked if there is one. If none of the above options is possible then a booking will be made on the next available economy seat that is of a middle or window type. If there is one a the neighboring seat will also be reserved.
 If there is nothing that can be booked meeting the above criteria then a booking can not be made
     * @param flight The flight on which the seat is being booked
     * @param seatType The type of seat being booked on the flight
     */
    @Override
    public Seat reserveFirstClass(Flight flight, SeatType seatType) {
            Plane plane = flight.getPlane();
            Seat avalSeat = plane.queryAvailableFirstClassSeat(seatType);

            // Primary criteria is met, then reservation is finished
            if((avalSeat != null) && (avalSeat.getSeatRow() < plane.getFirstClassRows())) {
                avalSeat.setIsReserved(true);
                return avalSeat;
            } else {
                    // Primary conditions were not met, secondary flow starts
                    avalSeat = plane.queryAvailableEconomySeat(SeatType.WINDOW);
                    // First check didn't return a MIDDLE type (Or Window by random), query again specifically for WINDOW type
                    if(!(avalSeat.getSeatType().equals(SeatType.MIDDLE)) && !(avalSeat.getSeatType().equals(SeatType.WINDOW))) avalSeat = plane.queryAvailableEconomySeat(SeatType.MIDDLE);

                    // Secondary flow was met, reserve the seat and a neighboring seat
                    if((avalSeat != null) && ((avalSeat.getSeatType().equals(SeatType.MIDDLE)) || (avalSeat.getSeatType().equals(SeatType.WINDOW)))) {

                            Seat left = plane.getLeft(avalSeat);
                            Seat right = plane.getRight(avalSeat);

                            // Check left first
                            if(left != null && !(left).isReserved()) {
                                left.setIsReserved(true);
                                avalSeat.setIsReserved(true);
                                return avalSeat;
                            }

                            // Check right second
                            if(right != null && !(right).isReserved()) {
                                right.setIsReserved(true);
                                avalSeat.setIsReserved(true);
                                return avalSeat;
                            }

                            // If we get here then none of the required conditions was met for making a booking
                            return null;
                    }
            }
            return avalSeat;
    }

    /**
     * Looks for an available economy seat to book.Preferably one of the given type.If this is not possible then and economy seat will be booked if there is one. If none of the above options is possible then a booking can not be made.
     * @param flight The flight for which the seat is being booked on
     * @param seatType The type of seat being booked on the flight
     */
    @Override
    public Seat reserveEconomy(Flight flight, SeatType seatType) {
            Plane plane = flight.getPlane();
            Seat avalSeat = plane.queryAvailableEconomySeat(seatType);

            if(avalSeat != null) {avalSeat.setIsReserved(true);}
            return avalSeat;
    }

}
