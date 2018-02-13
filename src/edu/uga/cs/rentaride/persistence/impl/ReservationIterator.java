package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class ReservationIterator implements Iterator<Reservation>{

	private ResultSet    rs = null;
	private ObjectLayer  objectLayer = null;
	private boolean      more = false;

	public ReservationIterator(ResultSet rs, ObjectLayer objectModel) throws RARException {
		this.rs = rs;
		this.objectLayer = objectModel;
		try {
			more = rs.next();
		}
		catch( Exception e ) {  // just in case...
			throw new RARException( "HourlyPriceIterator: Cannot create HourlyPrice iterator; root cause: " + e );
		}
	}

	public boolean hasNext() 
	{ 
		return more; 
	}

	public Reservation next() {

		Reservation reservation = null;
		Long id;
		Date pickupTime = null;
		int rentalDuration;    	

		Customer customer = null;
		Long customerId;
		String firstName;
		String lastName;
		String username;
		String password;
		String email;
		String address;
		UserStatus status;
		Date memberUntil;
		String licNumber;
		String licState;
		String ccNumber;
		Date ccExpiry;
		int isAdmin;

		VehicleType vehicleType = null;
		long vehicleTypeId;
		String vehicleTypeName;

		RentalLocation rentalLocation = null;
		long rentalLocationId;
		String rentalLocationName;
		String rentalLocationAddress;
		int capacity;

		Rental rental;
		long rentalId;
		Date actualPickupTime;
		Date actualReturnTime;
		int charges;
		

		if( more ) {

			try {
				//SELECT r.id, r.Pickup, r.Length, r.VehicleTypeId, r.RentalLocationId, 
				//r.CustomerId, l.Name as RentalLocationName, l.Address as RentalLocationAddress, 
				//l.Capacity, v.Name as VehicleTypeName, c.FirstName, c.LastName, c.Username, 
				//c.Password, c.Email, c.Address, c.Status, c.MemberUntil, c.LicState, c.LicNumber,
				//c.CCNumber, c.CCExpiry, c.IsAdmin, t.id as RentalId, t.Pickup as actualPickup,
				//t.return, t.charges, t.condition 
				id = rs.getLong( 1 );
				
				Timestamp timestamp = rs.getTimestamp(2);
				if (timestamp != null)
					pickupTime = new java.util.Date(timestamp.getTime());
				
				
				rentalDuration = rs.getInt(3);

				vehicleTypeId = rs.getLong(4);
				rentalLocationId = rs.getLong(5);
				customerId = rs.getLong(6);
				

				rentalLocationName = rs.getString(7);
				rentalLocationAddress = rs.getString(8);
				capacity = rs.getInt(9);

				vehicleTypeName = rs.getString(10);

				firstName = rs.getString(11);
				lastName = rs.getString(12);
				username = rs.getString(13);
				password = rs.getString(14);
				email = rs.getString(15);
				address = rs.getString(16);
				status = UserStatus.valueOf(rs.getString(17));
				memberUntil = rs.getDate(18);
				licState = rs.getString(19);
				licNumber = rs.getString(20);
				ccNumber = rs.getString(21); 
				ccExpiry = rs.getDate(22);
				isAdmin = rs.getInt(23);
//				
				rentalId = rs.getLong(24);
				actualPickupTime = rs.getDate(25);
				actualReturnTime = rs.getDate(26);
				charges = rs.getInt(27);
				
				

				more = rs.next();
			}
			catch( Exception e ) {      // just in case...
				throw new NoSuchElementException( "reservationIterator: No next Reservation object; root cause: " + e );
			}
			vehicleType = objectLayer.createVehicleType(vehicleTypeName);
			vehicleType.setId(vehicleTypeId);
			
			rentalLocation = objectLayer.createRentalLocation(rentalLocationName, rentalLocationAddress, capacity);
			rentalLocation.setId(rentalLocationId);
			
			customer = objectLayer.createCustomer(firstName, lastName, username, email, password, memberUntil, memberUntil, licState, licNumber, address, ccNumber, ccExpiry);
			customer.setId(customerId);
			
			
			
					

			try {//PROBLEM where is rental?
				
				rental = objectLayer.createRental();
				rental.setId(rentalId);
				rental.setPickupTime(actualPickupTime);
				rental.setReturnTime(actualReturnTime);
				rental.setCharges(charges);
				
				reservation = objectLayer.createReservation(vehicleType, rentalLocation, customer, pickupTime, rentalDuration);
				reservation.setId(id);  
				reservation.setRental(rental);
								
			}
			catch( RARException ce ) {
				// safe to ignore: we explicitly set the persistent id of the founder Person object above!
			}

			return reservation;
		}
		else {
			throw new NoSuchElementException( "reservationIterator: No next reservation object" );
		}

	}

	public void remove()
	{
		throw new UnsupportedOperationException();
	}

}
