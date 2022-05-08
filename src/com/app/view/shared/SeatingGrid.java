package com.app.view.shared;

import com.app.Constants;
import com.app.SeatType;
import com.app.entities.Seat;
import com.app.entities.plane.Plane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

/**
 * A grid view which describes a seating map for a given plane
 * @author jason
 */
public class SeatingGrid extends JPanel {
    
    private final Plane plane;
    private final ActionListener btnListener;

    public SeatingGrid(Plane plane) {
        this.plane = plane;
        this.btnListener = null;
        buildView();
    }
    
    public SeatingGrid(Plane plane, ActionListener btnListener) {
        this.plane = plane;
        this.btnListener = btnListener;
        buildView();
    }
    
    private void buildView() {
        this.setLayout(
                new GridLayout(0, this.plane.getTotalColumns() + 1)
        );
        this.setMaximumSize(Constants.MAIN_PANEL_MAX);
        this.setPreferredSize(Constants.MAIN_PANEL_PREF);
        
        // Top left coner fill
        JLabel fillerArea = new JLabel();
        fillerArea.setOpaque(true);
        fillerArea.setBackground(Color.white);
        fillerArea.setPreferredSize(Constants.GRID_HEADER_LABEL);
        this.add(fillerArea);
        
        // Seating grid header row h - header
        for(int h = 0; h < this.plane.getTotalColumns(); h++) {
            char columnChar = (char) ('a' + (h - 32));
            JLabel columnLabel = new JLabel(
                    String.valueOf(columnChar),
                    SwingConstants.CENTER
            );
            columnLabel.setPreferredSize(Constants.GRID_HEADER_LABEL);
            columnLabel.setOpaque(true);
            columnLabel.setBackground(Color.white);
            columnLabel.setBorder(new MatteBorder(0, 0, 1, 0, Color.black));
            this.add(columnLabel);
        }
        
        // Seating grid seat rows r - row
        for(int r = 0; r < this.plane.getTotalRows(); r++) {
            JLabel rowLabel = new JLabel(
                    String.valueOf(r + 1),
                    SwingConstants.CENTER
            );
            rowLabel.setPreferredSize(Constants.GRID_HEADER_LABEL);
            rowLabel.setOpaque(true);
            rowLabel.setBackground(Color.white);
            rowLabel.setBorder(new MatteBorder(0, 0, 0, 1, Color.black));
            this.add(rowLabel);
            
            // Seats creation s - seats
            for (int s = 0; s < this.plane.getTotalColumns(); s++) {
                char column = (char) ('a' - 1 + (s + 1));
                Seat seat = this.plane.getSeat(r, column);
                
                String seatString = String.format("%s - %s",
                        seat.isFirstClass() ? "F" : "E",
                        seat.getSeatType().getShortHand()
                );
                
                // Determine what type of component to add for seat representation
                if(this.btnListener == null) {
                    JLabel seatLabel = this.getSeatLabel(
                            seatString, 
                            seat.isReserved() ? Color.red : Color.green, 
                            seat.isReserved() ? Color.white : Color.black,
                            Constants.GRID_HEADER_LABEL);
                    this.add(seatLabel);
                } else {
                    JButton seatBtn = this.getSeatButton(
                            seatString, 
                            seat, 
                            seat.isReserved() ? Color.red : Color.green, 
                            seat.isReserved() ? Color.white : Color.black,
                            Constants.GRID_HEADER_LABEL);
                    this.add(seatBtn);
                }
            }
        }
    }
    
    private JButton getSeatButton(String btnText, Seat seat, Color color, Color fontColor, Dimension size) {
        JButton seatBtn = new JButton(btnText);
        seatBtn.setActionCommand(seat.getSeatRow() + "-" + seat.getSeatColumn());
        seatBtn.addActionListener(this.btnListener);
        seatBtn.setBackground(color);
        seatBtn.setForeground(fontColor);
        seatBtn.setPreferredSize(size);
        
        return seatBtn;
    }
    
    private JLabel getSeatLabel(String labelText, Color color, Color fontColor, Dimension size) {
        JLabel seatLabel = new JLabel(labelText, SwingConstants.CENTER);
        seatLabel.setOpaque(true);
        seatLabel.setBackground(color);
        seatLabel.setForeground(fontColor);
        seatLabel.setPreferredSize(size);
        
        return seatLabel;
    }
    
    /**
     * Helper method to build a key panel for the seating grid
     * @return The JPanel representing a key
     */
    public static JPanel getSeatingGridKey() {
        JPanel keyPanel = new JPanel();
        keyPanel.setBorder(new MatteBorder(1, 0, 1, 0, Color.black));
        
        JLabel reservedBox = new JLabel(" ");
        reservedBox.setPreferredSize(new Dimension(15,15));
        reservedBox.setOpaque(true);
        reservedBox.setBackground(Color.red);
        JLabel reservedLabel = new JLabel("- Reserved");
        
        JLabel availableBox = new JLabel(" ");
        availableBox.setPreferredSize(new Dimension(15, 15));
        availableBox.setOpaque(true);
        availableBox.setBackground(Color.green);
        JLabel availableLabel = new JLabel("- Available");
        
        JLabel firstLabel = new JLabel("F - First class");
        JLabel economyLabel = new JLabel("E - Economy class");
        
        keyPanel.add(reservedBox);
        keyPanel.add(reservedLabel);
        keyPanel.add(availableBox);
        keyPanel.add(availableLabel);
        keyPanel.add(firstLabel);
        keyPanel.add(economyLabel);
        
        for(SeatType type : SeatType.values()) {
            keyPanel.add(
                    new JLabel(
                            type.getShortHand() + " - " + type.getSeatName()
                    )
            );
        }
        
        return keyPanel;
    }
    
    /**
     * Helper method do build a seating grid that can be scrolled vertically
     * @param grid A previously built non-scrollable seating grid
     * @return  A JScrollPane containing the seating grid for the flight
     */
    public static JScrollPane getScrollableGrid(SeatingGrid grid) {
        JScrollPane seatsScroll = new JScrollPane(grid, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        seatsScroll.setPreferredSize(Constants.MAIN_PANEL_PREF);
        seatsScroll.setMaximumSize(Constants.MAIN_PANEL_MAX);
        
        return seatsScroll;
    }
}
