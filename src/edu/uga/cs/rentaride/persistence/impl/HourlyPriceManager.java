package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class HourlyPriceManager{

	private ObjectLayer objectLayer = null;
	private Connection   conn = null;

	public HourlyPriceManager(Connection conn, ObjectLayer objectLayer) {
		this.conn = conn;
		this.objectLayer = objectLayer;

	}

	public void save (HourlyPrice hourlyPrice) throws RARException{

		String               insertHourlyPriceSql = "insert into HourlyPrice ( MinHrs, MaxHrs, Price, VehicleTypeId ) values ( ?, ?, ?, ? )";
		String               updateHourlyPriceSql = "update HourlyPrice set MinHrs = ?, MaxHrs = ?, Price = ?, VehicleTypeId = ? where Id = ?";
		PreparedStatement    stmt = null;
		int                  inscnt;
		long                 hourlyPriceId;

		try {

			if( !hourlyPrice.isPersistent() )
				stmt = (PreparedStatement) conn.prepareStatement( insertHourlyPriceSql );
			else
				stmt = (PreparedStatement) conn.prepareStatement( updateHourlyPriceSql );

			if( hourlyPrice.getMinHours() != 0 ) // name is unique unique and non null
				stmt.setInt(1, hourlyPrice.getMinHours() );
			else 
				throw new RARException( "HourlyPriceManager.save: can't save a HourlyPrice: minHours is undefined" );

			if( hourlyPrice.getMaxHours() != 0 )
				stmt.setInt( 2, hourlyPrice.getMaxHours() );
			else
				throw new RARException( "HourlyPriceManager.save: can't save a HourlyPrice: maxHours is undefined" );

			if( hourlyPrice.getPrice() != 0 ) {
				stmt.setInt(3, hourlyPrice.getPrice());
			}
			else
				throw new RARException( "HourlyPriceManager.save: can't save a HourlyPrice: price is undefined" );

			if( hourlyPrice.getVehicleType() != null && hourlyPrice.getVehicleType().isPersistent() )
				stmt.setLong(4, hourlyPrice.getVehicleType().getId());
			else 
				throw new RARException( "HourlyPriceManager.save: can't save a HourlyPrice: vehicleType is not set or not persistent" );

			if( hourlyPrice.isPersistent() )
				stmt.setLong( 5, hourlyPrice.getId() );

			
			inscnt = stmt.executeUpdate();

			if( !hourlyPrice.isPersistent() ) {
				if( inscnt >= 1 ) {
					String sql = "select last_insert_id()";
					if( stmt.execute( sql ) ) { // statement returned a result

						// retrieve the result
						ResultSet r = stmt.getResultSet();

						// we will use only the first row!
						//
						while( r.next() ) {

							// retrieve the last insert auto_increment value
							hourlyPriceId = r.getLong( 1 );
							if( hourlyPriceId > 0 )
								hourlyPrice.setId( hourlyPriceId ); // set this person's db id (proxy object)
						}
					}
				}
				else
					throw new RARException( "HourlyPriceManager.save: failed to save a HourlyPrice" );
			}
			else {
				if( inscnt < 1 )
					throw new RARException( "HourlyPriceManager.save: failed to save a HourlyPrice" ); 
			}
		}
		catch( SQLException e ) {
			e.printStackTrace();
			throw new RARException( "HourlyPriceManager.save: failed to save a HourlyPrice: " + e );
		}


	}// save function ends

	public void delete(HourlyPrice hourlyPrice)
			throws RARException {
		String deleteHourlyPriceSql = "delete from HourlyPrice where id = ?";
		PreparedStatement stmt = null;
		int inscnt;

		if (!hourlyPrice.isPersistent()) // is the HourlyPrice persistent?  If not, nothing to actually delete
		{
			return;
		}

		try {
			stmt = (PreparedStatement) conn.prepareStatement(deleteHourlyPriceSql);
			stmt.setLong(1, hourlyPrice.getId());
			inscnt = stmt.executeUpdate();
			if (inscnt == 1) {
				return;
			} else {
				throw new RARException("HourlyPriceManager.delete: failed to delete a HourlyPrice");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RARException("HourlyPriceManager.delete: failed to delete a HourlyPrice: " + e);
		}
	}// delete function ends

	public VehicleType restoreVehicleType(HourlyPrice hourlyPrice) 
			throws RARException
	{
		String       selectHourlyPriceSql = "select v.id, v.name from VehicleType v, HourlyPrice h where v.id = h.VehicleTypeId";              
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Person object instance
		query.append( selectHourlyPriceSql );

		if( hourlyPrice != null ) {
			if( hourlyPrice.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and h.id = " + hourlyPrice.getId() );           
			else {

				if( hourlyPrice.getMinHours() != 0 )
					condition.append( " and h.MinHrs = '" + hourlyPrice.getMinHours() + "'" );   

				if( hourlyPrice.getMaxHours() != 0  ) {
					condition.append( " and h.MaxHrs  = '" + hourlyPrice.getMaxHours() + "'" );
				}

				if( hourlyPrice.getPrice() != 0  ) {
					condition.append( " and h.Price  = '" + hourlyPrice.getPrice() + "'" );
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
			throw new RARException( "HourlyPriceManager.restoreVehicleType: Could not restore persistent VehicleTypeIteratorVehicle Type object; Root cause: " + e );
		}

		// if we reach this point, it's an error
		throw new RARException( "HourlyPriceManager.restoreVehicleType: Could not restore persistent VehicleTypeIteratorVehicle Type object" );

	}///function  ends


	public Iterator<HourlyPrice> restore(HourlyPrice hourlyPrice) 
			throws RARException
	{
		//String       selectClubSql = "select id, name, address, established, founderid from club";
		String       selectHourlyPriceSql = "SELECT h.Id, h.minHrs, h.MaxHrs, h.Price, h.VehicleTypeId, v.Name FROM HourlyPrice h, VehicleType v WHERE v.id=h.VehicleTypeId";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectHourlyPriceSql );

		if( hourlyPrice != null ) {
			if( hourlyPrice.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and h.id = " + hourlyPrice.getId() );
			else {

				if( hourlyPrice.getMinHours() != 0 )
					condition.append( " and MinHrs = '" + hourlyPrice.getMinHours() + "'" );   

				if( hourlyPrice.getMaxHours() != 0 ) {					
					condition.append( " and MaxHrs = '" + hourlyPrice.getMaxHours() + "'" );
				}
				
				if( hourlyPrice.getPrice() != 0 ) {					
					condition.append( " and Price = '" + hourlyPrice.getPrice() + "'" );
				}
				
				if( hourlyPrice.getVehicleType() != null) {					
					condition.append( " and h.VehicleTypeId = '" + hourlyPrice.getVehicleType().getId() + "'" );
				}
				
				if( condition.length() > 0 )
                    query.append( condition );
				
				query.append( " ORDER BY h.id " );
			}
		}

		try {
			stmt = conn.createStatement();
			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				return new HourlyPriceIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "HourlyPriceManager.restore: Could not restore persistent HourlyPrice object; Root cause: " + e );
		}

		throw new RARException( "HourlyPriceManager.restore: Could not restore persistent HourlyPrice object" );
	}
	
	public Iterator<HourlyPrice> restoreVehicleTypeHourlyPrice(VehicleType vehicleType) 
			throws RARException
	{
		String       selectHourlyPriceSql = "SELECT h.Id, h.minHrs, h.MaxHrs, h.Price, h.VehicleTypeId, v.Name FROM HourlyPrice h, VehicleType v WHERE v.id=h.VehicleTypeId";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectHourlyPriceSql );

		if( vehicleType != null ) {
			if( vehicleType.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and h.VehicleTypeId = " + vehicleType.getId() );
			else 
				throw new RARException( "HourlyPriceManager.restoreVehicleTypeHourlyPrice: vehicleTypeId is not defined.") ;
		}

		try {

			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				return new HourlyPriceIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "HourlyPriceManager.restoreVehicleTypeHourlyPrice: Could not restore persistent VehicleType object; Root cause: " + e );
		}

		throw new RARException( "HourlyPriceManager.restoreVehicleTypeHourlyPrice: Could not restore persistent VehicleType object" );
	}
	
}
