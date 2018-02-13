package edu.uga.cs.rentaride.entity.impl;



import java.util.Date;


import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.persistence.impl.Persistent;

public class RentalImpl extends Persistent implements Rental {
    Date pickuptime;
    Date returntime;
    int charges;   
    Reservation reservation;
    Vehicle vehicle;
    Customer customer;
    
    public RentalImpl() {

	}
    
	public RentalImpl(Date pickuptime, Date returntime, int charges, Reservation reservation, Vehicle vehicle,
			Customer customer) {
		super(-1);
		this.pickuptime = pickuptime;
		this.returntime = returntime;
		this.charges = charges;
		this.reservation = reservation;
		this.vehicle = vehicle;
		this.customer = customer;
		
	}


	public Date getPickupTime() {
		return pickuptime;
	}

	
	public void setPickupTime(Date pickupTime) {
		this.pickuptime=pickupTime;
	}

	
	public Date getReturnTime() {
		
		return returntime;
	}

	
	public void setReturnTime(Date returnTime) throws RARException {
		
		this.returntime=returnTime;
	}

	
	public Reservation getReservation() {
		
		return reservation;
	}

	
	public void setReservation(Reservation reservation) {
		
		this.reservation=reservation;
	}

	
	public Vehicle getVehicle() {
		
		return vehicle;
	}

	
	public void setVehicle(Vehicle vehicle) {
		
		this.vehicle=vehicle;
	}

	
	public Customer getCustomer() {
		
		return customer;
	}

	
	public void setCustomer(Customer customer) {
		
		this.customer=customer;
	}
	
	public int getCharges() {
		
		return charges;
	}
	public void setCharges(int charges) {
		
		this.charges=charges;
	}

	public String toString()
    {
        return "Rental [" + getId() + "] " + "pickuptime:"+ getPickupTime() + " returntime:" + getReturnTime() + " charges: " + getCharges()
        + " reservation:" + getReservation() + " vehicle: " + getVehicle() + " customer: " + getCustomer();
    }
    
    public boolean equals( Object otherRental )
    {
        if( otherRental == null )
            return false;
        if( otherRental instanceof Rental ) // name is a unique attribute
            return (getReservation()== ((Rental)otherRental).getReservation() 
            		&& getPickupTime() == ((Rental)otherRental).getPickupTime()
            		&& getReturnTime()== ((Rental)otherRental).getReturnTime()
            		&& getVehicle() ==((Rental)otherRental).getVehicle()
            		&& getCustomer() ==  ((Rental)otherRental).getCustomer()	
            		&& getCharges() == ((Rental)otherRental).getCharges()
            		);
        
        return false;        
        
    }
	
}

