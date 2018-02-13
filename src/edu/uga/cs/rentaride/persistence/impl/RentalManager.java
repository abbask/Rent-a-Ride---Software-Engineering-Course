package edu.uga.cs.rentaride.persistence.impl;





import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;



import edu.uga.cs.rentaride.*;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class RentalManager {

	private ObjectLayer objectLayer = null;
	private Connection   conn = null;

	public RentalManager( Connection conn, ObjectLayer objectLayer )
	{
		this.conn = conn;
		this.objectLayer = objectLayer;
	}

	public void save(Rental rental) 
			throws RARException
	{
		String               insertRentalSql = "insert into Rental ( Pickup, "
				+ "ReturnTime, Charges, ReservationId ,VehicleId, CustomerId) values ( ?, ?, ?, ?, ?, ?)";
		String               updateRentalSql = "update Rental set Pickup = ?, ReturnTime = ?,Charges=?, ReservationId = ?, VehicleId = ? , CustomerId=? where id = ?";		
		PreparedStatement    stmt = null;
		int                  inscnt;
		long                 rentalId;

		try {

			if( !rental.isPersistent() )
				stmt = (PreparedStatement) conn.prepareStatement( insertRentalSql );
			else
				stmt = (PreparedStatement) conn.prepareStatement( updateRentalSql );
			
//Pickup, Return, Charges, Condition, ReservationId ,VehicleId, CustomerId
			if( rental.getPickupTime() != null ){
				java.util.Date jDate = rental.getPickupTime();
				java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
				stmt.setDate( 1,  sDate );					
			}
			else 
				throw new RARException( "RentalManager.save: can't save a Rental: Pickup Time is undefined" );
			
			if( rental.getReturnTime() != null ){
				java.util.Date jDate = rental.getReturnTime();
				java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
				stmt.setDate( 2,  sDate );					
			}
			else 
				stmt.setNull(2, java.sql.Types.DATE);
//				throw new RARException( "RentalManager.save: can't save a Rental: Return Time is undefined" );

			if( rental.getCharges() != 0 ) // name is unique unique and non null
				stmt.setInt( 3, rental.getCharges() );
			else
				stmt.setNull(3, java.sql.Types.INTEGER);
//				throw new RARException( "RentalManager.save: can't save a Rental: Charges is undefined" );					
			
			
			 //ReservationId ,VehicleId, CustomerId
			if( rental.getReservation() != null && rental.getReservation().isPersistent() )
				stmt.setLong(4, rental.getReservation().getId());
			else 
				throw new RARException( "RentalManager.save: can't save a reservation: Reservation is not set or not persistent" );
			
			if( rental.getVehicle() != null && rental.getVehicle().isPersistent() )
				stmt.setLong(5, rental.getVehicle().getId());
			else 
				throw new RARException( "RentalManager.save: can't save a vehcile: Vehicle is not set or not persistent" );
			
			if( rental.getCustomer() != null && rental.getCustomer().isPersistent() )
				stmt.setLong(6, rental.getCustomer().getId());
			else 
				throw new RARException( "RentalManager.save: can't save a Customer: Vehicle is not set or not persistent" );
			
			if( rental.isPersistent() )
				stmt.setLong( 7, rental.getId() );
//			System.out.println(stmt.toString());
			inscnt = stmt.executeUpdate();

			if( !rental.isPersistent() ) {
				if( inscnt >= 1 ) {
					String sql = "select last_insert_id()";
					if( stmt.execute( sql ) ) { // statement returned a result

						// retrieve the result
						ResultSet r = stmt.getResultSet();

						// we will use only the first row!
						//
						while( r.next() ) {

							// retrieve the last insert auto_increment value
							rentalId = r.getLong( 1 );
							if( rentalId > 0 )
								rental.setId( rentalId ); // set this person's db id (proxy object)
						}
					}
				}
				else
					throw new RARException( "RentalManager.save: failed to save a Rental" );
			}
			else {
				if( inscnt < 1 )
					throw new RARException( "RentalManager.save: failed to save a Rental" ); 
			}
		}
		catch( SQLException e ) {
			e.printStackTrace();
			throw new RARException( "RentalManager.save: failed to save a Rental: " + e );
		}

	}

	public Iterator<Rental> restore(Rental rental) 
			throws RARException
	{
		String       selectRentalSql = "SELECT r.Id,r.Pickup, r.ReturnTime,r.Charges,r.ReservationId,r.VehicleId,r.CustomerId "
				+ ",re.Id,re.Pickup,re.Length,re.VehicleTypeId,re.RentalLocationId,re.CustomerId"
				+",vt.Id,vt.Name"
				+",rl.Id,rl.Name,rl.Address,rl.Capacity"
				+",c.Id,c.Firstname,c.Lastname,c.Username,c.Password,c.Email,c.Address,c.Status,c.CreatedDate,c.MemberUntil,c.LicState,c.LicNumber,c.CCNumber,c.CCExpiry,c.IsAdmin"
				+",v.Id,v.Make,v.Model,v.Year,v.Mileage,v.Tag,v.LastService,v.Status,v.VehicleCondition,v.RentalLocationId,v.VehicleTypeId"
				+" from Rental r, Reservation re, VehicleType vt, RentalLocation rl, Vehicle v, Customer c "
				+ " where r.ReservationId=re.Id AND re.VehicleTypeId=vt.Id AND re.RentalLocationId=rl.Id AND re.CustomerId=c.Id "
				+ "AND r.VehicLeId=v.Id AND r.CustomerId=c.Id"; 
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectRentalSql );

		if( rental != null ) {
			if( rental.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and r.id = " + rental.getId() );
			else {

				if( rental.getPickupTime() != null )
					condition.append( " and r.Pickup = '" + rental.getPickupTime() + "'" );   

				if( rental.getReturnTime() != null ) {
					condition.append( " and r.Return = '" + rental.getReturnTime() + "'" );
				}
				
				if( rental.getCharges() != 0 ) {
					condition.append( " and r.Charges = '" + rental.getCharges() + "'" );
				}	
				
				if (rental.getReservation() != null){
					condition.append( " and re.Id = '" + rental.getReservation().getId() + "'" );
				}
				
				if(rental.getVehicle() != null){
					condition.append( " and v.Id = " + rental.getVehicle().getId() + "'" );
				}
				
				if( condition.length() > 0 )
                    query.append( condition );
											
			}
			
		}

		

		try {
			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				return new RentalIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "RentalManager.restore: Could not restore persistent Rental object; Root cause: " + e );
		}

		throw new RARException( "RentalManager.restore: Could not restore persistent Rental object" );
	}

	public Vehicle resotreVehicle(Rental rental) throws RARException
	{
		
		/*//h.Id, h.Make, h.Model, h.Year, h.Mileage, h.Tag, "
//+ "h.LastService, h.Status, h.VehicleCondition,
 // v.id as VehicleTypeId, v.Name as VehicleTypeName, "
//+ "r.id as RentalLocationId, r.Name as RentalLocationName, r.Address as RentalLocationAddress, "
//+ "r.Capacity */
		String       selectRentalSql = "SELECT v.Id, v.Make, v.Model, v.Year, v.Mileage, v.Tag, v.LastService, v.Status, v.VehicleCondition"
				+",vt.Id,vt.Name"
				+ ",rl.id as RentalLocaiotnId, rl.Name as RentalLocationName, rl.Address as RentalLocationAddress, rl.Capacity "
				+",r.id, r.Pickup as ActualPickupTime, r.ReturnTime, r.Charges, r.ReservationId,r.VehicleId "
				+ "FROM Rental r, "
				+ "Vehicle v, VehicleType vt, RentalLocation rl WHERE"
				+ " r.VehicleId = v.id AND v.RentalLocationId = rl.id "
				+ " AND v.VehicleTypeId = vt.id";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectRentalSql );

		if( rental != null ) {
			if( rental.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and r.id = " + rental.getId() );
			else {

				if( rental.getPickupTime() != null )
					condition.append( " and r.Pickup = '" + rental.getPickupTime() + "'" );   

				if( rental.getReturnTime() != null ) {
					condition.append( " r.Return = '" + rental.getReturnTime() + "'" );
				}
				
				if( rental.getCharges() != 0 ) {
					condition.append( " r.Charges = '" + rental.getCharges() + "'" );
				}
											
			}
		}

		try {

			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				Iterator<Vehicle> vehicleIter = new VehicleIterator( r, objectLayer );
				if( vehicleIter != null && vehicleIter.hasNext() ) {
					return vehicleIter.next();
				}
				else
					return null;
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "RentalManager.restore: Could not restore persistent Vehicle object; Root cause: " + e );
		}

		throw new RARException( "RentalManager.restore: Could not restore persistent Vehicle object" );
	}
	

	public Customer restoreCustomer(Rental rental) throws RARException
	{
		/*				//Id, Firstname, Lastname, Username, Password, Email, Address, Status, CreatedDate, MemberUntil, 
				//LicState, LicNumber, 
				//CCNumber, CCExpiry FROM Customer Where IsAdmin=0";*/
		String       selectRentalSql = "SELECT c.Id,c.Firstname, c.Lastname, c.Username, c.Password, c.Email, c.Address, c.Status,c.CreatedDate,c.MemberUntil, "
				+ "c.LicState, c.LicNumber, c.CCNumber, c.CCExpiry, "
				+"r.id, r.Pickup as ActualPickupTime, r.ReturnTime, r.Charges, r.ReservationId, r.VehicleId, r.CustomerId"
				+ "FROM Rental r, Customer c WHERE"
				+ "r.CustomerId = c.id";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectRentalSql );

		if( rental != null ) {
			if( rental.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and r.id = " + rental.getId() );
			else {

				if( rental.getPickupTime() != null )
					condition.append( " and r.Pickup = '" + rental.getPickupTime() + "'" );   

				if( rental.getReturnTime() != null ) {
					condition.append( " r.Return = '" + rental.getReturnTime() + "'" );
				}
				
				if( rental.getCharges() != 0 ) {
					condition.append( " r.Charges = '" + rental.getCharges() + "'" );
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
			throw new RARException( "RentalManager.restore: Could not restore persistent Vehicle object; Root cause: " + e );
		}

		throw new RARException( "RentalManager.restore: Could not restore persistent Vehicle object" );
	}
	
	public Reservation restoreReservation(Rental rental) throws RARException
	{
		//SELECT r.id, r.Pickup, r.Length, r.VehicleTypeId, r.RentalLocationId, 
		//r.CustomerId, l.Name as RentalLocationName, l.Address as RentalLocationAddress, 
		//l.Capacity, 
		//v.Name as VehicleTypeName,
		//c.FirstName, c.LastName, c.Username, 
		//c.Password, c.Email, c.Address, c.Status, c.MemberUntil, c.LicState, c.LicNumber,
		//c.CCNumber, c.CCExpiry, c.IsAdmin,
		//t.id as RentalId, t.Pickup as actualPickup,t.return, t.charges
		
		String       selectRentalSql = "SELECT s.Id,s.Pickup, s.Length,s.VehicleTypeId,s.RentalLocationId,s.CustomerId"
				+ ",rl.id as RentalLocaiotnId, rl.Name as RentalLocationName, rl.Address as RentalLocationAddress, rl.Capacity"
				+",vt.Id,vt.Name"
				+",c.Id,c.Firstname,c.Lastname,c.Username,c.Password,c.Email,c.Address,c.Status,c.CreatedDate,c.MemberUntil,c.LicState,c.LicNumber,c.CCNumber,c.CCExpiry,c.IsAdmin"
				+",r.Id,r.Pickup, r.ReturnTime,r.Charges,r.ReservationId,r.VehicleId,r.CustomerId "
				+"FROM Reservation s, Rental r, RentalLocation rl, VehicleType vt, Customer c"
			    +"WHERE r.ReservationId = s.Id AND s.VehicleTypeId = vt.Id AND s.CustomerId = c.Id AND s.RentalLocationId = rl.Id";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectRentalSql );

		if( rental != null ) {
			if( rental.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and r.id = " + rental.getId() );
			else {

				if( rental.getPickupTime() != null )
					condition.append( " and r.Pickup = '" + rental.getPickupTime() + "'" );   

				if( rental.getReturnTime() != null ) {
					condition.append( " r.Return = '" + rental.getReturnTime() + "'" );
				}
				
				if( rental.getCharges() != 0 ) {
					condition.append( " r.Charges = '" + rental.getCharges() + "'" );
				}				
											
			}
		}

		try {

			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				Iterator<Reservation> reservationIter = new ReservationIterator( r, objectLayer );
				if( reservationIter != null && reservationIter.hasNext() ) {
					return reservationIter.next();
				}
				else
					return null;
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "RentalManager.restore: Could not restore persistent Vehicle object; Root cause: " + e );
		}

		throw new RARException( "RentalManager.restore: Could not restore persistent Vehicle object" );
	}

	public void delete(Rental rental) 
			throws RARException
	{
		String               deleteRentalSql = "delete from Rental where id = ?";              
		PreparedStatement    stmt = null;
		int                  inscnt;

		if( !rental.isPersistent() ) // is the Membership object persistent?  If not, nothing to actually delete
			return;

		try {
			stmt = (PreparedStatement) conn.prepareStatement( deleteRentalSql );          
			stmt.setLong( 1, rental.getId() );
			inscnt = stmt.executeUpdate();

			if( inscnt == 1 ) {
				return;
			}
			else
				throw new RARException( "RentalManager.delete: failed to delete a Rental" );
		}
		catch( SQLException e ) {
			e.printStackTrace();
			throw new RARException( "RentalManager.delete: failed to delete a Rental: " + e );        
		}
	}
	
	public Iterator<Rental> restoreRentalCustomer( Customer customer ) throws RARException
	{
		String         selectRentalSql = "SELECT r.Id,r.Pickup, r.ReturnTime,r.Charges,r.ReservationId,r.VehicleId,r.CustomerId "
				+ ",re.Id,re.Pickup,re.Length,re.VehicleTypeId,re.RentalLocationId,re.CustomerId"
				+",vt.Id,vt.Name"
				+",rl.Id,rl.Name,rl.Address,rl.Capacity"
				+",c.Id,c.Firstname,c.Lastname,c.Username,c.Password,c.Email,c.Address,c.Status,c.CreatedDate,c.MemberUtil,c.LicStae,c.LicNumber,c.CCNumber,c.CCExpiry,c.IsAdmin"
				+",v.Id,v.Make,v.Model,v.Year,v.Mileage,v.Tag,v.LastService,v.Status,v.VehicleCondition,v.ReantalLocationId,v.VehicleTypeId"
				+"from Rental r, Reservation re, VehicleType vt, RentalLocation rl, Vehicle v, Customer c "
				+ "where r.ReservationId=re.Id AND re.VehicleTypeId=v.Id AND re.RentalLocationId=rl.Id AND re.CustomerId=c.Id"
				+ "AND r.VehiceId=v.Id AND r.CustomerId=c.Id"; 
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectRentalSql );

		if( customer != null ) {
			if( customer.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and r.CustomerId = " + customer.getId() );
			else 
				throw new RARException( "RentalManager.restoreRentalCustomer: CustomerId is not defined.") ;				
		}

		try {

			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				return new RentalIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "RentalManager.restoreRentalCustomer: Could not restore persistent Customer object; Root cause: " + e );
		}

		throw new RARException( "RentalManager.restoreRentalCustomer: Could not restore persistent Customer object" );
	}

}

