package edu.uga.cs.rentaride.logic.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class FindVehicle_CTRL {

	private ObjectLayer objectLayer = null;

	public FindVehicle_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}

	public List<Vehicle> findVehicle(long vehicleId, long vehicleTypeId, String make, String model,
			int year, String registrationTag, int mileage, Date lastServiced, 
			long rentalLocationId, VehicleCondition vehicleCondition, VehicleStatus vehicleStatus ) throws RARException{
		Vehicle 				vehicle = null;
		Vehicle 				modelVehicle = null;
		Iterator<Vehicle>    	vehicleIter = null;
		List<Vehicle> 			lstVehicle = null;

		RentalLocation 			rentalLocation = null;
		RentalLocation 			modelRentalLocation = null;
		Iterator<RentalLocation>rentalLocationIter = null;

		VehicleType 			vehicleType = null;
		VehicleType 			modelVehicleType = null;
		Iterator<VehicleType> vehicleTypeIter = null;

		if (vehicleId != 0){

			modelVehicle = objectLayer.createVehicle();
			modelVehicle.setId(vehicleId);
			vehicleIter = objectLayer.findVehicle(modelVehicle);
			lstVehicle = new LinkedList<Vehicle>();
			if (vehicleIter.hasNext()){
				vehicle = vehicleIter.next();
				lstVehicle.add(vehicle);
			}

		}
		else{

			if (rentalLocationId != 0){
				modelRentalLocation = objectLayer.createRentalLocation();
				modelRentalLocation.setId(rentalLocationId);
				rentalLocationIter = objectLayer.findRentalLocation(modelRentalLocation);
				if (rentalLocationIter.hasNext())
					rentalLocation = rentalLocationIter.next();
			}

			if (vehicleTypeId != 0){
				modelVehicleType = objectLayer.createVehicleType();
				modelVehicleType.setId(vehicleTypeId);
				vehicleTypeIter = objectLayer.findVehicleType(modelVehicleType);
				if (vehicleTypeIter.hasNext())
					vehicleType = vehicleTypeIter.next();
			}


			modelVehicle = objectLayer.createVehicle();
//			modelVehicle.setId(vehicleId);
			modelVehicle.setVehicleType(vehicleType);
			modelVehicle.setRentalLocation(rentalLocation);

			//				long vehicleId, long vehicleTypeId, String make, String model,
			//	            int year, String registrationTag, int mileage, Date lastServiced, 
			//	            long rentalLocationId, VehicleCondition vehicleCondition, 
			//				VehicleStatus vehicleStatus
			modelVehicle.setMake(make);
			modelVehicle.setModel(model);
			modelVehicle.setYear(year);
			modelVehicle.setRegistrationTag(registrationTag);
			modelVehicle.setMileage(mileage);
			modelVehicle.setLastServiced(lastServiced);
			modelVehicle.setCondition(vehicleCondition);
			modelVehicle.setStatus(vehicleStatus);



			vehicleIter = objectLayer.findVehicle(modelVehicle);
			lstVehicle = new LinkedList<Vehicle>();
			while(vehicleIter.hasNext()){
				vehicle = vehicleIter.next();
				lstVehicle.add(vehicle);
			}

		}//else ends

		return lstVehicle;

	}

}
