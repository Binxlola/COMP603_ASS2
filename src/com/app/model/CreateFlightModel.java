package com.app.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jason
 */
public class CreateFlightModel extends ViewModel {
    
    private final static String[] FORM_LABELS = {"Flight Number", "Depature City", "Arrival City", "Depature Time (hh:mm)", "Plane Type"};
    private final static String[] FORM_PATTERNS = {"[a-zA-Z]{2}[0-9]{3}", "[a-zA-Z]+", "[a-zA-Z]+", "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", null};
    private final Map<String, String> inputs;
    
    public CreateFlightModel() {
        this.inputs = new LinkedHashMap();
        
        // Confirm we have matching number of labels to patterns
        if(FORM_LABELS.length == FORM_PATTERNS.length) {
            
            // Build inputs map
            for(int i = 0; i < FORM_LABELS.length; i++) {
                String labelText = FORM_LABELS[i];
                String validationPattern = FORM_PATTERNS[i];
                this.inputs.put(labelText, validationPattern);
            }
            
            
        } else {
            Logger.getLogger(CreateFlightModel.class.getName())
                    .log(
                        Level.SEVERE, 
                        null, 
                        "Create view panel inputs and input patterns do not match!"
                    );
        }
    }
    
    public Map<String, String> getInputs() {
        return this.inputs;
    }
}
