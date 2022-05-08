package com.app.view;

import com.app.Constants;
import com.app.controller.MakeBookingController;
import com.app.model.MakeBookingModel;
import com.app.view.shared.SeatingGrid;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author jason
 */
public class MakeBookingView extends PrimaryView {
    
    private JComboBox serviceClass, seatType, flightSelect, airlineSelect;
    private SeatingGrid seatingMap;
    private JScrollPane gridScroll;
    
    public MakeBookingView() {
        super();
        this.model = new MakeBookingModel();
        this.controller = new MakeBookingController(this);
        this.header = "Welcome to " + ((MakeBookingModel) this.model).getSelectedAirline().getAirlineName();
    }
    
    /**
     * Helper method to build the booking options panel
     * @return A JPanel with booking options inputs
     */
    private JPanel buildBookingOptionsPanel() {
        JPanel optionsPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );
        
        // AIRLINE OPTION
        JLabel airlineLabel = new JLabel("Airline", JLabel.TRAILING);
        airlineLabel.setPreferredSize(Constants.LABEL_X_SMALL);
        airlineLabel.setMaximumSize(Constants.LABEL_X_SMALL);
        
        this.airlineSelect = new JComboBox(
                ((MakeBookingModel) this.model).getAirlines().toArray()
        );
        this.airlineSelect.setSelectedItem(
                ((MakeBookingModel) this.model).getSelectedAirline()
        );
        this.airlineSelect.setActionCommand("airline");
        this.airlineSelect.addActionListener(this.controller);
        this.airlineSelect.setMaximumSize(Constants.INPUT_MED);
        airlineLabel.setLabelFor(this.airlineSelect);
        
         // FLIGHT OPTION
        JLabel flightLabel = new JLabel("Flight", JLabel.TRAILING);
        flightLabel.setPreferredSize(Constants.LABEL_X_SMALL);
        flightLabel.setMaximumSize(Constants.LABEL_X_SMALL);
        
        this.flightSelect = new JComboBox(
                ((MakeBookingModel) this.model).getFlights().toArray()
        );
        this.flightSelect.setSelectedItem(
                ((MakeBookingModel) this.model).getSelectedFlight()
        );
        this.flightSelect.setActionCommand("flight");
        this.flightSelect.addActionListener(this.controller);
        this.flightSelect.setMaximumSize(Constants.INPUT_MED);
        flightLabel.setLabelFor(this.flightSelect);
        
        // SERVICE OPTION
        JLabel serviceLabel = new JLabel("Class", JLabel.TRAILING);
        serviceLabel.setPreferredSize(Constants.LABEL_X_SMALL);
        serviceLabel.setMaximumSize(Constants.LABEL_X_SMALL);
        
        this.serviceClass = new JComboBox(
                ((MakeBookingModel) this.model).getServiceClasses()
        );
        this.serviceClass.setSelectedItem(
                ((MakeBookingModel) this.model).getSelectedService()
        );
        this.serviceClass.setActionCommand("class");
        this.serviceClass.addActionListener(this.controller);
        this.serviceClass.setMaximumSize(Constants.INPUT_MED);
        serviceLabel.setLabelFor(this.serviceClass);
        
        // SEAT OPTION
        JLabel seatLabel = new JLabel("Type", JLabel.TRAILING);
        seatLabel.setPreferredSize(Constants.LABEL_X_SMALL);
        seatLabel.setMaximumSize(Constants.LABEL_X_SMALL);
        
        this.seatType = new JComboBox(
                ((MakeBookingModel) this.model).getSeatTypes()
        );
        this.seatType.setSelectedItem(
                ((MakeBookingModel) this.model).getSelectedType()
        );
        this.seatType.setActionCommand("type");
        this.seatType.addActionListener(this.controller);
        this.seatType.setMaximumSize(Constants.INPUT_SMALL);
        seatLabel.setLabelFor(this.seatType);
        
        // ADD ALL OPTIONS TO CONTAINER
        optionsPanel.add(airlineLabel);
        optionsPanel.add(this.airlineSelect);
        optionsPanel.add(flightLabel);
        optionsPanel.add(this.flightSelect);
        optionsPanel.add(serviceLabel);
        optionsPanel.add(this.serviceClass);
        optionsPanel.add(seatLabel);
        optionsPanel.add(this.seatType);
        
        return optionsPanel;
    }
    
    //    ==== GETTERS OR SETTERS    
    public JComboBox getAirlineSelect() {return this.airlineSelect;}
    public JComboBox getFlightSelect() {return this.flightSelect;}
    public JComboBox getClassSelect() {return this.serviceClass;}
    public JComboBox getTypeSelect() {return this.seatType;}
    
    //    ==== OVERRIDE METHODS    
    @Override
    public void buildMainView() {
        this.setLayout(
            new BoxLayout(this, BoxLayout.PAGE_AXIS)
        );
        this.setAlignmentY(TOP_ALIGNMENT);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        // Create and seat seating components
        this.seatingMap = new SeatingGrid(
                ((MakeBookingModel) this.model).getPlane()
        );
        this.gridScroll = SeatingGrid.getScrollableGrid(this.seatingMap);
        
        this.add(SeatingGrid.getSeatingGridKey());
        this.add(Box.createVerticalStrut(20));
        this.add(this.gridScroll);
        this.add(this.buildBookingOptionsPanel());
        
        // If there are message to be displayed
        if(!(this.model.getUserMessages().isEmpty())) {
            this.add(this.buildMessageViewPane());
        }
    }

    @Override
    public void buildFooterComponents() {
        JButton backBtn, bookBtn;
     
        backBtn = new JButton("Back");
        backBtn.setActionCommand("back");
        backBtn.addActionListener(this.controller);
        
        bookBtn = new JButton("Book");
        bookBtn.setActionCommand("submit");
        bookBtn.setBackground(Constants.ACTION_BTN_BACK);
        bookBtn.setForeground(Constants.ACTION_BTN_FORE);
        bookBtn.addActionListener(this.controller);

        this.footerComponents.add(backBtn);
        this.footerComponents.add(bookBtn);
    }

    @Override
    public void update() {
        this.removeAll();
        this.buildMainView();
        this.revalidate();
    }
}
