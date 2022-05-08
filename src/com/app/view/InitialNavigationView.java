package com.app.view;

import com.app.controller.InitialNavigationController;
import com.app.model.InitialNavigationModel;
import java.awt.Component;
import static java.awt.Component.TOP_ALIGNMENT;
import java.util.Collections;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author jason
 */
public final class InitialNavigationView extends PrimaryView {
    
    private JButton makeBooking, cancelBooking, addFlight, planeConfig;
    
    public InitialNavigationView() {
        super("Welcome to Airline Reservation Application");
        this.controller = new InitialNavigationController(this);
        this.model = new InitialNavigationModel();
    }
    
    @Override
    public List<JComponent> getFooterComponents() {return Collections.EMPTY_LIST;}
    
    @Override
    public void buildMainView() {
        this.setLayout(
            new BoxLayout(this, BoxLayout.PAGE_AXIS)
        );
        this.setAlignmentY(TOP_ALIGNMENT);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create panel components
        JPanel navPanel = new JPanel();
        this.planeConfig = new JButton("Create plane config");
        this.planeConfig.setActionCommand("new_plane");
        this.planeConfig.addActionListener(this.controller);
        
        this.addFlight = new JButton("Create new flight");
        this.addFlight.setActionCommand("new_flight");
        this.addFlight.addActionListener(this.controller);
        
        this.makeBooking = new JButton("Make a booking");
        this.makeBooking.setActionCommand("new_booking");
        this.makeBooking.addActionListener(this.controller);
        
        this.cancelBooking = new JButton("Cancel a booking");
        this.cancelBooking.setActionCommand("cancel_booking");
        this.cancelBooking.addActionListener(this.controller);
        
        // Add panel components to panel
        navPanel.add(this.planeConfig);
        navPanel.add(this.addFlight);
        navPanel.add(this.makeBooking);
        navPanel.add(this.cancelBooking);
        
        this.add(Box.createVerticalStrut(20));
        this.add(navPanel);
        this.add(Box.createVerticalStrut(20));
        
        // If there are message to be displayed
        if(!(this.model.getUserMessages().isEmpty())) {
            this.add(this.buildMessageViewPane());
            this.add(Box.createVerticalStrut(20));
        }
    }

    @Override
    public void buildFooterComponents() {}

    @Override
    public void update() {
        this.removeAll();
        this.buildMainView();
        this.revalidate();
    }

}
