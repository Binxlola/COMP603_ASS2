package com.app.view;

import com.app.Constants;
import com.app.DBHelper;
import com.app.controller.CreateFlightController;
import com.app.model.CreateFlightModel;
import java.awt.Component;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author jason
 */
public final class CreateFlightView extends PrimaryView {
    
    private JButton submitBtn, cancelBtn;
    private final Map<String, Map<JComponent, String>> formInputs;
    
    public CreateFlightView() throws Exception {
        super("Welcome to Airline Reservation Application");
        this.model = new CreateFlightModel();
        this.formInputs = new HashMap();
        this.controller = new CreateFlightController(this, formInputs);
    }
    
    @Override
    public void buildMainView() {
        this.setLayout(
            new BoxLayout(this, BoxLayout.PAGE_AXIS)
        );
        this.setAlignmentY(TOP_ALIGNMENT);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel formPanel = new JPanel(
                new GridLayout(0, 2, 6, 6)
        );
        formPanel.setPreferredSize(Constants.MAIN_PANEL_SMALL);
        formPanel.setMaximumSize(Constants.MAIN_PANEL_MAX);
        // Confirm that a validations pattern has been set for each input, if not throw error
        ((CreateFlightModel) this.model).getInputs().forEach((String k, String v) -> {
            JLabel label = new JLabel(k);
            label.setPreferredSize(Constants.LABEL_X_SMALL);
            label.setMaximumSize(Constants.LABEL_X_SMALL);
            JComponent formInput = ("Plane Type").equals(k) ? 
                        new JComboBox(
                                DBHelper.getInstance().findAllPlanes().toArray()
                        ) : 
                        new JTextField(10);
            formInput.setName(k);
            formInput.setMaximumSize(Constants.INPUT_MED);
            
            Map<JComponent, String> inputValidationPair = new HashMap();
            formPanel.add(label);
            formPanel.add(formInput);
            inputValidationPair.put(formInput, v);
            this.formInputs.put(k, inputValidationPair);
        });
        
        this.add(Box.createVerticalStrut(20));
        this.add(formPanel);
        this.add(Box.createVerticalStrut(20));
        
        // If there are message to be displayed
        if(!(this.model.getUserMessages().isEmpty())) {
            this.add(this.buildMessageViewPane());
        }
    }

    @Override
    public void buildFooterComponents() {
        this.submitBtn = new JButton("Submit");
        this.submitBtn.setActionCommand("submit");
        this.submitBtn.setBackground(Constants.ACTION_BTN_BACK);
        this.submitBtn.setForeground(Constants.ACTION_BTN_FORE);
        this.submitBtn.addActionListener(this.controller);
        
        this.cancelBtn = new JButton("Back");
        this.cancelBtn.setActionCommand("back");
        this.cancelBtn.addActionListener(this.controller);
        
        this.footerComponents.add(cancelBtn);
        this.footerComponents.add(submitBtn);
    }

    @Override
    public void update() {
        this.removeAll();
        this.buildMainView();
        this.revalidate();
    }

}
