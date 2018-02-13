package edu.uga.cs.rentaride.persistence;

import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentARideConfig;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;



/**
 * This is the interface to the Persistence Layer subsystem of the Rent-A-Ride system.
 * 
 * Each entity class has three types of operations: restore, store, and delete.  The
 * store operation does an update, if the given argument has already been stored previously
 * (i.e., it is already persistent).
 */
public interface PersistenceLayer
{
    
    /** 
     * Restore all Administrator objects that match attributes of the model Administrator.
     * @param modelAdministrator the model Administrator; if null is provided, all Administrator objects will be returned
     * @return an Iterator of the located Administrator objects
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Iterator<Administrator> restoreAdministrator( Administrator modelAdministrator ) throws RARException;
    
    /** 
     * Store a given Administrator object in the persistent data store.  
     * If the Administrator object to be stored is already persistent, the persistent 
     * object in the data store is updated.
     * @param administrator the Administrator to be stored
     * @throws RARException in case an error occurred during the store operation 
     */
    public void storeAdministrator( Administrator administrator ) throws RARException;
    
    /** 
     * Delete a given Administrator object from the persistent data store.
     * @param administrator the Administrator to be deleted
     * @throws RARException in case an error occurred during the delete operation 
     */
    public void deleteAdministrator( Administrator administrator ) throws RARException; 
    
    /** 
     * Restore all Customer objects that match attributes of the model Customer.
     * @param modelCustomer the model Customer; if null is provided, all Customer objects will be returned
     * @return an Iterator of the located Customer objects
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Iterator<Customer> restoreCustomer( Customer modelCustomer ) throws RARException;
    
    /** 
     * Store a given Customer object in the persistent data store.
     * If the Customer object to be stored is already persistent, the persistent 
     * object in the data store is updated.
     * @param customer the Customer to be stored
     * @throws RARException in case an error occurred during the store operation 
     */
    public void storeCustomer( Customer customer ) throws RARException;
    
    // No need to have a delete Customer method, as a Customer will be removed by changing 
    // UserStatus
    /** 
     * Delete a given Customer object from the persistent data store.
     * @param customer the Customer to be deleted
     * @throws RARException in case an error occurred during the delete operation 
     */
    // public void deleteCustomer( Customer customer ) throws RARException;      
    
    /** 
     * Restore all RentalLocation objects that match attributes of the model RentalLocation.
     * @param modelRentalLocation the model RentalLocation; if null is provided, all RentalLocation objects will be returned
     * @return an Iterator of the located RentalLocation objects
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Iterator<RentalLocation> restoreRentalLocation( RentalLocation modelRentalLocation ) throws RARException;
    
    /** 
     * Store a given RentalLocation object in the persistent data store.
     * If the Administrator object to be stored is already persistent, the persistent
     * object in the data store is updated.
     * @param rentalLocation the RentalLocation to be stored
     * @throws RARException in case an error occurred during the store operation 
     */
    public void storeRentalLocation( RentalLocation rentalLocation ) throws RARException;
    
    /** 
     * Delete a given RentalLocation object from the persistent data store.
     * @param rentalLocation the RentalLocation to be deleted
     * @throws RARException in case an error occurred during the delete operation 
     */
    public void deleteRentalLocation( RentalLocation rentalLocation ) throws RARException;    
    
    /** 
     * Restore all Reservation objects that match attributes of the model Reservation.
     * @param modelReservation the model Reservation; if null is provided, all Reservation objects will be returned
     * @return an Iterator of the located Reservation objects
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Iterator<Reservation> restoreReservation( Reservation modelReservation ) throws RARException;
    
    /** 
     * Store a given Reservation object in the persistent data store.
     * If the Reservation object to be stored is already persistent, the persistent
     * object in the data store is updated.
     * @param administrator the administrator to be stored
     * @throws RARException in case an error occurred during the store operation 
     */
    public void storeReservation( Reservation reservation ) throws RARException;
    
    /** 
     * Delete a given Reservation object from the persistent data store.
     * @param reservation the Reservation to be deleted
     * @throws RARException in case an error occurred during the delete operation 
     */
    public void deleteReservation( Reservation reservation ) throws RARException;
    
