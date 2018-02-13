package edu.uga.cs.rentaride.logic.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class FindReservation_CTRL {

	private ObjectLayer objectLayer = null;

	public FindReservation_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}

	public List<Reservation> findCustomerReservation(long customerId) throws RARException{

		Customer 				 modelCustomer = null;		
		Iterator<Reservation>    reservationIter = null;
		List<Reservation> 		 lstReservation = null;
		

		modelCustomer = objectLayer.createCustomer();        
		lstReservation = new LinkedList<Reservation>();        
		modelCustomer.setId(customerId);


		reservationIter = objectLayer.restoreCustomerReservation(modelCustomer);
		while( reservationIter.hasNext() ){
			Reservation reservation = reservationIter.next();                        	
			lstReservation.add(reservation);       	
		}        
		
		
		
		return lstReservation;
	}


	public List<Reservation> findReservation(long reservationId, Date pickupTime, int rentalDuration, 
			long customerId, long rentalLocationId, long vehicleTypeId, long rentalId) throws RARException{

		Reservation 			 modelReservation = null;		
		Iterator<Reservation>    reservationIter = null;
		List<Reservation> 		 lstReservation = null;

		RentalLocation			rentalLocation = null;
		RentalLocation 			 modelRentalLocation = null;
		Iterator<RentalLocation>    rentalLocationIter = null;

		VehicleType				vehicleType = null;
		VehicleType 			modelVehicleType = null;
		Iterator<VehicleType>	vehicleTypeIter = null;

		Customer				customer = null;
		Customer 				modelCustomer = null;
		Iterator<Customer>		customerIter = null;

		Rental					rental = null;
		Rental					modelRental = null;
		Iterator<Rental>		rentalIter = null;

		if (reservationId != 0){
			modelReservation = objectLayer.createReservation();       
			lstReservation = new LinkedList<Reservation>(); 
			modelReservation.setId(reservationId);
			reservationIter = objectLayer.findReservation(modelReservation);
			if( reservationIter.hasNext() ){
				Reservation reservation = reservationIter.next();                        	
				lstReservation.add(reservation);       	
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

			if (customerId != 0){
				modelCustomer = objectLayer.createCustomer();
				modelCustomer.setId(customerId);
				customerIter = objectLayer.findCustomer(modelCustomer);
				if (customerIter.hasNext())
					customer = customerIter.next();

			}

			if(rentalId != 0){
				modelRental = objectLayer.createRental();
				modelRental.setId(rentalId);
				rentalIter = objectLayer.findRental(modelRental);
				if (rentalIter.hasNext()){
					rental = rentalIter.next();
				}
			}


			modelReservation = objectLayer.createReservation();       
			lstReservation = new LinkedList<Reservation>(); 

			if (rentalLocation != null)
				modelReservation.setRentalLocation(rentalLocation);

			if(vehicleType != null)
				modelReservation.setVehicleType(vehicleType);

			if(customer != null)
				modelReservation.setCustomer(customer);

			if(rental != null)
				modelReservation.setRental(rental);
			
			if(pickupTime != null)
				modelReservation.setPickupTime(pickupTime);
			
			if (rentalDuration != 0)
				modelReservation.setRentalDuration(rentalDuration);


			reservationIter = objectLayer.findReservation(modelReservation);
			while( reservationIter.hasNext() ){
				Reservation reservation = reservationIter.next();                        	
				lstReservation.add(reservation);       	
			}        
		}
		return lstReservation;
	}

}
