package com.app.entities;

import com.app.entities.plane.Plane;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQuery;


/**
 *
 * @author jason
 */

@Entity
@Table(name = "Flights")
@NamedQuery(name = "Flight_findAll", query = "SELECT f FROM Flight f")
public class Flight implements Serializable {
    
    public Flight() {}
    
    public Flight(String flightNum, String depCity, String arrCity, String depTime, Plane plane) {
        this.flightNumber = flightNum;
        this.depCity = depCity;
        this.arrCity = arrCity;
        this.depTime = depTime;
        this. plane = plane;
    }


    @Id
    @GeneratedValue
    private UUID id;
    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}

    @Column(name = "flightNumber")
    private String flightNumber;
    public String getFlightNumber() {return flightNumber;}
    public void setFlightNumber(String flightNumber) {this.flightNumber = flightNumber;}

    @Column(name = "depatureCity", nullable = false)
    private String depCity;
    public String getDepatureCity() {return depCity;}
    public void setDepatureCity(String depCity) {this.depCity = depCity;}

    @Column(name = "arrivalCity", nullable = false)
    private String arrCity;
    public String getArrivalCity() {return arrCity;}
    public void setArrivalCity(String arrCity) {this.arrCity = arrCity;}

    @Column(name = "depatureTime")
    private String depTime;
    public String getDepatureTime() {return depTime;}
    public void setDepatureTime(String depTime) {this.depTime = depTime;}
    
   @ManyToOne(optional = false, cascade = CascadeType.ALL)
   private Plane plane;
   public Plane getPlane() {return this.plane;}
   public void setPlane(Plane plane) {this.plane = plane;}
   
   @Override
   public String toString() {
       String temp = String.format("%s: %s to %s departing at %s",
               this.flightNumber,
               this.depCity,
               this.arrCity,
               this.depTime
       );
       
       return temp;
   }
}
