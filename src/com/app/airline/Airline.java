package com.app.airline;

import com.app.entities.Seat;
import com.app.SeatType;
import com.app.entities.Flight;

/**
 * parent class for a specific airline
 * @author Jason Smit
 *
 */
public abstract class Airline {
	
    private final String airlineName;

    public Airline(String airlineName) {
            this.airlineName = airlineName;
    }

    public String getAirlineName() {return this.airlineName;}

    @Override
    public String toString() {
            return this.airlineName;
    }
	
	
    /**
     * Used for reserving first class seat with an airline
     * @param aFlight The flight which the reservation will be made on
     * @param aType The type of seat to be reserved
     * @return The seat that was reserved
     */
    abstract public Seat reserveFirstClass(Flight aFlight, SeatType aType);
    /**
     * Used for reserving economy class seat with an airline
     * @param aFlight The flight which the reservation will be made on
     * @param aType The type of seat to be reserved
     * @return The seat that was reserved
     */
    abstract public Seat reserveEconomy(Flight aFlight, SeatType aType);
	

}
