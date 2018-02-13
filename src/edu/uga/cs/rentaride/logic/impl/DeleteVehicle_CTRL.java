package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class DeleteVehicle_CTRL {
	
	private ObjectLayer objectLayer = null;

	public DeleteVehicle_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public void deleteVehicle(Long id) throws RARException{
		Vehicle              modelVehicle = null;
        Iterator<Vehicle>    vehicleIter = null;

        // check if a hourly price with this information already exists
        modelVehicle = objectLayer.createVehicle();
        modelVehicle.setId(id);
        
        vehicleIter = objectLayer.findVehicle(modelVehicle);
        if( ! vehicleIter.hasNext() )
            throw new RARException( "No such a Vehicle exists id=" + id );
        
        objectLayer.deleteVehicle(modelVehicle);                               

	}

}
