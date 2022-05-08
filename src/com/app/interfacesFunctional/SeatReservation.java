package com.app.interfacesFunctional;

import com.app.entities.Seat;
import com.app.SeatType;
import com.app.airline.Airline;
import com.app.entities.Flight;

/**
 * This interface defines a function type intended for method referencing
 * @author jason
 */
@FunctionalInterface
public interface SeatReservation {
    Seat reserve(Airline airline, Flight flight, SeatType seatType);
}
