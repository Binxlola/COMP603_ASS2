package com.app;

import com.app.entities.Seat;
import com.app.interfacesFunctional.SeatReservation;
import com.app.airline.Airline;
import com.app.entities.Flight;

/**
 * Defines all possible service class types for booking, each type should have a 
 * defined full name. As well as a corresponding method reference which represents the type
 * of eat reservation meant to take place for each service type.
 * @author jason
 */
public enum ServiceClass {
    ECONOMY("Economy Class", ServiceClass::reserveEconomy), FIRST("First Class", ServiceClass::reserveFirst);
    
    private final String fullName;
    private final SeatReservation reservationMethod;
    
    private ServiceClass(String fullName, SeatReservation reservationMethod) {
        this.fullName = fullName;
        this.reservationMethod = reservationMethod;
    }
    
    /**
     * Used to reserve a seat for a flight. This method is meant to act similar to that 
     * of a factory. Given the input, it is expected that the correct logic for 
     * reservation based on service type will take place.
     * @param airline The airline in which the reservation being made with
     * @param flight The flight for with the reservation is being made on
     * @param seatType The requested seat type for the reservation being made
     * @return The reserved seat, can be null if no seat was available
     */
    public Seat reserve(Airline airline, Flight flight, SeatType seatType) {
        return reservationMethod.reserve(airline, flight, seatType);
    }
    
    /**
     * Calls the appropriate method when making a reservation of economy class type
     * @param airline The airline in which the reservation being made with
     * @param flight The flight for with the reservation is being made on
     * @param seatType The requested seat type for the reservation being made
     * @return The reserved seat, can be null if no seat was available
     */
    private static Seat reserveEconomy(Airline airline, Flight flight, SeatType seatType) {
        return airline.reserveEconomy(flight, seatType);
    }
    
    /**
     * Calls the appropriate method when making a reservation of first class type
     * @param airline The airline in which the reservation being made with
     * @param flight The flight for with the reservation is being made on
     * @param seatType The requested seat type for the reservation being made
     * @return The reserved seat, can be null if no seat was available
     */
    private static Seat reserveFirst(Airline airline, Flight flight, SeatType seatType) {
        return airline.reserveFirstClass(flight, seatType);
    }
    
    @Override
    public String toString() {
        return this.fullName;
    }

}
