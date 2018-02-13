package edu.uga.cs.rentaride.logic.impl;

import java.util.Date;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class CreateRental_CTRL {
	
	private ObjectLayer objectLayer = null;

	public CreateRental_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public long createRental( long reservationId, long customerId, long vehicleId, Date pickupTime) throws RARException{
		
		Rental				 rental = null;
		Rental              modelRental = null;
        Iterator<Rental>    rentalIter = null; 
        
		Vehicle				 vehicle = null;
		Vehicle              modelVehicle = null;
        Iterator<Vehicle>    vehicleIter = null;      
        
        Customer				 customer = null;
        Customer              modelCustomer = null;
        Iterator<Customer>    customerIter = null;
		
		Reservation				 reservation = null;
		Reservation              modelReservation = null;
        Iterator<Reservation>    reservationIter = null;
               

        modelVehicle = objectLayer.createVehicle();
        modelVehicle.setId(vehicleId);
        	
        vehicleIter = objectLayer.findVehicle(modelVehicle);
        if( vehicleIter.hasNext() )
            vehicle = vehicleIter.next();
       
        
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
        
        // create the Reservation
        
        modelRental = objectLayer.createRental();        
        modelRental.setVehicle(vehicle);        
        modelRental.setCustomer(customer);
        modelRental.setReservation(reservation);
        modelRental.setPickupTime(pickupTime);
        
        objectLayer.storeRental(modelRental);
       
        return modelRental.getId();
	}

}
