package com.app.model;

import com.app.SeatType;
import com.app.ServiceClass;
import com.app.airline.Airline;
import com.app.airline.SimpleWay;
import com.app.airline.SmartLine;
import com.app.entities.Flight;
import com.app.entities.plane.Plane;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jason
 */
public class MakeBookingModel extends ViewModel {
    
    private final List<Flight> flights;
    private final List<Airline> airlines;
    private final ServiceClass[] serviceClasses;
    private final SeatType[] seatTypes;
    private Flight selectedFlight;
    private Airline selectedAirline;
    private ServiceClass service;
    private SeatType type;
    private Plane plane;
    private int numColumns;
    private int numRows;
    
    public MakeBookingModel() {
        super();
        this.flights = this.dbHelper.findAllFlights();
        
        // Add available airlines
        this.airlines = new ArrayList();
        this.airlines.add(new SimpleWay("Simple Way Airline"));
        this.airlines.add(new SmartLine("Smart Line Airline"));
        
        this.selectedAirline = this.airlines.get(0);
        this.serviceClasses = ServiceClass.values();
        this.seatTypes = SeatType.values();
        this.service = this.serviceClasses[0];
        this.type = this.seatTypes[0];
        
        if(!this.flights.isEmpty()) {
            this.selectedFlight = this.flights.get(0);
            this.plane = this.selectedFlight.getPlane();
        }
    }
    
    //    ==== GETTERS OR SETTERS   
    public List<Flight> getFlights() {return this.flights;}    
    public List<Airline> getAirlines() {return this.airlines;}
    public ServiceClass[] getServiceClasses() {return this.serviceClasses;}
    public SeatType[] getSeatTypes() {return this.seatTypes;}
    
    public void setSelectedAirline(Airline airline) {this.selectedAirline = airline;}
    public Airline getSelectedAirline() {return this.selectedAirline;}
    public Flight getSelectedFlight() {return this.selectedFlight;}
    public void setSelectedFlight(Flight flight) {
        this.selectedFlight = flight;
        this.plane = flight.getPlane();
        this.numColumns = this.plane.getTotalColumns();
        this.numRows = this.plane.getTotalRows();
    }
    public ServiceClass getSelectedService() {return this.service;}
    public void setSelectedService(ServiceClass service) {this.service = service;}
    public SeatType getSelectedType() {return this.type;}
    public void setSelectedType(SeatType type) {this.type = type;}
    public Plane getPlane() {return this.plane;}
    public int getNumColumns() {return this.numColumns;}
    public int getNumRows() {return this.numRows;}

}
