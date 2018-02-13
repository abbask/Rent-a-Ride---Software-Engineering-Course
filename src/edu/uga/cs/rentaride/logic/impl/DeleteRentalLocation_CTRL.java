package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class DeleteRentalLocation_CTRL {
	
	private ObjectLayer objectLayer = null;

	public DeleteRentalLocation_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
		
		public void deleteRentalLocation(Long id) throws RARException{
			RentalLocation              modelRentalLocation = null;
	        Iterator<RentalLocation>    RentalLocationIter = null;
	        RentalLocation				rentalLocation = null;
	        Vehicle                      modelVehicle=null;
	        Iterator<Vehicle>            VehicleIter = null;

	        
	        // check if a hourly price with this information already exists
	        modelRentalLocation = objectLayer.createRentalLocation();
	        modelRentalLocation.setId(id);
	        RentalLocationIter = objectLayer.findRentalLocation(modelRentalLocation);
	        if (RentalLocationIter.hasNext())
	        	rentalLocation = RentalLocationIter.next();
	        
	        VehicleIter = objectLayer.restoreVehicleRentalLocation(rentalLocation);
	        
	        if( VehicleIter.hasNext() )
	            throw new RARException( "We have one or more car in this Rental Location" + id );
	        
	        objectLayer.deleteRentalLocation(modelRentalLocation);         
                  
		
	}

}
