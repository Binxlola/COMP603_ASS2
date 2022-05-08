package com.app.model;

import com.app.PlaneType;

/**
 *
 * @author jason
 */
public class CreatePlaneModel extends ViewModel {
    
    private final PlaneType[] planeTypes;
    
    public CreatePlaneModel() {
        this.planeTypes = PlaneType.values();
    }
    
    public PlaneType[] getPlaneTypes() {return this.planeTypes;}

}
