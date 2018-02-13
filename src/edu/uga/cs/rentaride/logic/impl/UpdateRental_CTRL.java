package edu.uga.cs.rentaride.logic.impl;

import java.util.Date;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class UpdateRental_CTRL {
	
	private ObjectLayer objectLayer = null;

	public UpdateRental_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}

	
	public void updateRental( long rentalId, long reservationId, long customerId, 
			long vehicleId, Date pickupTime,  Date returnTime, int charges) throws RARException{
		

		Rental				 	rental = null;
		Rental              	modelRental = null;
		Iterator<Rental>    	rentalIter = null;

		Customer				 customer = null;
		Customer              	modelCustomer = null;
		Iterator<Customer>    	customerIter = null;

		Reservation				 reservation = null;
		Reservation              modelReservation = null;
		Iterator<Reservation>    reservationIter = null;

		Vehicle				 	vehicle = null;
		Vehicle              	modelVehicle = null;
		Iterator<Vehicle>    	vehicleIter = null;
		
		modelCustomer = objectLayer.createCustomer();
		modelCustomer.setId(customerId);

		customerIter = objectLayer.findCustomer(modelCustomer);
		if( customerIter.hasNext() )
			customer = customerIter.next(); 
		
		modelReservation = objectLayer.createReservation();
		modelReservation.setId(reservationId);

		reservationIter = objectLayer.findReservation(modelReservation);
		if( reservationIter.hasNext() )
			reservation = reservationIter.next();


		modelVehicle = objectLayer.createVehicle();
		modelVehicle.setId(vehicleId);

		vehicleIter = objectLayer.findVehicle(modelVehicle);
		if( vehicleIter.hasNext() )
			vehicle = vehicleIter.next();

		
		
		///
		modelRental = objectLayer.createRental();
		if (rentalId != 0){
			modelRental.setId(rentalId);
			rentalIter = objectLayer.findRental(modelRental);
			if (rentalIter.hasNext())
				rental = rentalIter.next();


		}

		if ( customerId != 0)
			rental.setCustomer(customer);
		
		if ( reservationId != 0 )
			rental.setReservation(reservation);
		
		if ( vehicleId != 0)
			rental.setVehicle(vehicle);
		
		if (pickupTime != null)
			rental.setPickupTime(pickupTime);
		
		if (returnTime != null)
			rental.setReturnTime(returnTime);
		
		if ( charges != 0 )
			rental.setCharges(charges);


		objectLayer.storeRental(rental);
		
		
	}
	

}
