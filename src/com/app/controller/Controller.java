package com.app.controller;

import com.app.DBHelper;
import com.app.view.App;
import com.app.view.PrimaryView;
import java.awt.event.ActionListener;

/**
 *
 * @author jason
 */
public abstract class Controller implements ActionListener {
    
    protected final AppController appInstance;
    protected final DBHelper dbHelper;
    private PrimaryView view;
    
    public Controller(PrimaryView view) {
        this.appInstance = App.getInstance().getController();
        this.dbHelper = DBHelper.getInstance();
        this.view = view;
    }
    
    public PrimaryView getView() {return this.view;}
    public void setView(PrimaryView view) {this.view = view;}

}
