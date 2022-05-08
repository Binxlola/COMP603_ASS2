package com.app.entities.plane;

import com.app.entities.Seat;
import com.app.SeatType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.NamedQuery;

/**
 *
 * @author jason
 */

@Entity
@Inheritance
@DiscriminatorColumn(name = "planeType")
@Table(name = "Planes")
@NamedQuery(name = "Plane_FindAll", query = "SELECT p FROM Plane p")
@NamedQuery(name = "Plane_FindByNameAndRows", query = "SELECT p FROM Plane p WHERE type(p) = :type and p.rows = :rows")
public abstract class Plane implements Serializable {
    
    @Transient
    protected final Map<Integer, Map<Character, Seat>> seats = new HashMap();
    
    public Plane() {}
    
    public Plane(int totalColumns, int totalRows, int firstRows) {
        this.nColumns = totalColumns;
        this.rows = totalRows;
        this.nFirstRows = firstRows;
        
    }
    
    @Id
    @GeneratedValue
    private UUID id;
    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}
    
    @Column(name = "totalRows")
    private int rows;
    public int getTotalRows() {return rows;}
    public void setTotalRows(int nRows) {this.rows = nRows;}
    
    @Column(name = "totalColumns")
    private int nColumns;
    public int getTotalColumns() {return nColumns;}
    public void setTotalColumns(int nColumns) {this.nColumns = nColumns;}
    
    @Column(name = "firstClassRows")
    private int nFirstRows;
    public int getFirstClassRows() {return nFirstRows;}
    public void setFirstClassRows(int nFirstRows) {this.nFirstRows = nFirstRows;}
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Seat> reservedSeats;
    public List<Seat> getReservedSeats() {return this.reservedSeats;}
    public void setReservedSeats(List<Seat> reservedSeats) {this.reservedSeats = reservedSeats;}
    public void addReservedSeat(Seat reservedSeat) {this.reservedSeats.add(reservedSeat);}
    
    /**
     * Finds the last seat column and converts it to it's respective character
     * @return Character representation of the last seating column
     */
    public char lastSeatColumn() {return (char) ('a' - 1 + this.nColumns);};
    
    /**
     * Given a specific seat will return the seat to the left.
     * @param seat The seat used for query
     * @return The seat directly to the left
     */
    public Seat getLeft(Seat seat) {
        int currentSeatRow = seat.getSeatRow();
        int nextSeatColumn = (char) Character.getDirectionality(
                Character.toLowerCase(seat.getSeatColumn())
        ) - 1;

        return this.seats
                .get(currentSeatRow)
                .get((char) nextSeatColumn);
    }
    
    /**
     * Given a specific seat will return the seat to the right.
     * @param seat The seat used for query
     * @return The seat directly to the right
     */
    public Seat getRight(Seat seat) {
        int currentSeatRow = seat.getSeatRow();
        int nextSeatColumn = (char) Character.getDirectionality(
                Character.toLowerCase(seat.getSeatColumn())
        ) + 1;

        return this.seats
                .get(currentSeatRow)
                .get((char) nextSeatColumn);
    };
    
    /**
     * Finds a seat that is available to be booked if there is one. Otherwise null is returned.
     * @param type The preferred type of seat.
     * @return The seat in economy matching the seatType if one was available, or any available seat in economy if
     * the specified type was not. If neither of those is met then returns null.
     */
    public Seat queryAvailableEconomySeat(SeatType type) {
        Seat lastAval = null;
        for(int i = this.nFirstRows; i < this.seats.size() ; i++) {
                for(int j = 0; j < this.nColumns; j++) {
                    char column = (char) ('a' - 1 + (j + 1));
                    Seat current = this.seats.get(i).get(column);

                    boolean isReserved = current.isReserved();
                    if(!isReserved && current.getSeatType() == type) return current;
                    else if(!isReserved) lastAval = current;
                }

        }

        return lastAval;
    };
    
    /**
     * Finds a seat that is available to be booked if there is one. Otherwise null is returned.
     * @param type The preferred type of seat.
     * @return The seat in first class matching the seatType if one was available, or any available seat in first class if
     * the specified type was not. If neither of those is met then returns null.
     */
    public Seat queryAvailableFirstClassSeat(SeatType type) {
        Seat lastAval = null;
        for(int i = 0; i < this.nFirstRows; i++) {
            for(int j=0;j<this.nColumns;j++) {
                char column = (char) ('a' - 1 + (j + 1));
                Seat current = this.seats.get(i).get(column);

                boolean isReserved = current.isReserved();
                if(!isReserved && current.getSeatType() == type) return current;
                else if(!isReserved) lastAval = current;
            }
        }

        return lastAval;
    }
    
    /**
     * Takes a row number and column letter and uses these to return the seat at the given position.
     * Before querying the seat map, the given character is converted to lower case and cast to it's int/ASCII value.
     * We then subtract the int value of the character 'a' and add one. This given us the numeric position of the character in the alphabet.
     * @param row The row the the seat will be in.
     * @param column The column the the seat will be in
     * @return The seat object representing the queried seat.
     */
    public Seat getSeat(int row, char column) {
        return this.seats.get(row).get(Character.toLowerCase(column));
    };
    
    public String getSeatingString() {
        String columns = "    "; // 4 spaces
            String temp = "";

            for(int x = 0; x < this.nColumns; x++) {
                columns += (char) ('a' + (x - 32)) + "      ";
            }

            for(int i = 0; i < this.rows; i++) {
                    temp += i < 9 ? " " + (i+1): (i+1); // line up single digits with double digits
                    for(int j=0;j<this.nColumns;j++) {
                        char column = (char) ('a' - 1 + (j + 1));
                        Seat seat = this.seats.get(i).get(column);
                        temp += seat.toString();
                    }
                    temp += "\n";
            }

            return String.format("%s\n%s", columns, temp);
    }
    
    //    SIMPLE GETTERS OR SETTER
    public Map<Integer, Map<Character, Seat>> getSeatsArray() {return this.seats;}
    
    @PostLoad()
    private void initMapAfterLoad() {
        this.initialiseSeatMap();
        
        // Set the reserved seats in the seat map
        this.reservedSeats.forEach(seat -> {
            Map<Character, Seat> seatRow = this.seats.get(seat.getSeatRow());
            seatRow.put(seat.getSeatColumn(), seat);
        });
        
    }
    
    abstract protected void initialiseSeatMap();
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + this.rows + " rows";
    }
}