    /** 
     * Restore all Rental objects that match attributes of the model Rental.
     * @param modelRental the model Rental; if null is provided, all Rental objects will be returned
     * @return an Iterator of the located Rental objects
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Iterator<Rental> restoreRental( Rental modelRental ) throws RARException;
    
    /** 
     * Store a given Rental object in the persistent data store.
     * If the Rental object to be stored is already persistent, the persistent
     * object in the data store is updated.
     * @param rental the Rental to be stored
     * @throws RARException in case an error occurred during the store operation 
     */
    public void storeRental( Rental rental ) throws RARException;
    
    /** 
     * Delete a given Rental object from the persistent data store.
     * @param rental the Rental to be deleted
     * @throws RARException in case an error occurred during the delete operation 
     */
    public void deleteRental( Rental rental ) throws RARException;    
    
    /** 
     * Restore all VehicleType objects that match attributes of the model VehicleType.
     * @param modelVehicleType the model VehicleType; if null is provided, all VehicleType objects will be returned
     * @return an Iterator of the located VehicleType objects
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Iterator<VehicleType> restoreVehicleType( VehicleType modelVehicleType ) throws RARException;

    /** 
     * Store a given VehicleType object in the persistent data store.  
     * If the VehicleType object to be stored is already persistent, the persistent 
     * object in the data store is updated.
     * @param vehicleType the VehicleType to be stored
     * @throws RARException in case an error occurred during the store operation 
     */
    public void storeVehicleType( VehicleType vehicleType ) throws RARException;
    
    /** 
     * Delete a given VehicleType object from the persistent data store.
     * @param vehicleType the VehicleType to be deleted
     * @throws RARException in case an error occurred during the delete operation 
     */
    public void deleteVehicleType( VehicleType vehicleType ) throws RARException;
    
    /** 
     * Restore all Vehicle objects that match attributes of the model Vehicle.
     * @param modelVehicle the model Vehicle; if null is provided, all Vehicle objects will be returned
     * @return an Iterator of the located Vehicle objects
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Iterator<Vehicle> restoreVehicle( Vehicle modelVehicle ) throws RARException;
    
    /** 
     * Store a given Vehicle object in the persistent data store.
     * If the Vehicle object to be stored is already persistent, the persistent
     * object in the data store is updated.
     * @param vehicle the Vehicle to be stored
     * @throws RARException in case an error occurred during the store operation 
     */
    public void storeVehicle( Vehicle vehicle ) throws RARException;
    
    /** 
     * Delete a given Vehicle object from the persistent data store.
     * @param vehicle the Vehicle to be deleted
     * @throws RARException in case an error occurred during the delete operation 
     */
    public void deleteVehicle( Vehicle vehicle ) throws RARException;
    
    /** 
     * Restore all Comment objects that match attributes of the model Comment.
     * @param modelComment the model Comment; if null is provided, all Comment objects will be returned
     * @return an Iterator of the located Comment objects
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Iterator<Comment> restoreComment( Comment modelComment ) throws RARException;
    
    /** 
     * Store a given Comment object in the persistent data store.
     * If the Comment object to be stored is already persistent, the persistent
     * object in the data store is updated.
     * @param comment the Comment to be stored
     * @throws RARException in case an error occurred during the store operation 
     */
    public void storeComment( Comment comment ) throws RARException;
    
    /** 
     * Delete a given Comment object from the persistent data store.
     * @param comment the Comment to be deleted
     * @throws RARException in case an error occurred during the delete operation 
     */
    public void deleteComment( Comment coment ) throws RARException;
    
    /** 
     * Restore all HourlyPrice objects that match attributes of the model HourlyPrice.
     * @param modelHourlyPrice the model HourlyPrice; if null is provided, all HourlyPrice objects will be returned
     * @return an Iterator of the located HourlyPrice objects
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Iterator<HourlyPrice> restoreHourlyPrice( HourlyPrice modelHourlyPrice ) throws RARException;
    
    /** 
     * Store a given HourlyPrice object in the persistent data store.
     * If the HourlyPrice object to be stored is already persistent, the persistent
     * object in the data store is updated.
     * @param hourlyPrice the HourlyPrice to be stored
     * @throws RARException in case an error occurred during the store operation 
     */
    public void storeHourlyPrice( HourlyPrice hourlyPrice ) throws RARException;
    
