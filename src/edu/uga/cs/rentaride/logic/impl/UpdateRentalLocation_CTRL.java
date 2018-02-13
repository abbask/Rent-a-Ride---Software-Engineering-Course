package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class UpdateRentalLocation_CTRL {
	
	private ObjectLayer objectLayer = null;

	public UpdateRentalLocation_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public void updateRentalLocation(long id, String name,String addr, int capacity ) throws RARException{
		
		RentalLocation				 rentalLocation = null;

        Iterator<RentalLocation>    rentalLocationIter = null;
        
        // retrieve the vehicle Type
        rentalLocation = objectLayer.createRentalLocation();
        rentalLocation.setName(name);
        rentalLocationIter = objectLayer.findRentalLocation(rentalLocation);
        while (rentalLocationIter.hasNext()){
        	rentalLocation = rentalLocationIter.next();
        }

        // check if the vehicle Tpye actually exists
        if( rentalLocation == null )
            throw new RARException( "A rental Location with this id does not exist: " + rentalLocation );

        // create the hourly Price
        
        rentalLocation = objectLayer.createRentalLocation(name, addr, capacity);
        rentalLocation.setId(id);
        
        objectLayer.storeRentalLocation(rentalLocation);
        
                  
		
	}

}
