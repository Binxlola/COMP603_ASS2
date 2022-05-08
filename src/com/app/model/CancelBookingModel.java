package com.app.model;

import com.app.entities.Flight;
import com.app.entities.plane.Plane;
import java.util.List;

/**
 *
 * @author jason
 */
public class CancelBookingModel extends ViewModel {
    
    private final List<Flight> flights;
    private Flight selectedFlight;
    private Plane plane;
    
    public CancelBookingModel() {
        super();
        this.flights = this.dbHelper.findAllFlights();
        this.selectedFlight = this.flights.get(0);
        this.plane = this.selectedFlight.getPlane();
    }
    
    
    //    ==== GETTERS OR SETTERS    
    public List<Flight> getFlights() {return this.flights;}    
    public Flight getSelectedFlight() {return this.selectedFlight;}
    public void setSelectedFlight(Flight flight) {
        this.selectedFlight = flight;
        this.plane = flight.getPlane();
    }
    public Plane getPlane() {return this.plane;}

}
