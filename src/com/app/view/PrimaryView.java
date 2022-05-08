package com.app.view;

import com.app.Constants;
import com.app.model.ViewModel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author jason
 */
public abstract class PrimaryView extends JPanel {
    
    protected String header;
    protected final List<JComponent> footerComponents;
    protected ActionListener controller;
    protected ViewModel model;
    
    public PrimaryView() {
        this.footerComponents = new ArrayList();
    }
    
    public PrimaryView(String header) {
        this.header = header;
        this.footerComponents = new ArrayList();
    }
    
    /**
     * Builds the scrollabel message display component
     * @return A JScroll panel to hold system messages
     */
    public JScrollPane buildMessageViewPane() {
        JPanel messageGrid = new JPanel(
                new GridLayout(0, 1)
        );
        
        this.model.getUserMessages().forEach(label -> messageGrid.add(label));
        JScrollPane scrollPane = new JScrollPane(
                messageGrid,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setPreferredSize(Constants.LABEL_MAX_WIDTH);
        scrollPane.setMaximumSize(Constants.LABEL_MAX_WIDTH);
        
        return scrollPane;
    }
    
    public void constructView() {
        buildMainView();
        buildFooterComponents();
    }
    
    public List<JComponent> getFooterComponents() {return this.footerComponents;}
    public String getHeader() {return this.header;}
    public ViewModel getModel() {return this.model;}
    
    public abstract void buildMainView();
    public abstract void buildFooterComponents();
//    public abstract JScrollPane buildMessagePane();
    public abstract void update();
}
