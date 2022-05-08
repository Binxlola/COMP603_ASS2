package com.app.controller;

import com.app.UserMessage;
import com.app.model.InitialNavigationModel;
import com.app.view.CancelBookingView;
import com.app.view.CreateFlightView;
import com.app.view.CreatePlaneView;
import com.app.view.InitialNavigationView;
import com.app.view.MakeBookingView;
import com.app.view.PrimaryView;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Acts as the controller for the InitialNavigationView, will listen for actions and navigate
 * through application based on actions
 * @author jason
 */
public class InitialNavigationController extends Controller {

    public InitialNavigationController(InitialNavigationView view) {
        super(view);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        PrimaryView newPanel = null;
        String action = e.getActionCommand();
        InitialNavigationModel model = (InitialNavigationModel) this.getView().getModel();
        
        // Clear user messages
        model.clearUserMessages();
        
        // Set panel to navigate to
        switch(action) {
            case "new_flight" -> {
                if(model.getPlanes().isEmpty()) {
                    model.addUserMessage(
                            UserMessage.ERROR.createLabel("There are no flight configurations for use in creating a flight")
                    );
                } else {
                    try {
                        newPanel = new CreateFlightView();
                    } catch (Exception ex) {
                        Logger.getLogger(InitialNavigationController.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println(ex);
                    }
                }
            }
            case "new_plane" -> newPanel = new CreatePlaneView();
            case "new_booking" -> {
                if(model.getFlights().isEmpty()) {
                    model.addUserMessage(
                            UserMessage.ERROR.createLabel("There are no created flights to make a booking for.")
                    );
                } else {
                    newPanel = new MakeBookingView();
                }
            }
            case "cancel_booking" -> {
                if(model.getFlights().isEmpty()) {
                    model.addUserMessage(
                            UserMessage.ERROR.createLabel("There are no created flights to cancel a booking for.")
                    );
                } else {
                    newPanel = new CancelBookingView();
                }
            }
        }
       
        // Set the new panel
        if(newPanel != null) {
            this.appInstance.setPanel(newPanel);
        } else {
            this.getView().update();
        }
    }
}
