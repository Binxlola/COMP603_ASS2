package com.app.controller;

import com.app.Constants;
import com.app.view.PrimaryView;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author jason
 */
public class AppController {
    
    private final Container container;
    private PrimaryView currentPanel;
    private JPanel headerPanel, footerPanel;
    
    public AppController(JFrame frame, Container continer) {
        this.container = continer;
    }
    
    /**
     * Takes a given panel and sets it as the new view for the user
     * Assumes a panel of type primary view with header, main and footer build methods
     * @param panel The view panel to display to the user
     */
    public void setPanel(PrimaryView panel) {
        // Construct the view
        panel.constructView();
        
        // Remove current panel and add new panel
        if(this.headerPanel != null) this.container.remove(this.headerPanel);
        if(this.currentPanel != null) this.container.remove(currentPanel);
        if(this.footerPanel != null) this.container.remove(this.footerPanel);
        
        
        // Setup and add header panel
        this.headerPanel = new JPanel();
        headerPanel.add(new JLabel(
                panel.getHeader()
        ));
        this.container.add(headerPanel, BorderLayout.NORTH);
        
        // Add center panel
        panel.setMaximumSize(Constants.MAIN_PANEL_MAX);
        panel.setPreferredSize(Constants.MAIN_PANEL_PREF);
        this.container.add(panel, BorderLayout.CENTER);
        this.currentPanel = panel;
        
        // Setup and add footer panel
        if(!panel.getFooterComponents().isEmpty()) {
            this.footerPanel = new JPanel(new FlowLayout());
            panel.getFooterComponents().forEach(component -> footerPanel.add(component));
            this.container.add(footerPanel, BorderLayout.SOUTH);
        }
        
        
        // Revalidate the frame and repaint to make changes visible
        this.container.revalidate();
    }

}