    /** 
     * Delete a given HourlyPrice object from the persistent data store.
     * @param hourlyPrice the HourlyPrice to be deleted
     * @throws RARException in case an error occurred during the delete operation 
     */
    public void deleteHourlyPrice( HourlyPrice hourlyPrice ) throws RARException;

    /** 
     * Restore the RentARideConfig object. The RentARideConfig is a singleton class, so there
     * is only one object to restore.
     * @return RentARideConfig the RentARideConfig configuration singleton object
     * @throws RARException in case an error occurred during the delete operation 
     */
    public RentARideConfig restoreRentARideConfig() throws RARException;
    
    /** 
     * Store the RentARideConfig object.
     * If the RentARideConfig object to be stored is already persistent, the persistent
     * object in the data store is updated.
     * @param rentARideConfig the RentARideConfig object to be stored
     * @throws RARException in case an error occurred during the delete operation 
     */
    public void storeRentARideConfig( RentARideConfig rentARideConfig ) throws RARException;

    // Associations
    //

    // Customer--creates-->Reservation;   multiplicity: 1 - *
    //
    /** 
     * Store a link between a Customer and a Reservation created by the Customer.
     * @param customer the Customer to be linked
     * @param reservation the Reservation to be linked
     * @throws RARException in case an error occurred during the store operation 
     */
    public void storeCustomerReservation( Customer customer, Reservation reservation ) throws RARException;

    /** 
     * Return the Customer who created a given Reservation.
     * @param reservation the Reservation
     * @return the Customer who made the Reservation
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Customer restoreCustomerReservation( Reservation reservation ) throws RARException;

    /** 
     * Return Reservations created by a given Customer.
     * @param customer the Customer
     * @return an Iterator with all Reservation created by the Customer
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Iterator<Reservation> restoreCustomerReservation( Customer customer ) throws RARException;

    /** 
     * Delete a link between a Customer and a Reservation created by the Customer.
     * @param customer the Customer
     * @param reservation the Reservation
     * @throws RARException in case an error occurred during the delete operation 
     */
    public void deleteCustomerReservation( Customer customer, Reservation reservation ) throws RARException;

    // Reservation--hasLocation-->RentalLocation;   multiplicity: * - 1
    //
    /** 
     * Store a link between a Reservation and a RentalLocation involved in the Reservation.
     * @param reservation the Reservation
     * @param rentalLocation the RentalLocation
     * @throws RARException in case an error occurred during the store operation 
     */
    public void storeReservationRentalLocation( Reservation reservation, RentalLocation rentalLocation ) throws RARException;

    /** 
     * Return the RentalLocation involved in a given Reservation.
     * @param reservation the Reservation
     * @return the Customer who made the Reservation
     * @throws RARException in case an error occurred during the restore operation 
     */
    public RentalLocation restoreReservationRentalLocation( Reservation reservation ) throws RARException;

    /** 
     * Return Reservations placed for a given RentalLocation.
     * @param rentalLocation the RentalLocation
     * @return an Iterator of Reservations placed for the RentalLocation
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Iterator<Reservation> restoreReservationRentalLocation( RentalLocation rentalLocation ) throws RARException;

    /** 
     * Delete a link between a Reservation and a RentalLocation involved in the Reservation.
     * @param reservation the Reservation
     * @param rentalLocation the RentalLocation
     * @throws RARException in case an error occurred during the delete operation 
     */
    public void deleteReservationRentalLocation( Reservation reservation, RentalLocation rentalLocation ) throws RARException;

    // Reservation--hasType-->VehicleType   multiplicity: * - 1
    //
    /** 
     * Store a link between a Reservation and a VehicleType involved in the Reservation.
     * @param reservation the Reservation
     * @param vehicleType the VehilceType
     * @throws RARException in case an error occurred during the store operation 
     */
    public void storeReservationVehicleType( Reservation reservation, VehicleType vehicleType ) throws RARException;   

    /** 
     * Return the VehicleType involved in a given Reservation.
     * @param reservation the Reservation
     * @throws RARException in case an error occurred during the restore operation 
     */
    public VehicleType restoreReservationVehicleType( Reservation reservation ) throws RARException;

