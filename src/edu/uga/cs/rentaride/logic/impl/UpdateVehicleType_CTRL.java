package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class UpdateVehicleType_CTRL {
	
	private ObjectLayer objectLayer = null;

	public UpdateVehicleType_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public void updateVehicleType(long id, String name ) throws RARException{
		
		VehicleType				 vehicleType = null;
  
        // create the hourly Price
        
        vehicleType = objectLayer.createVehicleType(name);
        vehicleType.setId(id);
        objectLayer.storeVehicleType(vehicleType);
                  
		
	}

}
