package edu.uga.cs.rentaride.logic.impl;

import java.util.Date;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class UpdateReservation_CTRL {

	private ObjectLayer objectLayer = null;

	public UpdateReservation_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}

	public void updateReservation( long reservationId, Date pickupTime, int rentalDuration, long customerId, long rentalLocationId, long vehicleTypeId, long rentalId ) throws RARException{

		VehicleType				 vehicleType = null;
		VehicleType              modelVehicleType = null;
		Iterator<VehicleType>    vehicleTypeIter = null;

		RentalLocation		     rentalLocation = null;
		RentalLocation           modelRentalLocation = null;
		Iterator<RentalLocation>    rentalLocationIter = null;

		Customer				 customer = null;
		Customer              modelCustomer = null;
		Iterator<Customer>    customerIter = null;

		Rental				 rental = null;
		Rental              modelRental = null;
		Iterator<Rental>    rentalIter = null;


		Reservation				 reservation = null;
		Reservation              modelReservation = null;
		Iterator<Reservation>    reservationIter = null;


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

		modelCustomer = objectLayer.createCustomer();
		modelCustomer.setId(customerId);

		customerIter = objectLayer.findCustomer(modelCustomer);
		if( customerIter.hasNext() )
			customer = customerIter.next(); 

		modelRental = objectLayer.createRental();
		modelRental.setId(rentalId);

		rentalIter = objectLayer.findRental(modelRental);
		if( rentalIter.hasNext() )
			rental = rentalIter.next(); 

		// create the Reservation

		modelReservation = objectLayer.createReservation();
		if (reservationId != 0){
			modelReservation.setId(reservationId);
			reservationIter = objectLayer.findReservation(modelReservation);
			if (reservationIter.hasNext())
				reservation = reservationIter.next();


		}

		//long reservationId, Date pickupTime, int rentalDuration, long customerId, 
		//long rentalLocationId, long vehicleTypeId, long rentalId
		if ( rentalLocationId != 0)
			reservation.setRentalLocation(rentalLocation);
		if ( customerId != 0 )
			reservation.setCustomer(customer);
		if ( vehicleTypeId != 0)
			reservation.setVehicleType(vehicleType);
		if (pickupTime != null)
			reservation.setPickupTime(pickupTime);
		if (rentalDuration != 0)
			reservation.setRentalDuration(rentalDuration);
		if ( rentalId != 0 )
			reservation.setRental(rental);


		objectLayer.storeReservation(reservation);


	}

}
