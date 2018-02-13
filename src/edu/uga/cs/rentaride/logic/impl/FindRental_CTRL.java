package edu.uga.cs.rentaride.logic.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class FindRental_CTRL {

	private ObjectLayer objectLayer = null;

	public FindRental_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}

	public List<Rental> findRental( long rentalId, long reservationId, long customerId, long vehicleId, Date pickupTime, Date returnTime, int charges ) throws RARException{

		Rental					rental = null;
		Rental              	modelRental = null;
		Iterator<Rental>    	rentalIter = null;
		List<Rental> 			lstRental = null;

		Reservation 				reservation = null;
		Reservation             	modelReservation = null;
		Iterator<Reservation>    	reservationIter = null;
		
		Customer 					customer = null;
		Customer 					modelCustomer = null;
		Iterator<Customer>			customerIter = null;
		
		Vehicle 					vehicle = null;
		Vehicle 					modelVehicle = null;
		Iterator<Vehicle>			vehicleIter = null;
		
		lstRental = new LinkedList<Rental>();
		
		if (rentalId != 0){

			modelRental = objectLayer.createRental();
			modelRental.setId(rentalId);
			if (pickupTime != null)
				modelRental.setPickupTime(pickupTime);
			
			if (returnTime != null)
				modelRental.setReturnTime(returnTime);
			
			if (charges != 0 )
				modelRental.setCharges(charges);
			
			rentalIter = objectLayer.findRental(modelRental);
			while (rentalIter.hasNext()){
				rental = rentalIter.next();
				lstRental.add(rental);
			}


		}
		else{
			if (reservationId != 0){
				modelReservation = objectLayer.createReservation();
				modelReservation.setId(reservationId);
				reservationIter = objectLayer.findReservation(modelReservation);
				if (reservationIter.hasNext())
					reservation = reservationIter.next();        		
			}
			
			
			if (customerId != 0){
				modelCustomer = objectLayer.createCustomer();
				modelCustomer.setId(customerId);
				customerIter = objectLayer.findCustomer(modelCustomer);
				if (customerIter.hasNext())
					customer = customerIter.next();

			}
			
			if(vehicleId != 0){
				modelVehicle = objectLayer.createVehicle();
				modelVehicle.setId(vehicleId);
				vehicleIter = objectLayer.findVehicle(modelVehicle);
				if (vehicleIter.hasNext())
					vehicle = vehicleIter.next();
				
			}
			
			modelRental = objectLayer.createRental();
			modelRental.setPickupTime(pickupTime);
			modelRental.setReturnTime(returnTime);
			modelRental.setCharges(charges);
			modelRental.setCustomer(customer);
			modelRental.setVehicle(vehicle);
			modelRental.setReservation(reservation);
			
			rentalIter = objectLayer.findRental(modelRental);
			while (rentalIter.hasNext()){
				rental = rentalIter.next();
				lstRental.add(rental);
			}


		}
//		System.out.println(lstRental.size());
		return lstRental;

	}

}
