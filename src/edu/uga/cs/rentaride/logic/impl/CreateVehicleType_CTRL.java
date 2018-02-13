package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class CreateVehicleType_CTRL {

	private ObjectLayer objectLayer = null;

	public CreateVehicleType_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public long createVehicleType( String name  ) throws RARException{
        VehicleType				 vehicleType = null;
		VehicleType              modelVehicleType = null;
        Iterator<VehicleType>    vehicleTypeIter = null;

        // check if a hourly price with this information already exists
        modelVehicleType = objectLayer.createVehicleType();
        modelVehicleType.setType(name);
        
        vehicleTypeIter = objectLayer.findVehicleType(modelVehicleType);
        if( vehicleTypeIter.hasNext() )
            throw new RARException( "A VehicleType with this definition already exists: ");

   
        // create the hourly Price
        
        vehicleType = objectLayer.createVehicleType(name);
        objectLayer.storeVehicleType(vehicleType);
        
     

        return vehicleType.getId();
	}
	
}
