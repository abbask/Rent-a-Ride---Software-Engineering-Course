package edu.uga.cs.rentaride.logic.impl;

import java.util.Date;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class UpdateVehicle_CTRL {
	
	private ObjectLayer objectLayer = null;

	public UpdateVehicle_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public void updateVehicle(long vehicleId, long vehicleTypeId, String make, String model,
			int year, String registrationTag, int mileage, Date lastServiced, 
			long rentalLocationId, VehicleCondition vehicleCondition, 
			VehicleStatus vehicleStatus ) throws RARException{
		
		
		Vehicle				 vehicle = null;
		Vehicle              modelVehicle = null;
        Iterator<Vehicle>    vehicleIter = null;
        
		VehicleType				 vehicleType = null;
		VehicleType              modelVehicleType = null;
        Iterator<VehicleType>    vehicleTypeIter = null;
        
        RentalLocation		     rentalLocation = null;
        RentalLocation           modelRentalLocation = null;
        Iterator<RentalLocation>    rentalLocationIter = null;
        
		
        modelVehicleType = objectLayer.createVehicleType();
        modelVehicleType.setId(vehicleTypeId);
        	
        vehicleTypeIter = objectLayer.findVehicleType(modelVehicleType);
        if( vehicleTypeIter.hasNext() )
            vehicleType = vehicleTypeIter.next();
        

        modelRentalLocation = objectLayer.createRentalLocation();
        modelRentalLocation.setId(rentalLocationId);
        	
        rentalLocationIter = objectLayer.findRentalLocation(modelRentalLocation);
        if( rentalLocationIter.hasNext() )
            rentalLocation = rentalLocationIter.next();
        
        modelVehicle = objectLayer.createVehicle();
        modelVehicle.setId(vehicleId);
        vehicleIter = objectLayer.findVehicle(modelVehicle);
        if( vehicleIter.hasNext() )
            vehicle = vehicleIter.next();
                
        if (vehicleType != null)
        	vehicle.setVehicleType(vehicleType);
        
        if (rentalLocation != null)
        	vehicle.setRentalLocation(rentalLocation);
        
        if (make != null)
        	vehicle.setMake(make);
        
        if (model != null)
        	vehicle.setModel(model);
        
        if (year != 0)
        	vehicle.setYear(year);
        
        if (registrationTag != null)
        	 vehicle.setRegistrationTag(registrationTag);
        
        if (mileage != 0)
        	vehicle.setMileage(mileage);
        
        if (lastServiced != null)
        	vehicle.setLastServiced(lastServiced);
        
        if (vehicleCondition != null)
        	vehicle.setCondition(vehicleCondition);
        
        if (vehicleStatus != null)
        	vehicle.setStatus(vehicleStatus);
                
        objectLayer.storeVehicle(vehicle);
        
	}

}
