package com.app.model;

import com.app.DBHelper;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;

/**
 *
 * @author jason
 */
public abstract class ViewModel {
    
    protected final DBHelper dbHelper;
    private final List<JLabel> userMessages;
    
    public ViewModel() {
        this.dbHelper = DBHelper.getInstance();
        this.userMessages = new ArrayList();
    }
    
    public List<JLabel> getUserMessages() {return this.userMessages;}
    
    public void addUserMessage(JLabel messageLabel) {
        this.userMessages.add(messageLabel);
    }
    
    public void clearUserMessages() {
        this.userMessages.clear();
    }

}
