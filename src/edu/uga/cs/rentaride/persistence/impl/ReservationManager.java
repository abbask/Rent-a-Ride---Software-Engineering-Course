package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class ReservationManager {

	private ObjectLayer objectLayer = null;
	private Connection   conn = null;

	public ReservationManager(Connection conn, ObjectLayer objectLayer) {
		this.conn = conn;
		this.objectLayer = objectLayer;

	}

	public void save (Reservation reservation) throws RARException{

		String               insertReservationSql = "insert into Reservation ( Pickup, Length, VehicleTypeId, RentalLocationId, CustomerId, RentalId ) values ( ?, ?, ?, ?, ?, ? )";
		String               updateReservationSql = "update Reservation set Pickup = ?, Length = ?, VehicleTypeId = ?, RentalLocationId = ?, CustomerId=?, RentalId=?  where Id = ?";
		PreparedStatement    stmt = null;
		int                  inscnt;
		long                 reservationId;
//		System.out.println("RentalId: " + reservation.getRental().getId());
		try {
			if( !reservation.isPersistent() )
				stmt = (PreparedStatement) conn.prepareStatement( insertReservationSql );
			else
				stmt = (PreparedStatement) conn.prepareStatement( updateReservationSql );

//			Pickup, Length, VehicleTypeId, RentalLocationId, CustomerId ) 
			if( reservation.getPickupTime() != null ){ // name is unique unique and non null							
				java.util.Date jDate = reservation.getPickupTime();
				java.sql.Timestamp sTime = new java.sql.Timestamp(jDate.getTime());
				stmt.setObject( 1,  sTime );	
//				stmt.setTime(1, sTime);
			}
			else 
				throw new RARException( "ReservationManager.save: can't save a Reservation: Pickup Time is undefined" );

			if( reservation.getRentalDuration() != 0 )
				stmt.setInt( 2, reservation.getRentalDuration());
			else
				throw new RARException( "ReservationManager.save: can't save a Reservation: Rental Duration is undefined" );			

			if( reservation.getVehicleType() != null && reservation.getVehicleType().isPersistent() )
				stmt.setLong(3, reservation.getVehicleType().getId());
			else 
				throw new RARException( "ReservationManager.save: can't save a Reservation: vehicleType is not set or not persistent" );

			if( reservation.getRentalLocation() != null && reservation.getRentalLocation().isPersistent() )
				stmt.setLong(4, reservation.getRentalLocation().getId());
			else 
				throw new RARException( "ReservationManager.save: can't save a Reservation: Rental Location is not set or not persistent" );

			if( reservation.getCustomer() != null && reservation.getCustomer().isPersistent() )
				stmt.setLong(5, reservation.getCustomer().getId());
			else 
				stmt.setNull(5, java.sql.Types.INTEGER);
			
			if( reservation.getRental() != null && reservation.getRental().isPersistent() )
				stmt.setLong(6, reservation.getRental().getId());
			else 
				stmt.setNull(6, java.sql.Types.INTEGER);
//				throw new RARException( "ReservationManager.save: can't save a Reservation: Rental Location is not set or not persistent" );

			if( reservation.isPersistent() )
				stmt.setLong( 7, reservation.getId() );

			inscnt = stmt.executeUpdate();

			if( !reservation.isPersistent() ) {
				if( inscnt >= 1 ) {
					String sql = "select last_insert_id()";
					if( stmt.execute( sql ) ) { // statement returned a result

						// retrieve the result
						ResultSet r = stmt.getResultSet();

						// we will use only the first row!
						//
						while( r.next() ) {

							// retrieve the last insert auto_increment value
							reservationId = r.getLong( 1 );
							if( reservationId > 0 )
								reservation.setId( reservationId ); // set this person's db id (proxy object)
						}
					}
				}
				else
					throw new RARException( "ReservationManager.save: failed to save a Reservation" );
			}
			else {
				if( inscnt < 1 )
					throw new RARException( "ReservationManager.save: failed to save a Reservation" ); 
			}
		}
		catch( SQLException e ) {
			e.printStackTrace();
			throw new RARException( "ReservationManager.save: failed to save a Reservation: " + e );
		}


	}// save function ends

	public void delete(Reservation reservation)
			throws RARException {
		String deleteReservationSql = "delete from Reservation where id = ?";
		PreparedStatement stmt = null;
		int inscnt;

		if (!reservation.isPersistent()) // is the reservation persistent?  If not, nothing to actually delete
		{
			return;
		}

		try {
			stmt = (PreparedStatement) conn.prepareStatement(deleteReservationSql);
			stmt.setLong(1, reservation.getId());
			inscnt = stmt.executeUpdate();
			if (inscnt == 1) {
				return;
			} else {
				throw new RARException("ReservationManager.delete: failed to delete a Reservation");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RARException("ReservationManager.delete: failed to delete a Reservation: " + e);
		}
	}// delete function ends

	public Iterator<Reservation> restore(Reservation reservation) 
			throws RARException
	{
		
		//String       selectClubSql = "select id, name, address, established, founderid from club";
//		String       selectReservationSql = "SELECT r.id, r.Pickup, r.Length, r.VehicleTypeId, r.RentalLocationId, r.CustomerId, l.Name as RentalLocationName, l.Address as RentalLocationAddress, l.Capacity, v.Name as VehicleTypeName, c.FirstName, c.LastName, c.Username, c.Password, c.Email, c.Address, c.Status, c.MemberUntil, c.LicState, c.LicNumber, c.CCNumber, c.CCExpiry, c.IsAdmin FROM Reservation r, VehicleType v, RentalLocation l, Customer c WHERE r.VehicleTypeId = v.id AND r.RentalLocationId = l.id AND r.CustomerId = c.id";
//		String       selectReservationSql = "SELECT r.id, r.Pickup, r.Length, r.VehicleTypeId, r.RentalLocationId, r.CustomerId,  l.Name as RentalLocationName, l.Address as RentalLocationAddress, l.Capacity, v.Name as VehicleTypeName, c.FirstName, c.LastName, c.Username, c.Password, c.Email, c.Address, c.Status, c.MemberUntil, c.LicState, c.LicNumber, c.CCNumber, c.CCExpiry, c.IsAdmin, r.rentalId, rent.Pickup, rent.ReturnTime, rent.Charges FROM Reservation r, VehicleType v, RentalLocation l, Customer c, Rental rent WHERE r.rentalId = rent.Id AND r.VehicleTypeId = v.id AND r.RentalLocationId = l.id AND r.CustomerId = c.id";
		String       selectReservationSql = "SELECT r.id, r.Pickup, r.Length, r.VehicleTypeId, r.RentalLocationId, r.CustomerId,  l.Name as RentalLocationName, l.Address as RentalLocationAddress, l.Capacity, v.Name as VehicleTypeName, c.FirstName, c.LastName, c.Username, c.Password, c.Email, c.Address, c.Status, c.MemberUntil, c.LicState, c.LicNumber, c.CCNumber, c.CCExpiry, c.IsAdmin, r.rentalId, rent.Pickup, rent.ReturnTime, rent.Charges FROM Reservation r left join VehicleType v on r.VehicleTypeId = v.id left join RentalLocation l on r.RentalLocationId = l.id left join Customer c on r.CustomerId = c.id left join Rental rent on r.rentalId = rent.Id";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectReservationSql );

		if( reservation != null ) {
			if( reservation.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " Where r.id = " + reservation.getId() );
			else {

				if( reservation.getPickupTime() != null )
					condition.append( " and Pickup = '" + reservation.getPickupTime() + "'" );   

				if( reservation.getRentalDuration() != 0 ) {					
					condition.append( " Length = '" + reservation.getRentalDuration() + "'" );
				}
				
				if (reservation.getVehicleType() != null){
					condition.append( " r.VehicleTypeId = '" + reservation.getVehicleType().getId() + "'" );
				}
				
				if( condition.length() > 0 )
					query.append(" WHERE ");
                    query.append( condition );

			}
		}

		try {
			System.out.println("nowwww");
			System.out.println(query);
			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
//			System.out.println(query);
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				return new ReservationIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "ReservationManager.restore: Could not restore persistent Reservation object; Root cause: " + e );
		}

		throw new RARException( "ReservationManager.restore: Could not restore persistent Reservation object" );
	}

	public VehicleType restoreVehicleType(Reservation reservation) 
			throws RARException
	{
		String       selectReservationSql = "SELECT r.id, r.Pickup, r.Length, r.VehicleTypeId, r.RentalLocationId, r.CustomerId, l.Name as RentalLocationName, l.Address as RentalLocationAddress, l.Capacity, v.Name as VehicleTypeName, c.FirstName, c.LastName, c.Username, c.Password, c.Email, c.Address, c.Status, c.MemberUntil, c.LicState, c.LicNumber, c.CCNumber, c.CCExpiry, c.IsAdmin FROM Reservation r, VehicleType v, RentalLocation l, Customer c WHERE r.VehicleTypeId = v.id AND r.RentalLocationId = l.id AND r.CustomerId = c.id";              
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Person object instance
		query.append( selectReservationSql );

		if( reservation != null ) {
			if( reservation.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and r.id = " + reservation.getId() );
			else {

				if( reservation.getPickupTime() != null )
					condition.append( " and Pickup = '" + reservation.getPickupTime() + "'" );   

				if( reservation.getRentalDuration() != 0 ) {					
					condition.append( " Length = '" + reservation.getRentalDuration() + "'" );
				}

			}
		}

		try {

			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				Iterator<VehicleType> vehicleTypeIter = new VehicleTypeIterator( r, objectLayer );
				if( vehicleTypeIter != null && vehicleTypeIter.hasNext() ) {
					return vehicleTypeIter.next();
				}
				else
					return null;
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "ReservationManager.restoreVehicleType: Could not restore persistent VehicleTypeIterator Type object; Root cause: " + e );
		}

		// if we reach this point, it's an error
		throw new RARException( "ReservationManager.restoreVehicleType: Could not restore persistent VehicleTypeIterator Type object" );

	}///function ends

	public RentalLocation restoreRentalLocation(Reservation reservation) 
			throws RARException
	{
		String       selectReservationSql = "SELECT r.id, r.Pickup, r.Length, r.VehicleTypeId, r.RentalLocationId, r.CustomerId, l.Name as RentalLocationName, l.Address as RentalLocationAddress, l.Capacity, v.Name as VehicleTypeName, c.FirstName, c.LastName, c.Username, c.Password, c.Email, c.Address, c.Status, c.MemberUntil, c.LicState, c.LicNumber, c.CCNumber, c.CCExpiry, c.IsAdmin FROM Reservation r, VehicleType v, RentalLocation l, Customer c WHERE r.VehicleTypeId = v.id AND r.RentalLocationId = l.id AND r.CustomerId = c.id";              
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Person object instance
		query.append( selectReservationSql );

		if( reservation != null ) {
			if( reservation.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and r.id = " + reservation.getId() );
			else {

				if( reservation.getPickupTime() != null )
					condition.append( " and Pickup = '" + reservation.getPickupTime() + "'" );   

				if( reservation.getRentalDuration() != 0 ) {					
					condition.append( " Length = '" + reservation.getRentalDuration() + "'" );
				}

			}
		}

		try {

			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				Iterator<RentalLocation> RentalLocationIter = new RentalLocationIterator( r, objectLayer );
				if( RentalLocationIter != null && RentalLocationIter.hasNext() ) {
					return RentalLocationIter.next();
				}
				else
					return null;
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "ReservationManager.restoreRentalLocation: Could not restore persistent RentalLocationIterator Type object; Root cause: " + e );
		}

		// if we reach this point, it's an error
		throw new RARException( "ReservationManager.restoreRentalLocation: Could not restore persistent RentalLocationIterator Type object" );

	}///function ends

	public Customer restoreCustomer(Reservation reservation) 
			throws RARException
	{
		String       selectReservationSql = "SELECT r.id, r.Pickup, r.Length, r.VehicleTypeId, r.RentalLocationId, r.CustomerId, l.Name as RentalLocationName, l.Address as RentalLocationAddress, l.Capacity, v.Name as VehicleTypeName, c.FirstName, c.LastName, c.Username, c.Password, c.Email, c.Address, c.Status, c.MemberUntil, c.LicState, c.LicNumber, c.CCNumber, c.CCExpiry, c.IsAdmin FROM Reservation r, VehicleType v, RentalLocation l, Customer c WHERE r.VehicleTypeId = v.id AND r.RentalLocationId = l.id AND r.CustomerId = c.id";              
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Person object instance
		query.append( selectReservationSql );

		if( reservation != null ) {
			if( reservation.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and r.id = " + reservation.getId() );
			else {

				if( reservation.getPickupTime() != null )
					condition.append( " and Pickup = '" + reservation.getPickupTime() + "'" );   

				if( reservation.getRentalDuration() != 0 ) {					
					condition.append( " Length = '" + reservation.getRentalDuration() + "'" );
				}

			}
		}

		try {
			
			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				Iterator<Customer> customerIter = new CustomerIterator( r, objectLayer );
				if( customerIter != null && customerIter.hasNext() ) {
					return customerIter.next();
				}
				else
					return null;
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "ReservationManager.restoreRentalLocation: Could not restore persistent RentalLocationIterator Type object; Root cause: " + e );
		}

		// if we reach this point, it's an error
		throw new RARException( "ReservationManager.restoreRentalLocation: Could not restore persistent RentalLocationIterator Type object" );

	}///function ends

	public Rental restoreRental(Reservation reservation) 
			throws RARException
	{
		String       selectReservationSql = "SELECT r.id, r.Pickup, r.Length, r.VehicleTypeId, r.RentalLocationId, r.CustomerId, l.Name as RentalLocationName, l.Address as RentalLocationAddress, l.Capacity, v.Name as VehicleTypeName, c.FirstName, c.LastName, c.Username, c.Password, c.Email, c.Address, c.Status, c.MemberUntil, c.LicState, c.LicNumber, c.CCNumber, c.CCExpiry, c.IsAdmin FROM Reservation r, VehicleType v, RentalLocation l, Customer c WHERE r.VehicleTypeId = v.id AND r.RentalLocationId = l.id AND r.CustomerId = c.id";              
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Person object instance
		query.append( selectReservationSql );

		if( reservation != null ) {
			if( reservation.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and r.id = " + reservation.getId() );
			else {

				if( reservation.getPickupTime() != null )
					condition.append( " and Pickup = '" + reservation.getPickupTime() + "'" );   

				if( reservation.getRentalDuration() != 0 ) {					
					condition.append( " Length = '" + reservation.getRentalDuration() + "'" );
				}

			}
		}

		try {

			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				Iterator<Rental> rentalIter = new RentalIterator( r, objectLayer );
				if( rentalIter != null && rentalIter.hasNext() ) {
					return rentalIter.next();
				}
				else
					return null;
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "ReservationManager.restoreRental: Could not restore persistent RentalIterator Type object; Root cause: " + e );
		}

		// if we reach this point, it's an error
		throw new RARException( "ReservationManager.restoreRental: Could not restore persistent RentalIterator Type object" );

	}///function ends


	public Iterator<Reservation> restoreCustomerReservation(Customer customer) 
			throws RARException
	{
		//String       selectClubSql = "select id, name, address, established, founderid from club";
		String       selectReservationSql = "SELECT r.id, r.Pickup, r.Length, r.VehicleTypeId, r.RentalLocationId, r.CustomerId,  l.Name as RentalLocationName, l.Address as RentalLocationAddress, l.Capacity, v.Name as VehicleTypeName, c.FirstName, c.LastName, c.Username, c.Password, c.Email, c.Address, c.Status, c.MemberUntil, c.LicState, c.LicNumber, c.CCNumber, c.CCExpiry, c.IsAdmin, r.rentalId, rent.Pickup, rent.ReturnTime, rent.Charges FROM Reservation r left join VehicleType v on r.VehicleTypeId = v.id left join RentalLocation l on r.RentalLocationId = l.id left join Customer c on r.CustomerId = c.id left join Rental rent on r.rentalId = rent.Id";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );
		
		// form the query based on the given Club object instance
		query.append( selectReservationSql );
		
		try {

			if( customer != null ) {
				if( customer.getId() >= 0 ) // id is unique, so it is sufficient to get a person
					query.append( " WHERE r.CustomerId = " + customer.getId() );
				else 
					throw new RARException( "ReservationManager.restoreCustomerReservation: CustomerId is not defined.") ;
			}
			System.out.println("here");
			System.out.println(query);
			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				return new ReservationIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "restoreCustomerReservation.restoreCustomerReservation: Could not restore persistent Reservation object; Root cause: " + e );
		}

		throw new RARException( "ReservationManager.restoreCustomerReservation: Could not restore persistent Reservation object" );
	}
	
	public Iterator<Reservation> restoreReservationRentalLocation(RentalLocation rentalLocation) 
			throws RARException
	{
		String       selectReservationSql = "SELECT r.id, r.Pickup, r.Length, r.VehicleTypeId, r.RentalLocationId, r.CustomerId, l.Name as RentalLocationName, l.Address as RentalLocationAddress, l.Capacity, v.Name as VehicleTypeName, c.FirstName, c.LastName, c.Username, c.Password, c.Email, c.Address, c.Status, c.MemberUntil, c.LicState, c.LicNumber, c.CCNumber, c.CCExpiry, c.IsAdmin FROM Reservation r, VehicleType v, RentalLocation l, Customer c WHERE r.VehicleTypeId = v.id AND r.RentalLocationId = l.id AND r.CustomerId = c.id";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectReservationSql );

		try {

			if( rentalLocation != null ) {
				if( rentalLocation.getId() >= 0 ) // id is unique, so it is sufficient to get a person
					query.append( " and r.RentalLocationId = " + rentalLocation.getId() );
				else 
					throw new RARException( "ReservationManager.restoreReservationRentalLocation: r.RentalLocationId is not defined.") ;
			}
			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				return new ReservationIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "restoreCustomerReservation.restoreReservationRentalLocation: Could not restore persistent RentalLocation object; Root cause: " + e );
		}

		throw new RARException( "ReservationManager.restoreReservationRentalLocation: Could not restore persistent RentalLocation object" );
	}

	public Iterator<Reservation> restoreReservationVehicleType( VehicleType vehicleType )throws RARException
	{
		String       selectReservationSql = "SELECT r.id, r.Pickup, r.Length, r.VehicleTypeId, r.RentalLocationId, r.CustomerId, l.Name as RentalLocationName, l.Address as RentalLocationAddress, l.Capacity, v.Name as VehicleTypeName, c.FirstName, c.LastName, c.Username, c.Password, c.Email, c.Address, c.Status, c.MemberUntil, c.LicState, c.LicNumber, c.CCNumber, c.CCExpiry, c.IsAdmin FROM Reservation r, VehicleType v, RentalLocation l, Customer c WHERE r.VehicleTypeId = v.id AND r.RentalLocationId = l.id AND r.CustomerId = c.id";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectReservationSql );

		try {

			if( vehicleType != null ) {
				if( vehicleType.getId() >= 0 ) // id is unique, so it is sufficient to get a person
					query.append( " and r.VehicleTypeId = " + vehicleType.getId() );
				else 
					throw new RARException( "ReservationManager.restoreReservationVehicleType: VehicleTypeId is not defined.") ;
			}
			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				return new ReservationIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "restoreCustomerReservation.restoreReservationVehicleType: Could not restore persistent VehicleType object; Root cause: " + e );
		}

		throw new RARException( "ReservationManager.restoreReservationRentalLocation: Could not restore persistent VehicleType object" );
	}
	
}
