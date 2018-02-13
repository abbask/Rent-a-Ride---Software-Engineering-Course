package edu.uga.cs.rentaride.entity;

import java.util.Date;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.persistence.Persistable;


/** This class represents a reservation made by a Rent-A-Ride customer, for a vehicle type at a
 * specific rental location to be rented at a specific date and time.  The requested duration of
 * a rental must be expressed as a positive number of hours, which is less than or equal to 72.
 * Once the customer actually rents a vehicle, the reservation will involve a rental object.
 *
 */
public interface Reservation 
    extends Persistable
{
    /** Return the intended pickup time.
     * @return the pickup time for this reservation
     */
    public Date           getPickupTime();
    
    /** Set the intended pickup time.
     * @param pickupTime the new pickup time for this reservation
     * @throws RARException in case the pickup time is in the past
     */
    public void           setPickupTime( Date pickupTime ) throws RARException;
    
    /** Return the intended rental duration (in hours).
     * @return the intended rental duration (in hours)
     */
    public int            getRentalDuration();
    
    /** Set the intended rental duration (in hours).
     * @param rentalDuration the new rental duration (in hours)
     * @throws RARException in case duration time is illegal
     */
    public void           setRentalDuration( int rentalDuration ) throws RARException;
    
    /**
     * @return
     */
    public Customer       getCustomer();
    /**
     * @param customer
     */
    public void           setCustomer( Customer customer );
    /**
     * @return
     */
    public VehicleType    getVehicleType();
    /**
     * @param vehicleType
     */
    public void           setVehicleType( VehicleType vehicleType );
    /**
     * @return
     */
    public RentalLocation getRentalLocation();
    /**
     * @param rentalLocation
     */
    public void           setRentalLocation( RentalLocation rentalLocation );
    /**
     * @return
     */
    public Rental         getRental();
    /**
     * @param rental
     */
    public void           setRental( Rental rental );
}
