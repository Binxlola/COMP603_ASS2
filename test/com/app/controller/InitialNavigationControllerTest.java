package com.app.controller;

import com.app.model.InitialNavigationModel;
import com.app.view.InitialNavigationView;
import java.awt.event.ActionEvent;
import java.util.Collections;
import javax.swing.JButton;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author jason
 */
public class InitialNavigationControllerTest {
    
    private final InitialNavigationView view;
    private final InitialNavigationModel model;
    private final InitialNavigationController controller;
    
    public InitialNavigationControllerTest() {
        this.view = new InitialNavigationView();
        this.model = (InitialNavigationModel) this.view.getModel();
        this.controller = (InitialNavigationController) this.view.getController();
        
        this.view.constructView();
    }

    /**
     * Test of actionPerformed method, of class InitialNavigationController.
     */
    @Test
    public void testActionPerformed() {
        ActionEvent e;
        JButton button = new JButton("test button");
        ((InitialNavigationModel)this.model).setFlights(Collections.EMPTY_LIST);
        ((InitialNavigationModel)this.model).setPlanes(Collections.EMPTY_LIST);
        
        // START TESTING
        ((InitialNavigationModel)this.model).clearUserMessages();
        System.out.println("Testing invalid create flight navigation...");
        e = new ActionEvent(button, 1, "new_flight");
        this.controller.actionPerformed(e);
        assertEquals(1, this.model.getUserMessages().size());
        
        ((InitialNavigationModel)this.model).clearUserMessages();
        System.out.println("Testing invalid new booking navigation...");
        this.model.clearUserMessages();
        e = new ActionEvent(button, 1, "new_booking");
        this.controller.actionPerformed(e);
        assertEquals(1, this.model.getUserMessages().size());
        
        ((InitialNavigationModel)this.model).clearUserMessages();
        System.out.println("Testing invalid cancel booking navigation...");
        this.model.clearUserMessages();
        e = new ActionEvent(button, 1, "cancel_booking");
        this.controller.actionPerformed(e);
        assertEquals(1, this.model.getUserMessages().size());
    }
    
}
