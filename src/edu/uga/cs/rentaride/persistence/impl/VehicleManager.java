package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class VehicleManager {



	private ObjectLayer objectLayer = null;
	private Connection   conn = null;

	public VehicleManager(Connection conn, ObjectLayer objectLayer) {
		this.conn = conn;
		this.objectLayer = objectLayer;

	}

	public void save (Vehicle vehicle) throws RARException{

		String               insertVehicleSql = "insert into Vehicle ( Make, Model, Year, Mileage,Tag,LastService,Status,VehicleCondition,RentalLocationId, VehicleTypeId ) values (  ?, ?, ? ,? ,? ,? , ?, ?, ?,? )";
		String               updateVehicleSql = "update Vehicle set Make = ?, Model = ?, Year = ?,Mileage = ?,Tag = ?,LastService = ?,Status = ?, VehicleCondition = ?,RentalLocationId = ?, vehicleTypeId=?   where Id = ?";
		PreparedStatement    stmt = null;
		int                  inscnt;
		long                 VehicleId;

		try {

			if( !vehicle.isPersistent() )
				stmt = (PreparedStatement) conn.prepareStatement( insertVehicleSql );
			else
				stmt = (PreparedStatement) conn.prepareStatement( updateVehicleSql );

			if( vehicle.getMake() != "" ) // name is unique unique and non null
				stmt.setString(1, vehicle.getMake() );
			else 
				throw new RARException( "VehicleManager.save: can't save a Vehicle: Make is undefined" );

			if( vehicle.getMileage() != 0 )
				stmt.setInt( 4, vehicle.getMileage() );
			else
				throw new RARException( "VehicleManager.save: can't save a Vehicle: Mileage is undefined" );

			if( vehicle.getModel() != "" ) {
				stmt.setString(2, vehicle.getModel());
			}
			else
				throw new RARException( "VehicleManager.save: can't save a Vehicle: price is undefined" );

			if( vehicle.getYear() != 0  )
				stmt.setLong(3, vehicle.getYear());
			else 
				throw new RARException( "VehicleManager.save: can't save a Vehicle: Year is not set or not persistent" );
			//"Tag,LastService,Status,RentalLocationId,VehicleTypeId )"

			if( vehicle.getRegistrationTag() != null  )
				stmt.setString(5, vehicle.getRegistrationTag());
			else 
				throw new RARException( "VehicleManager.save: can't save a Vehicle: Regiteration Tag is not set or not persistent" );


			if( vehicle.getLastServiced()!= null ){
				java.util.Date jDate = vehicle.getLastServiced();
				java.sql.Timestamp sTime = new java.sql.Timestamp(jDate.getTime());
				stmt.setObject( 6,  sTime );
				
			}
			else 
				throw new RARException( "VehicleManager.save: can't save a Vehicle: Last Service is not set or not persistent" );


			if( vehicle.getStatus() != null )
				stmt.setObject(7, vehicle.getStatus().toString());
			else 
				throw new RARException( "VehicleManager.save: can't save a Vehicle: Status is not set or not persistent" );

			if( vehicle.getCondition() != null )
				stmt.setObject(8, vehicle.getCondition().toString());
			else 
				throw new RARException( "VehicleManager.save: can't save a Vehicle: Condition is not set or not persistent" );

			if( vehicle.getRentalLocation() != null && vehicle.getRentalLocation().isPersistent() )
				stmt.setLong(9, vehicle.getRentalLocation().getId());
			else 
				throw new RARException( "VehicleManager.save: can't save a Vehicle: vehicleType is not set or not persistent" );

			if( vehicle.getVehicleType() != null && vehicle.getVehicleType().isPersistent() )
				stmt.setLong(10, vehicle.getVehicleType().getId());
			else 
				throw new RARException( "VehicleManager.save: can't save a Vehicle: vehicleType is not set or not persistent" );


			if( vehicle.isPersistent() )
				stmt.setLong( 11, vehicle.getId() );

//			System.out.println(stmt.toString());
			inscnt = stmt.executeUpdate();

			if( !vehicle.isPersistent() ) {
				if( inscnt >= 1 ) {
					String sql = "select last_insert_id()";
					if( stmt.execute( sql ) ) { // statement returned a result

						// retrieve the result
						ResultSet r = stmt.getResultSet();

						// we will use only the first row!
						//
						while( r.next() ) {

							// retrieve the last insert auto_increment value
							VehicleId = r.getLong( 1 );
							if( VehicleId > 0 )
								vehicle.setId( VehicleId ); // set this person's db id (proxy object)
						}
					}
				}
				else
					throw new RARException( "VehicleManager.save: failed to save a Vehicle" );
			}
			else {
				if( inscnt < 1 )
					throw new RARException( "VehicleManager.save: failed to save a Vehicle" ); 
			}
		}
		catch( SQLException e ) {
			e.printStackTrace();
			throw new RARException( "VehicleManager.save: failed to save a Vehicle: " + e );
		}


	}// save function ends

	public void delete(Vehicle vehicle)
			throws RARException {
		String deleteVehicleSql = "delete from Vehicle where id = ?";
		PreparedStatement stmt = null;
		int inscnt;

		if (!vehicle.isPersistent()) // is the Vehicle persistent?  If not, nothing to actually delete
		{
			return;
		}

		try {
			stmt = (PreparedStatement) conn.prepareStatement(deleteVehicleSql);
			stmt.setLong(1, vehicle.getId());
			inscnt = stmt.executeUpdate();
			if (inscnt == 1) {
				return;
			} else {
				throw new RARException("VehicleManager.delete: failed to delete a Vehicle");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RARException("VehicleManager.delete: failed to delete a Vehicle: " + e);
		}
	}// delete function ends

	public Iterator<Vehicle> restore(Vehicle vehicle) 
			throws RARException
	{
		String       selectClubSql = "SELECT h.Id, h.Make, h.Model, h.Year, h.Mileage, h.Tag, "
				+ "h.LastService, h.Status, h.VehicleCondition, v.id as VehicleTypeId, v.Name as VehicleTypeName, "
				+ "r.id as RentalLocationId, r.Name as RentalLocationName, r.Address as RentalLocationAddress, "
				+ "r.Capacity FROM Vehicle h, VehicleType v, RentalLocation r "
				+ "WHERE h.RentalLocationId = r.id AND h.VehicleTypeId = v.id";

		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectClubSql );

		if( vehicle != null ) {
			if( vehicle.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and h.id = " + vehicle.getId() );
			else {

				if( vehicle.getMake() != null)
					condition.append( " and Make = '" + vehicle.getMake() + "'" );   

				if( vehicle.getModel()!= null ) {					
					condition.append( " and Model = '" + vehicle.getModel() + "'" );
				}

				if( vehicle.getYear()!= 0 ) {					
					condition.append( " and Year = '" + vehicle.getYear() + "'" );
				}

				if( vehicle.getMileage()!= 0 ) {					
					condition.append( " and Mileage = '" + vehicle.getMileage() + "'" );
				}

				if( vehicle.getRegistrationTag()!= null ) {					
					condition.append( " and Tag = '" + vehicle.getRegistrationTag() + "'" );
				}

				if( vehicle.getLastServiced()!= null ) {					
					condition.append( " and LastServiced = '" + vehicle.getLastServiced() + "'" );
				}

				if( vehicle.getStatus()!= null ) {					
					condition.append( " and Status = '" + vehicle.getStatus() + "'" );
				}

				if( vehicle.getCondition() != null ) {					
					condition.append( " and Condition = '" + vehicle.getCondition() + "'" );
				}
				
				if ( vehicle.getRentalLocation() != null)
					if (vehicle.getRentalLocation().getId() != 0)
						condition.append( " and r.id = '" + vehicle.getRentalLocation().getId() + "'" );
				
				if ( vehicle.getVehicleType() != null)
					if (vehicle.getVehicleType().getId() != 0)
						condition.append( " and v.id = '" + vehicle.getVehicleType().getId() + "'" );
				
				if( condition.length() > 0 )
                    query.append( condition );

			}
		}

		try {
			System.out.println(query);
			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
//			System.out.println(query);
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				return new VehicleIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "VehicleManager.restore: Could not restore persistent Vehicle object; Root cause: " + e );
		}

		throw new RARException( "VehicleManager.restore: Could not restore persistent Vehicle object" );
	}

	public VehicleType restoreVehicleType(Vehicle vehicle) throws RARException
	{
		String       selectClubSql = "SELECT h.Id, h.Make, h.Model, h.Year, h.Mileage, h.Tag, "
				+ "h.LastService, h.Status, h.VehicleCondition v.id as VehicleTypeId, v.Name as VehicleTypeName, "
				+ "r.id as RentalLocationId, r.Name as RentalLocationName, r.Address as RentalLocationAddress "
				+ "r.Capacity FROM Vehicle h, VehicleType v, RentalLocation r "
				+ "WHERE h.RentalLocationId = r.id AND h.VehicleTypeId = v.id";

		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectClubSql );

		if( vehicle != null ) {
			if( vehicle.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and h.id = " + vehicle.getId() );
			else {

				if( vehicle.getMake() != null)
					condition.append( " and Make = '" + vehicle.getMake() + "'" );   

				if( vehicle.getModel()!= null ) {					
					condition.append( " and Model = '" + vehicle.getModel() + "'" );
				}

				if( vehicle.getYear()!= 0 ) {					
					condition.append( " and Year = '" + vehicle.getYear() + "'" );
				}

				if( vehicle.getMileage()!= 0 ) {					
					condition.append( " and Mileage = '" + vehicle.getMileage() + "'" );
				}

				if( vehicle.getRegistrationTag()!= null ) {					
					condition.append( " and Tag = '" + vehicle.getRegistrationTag() + "'" );
				}

				if( vehicle.getLastServiced()!= null ) {					
					condition.append( " and LastServiced = '" + vehicle.getLastServiced() + "'" );
				}

				if( vehicle.getStatus()!= null ) {					
					condition.append( " and Status = '" + vehicle.getStatus() + "'" );
				}

				if( vehicle.getCondition() != null ) {					
					condition.append( " and Condition = '" + vehicle.getCondition() + "'" );
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
			throw new RARException( "VehicleManager.restore: Could not restore persistent VehicleType object; Root cause: " + e );
		}

		throw new RARException( "VehicleManager.restore: Could not restore persistent VehicleType object" );
	}

	public RentalLocation restoreRentalLocation(Vehicle vehicle) throws RARException
	{
		System.out.println("restoreRentalLocation");
		String       selectClubSql = "SELECT h.Id, h.Make, h.Model, h.Year, h.Mileage, h.Tag, "
				+ "h.LastService, h.Status, h.VehicleCondition, v.id as VehicleTypeId, v.Name as VehicleTypeName, "
				+ "r.id as RentalLocationId, r.Name as RentalLocationName, r.Address as RentalLocationAddress "
				+ "r.Capacity FROM Vehicle h, VehicleType v, RentalLocation r "
				+ "WHERE h.RentalLocationId = r.id AND h.VehicleTypeId = v.id";

		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectClubSql );

		if( vehicle != null ) {
			if( vehicle.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and h.id = " + vehicle.getId() );
			else {

				if( vehicle.getMake() != null)
					condition.append( " and Make = '" + vehicle.getMake() + "'" );   

				if( vehicle.getModel()!= null ) {					
					condition.append( " and Model = '" + vehicle.getModel() + "'" );
				}

				if( vehicle.getYear()!= 0 ) {					
					condition.append( " and Year = '" + vehicle.getYear() + "'" );
				}

				if( vehicle.getMileage()!= 0 ) {					
					condition.append( " and Mileage = '" + vehicle.getMileage() + "'" );
				}

				if( vehicle.getRegistrationTag()!= null ) {					
					condition.append( " and Tag = '" + vehicle.getRegistrationTag() + "'" );
				}

				if( vehicle.getLastServiced()!= null ) {					
					condition.append( " and LastServiced = '" + vehicle.getLastServiced() + "'" );
				}

				if( vehicle.getStatus()!= null ) {					
					condition.append( " and Status = '" + vehicle.getStatus() + "'" );
				}

				if( vehicle.getCondition() != null ) {					
					condition.append( " and Condition = '" + vehicle.getCondition() + "'" );
				}
			}
			query.append(condition);
		}

		try {
			System.out.println("this one:");
			System.out.println(query);
			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				Iterator<RentalLocation> rentalLocationIter = new RentalLocationIterator( r, objectLayer );
				if( rentalLocationIter != null && rentalLocationIter.hasNext() ) {
					return rentalLocationIter.next();
				}
				else
					return null;
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "VehicleManager.restore: Could not restore persistent Rental Location object; Root cause: " + e );
		}

		throw new RARException( "VehicleManager.restore: Could not restore persistent Rental Location object" );
	}

	public Iterator<Vehicle> restoreVehicleRentalLocation(RentalLocation rentalLocation) throws RARException
	{
		String       selectClubSql = "SELECT h.Id, h.Make, h.Model, h.Year, h.Mileage, h.Tag, "
				+ "h.LastService, h.Status, h.VehicleCondition, v.id as VehicleTypeId, v.Name as VehicleTypeName, "
				+ "r.id as RentalLocationId, r.Name as RentalLocationName, r.Address as RentalLocationAddress, "
				+ "r.Capacity FROM Vehicle h, VehicleType v, RentalLocation r "
				+ "WHERE h.RentalLocationId = r.id AND h.VehicleTypeId = v.id";

		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectClubSql );

		if( rentalLocation != null ) {
			if( rentalLocation.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and r.id = " + rentalLocation.getId() );
			else 
				throw new RARException( "VehicleManager.restoreVehicleRentalLocation: rentalLocationId is not defined.") ;

		}

		try {
			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				return new VehicleIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "VehicleManager.restoreVehicleRentalLocation: Could not restore persistent RentalLocation object; Root cause: " + e );
		}

		throw new RARException( "VehicleManager.restoreVehicleRentalLocation: Could not restore persistent RentalLocation object" );
	}

	public Iterator<Vehicle> restoreVehicleVehicleType( VehicleType vehicleType ) throws RARException
	{
		String       selectClubSql = "SELECT h.Id, h.Make, h.Model, h.Year, h.Mileage, h.Tag, "
				+ "h.LastService, h.Status, h.VehicleCondition, v.id as VehicleTypeId, v.Name as VehicleTypeName, "
				+ "r.id as RentalLocationId, r.Name as RentalLocationName, r.Address as RentalLocationAddress, "
				+ "r.Capacity FROM Vehicle h, VehicleType v, RentalLocation r "
				+ "WHERE h.RentalLocationId = r.id AND h.VehicleTypeId = v.id";

		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectClubSql );

		if( vehicleType != null ) {
			if( vehicleType.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and v.id = " + vehicleType.getId() );
			else 
				throw new RARException( "VehicleManager.restoreVehicleVehicleType: vehicleTypeId is not defined.") ;

		}

		try {

			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
//			System.out.println(query);
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				return new VehicleIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "VehicleManager.restoreVehicleVehicleType: Could not restore persistent VehicleType object; Root cause: " + e );
		}

		throw new RARException( "VehicleManager.restoreVehicleVehicleType: Could not restore persistent VehicleType object" );
	}
}
