package com.app.controller;

import com.app.entities.Seat;
import com.app.SeatType;
import com.app.ServiceClass;
import com.app.UserMessage;
import com.app.airline.Airline;
import com.app.entities.Flight;
import com.app.entities.plane.Plane;
import com.app.model.MakeBookingModel;
import com.app.view.InitialNavigationView;
import com.app.view.MakeBookingView;
import com.app.view.PrimaryView;
import java.awt.event.ActionEvent;

/**
 *
 * @author jason
 */
public class MakeBookingController extends Controller {
    
    public MakeBookingController(MakeBookingView view) {
        super(view);
    }
    
    /**
     * Called when a user requests to book a seat on a given flight.Notifies user if a reservation was able to be made or not.
     * @param serviceClass The class of seat the user wants to book
     * @param seatType The type of seat to be booked
     * @param flight The flight on which the seat should be booked
     * @param airline The airline of which is making the booking
     */
    private void makeBooking(ServiceClass serviceClass, SeatType seatType, Flight flight, Airline airline) {
        // Rest user messages
        this.getView().getModel().clearUserMessages();
        
        
        Seat reservedSeat = serviceClass.reserve(airline, flight, seatType);
        Plane plane = flight.getPlane();
        
        plane.addReservedSeat(reservedSeat);
        this.dbHelper.saveOrRemove(plane, true);
        this.getView().getModel().addUserMessage(
                reservedSeat != null ? UserMessage.NOTIFICATION.createLabel(reservedSeat.getSeatDescription()) : UserMessage.ERROR.createLabel("A booking could not be made")
        );
        
        this.getView().update();
    }
    
    /**
     * Update model to reflect selected airline
     */
    private void updateSelectedAirline() {
        ((MakeBookingModel) this.getView().getModel()).setSelectedAirline(
                (Airline) ((MakeBookingView) this.getView()).getAirlineSelect().getSelectedItem()
        );
    }
    
    /**
     * Update model to reflect selected flight
     * and update the seating grid
     */
    private void updateSelectedFlight() {
        ((MakeBookingModel) this.getView().getModel()).setSelectedFlight(
                (Flight) ((MakeBookingView) this.getView()).getFlightSelect().getSelectedItem()
        );
        
        ((MakeBookingView) this.getView()).update();
    }
    
    /**
     * Update model to reflect selected service class
     */
    private void updateSelectedClass() {
        ((MakeBookingModel) this.getView().getModel()).setSelectedService(
                (ServiceClass) ((MakeBookingView) this.getView()).getClassSelect().getSelectedItem()
        );
    }
    
    /**
     * Update model to reflect selected seat type
     */
    private void updateSelectedType() {
        ((MakeBookingModel) this.getView().getModel()).setSelectedType(
                (SeatType) ((MakeBookingView) this.getView()).getTypeSelect().getSelectedItem()
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PrimaryView newPanel = null;
        String action = e.getActionCommand();
        
        // Set panel to navigate to
        switch(action) {
            case "airline" -> this.updateSelectedAirline();
            case "flight" -> this.updateSelectedFlight();
            case "class" -> this.updateSelectedClass();
            case "type" -> this.updateSelectedType();
            case "back" -> newPanel = new InitialNavigationView();
            case "submit" -> this.makeBooking(
                    ((MakeBookingModel) this.getView().getModel()).getSelectedService(),
                    ((MakeBookingModel) this.getView().getModel()).getSelectedType(),
                    ((MakeBookingModel) this.getView().getModel()).getSelectedFlight(), 
                    ((MakeBookingModel) this.getView().getModel()).getSelectedAirline()
            );
        }
       
        // Set the new panel
        if(newPanel != null) {
            this.appInstance.setPanel(newPanel);
        }
    }

}
