package com.app.controller;

import com.app.entities.plane.Plane;
import com.app.model.CreateFlightModel;
import com.app.view.CreateFlightView;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jason
 */
public class CreateFlightControllerTest {
    
    private final CreateFlightView view;
    private final CreateFlightModel model;
    private final CreateFlightController controller;
    
    public CreateFlightControllerTest() {
        this.view = new CreateFlightView();
        this.model = (CreateFlightModel) this.view.getModel();
        this.controller = (CreateFlightController) this.view.getController();
        
        this.view.constructView();
    }
    
    @Before
    public void setup() {
        this.model.clearUserMessages();
        this.view.update();
    }
  
    /**
     * OVERRIDES the formInput structure initially built in the test view
     * @param flightStr The flight string to be set
     * @param cityStr The city string to be set
     * @param timeStr The time string to be set
     * @param planeObj The plane Object to be set
     */
    private void buildInputs(String flightStr, String cityStr, String timeStr, Plane planeObj) {
        this.view.getFormInputs().clear();
        
        // MIMIC FORM INPUT SCRUCTURE AND OVERRIDE
        JTextField flightNumber = new JTextField();
        JTextField city = new JTextField();
        JTextField time = new JTextField();
        JComboBox plane = new JComboBox();
        
        flightNumber.setText(flightStr);
        city.setText(cityStr);
        time.setText(timeStr);
        plane.setSelectedItem(planeObj);
        
        Map flightPair = new HashMap();
        Map cityPair = new HashMap();
        Map timePair = new HashMap();
        Map planePair = new HashMap();
        
        flightPair.put(flightNumber, "[a-zA-Z]{2}[0-9]{3}");
        cityPair.put(city, "[a-zA-Z]+");
        timePair.put(time, "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$");
        planePair.put(plane, null);
        
        this.view.getFormInputs().put("Flight Number", flightPair);
        this.view.getFormInputs().put("Depature City", cityPair);
        this.view.getFormInputs().put("Arrival City", cityPair);
        this.view.getFormInputs().put("Depature Time (hh:mm)", timePair);
        this.view.getFormInputs().put("Plane Type", planePair);
    }

    /**
     * Test of actionPerformed method, of class CreateFlightController.
     */
    @Test
    public void testActionPerformed() {
        System.out.println("Testing CreateFlightController ActionPerformed [action=submit]\n");
        ActionEvent e = new ActionEvent(this, 0, "submit");
        
        // START TESTING
        System.out.println("Testing all fields form fields invalid input...");
        this.buildInputs("","","",null);
        this.controller.actionPerformed(e);
        assertEquals(5, this.model.getUserMessages().size());
        
        System.out.println("Testing text fields with valid input...");
        this.model.clearUserMessages();
        this.buildInputs("AC123","Auckland","15:00",null);
        this.controller.actionPerformed(e);
        assertEquals(1, this.model.getUserMessages().size());
    }
    
}
