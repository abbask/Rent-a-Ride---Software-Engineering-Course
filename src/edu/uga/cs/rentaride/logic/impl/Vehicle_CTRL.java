package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.object.impl.ObjectLayerImpl;

public class Vehicle_CTRL {
	
	private ObjectLayer objectLayer = null;

	public Vehicle_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public List<Vehicle> restoreVehicleRentalLocation(RentalLocation rentalLocation)throws RARException{
		List<Vehicle> vehicles = null;
		Iterator<Vehicle> 	vehicleIterator = null;
		Vehicle vehicle = null;
		
//		RentalLocation modelRentalLocation = objectLayer.createRentalLocation();
		vehicles = new LinkedList<Vehicle>();
		
		vehicleIterator = objectLayer.restoreVehicleRentalLocation(rentalLocation);
		while (vehicleIterator.hasNext()){
			vehicle = vehicleIterator.next();
			vehicles.add(vehicle);
		}
		
		return vehicles;
	}

}
