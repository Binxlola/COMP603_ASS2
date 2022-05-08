package com.app;

import com.app.entities.plane.Airbus;
import com.app.entities.plane.Boeing;
import com.app.entities.plane.Plane;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.DiscriminatorValue;

/**
 * An enumeration of all possible plane types
 * @author Jason Smit
 *
 */
public enum PlaneType {
    BOEING(Boeing.class), AIRBUS(Airbus.class);
	
    private final Class<? extends Plane> planeClass;
    private final String planeName;

    private PlaneType(Class<? extends Plane> planeClass) {
        this.planeClass = planeClass;
        this.planeName = planeClass.getAnnotation(DiscriminatorValue.class).value();
    }
    
    public Plane getInstantiatedPlane(int totalRows) {
        try {
            return this.planeClass.getDeclaredConstructor(int.class).newInstance(totalRows);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(PlaneType.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Class<? extends Plane> getPlaneClass() {
        return this.planeClass;
    }
    
    @Override
    public String toString() {
        return this.planeName;
    }

}