    /** 
     * Return Reservations involving a given VehicleType.
     * @param vehicleType the VehicleType
     * @return an Iterator of VehicleTypes involved in the Reservation
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Iterator<Reservation> restoreReservationVehicleType( VehicleType vehicleType ) throws RARException;

    /** 
     * Delete a link between a Reservation and a VehicleType involved in the Reservation.
     * @param reservation the Reservation
     * @param vehicleType the VehicleType
     * @throws RARException in case an error occurred during the delete operation 
     */
    public void deleteReservationVehicleType( Reservation reservation, VehicleType vehicleType ) throws RARException;  
    
    // Vehicle--locatedAt-->RentalLocation;   multiplicity: * - 1
    //
    /** 
     * Store a link between a Vehicle and a RentalLocation, where the vehicle is located.
     * @param vehicle the Vehicle
     * @param rentalLocation the RentalLocation
     * @throws RARException in case an error occurred during the store operation 
     */
    public void storeVehicleRentalLocation( Vehicle vehicle, RentalLocation rentalLocation ) throws RARException;   

    /** 
     * Return the RentalLocation where a given Vehicle is located.
     * @param vehicle the Vehicle
     * @throws RARException in case an error occurred during the restore operation 
     */
    public RentalLocation restoreVehicleRentalLocation( Vehicle vehicle ) throws RARException;

    /** 
     * Return the Vehicles located at a given RentalLocation.
     * @param rentalLocation the RentalLocation
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Iterator<Vehicle> restoreVehicleRentalLocation( RentalLocation rentalLocation ) throws RARException;

    /** 
     * Delete a link between a Vehicle and a RentalLocation.
     * @param reservation the Vehicle
     * @param rentalLocation the RentalLocation
     * @throws RARException in case an error occurred during the delete operation 
     */
    public void deleteVehicleRentalLocation( Vehicle vehicle, RentalLocation rentalLocation ) throws RARException;

    // Vehicle--hasType-->VehicleType   multiplicity: * - 1
    //
    /** 
     * Store a link between a Vehicle and a VehicleType.
     * @param vehicle the Vehicle
     * @param vehicleType the VehicleType
     * @throws RARException in case an error occurred during the store operation 
     */
    public void storeVehicleVehicleType( Vehicle vehicle, VehicleType vehicleType ) throws RARException;

    /** 
     * Return the VehicleType of a given Vehicle.
     * @param vehicle the Vehicle
     * @return the VehicleType of the Vehicle
     * @throws RARException in case an error occurred during the retrieve operation 
     */
    public VehicleType restoreVehicleVehicleType( Vehicle vehicle ) throws RARException;

    /** 
     * Return all Vehicles classified as having a given VehicleType.
     * @param vehicleType the VehicleType
     * @return an Iterator of Vehicles having the VehicleType
     * @throws RARException in case an error occurred during the retrieve operation 
     */
    public Iterator<Vehicle> restoreVehicleVehicleType( VehicleType vehicleType ) throws RARException;

    /** 
     * Delete a link between a Vehicle and a VehicleType.
     * @param vehicle the Vehicle
     * @param vehicleType the VehicleType
     * @throws RARException in case an error occurred during the delete operation 
     */
    public void deleteVehicleVehicleType( Vehicle vehicle, VehicleType vehicleType ) throws RARException;

    // VehicleType--has-->PriceSetting   multiplicity: 1 - *
    //
    /** 
     * Store a link between a VehicleType and an HourlyPrice.
     * @param vehicleType the VehicleType
     * @param priceSetting the HourlyPrice
     * @throws RARException in case an error occurred during the store operation 
     */
    public void storeVehicleTypeHourlyPrice( VehicleType vehicleType, HourlyPrice hourlyPrice ) throws RARException;

    /** 
     * Return a VehicleType for this HourlyPrice setting.
     * @param hourlyPrice the HourlyPrice
     * @return the VehicleType of this HourlyPrice
     * @throws RARException in case an error occurred during the restore operation 
     */
    public VehicleType restoreVehicleTypeHourlyPrice( HourlyPrice hourlyPrice ) throws RARException;

    /** 
     * Return all HourlyPrice settings for a given VehicleType.
     * @param vehicleType the VehicleType
     * @return an Iterator of HourlyPrice objects for the VehicleType
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Iterator<HourlyPrice> restoreVehicleTypeHourlyPrice( VehicleType vehicleType ) throws RARException;    

    /** 
     * Delete a link between a VehicleType and an HourlyPrice.
     * @param vehicleType the VehicleType
     * @param hourlyPrice the HourlyPrice
     * @throws RARException in case an error occurred during the delete operation 
     */
    public void deleteVehicleTypeHourlyPrice( VehicleType vehicleType, HourlyPrice hourlyPrice ) throws RARException;

