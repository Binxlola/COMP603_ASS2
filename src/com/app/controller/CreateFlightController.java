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
    
    public CreateFlightController(CreateFlightView view) {
        super(view);
    }
    
    /**
     * Helper method for validation of text field input
     * @param textField The JTextField component to be validated
     * @param pattern The pattern to be used in validation
     */
    private void validateTextField(ViewModel model, JTextField textField, String pattern) {
        
        if(pattern != null && !textField.getText().matches(pattern)) {
            model.addUserMessage(
                   UserMessage.ERROR.createLabel("Invalid Input for form field: " + textField.getName() + " using pattern '" + pattern + "'")
            );
        }
    }
    
    /**
     * Helper method for validation of a combo box input
     * @param box The JComboBox to be validated
     */
    private void validateComboBox(ViewModel model, JComboBox box) {
        
        if(box.getSelectedItem() == null) {
            model.addUserMessage(
                UserMessage.ERROR.createLabel("Input field: " + box.getName() + " can not be left blank")
            );
        }
    }
    
     /**
     * Validates the form inputs required on a submit action
     * @return Returns wether the error array is empty or not
     */
    private boolean validateFormInputs(ViewModel model, Map<String, Map<JComponent, String>> inputs) {
        
        // Iterates multiple inputs and validates (Should be ONLY one inner iteration)
        inputs.forEach((key, pair) -> {
            pair.forEach((input, validation) -> {
                if(input instanceof JTextField) this.validateTextField(
                        model, 
                        (JTextField) input, 
                        validation
                );
                
                if(input instanceof JComboBox) this.validateComboBox(
                        model, 
                        (JComboBox) input
                );
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
        ViewModel model = this.getView().getModel();
        Map<String, Map<JComponent, String>> inputs = ((CreateFlightView) this.getView()).getFormInputs();
        boolean isValid = validateFormInputs(model, inputs);
        
        // Input is valid we can submit
        if(isValid) {
            // Not the best way (but it works)
            String flightNum = ((JTextField) inputs.get("Flight Number").keySet().toArray()[0]).getText();
            String depCity = ((JTextField) inputs.get("Depature City").keySet().toArray()[0]).getText();
            String arrCity = ((JTextField) inputs.get("Arrival City").keySet().toArray()[0]).getText();
            String depTime = ((JTextField) inputs.get("Depature Time (hh:mm)").keySet().toArray()[0]).getText();
            Plane plane = (Plane) ((JComboBox) inputs.get("Plane Type").keySet().toArray()[0]).getSelectedItem();
            
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
