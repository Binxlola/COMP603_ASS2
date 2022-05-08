package com.app.entities.plane;

import com.app.entities.Seat;
import com.app.SeatPosition;
import com.app.SeatType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Represents an AirBus aircraft
 * @author Jason Smit
 *
 */
@Entity
@DiscriminatorValue("AirBus")
public class Airbus extends Plane implements Serializable {
    
    public Airbus() {}
    
    public Airbus(int totalRows) {
        super(
                9, 
                totalRows, 
                Math.toIntExact(Math.round(totalRows * 0.5))
        );
    }

    /**
     * Creates a mapping of seats specific the the AirBus aircraft.
     */
    @Override
    protected void initialiseSeatMap() {
        // Iterate rows then columns
        for(int i=0;i<this.getTotalRows();i++) { // Row
            Map<Character, Seat> currentRow = new HashMap();

            for(int j=0;j<this.getTotalColumns();j++) { // Column
                char column = (char) ('a' - 1 + (j + 1));
                SeatType type;

                // Find the seat type
                if(j == 0 || j == this.getTotalColumns() - 1) {type = SeatType.WINDOW;}
                else if ((j >= 2 && j <= this.getTotalColumns() - 3) && j != 4) {type = SeatType.AISLE;}
                else {type = SeatType.MIDDLE;}

                // Create seat in array with correct attributes.
                currentRow.put(
                        column, 
                        new Seat(i, column, type, i < this.getFirstClassRows())
                );
            }

            this.seats.put(i, currentRow);
        }	
    }
}
