package com.app.controller;

import com.app.UserMessage;
import com.app.entities.Flight;
import com.app.entities.plane.Plane;
import com.app.model.ViewModel;
import com.app.view.CreateFlightView;
import com.app.view.InitialNavigationView;
import com.app.view.PrimaryView;
import java.awt.event.ActionEvent;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author jason
 */
public class CreateFlightController extends Controller {
    
    private final Map<String, Map<JComponent, String>> formInputs;
    
    public CreateFlightController(CreateFlightView view, Map<String, Map<JComponent, String>> formInputs) {
        super(view);
        this.formInputs = formInputs;
    }
    
     /**
     * Validates the form inputs required on a submit action
     * @return Returns wether the error array is empty or not
     */
    private boolean validateFormInputs() {
        // Rest user messages 
        this.getView().getModel().clearUserMessages();
        ViewModel model = this.getView().getModel();
        
        // Iterates multiple inputs and validates
        this.formInputs.forEach((k, v) -> {
            // iterates Single map for extra input data
            v.forEach((input, validation) -> {
                if(input instanceof JTextField && validation != null) {
                    JTextField textField = (JTextField) input;
                    if(!textField.getText().matches(validation)) {
                        model.addUserMessage(
                               UserMessage.ERROR.createLabel("Invalid Input for form field: " + input.getName() + " using pattern '" + validation + "'")
                        );
                    }
                } else if(input instanceof JComboBox && ((JComboBox) input).getSelectedItem() == null) {
                    model.addUserMessage(
                           UserMessage.ERROR.createLabel("Input field: " + input.getName() + " can not be left blank")
                    );
                }
            });
        });
        
        return model.getUserMessages().isEmpty();
    }
    
    
    /**
     * Validates user form inputs, given successful validation, a new plane with 
     * given data will be saved to database.
     * Upon a successful save, user will be redirected to InitialNavigationView
     */
    private void submit() {
        boolean isValid = validateFormInputs();
        ViewModel model = this.getView().getModel();
        
        // Input is valid we can submit
        if(isValid) {
            // Not the best way (but it works)
            String flightNum = ((JTextField) this.formInputs.get("Flight Number").keySet().toArray()[0]).getText();
            String depCity = ((JTextField) this.formInputs.get("Depature City").keySet().toArray()[0]).getText();
            String arrCity = ((JTextField) this.formInputs.get("Arrival City").keySet().toArray()[0]).getText();
            String depTime = ((JTextField) this.formInputs.get("Depature Time (hh:mm)").keySet().toArray()[0]).getText();
            Plane plane = (Plane) ((JComboBox) this.formInputs.get("Plane Type").keySet().toArray()[0]).getSelectedItem();
            
            // Save the new Flight or add flight exists error and return
            try {
                this.dbHelper.saveOrRemove(
                    new Flight(flightNum, depCity, arrCity, depTime, plane),
                    true
                );
            } catch (ConstraintViolationException e) {
                model.addUserMessage(
                           UserMessage.ERROR.createLabel("A flight with flight number: " + flightNum + " already exists in the database!")
                );
            }
            
            // There were no error we can let user know flighter was created
            model.addUserMessage(
                           UserMessage.NOTIFICATION.createLabel("Flight created")
                );
        }
        
        
        this.getView().update();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        PrimaryView newPanel = null;
        String action = e.getActionCommand();
        
        // Set panel to navigate to
        switch(action) {
            case "submit" -> this.submit();
            case "back" -> newPanel = new InitialNavigationView();
        }
       
        // Set the new panel
        if(newPanel != null) {
            this.appInstance.setPanel(newPanel);
        }
    }

}
