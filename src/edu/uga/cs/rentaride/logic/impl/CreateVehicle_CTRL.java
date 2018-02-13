package edu.uga.cs.rentaride.logic.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class CreateVehicle_CTRL {

	private ObjectLayer objectLayer = null;

	public CreateVehicle_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}


	public long storeVehicle(long vehicleId, long vehicleTypeId, String make, String model,
			int year, String registrationTag, int mileage, Date lastServiced, 
			long rentalLocationId, VehicleCondition vehicleCondition, 
			VehicleStatus vehicleStatus ) throws RARException{

		VehicleType              vehicleType = null;
		VehicleType              modelVehicleType = null;
		Iterator<VehicleType>    vehicleTypeIter = null;

		RentalLocation		     rentalLocation = null;
		RentalLocation           modelRentalLocation = null;
		Iterator<RentalLocation>    rentalLocationIter = null;

		Vehicle				 vehicle = null;
		Vehicle              modelVehicle = null;
		Iterator<Vehicle>    vehicleIter = null;
		
		if (vehicleId != 0){
			modelVehicle = objectLayer.createVehicle();
			modelVehicle.setId(vehicleId);

			vehicleIter = objectLayer.findVehicle(modelVehicle);
			if( vehicleIter.hasNext() )
				vehicle = vehicleIter.next();
		}
		else{
			vehicle = objectLayer.createVehicle();
		}


		if (rentalLocationId != 0){
			modelRentalLocation = objectLayer.createRentalLocation();
			modelRentalLocation.setId(rentalLocationId);

			rentalLocationIter = objectLayer.findRentalLocation(modelRentalLocation);
			if( rentalLocationIter.hasNext() )
				rentalLocation = rentalLocationIter.next();
		}

		if (vehicleTypeId != 0 ){
			modelVehicleType = objectLayer.createVehicleType();
			modelVehicleType.setId(vehicleTypeId);

			vehicleTypeIter = objectLayer.findVehicleType(modelVehicleType);
			if( vehicleTypeIter.hasNext() )
				vehicleType = vehicleTypeIter.next();
		}

		
		if (vehicleTypeId != 0)
			vehicle.setVehicleType(vehicleType);
		
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
			
		if (rentalLocationId != 0)
			vehicle.setRentalLocation(rentalLocation);
		
		if (vehicleCondition != null )
			vehicle.setCondition(vehicleCondition);
		
		if (vehicleStatus != null)
			vehicle.setStatus(vehicleStatus);

		

		objectLayer.storeVehicle(vehicle);

		return vehicle.getId();
	}
}