    // Rental--describedBy-->Comment   multiplicity: 1 - *
    //
    /** 
     * Store a link between a Rental and a Comment describing the Rental.
     * @param rental the Rental 
     * @param comment the Comment
     * @throws RARException in case an error occurred during the store operation 
     */
    public void storeRentalComment( Rental rental, Comment comment ) throws RARException;   

    /** 
     * Return a Rental described by a Comment.
     * @param comment the Comment
     * @return the Rental described by the Comment
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Rental restoreRentalComment( Comment comment ) throws RARException;

    /** 
     * Return all Comments describing a Rental.
     * @param rental the Rental
     * @return an Iterator of Comment objects for the Rental
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Iterator<Comment> restoreRentalComment( Rental rental ) throws RARException;    

    /** 
     * Delete a link between a Rental and a Comment describing the Rental.
     * @param rental the Rental
     * @param comment the Comment
     * @throws RARException in case an error occurred during the delete operation 
     */
    public void deleteRentalComment( Rental rental, Comment comment ) throws RARException;

    // Customer--commentedBy-->Comment   multiplicity: 1 - *
    //
    // This is a derived association, so the link should be created and deleted by the primary
    // association's create and delete operations.  In this case, via the Customer creates Reservation to 
    // the Rental association class, and then via describedBy to Comment.  So, this link should be 
    // automatically created when a Comment object is created and linked to a Rental.
    // Similarly, this link should be deleted when a Comment object is deleted and unlinked from a Rental.
    //
    /** 
     * Store a link between a Customer and a Comment.
     * @param customer the Customer
     * @param comment the Comment
     * @throws RARException in case an error occurred during the store operation 
     */
    // public void storeCustomerComment( Customer customer, Comment comment ) throws RARException;   

    /** 
     * Return a Customer who made a given a Comment.
     * @param comment the Comment
     * @return the Customer who made the Comment
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Customer restoreCustomerComment( Comment comment ) throws RARException;

    /** 
     * Return all Comments made by a Customer.
     * @param customer the Customer
     * @return an Iterator of Comment objects commented by the Customer
     * @throws RARException in case an error occurred during the restore operation 
     */
    public Iterator<Comment> restoreCustomerComment( Customer customer ) throws RARException;    

    /** 
     * Delete a link between a Customer and a Comment.
     * @param customer the Customer
     * @param comment the Comment
     * @throws RARException in case an error occurred during the delete operation 
     */
    // public void deleteCustomerComment( Customer customer, Comment comment ) throws RARException;

    // Rental--rentedBy-->Customer   multiplicity: * - 1
    //
    // This is a derived association, so the link should be created and deleted by the primary
    // association's create and delete operations.  In this case, via the Customer creates Reservation 
    // and then to the Rental association class.  So, this link should be created automatically
    // when a Rental object is created for a Reservation.  Similarly, this link should be automatically 
    // deleted when the Rental association class object instance is deleted.
    //
    /** 
     * Store a link between a Rental and a Customer involved in it.
     * @param rental the Rental 
     * @param customer the Customer
     * @throws RARException in case either the rental or the customer is null or another error occurs
     */
    // public void createRentalCustomer( Rental rental, Customer customer ) throws RARException;
    
    /** 
     * Return the Customer involved in the given Rental.
     * @param rental the Rental
     * @return the Customer involved in the Rental
     * @throws RARException in case either the rental is null or another error occurs
     */
    public Customer restoreRentalCustomer( Rental rental ) throws RARException;
    
    /** 
     * Return all Rentals by a given Customer.
     * @param customer the Customer
     * @return an iterator of Rentals of the Customer
     * @throws RARException in case either customer is null or another error occurs
     */
    public Iterator<Rental> restoreRentalCustomer( Customer customer ) throws RARException;
    
    /** 
     * Delete a link between a Rental and a Customer involved in it.
     * @param rental the Rental
     * @param customer the Customer
     * @throws RARException in case either the rental or the customer is null or another error occurs
     */
    // public void deleteRentalCustomer( Rental rental, Customer customer ) throws RARException;
}
