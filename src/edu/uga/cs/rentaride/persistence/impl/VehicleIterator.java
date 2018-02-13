package edu.uga.cs.rentaride.persistence.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;


import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleCondition;
//import edu.uga.cs.rentaride.entity.vehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;

import java.sql.ResultSet;
import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class VehicleIterator implements Iterator<Vehicle> {


	private ResultSet    rs = null;
	private ObjectLayer  objectLayer = null;
	private boolean      more = false;

	public VehicleIterator(ResultSet rs, ObjectLayer objectModel) throws RARException {
		this.rs = rs;
		this.objectLayer = objectModel;
		try {
			more = rs.next();
		}
		catch( Exception e ) {  // just in case...
			throw new RARException( "Vehicle: Cannot create Vehicle iterator; root cause: " + e );
		}
	}


	public boolean hasNext() {
		
		return more;
	}

	@Override
	public   Vehicle next() {

		//		SELECT h.Id, h.Make, h.Model, h.Year, h.Mileage, h.Tag, "
		//				+ "h.LastService, h.Status, v.id as VehicleTypeId, v.Name as VehicleTypeName, "
		//				+ "r.id as RentalLocationId, r.Name as RentalLocationName, r.Address as RentalLocationAddress "
		//				+ "r.Capacity FROM Vehicle h, VehicleType v, RentalLocation r "
		//				+ "WHERE h.RentalLocationId = r.id AND h.VehicleTypeId = v.id

		Vehicle vehicle=null;
		long id;
		String make;
		String model;
		int year;
		int mileage;
		String registrationTag;
		Date lastServiced;
		VehicleStatus vehicleStatus;
		VehicleCondition vehicleCondition;

		VehicleType vehicleType = null ;
		long VehicleTypeId;
		String VehicleTypeName;

		RentalLocation rentalLocation;
		long RentalLocationId;
		String RentalLocationName;
		String RentalLocationAddress;
		int Capacity;


		if( more ) {

			try {

//h.Id, h.Make, h.Model, h.Year, h.Mileage, h.Tag, "
//+ "h.LastService, h.Status, h.VehicleCondition, v.id as VehicleTypeId, v.Name as VehicleTypeName, "
//+ "r.id as RentalLocationId, r.Name as RentalLocationName, r.Address as RentalLocationAddress, "
//+ "r.Capacity  

				id = rs.getLong(1);
				make=rs.getString(2);
				model=rs.getString(3);
				year=rs.getInt(4);
				mileage=rs.getInt(5);
				registrationTag=rs.getString(6);			
				lastServiced=rs.getDate(7);
				vehicleStatus=VehicleStatus.valueOf(rs.getString(8));
				vehicleCondition=VehicleCondition.valueOf(rs.getString(9));
				
				VehicleTypeId = rs.getLong(10);
				VehicleTypeName = rs.getString(11);
				
				RentalLocationId = rs.getLong(12);
				RentalLocationName = rs.getString(13);
				RentalLocationAddress = rs.getString(14);
				Capacity = rs.getInt(15);							

				more = rs.next();
			}
			catch( Exception e ) {      // just in case...
				throw new NoSuchElementException( "VehicleIterator: No next HourlyPrice object; root cause: " + e );
			}
			
			rentalLocation = objectLayer.createRentalLocation(RentalLocationName, RentalLocationAddress, Capacity);
			rentalLocation.setId(RentalLocationId);
			
			vehicleType = objectLayer.createVehicleType(VehicleTypeName);
			vehicleType.setId(VehicleTypeId);

			try {
				vehicle = objectLayer.createVehicle(vehicleType, make, model, year, registrationTag, mileage, lastServiced, rentalLocation, vehicleCondition, vehicleStatus);            	              
				vehicle.setId(id);
			}
			catch( RARException ce ) {
				// safe to ignore: we explicitly set the persistent id of the founder Person object above!
			}

			return vehicle;
		}
		else {
			throw new NoSuchElementException( "HourlyPriceIterator: No next HourlyPrice object" );
		}

	}




}
