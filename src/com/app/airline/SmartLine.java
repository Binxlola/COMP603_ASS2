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
public class SmartLine extends Airline {

    public SmartLine(String airlineName) {
            super(airlineName);
    }

    /**
     * Looks for an available first class seat to book.Preferably one of the given type.If this is not possible then and first seat will be booked if there is one. If none of the above options is possible then a booking will be made on the next available economy seat that is of a middle type. If there is one then the neighboring seats will also be reserved.
 If there is nothing that can be booked meeting the above criteria then a booking can not be made
     * @param flight The flight on which the seat is being booked
     * @param seatType The type of seat that is being booked on the flight
     */
    @Override
    public Seat reserveFirstClass(Flight flight, SeatType seatType) {
        Plane plane = flight.getPlane();
        Seat avalSeat = plane.queryAvailableFirstClassSeat(seatType);

        if(avalSeat == null) {avalSeat = plane.queryAvailableEconomySeat(SeatType.MIDDLE);}

        if(avalSeat != null && !avalSeat.isFirstClass()) {

                //Economy mid seat was found, reserve the neighboring seats for extra room
                if((avalSeat.getSeatRow() >= plane.getFirstClassRows()) && (avalSeat.getSeatType().equals(SeatType.MIDDLE))) {

                        // Account for already booked seats, if either is already booked we can not make a reservation
                        if(plane.getLeft(avalSeat).isReserved() || plane.getRight(avalSeat).isReserved()) {return null;}

                        // Reserve seat according to requirements
                        plane.getLeft(avalSeat).setIsReserved(true);
                        plane.getRight(avalSeat).setIsReserved(true);
                        avalSeat.setIsReserved(true);
                }
        } else {
            assert avalSeat != null;
            avalSeat.setIsReserved(true);
        }

        return avalSeat;
    }

    /**
     * Looks for an available economy seat to book.Preferably one of the given type.If this is not possible then and economy seat will be booked if there is one. If none of the above options is possible then a booking can not be made.
     * @param flight The flight on which the seat is being booked
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
