package com.app.view;

import com.app.controller.AppController;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Objects;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author jason
 */
public class App {
    private static App APP;
    private final AppController controller;
    
    private App() {
        
        // Set up the main application frame and create view controller
        JFrame frame = new JFrame("Airline Booking");
        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());
        this.controller = new AppController(frame, container);
        
        // Set up bottom panel of the border layout
        JPanel bottomPanel = new JPanel();
        container.add(bottomPanel, BorderLayout.SOUTH);
        
        // Set main frame configuration
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(new Dimension(1000, 600));
        frame.setVisible(true);
        frame.setResizable(false);
    }
    
    public static App getInstance() {
        if(Objects.isNull(APP)) {
            APP = new App();
            APP.start();
        }
        
        return APP;
    }
    
    /**
     * Used to properly start the application after app construction has completed.
     */
    private void start() {
        this.controller.setPanel(
            new InitialNavigationView()
        );
    }
    
    public AppController getController() {
        return this.controller;
    }

}
