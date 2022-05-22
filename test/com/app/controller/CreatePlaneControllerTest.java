package com.app.controller;

import com.app.PlaneType;
import com.app.model.CreatePlaneModel;
import com.app.view.CreatePlaneView;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author jason
 */
public class CreatePlaneControllerTest {
    
    private final CreatePlaneView view;
    private final CreatePlaneModel model;
    private final CreatePlaneController controller;
    
    public CreatePlaneControllerTest() {
        this.view = new CreatePlaneView();
        this.model = (CreatePlaneModel) this.view.getModel();
        this.controller = (CreatePlaneController) this.view.getController();
        
        this.view.constructView();
    }
    
    @Before
    public void setUp() {
        this.model.clearUserMessages();
        this.view.update();
    }
   
    /**
     * OVERRIDES the formInput structure initially built in the test view
     * @param plane The selected plane type
     * @param rowsVal The string value to be set for plane rows
     */
    private void buildInputs(PlaneType plane, String rowsVal) {
        JComboBox planeSelect = this.view.getPlaneSelect();
        JTextField rowInput = this.view.getPlaneRows();
        
        planeSelect.setSelectedItem(plane);
        rowInput.setText(rowsVal);
    }

    /**
     * Test of actionPerformed method, of class CreatePlaneController.
     */
    @Test
    public void testActionPerformed() {
        System.out.println("Testing CreatePlaneController ActionPerformed [action=submit]\n");
        ActionEvent e = new ActionEvent(this, 0, "submit");
        
        // START TESTING
        System.out.println("Testing all fields form fields invalid input...");
        this.buildInputs(null, "");
        this.controller.actionPerformed(e);
        assertEquals(2, this.model.getUserMessages().size());
        
        System.out.println("Testing text fields with valid input...");
        this.model.clearUserMessages();
        this.buildInputs(null, "11");
        this.controller.actionPerformed(e);
        assertEquals(1, this.model.getUserMessages().size());
    }
    
}
