package com.app;

import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author jason
 */
public enum UserMessage {
    
    ERROR("Error", Color.red),
    NOTIFICATION("Notification", Color.blue),
    WARN("Warn", Color.orange);
	
    private final String typeName;
    private final Color messageColor;

    private UserMessage(String typeName, Color messageColor) {
        this.typeName = typeName;
        this.messageColor = messageColor;
    }

    public String getTypeName() {return this.typeName;}
    
    public JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.setBackground(Color.white);
        label.setForeground(this.messageColor);
        label.setPreferredSize(Constants.LABEL_MAX_WIDTH);
        
        return label;
    }

    @Override
    public String toString() {
        return this.typeName;
    }

}
