package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.object.impl.ObjectLayerImpl;

public class RentalLocation_CTRL {
	
	private ObjectLayer objectLayer = null;

	public RentalLocation_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public List<RentalLocation> findRentalLocation()throws RARException{
		List<RentalLocation> rentalLocations = null;
		Iterator<RentalLocation> 	rentalLocationIterator = null;
		RentalLocation rentalLocation = null;
		
		RentalLocation modelRentalLocation = objectLayer.createRentalLocation();
		rentalLocations = new LinkedList<RentalLocation>();
		
		rentalLocationIterator = objectLayer.findRentalLocation(modelRentalLocation);
		while (rentalLocationIterator.hasNext()){
			rentalLocation = rentalLocationIterator.next();
			rentalLocations.add(rentalLocation);
		}
		
		return rentalLocations;
	}

}
