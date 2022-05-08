package com.app.view;

import com.app.Constants;
import com.app.controller.CreatePlaneController;
import com.app.PlaneType;
import com.app.model.CreatePlaneModel;
import java.awt.Component;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author jason
 */
public final class CreatePlaneView extends PrimaryView {
    
    private JComboBox planeSelect;
    private JTextField planeRows;
    
    public CreatePlaneView() {
        super("Fill out the form below and click submit to add a new plane configuration.");
        this.controller = new CreatePlaneController(this);
        this.model = new CreatePlaneModel();

    }
    
    @Override
    public void buildMainView() {
        this.setLayout(
            new BoxLayout(this, BoxLayout.PAGE_AXIS)
        );
        this.setAlignmentY(TOP_ALIGNMENT);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel inputPanel = new JPanel();
        JLabel planeTypeLabel = new JLabel("Plane type:", JLabel.TRAILING);
        planeTypeLabel.setPreferredSize(Constants.LABEL_SMALL);
        this.planeSelect = new JComboBox(
                ((CreatePlaneModel) this.model).getPlaneTypes());
        
        JLabel planeRowsLabel = new JLabel("Total seat rows:", JLabel.TRAILING);
        planeRowsLabel.setPreferredSize(Constants.LABEL_SMALL);
        this.planeRows = new JTextField(10);
        
        // Add all nodes to formPanel
        inputPanel.add(planeTypeLabel);
        inputPanel.add(this.planeSelect);
        inputPanel.add(planeRowsLabel);
        inputPanel.add(this.planeRows);
        
        this.add(inputPanel);
        this.add(Box.createVerticalStrut(20));
        
        // If there are message to be displayed
        if(!(this.model.getUserMessages().isEmpty())) {
            this.add(this.buildMessageViewPane());
            this.add(Box.createVerticalStrut(20));
        }
    }

    @Override
    public void buildFooterComponents() {
        JButton submitBtn, cancelBtn;
        
        submitBtn = new JButton("Submit");
        submitBtn.setActionCommand("submit");
        submitBtn.setBackground(Constants.ACTION_BTN_BACK);
        submitBtn.setForeground(Constants.ACTION_BTN_FORE);
        submitBtn.addActionListener(this.controller);
        
        cancelBtn = new JButton("Back");
        cancelBtn.setActionCommand("back");
        cancelBtn.addActionListener(this.controller);
        
        this.footerComponents.add(cancelBtn);
        this.footerComponents.add(submitBtn);
    }
    
    @Override
    public void update() {
        this.removeAll();
        this.buildMainView();
        this.revalidate();
    }

    public JComboBox getPlaneSelect() {return this.planeSelect;}
    public JTextField getPlaneRows() {return this.planeRows;}
}
