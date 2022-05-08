package com.app.entities;

import com.app.SeatType;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a given seat on an airline flight
 * @author Jason Smit
 *
 */
@Entity
@Table(name = "ReservedSeats")
public class Seat implements Serializable {
    
    public Seat() {}
	
    public Seat(int seatRow, char seatColumn, SeatType seatType, boolean isFirstClass) {
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
        this.seatType = seatType;
        this.isFirstClass = isFirstClass;
    }
    
    @Id
    @GeneratedValue
    private UUID id;
    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}
    
    @Column(name = "isFirstClass")
    private boolean isFirstClass;
    public boolean isFirstClass() {return this.isFirstClass;}
    public void setIsFirstClass(boolean isFirstClass) {this.isFirstClass = isFirstClass;}
    
    @Column(name = "isReserved")
    private boolean isReserved;
    public boolean isReserved() {return this.isReserved;}
    public void setIsReserved(boolean isReserved) {this.isReserved = isReserved;}
    
    @Column(name = "seatType")
    private SeatType seatType;
    public SeatType getSeatType() {return this.seatType;}
    public void setSeatType(SeatType seatType) {this.seatType = seatType;}
    
    @Column(name = "SeatRow")
    private int seatRow;
    public int getSeatRow() {return this.seatRow;}
    public void setSeatRow(int seatRow) {this.seatRow = seatRow;}
    
    @Column(name = "SeatColumn")
    private char seatColumn;
    public char getSeatColumn() {return this.seatColumn;}
    public void setSeatColumn(char seatColumn) {this.seatColumn = seatColumn;}
	
    @Override
    public String toString() {
            String type = this.isFirstClass ? String.valueOf(this.seatType.getShortHand()).toUpperCase() : String.valueOf(this.seatType.getShortHand());
            String status = this.isReserved ? "X" : "_";
            return String.format("[ %s %s ]", type,status);
    }
	
    /**
     * Creates a descriptive string about the current seat.
     * @return The descriptive String
     */
    public String getSeatDescription() {
            return String.format("\nThe %s class %s seat at %s%s is %s\n", 
                            this.isFirstClass ? "first" : "economy",
                            this.seatType,
                            this.seatRow + 1,
                            Character.toUpperCase(this.seatColumn),
                            this.isReserved ? "reserved" : "not reserved");
    }
}
