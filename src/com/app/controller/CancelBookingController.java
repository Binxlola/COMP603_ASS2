package com.app.controller;

import com.app.UserMessage;
import com.app.entities.Flight;
import com.app.entities.Seat;
import com.app.entities.plane.Plane;
import com.app.model.CancelBookingModel;
import com.app.view.CancelBookingView;
import com.app.view.InitialNavigationView;
import com.app.view.PrimaryView;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import org.hibernate.HibernateException;

/**
 *
 * @author jason
 */
public class CancelBookingController extends Controller {
    
    public CancelBookingController(CancelBookingView view) {
        super(view);
    }
    
    /**
     * Update model to reflect selected flight
     * and update the seating grid
     */
    private void updateSelectedFlight() {
        ((CancelBookingModel) this.getView().getModel()).setSelectedFlight(
                (Flight) ((CancelBookingView) this.getView()).getFlightSelect().getSelectedItem()
        );
        
        ((CancelBookingView) this.getView()).update();
    }
    
    /**
     * Given a seat location will attempt to cancel the seat reservation if there is one
     * @param seatLocation A String[2] with seat row and column
     */
    private void cancelBooking(String[] seatLocation) {
        // Rest user messages
        this.getView().getModel().clearUserMessages();
        
        JLabel userMessage;
        int row = Integer.valueOf(seatLocation[0]);
        char column = seatLocation[1].charAt(0);
        Plane plane = ((CancelBookingModel) this.getView().getModel()).getPlane();
        Seat seat = plane.getSeat(row, column);
        
        if(seat != null && seat.isReserved()) {
            seat.setIsReserved(false);
            try {
                this.dbHelper.saveOrRemove(plane, true);
                userMessage = UserMessage.NOTIFICATION.createLabel("Seat booking was canceled for seat: " + (seat.getSeatRow() + 1) + seat.getSeatColumn());
            } catch (HibernateException e) {
                userMessage = UserMessage.ERROR.createLabel("Seat booking cancellation could not be completed");
            }
        } else {
            userMessage = UserMessage.NOTIFICATION.createLabel("Seat is not reserved and can not be canceled");
        }
        
        this.getView().getModel().addUserMessage(userMessage);
        this.getView().update();
        
    }
    
    //    ==== OVERRIDE METHODS   
    @Override
    public void actionPerformed(ActionEvent e) {
        PrimaryView newPanel = null;
        String action = e.getActionCommand();
        
        // Set panel to navigate to
        switch(action) {
            case "flight_change" -> this.updateSelectedFlight();
            case "back" -> newPanel = new InitialNavigationView();
            default -> this.cancelBooking(action.split("-"));
        }
       
        // Set the new panel
        if(newPanel != null) {
            this.appInstance.setPanel(newPanel);
        }
    }
}
