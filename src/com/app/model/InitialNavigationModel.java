package com.app.model;

import com.app.entities.Flight;
import com.app.entities.plane.Plane;
import java.util.List;

/**
 *
 * @author jason
 */
public class InitialNavigationModel extends ViewModel {
    
    private List<Flight> flights;
    private List<Plane> planes;
    
    public InitialNavigationModel() {
        super();
        
        this.flights = this.dbHelper.findAllFlights();
        this.planes = this.dbHelper.findAllPlanes();
    }
    
    public List<Flight> getFlights() {return this.flights;}
    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
    public List<Plane> getPlanes() {return this.planes;}
    public void setPlanes(List<Plane> planes) {
        this.planes = planes;
    }

}
