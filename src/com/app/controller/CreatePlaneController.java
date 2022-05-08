package com.app.controller;

import com.app.PlaneType;
import com.app.UserMessage;
import com.app.model.ViewModel;
import com.app.view.CreatePlaneView;
import com.app.view.InitialNavigationView;
import com.app.view.PrimaryView;
import java.awt.event.ActionEvent;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author jason
 */
public class CreatePlaneController extends Controller {
    
    public CreatePlaneController(CreatePlaneView view) {
        super(view);
    }
    
    
    /**
     * Validates the form inputs required on a submit action
     * @return Returns wether the error array is empty or not
     */
    private boolean validateFormInputs() {
        ViewModel model = this.getView().getModel();
        
        // Clear user messages
        model.clearUserMessages();
        
        CreatePlaneView currentView = (CreatePlaneView) getView();
        
        // Plane type validation
        PlaneType type = (PlaneType) currentView.getPlaneSelect().getSelectedItem();
        if(type == null) {
            model.addUserMessage(
                UserMessage.ERROR.createLabel("A plane type must be selected")
            );
        }
        
        // Plane rows validation
        String numRows = currentView.getPlaneRows().getText().trim();
        if(!("".equals(numRows))) {
            try {
                int rows = Integer.parseInt(numRows);
                
                if (rows < 10) {
                    model.addUserMessage(
                        UserMessage.ERROR.createLabel("The minimum number of rows is 10")
                    );
                } else if (type != null && this.dbHelper.planeConfigExists(type.getPlaneClass(), rows)) {
                    model.addUserMessage(
                        UserMessage.ERROR.createLabel("This plane configuration already exists.")
                    );
                }
            } catch (NumberFormatException e) {
                model.addUserMessage(
                    UserMessage.ERROR.createLabel("Numer of rows must be an integer value.")
                );
            }
        } else {
            model.addUserMessage(
                UserMessage.ERROR.createLabel("Number of rows can not be empty.")
            );
        }
        
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
            CreatePlaneView currentView = (CreatePlaneView) getView();
            PlaneType type = (PlaneType) currentView.getPlaneSelect().getSelectedItem();
            int numRows = Integer.parseInt(currentView.getPlaneRows().getText().trim());
            
            // Save the new plane or notify user of existing plane
            try {
                this.dbHelper.saveOrRemove(
                    type.getInstantiatedPlane(numRows), true
                );
                
                // No errors, let user know plane config was created
                model.addUserMessage(
                    UserMessage.NOTIFICATION.createLabel("Plane config added")
                );
            } catch (ConstraintViolationException e) {
                model.addUserMessage(
                    UserMessage.ERROR.createLabel(
                        String.format("A plane of type %s with %s rows already exists in the database!", type, numRows)
                    )
                );
            }
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
