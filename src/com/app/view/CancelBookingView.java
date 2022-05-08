package com.app.view;

import com.app.Constants;
import com.app.DBHelper;
import com.app.controller.CancelBookingController;
import com.app.entities.Flight;
import com.app.model.CancelBookingModel;
import com.app.view.shared.SeatingGrid;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import static java.awt.Component.TOP_ALIGNMENT;
import javax.swing.Box;
import javax.swing.JLabel;

/**
 *
 * @author jason
 */
public class CancelBookingView extends PrimaryView {
    
    private JScrollPane seatingGrid;
    private SeatingGrid seatingMap;
    private JComboBox flightSelect;
    
    public CancelBookingView() {
        super("Cancel a booking");
        this.controller = new CancelBookingController(this);
        this.model = new CancelBookingModel();
    }
    
    /**
     * Build a JPanel component for user inputs
     * @return A JPanel with a variety of user input components
     */
    private JPanel buildInputPanel() {
        JPanel inputPanel = new JPanel();
        
        JLabel flightLabel = new JLabel("Flight", JLabel.TRAILING);
        flightLabel.setPreferredSize(Constants.LABEL_X_SMALL);
        flightLabel.setMaximumSize(Constants.LABEL_X_SMALL);
        
        this.flightSelect = new JComboBox(
                ((CancelBookingModel) this.model).getFlights().toArray()
        );
        this.flightSelect.setSelectedItem(
                ((CancelBookingModel) this.model).getSelectedFlight()
        );
        this.flightSelect.setActionCommand("flight_change");
        this.flightSelect.addActionListener(this.controller);
        this.flightSelect.setMaximumSize(Constants.INPUT_MED);
        flightLabel.setLabelFor(this.flightSelect);
        
        inputPanel.add(flightSelect);
        return inputPanel;
    }
    
    //    ==== SIMPLE GETTERS OR SETTERS    
    public JComboBox getFlightSelect() {return this.flightSelect;}

    //    ==== OVERRIDE METHODS    
    @Override
    public void buildMainView() {
        this.setLayout(
            new BoxLayout(this, BoxLayout.PAGE_AXIS)
        );
        this.setAlignmentY(TOP_ALIGNMENT);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //    ==== SEATING GRID ====
        this.seatingMap = new SeatingGrid(
                ((CancelBookingModel) this.model).getPlane(),
                this.controller
        );
        this.seatingGrid = SeatingGrid.getScrollableGrid(seatingMap);
        
        this.add(SeatingGrid.getSeatingGridKey());
        this.add(Box.createVerticalStrut(5));
        this.add(this.seatingGrid);
        this.add(this.buildInputPanel());
        
        // If there are message to be displayed
        if(!(this.model.getUserMessages().isEmpty())) {
            this.add(this.buildMessageViewPane());
        }
    }

    @Override
    public void buildFooterComponents() {
        JButton cancelBtn, submitBtn;
        
        cancelBtn = new JButton("Back");
        cancelBtn.setActionCommand("back");
        cancelBtn.addActionListener(this.controller);
        
        submitBtn = new JButton("Submit");
        submitBtn.setActionCommand("submit");
        submitBtn.setBackground(Constants.ACTION_BTN_BACK);
        submitBtn.setForeground(Constants.ACTION_BTN_FORE);
        submitBtn.addActionListener(this.controller);

        this.footerComponents.add(cancelBtn);
        this.footerComponents.add(submitBtn);
    }

    @Override
    public void update() {
        this.removeAll();
        this.buildMainView();
        this.revalidate();
    }
    
}
