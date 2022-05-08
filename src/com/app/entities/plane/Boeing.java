package com.app.entities.plane;


import com.app.entities.Seat;
import com.app.SeatType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Represents a Boeing aircraft
 * @author Jason Smit
 *
 */
@Entity
@DiscriminatorValue("Boeing")
public class Boeing extends Plane implements Serializable {
    
    public Boeing() {}
	
    public Boeing(int totalRows) {
            super(
                    7,
                    totalRows,
                    Math.toIntExact(Math.round(totalRows * 0.4))
            );
    }

    /**
     * Creates a mapping of seats specific the the Boeing aircraft.
     */
    @Override
    protected void initialiseSeatMap() {
        // Iterate rows then columns
        for(int i = 0; i < this.getTotalRows(); i++) { // row
            Map<Character, Seat> currentRow = new HashMap();

            for(int j = 0; j < this.getTotalColumns(); j++) { // column
                char column = (char) ('a' - 1 + (j + 1));
                SeatType type;

                // Find the seat type
                if(j == 0 || j == this.getTotalColumns() - 1) {type = SeatType.WINDOW;}
                else if ((j >= 1 || j <= this.getTotalColumns() - 2) && j != 3) {type = SeatType.AISLE;}
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
