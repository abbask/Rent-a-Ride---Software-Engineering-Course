package edu.uga.cs.rentaride.entity.impl;

import java.util.Date;

import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.persistence.impl.Persistent;

public class ReservationImpl extends Persistent implements Reservation{
	
	private Date pickupTime;
	private int rentalDuration;
	private Customer customer;
	private VehicleType vehicleType;
	private RentalLocation rentalLocation;
	private Rental rental;
	
	public ReservationImpl() {
		
	}
	
	public ReservationImpl(Date pickupTime, int rentalDuration, Customer customer, 
			VehicleType vehicleType, RentalLocation rentalLocation, Rental rental){
		this.pickupTime = pickupTime;
		this.rentalDuration = rentalDuration;
		this.customer = customer;
		this.vehicleType = vehicleType;
		this.rentalLocation = rentalLocation;
		this.rental = rental;
		
	}

	public Date getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}

	public int getRentalDuration() {
		return rentalDuration;
	}

	public void setRentalDuration(int rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public RentalLocation getRentalLocation() {
		return rentalLocation;
	}

	public void setRentalLocation(RentalLocation rentalLocation) {
		this.rentalLocation = rentalLocation;
	}

	public Rental getRental() {
		return rental;
	}

	public void setRental(Rental rental) {
		this.rental = rental;
	}
	
	public String toString() {
		return "Reservation [" + getId() + "] PickupTime: " + getPickupTime() + ""
				+ " Rental Duration: " + getRentalDuration() + " Vehicle[" + getVehicleType().getId()
				+ "]: " + getVehicleType().getType() + " From Location: " + getRentalLocation().getName(); 
	}

	public boolean equals(Object otherReservation) {
		if (otherReservation == null) {
			return false;
		}
		if (otherReservation instanceof Reservation) // 
		{
			if (getPickupTime()==((Reservation) otherReservation).getPickupTime() 
					&& getRentalDuration()==((Reservation) otherReservation).getRentalDuration()
					&& getVehicleType()==((Reservation) otherReservation).getVehicleType()
					&& getRentalLocation() == ((Reservation) otherReservation).getRentalLocation()
					&& getRental() == ((Reservation) otherReservation).getRental()
					){
				return true;
			}
			
		}
		return false;
	}

}
