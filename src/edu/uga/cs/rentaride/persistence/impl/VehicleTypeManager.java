package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class VehicleTypeManager {
	

	private ObjectLayer objectLayer = null;
	private Connection   conn = null;

	public VehicleTypeManager(Connection conn, ObjectLayer objectLayer) {
		this.conn = conn;
		this.objectLayer = objectLayer;

	}

	public void save (VehicleType VehicleType) throws RARException{

		String               insertVehicleTypeSql = "insert into VehicleType ( Name) values ( ? )";
		String               updateVehicleTypePriceSql = "update VehicleType set Name = ? where Id = ?";
		PreparedStatement    stmt = null;
		int                  inscnt;
		long                 VehicleTypeId;

		try {

			if( !VehicleType.isPersistent() )
				stmt = (PreparedStatement) conn.prepareStatement( insertVehicleTypeSql );
			else
				stmt = (PreparedStatement) conn.prepareStatement( updateVehicleTypePriceSql );

			if( VehicleType.getType() != "" ) // name is unique unique and non null
				stmt.setString(1, VehicleType.getType() );
			else 
				throw new RARException( "VehicleTypeManager.save: can't save a VehicleType: minHours is undefined" );
			if( VehicleType.isPersistent() )
				stmt.setLong( 2, VehicleType.getId() );
//			System.out.println(stmt);
			inscnt = stmt.executeUpdate();

			if( !VehicleType.isPersistent() ) {
				if( inscnt >= 1 ) {
					String sql = "select last_insert_id()";
					if( stmt.execute( sql ) ) { // statement returned a result

						// retrieve the result
						ResultSet r = stmt.getResultSet();

						// we will use only the first row!
						//
						while( r.next() ) {

							// retrieve the last insert auto_increment value
							VehicleTypeId = r.getLong( 1 );
							if( VehicleTypeId > 0 )
								VehicleType.setId( VehicleTypeId ); // set this person's db id (proxy object)
						}
					}
				}
				else
					throw new RARException( "VehicleTypeManager.save: failed to save a VehicleType" );
			}
			else {
				if( inscnt < 1 )
					throw new RARException( "VehicleTypeManager.save: failed to save a VehicleType" ); 
			}
		}
		catch( SQLException e ) {
			e.printStackTrace();
			throw new RARException( "VehicleTypeManager.save: failed to save a VehicleType: " + e );
		}


	}// save function ends

	public void delete(VehicleType VehicleType)
			throws RARException {
		String deleteVehicleTypeSql = "delete from VehicleType where id = ?";
		PreparedStatement stmt = null;
		int inscnt;

		if (!VehicleType.isPersistent()) // is the VehicleType persistent?  If not, nothing to actually delete
		{
			return;
		}

		try {
			stmt = (PreparedStatement) conn.prepareStatement(deleteVehicleTypeSql);
			stmt.setLong(1, VehicleType.getId());
			inscnt = stmt.executeUpdate();
			if (inscnt == 1) {
				return;
			} else {
				throw new RARException("VehicleTypeManager.delete: failed to delete a VehicleType");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RARException("VehicleTypeManager.delete: failed to delete a VehicleType: " + e);
		}
	}// delete function ends
	
//**********************************************************************************

	
	public Iterator<VehicleType> restore(VehicleType vehicletype) 
			throws RARException
	{
		//String       selectClubSql = "select id, name, address, established, founderid from club";
		String       selectClubSql = "SELECT Id, Name FROM VehicleType";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectClubSql );

		if( vehicletype != null ) {
			if( vehicletype.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				
				query.append( " WHERE Id = " + vehicletype.getId() );
			else {

				if( vehicletype.getType() != null )
					
					condition.append( " Name = '" + vehicletype.getType() + "'" );   
				
				if( condition.length() > 0 ){
					query.append(" WHERE ");
                    query.append( condition );
				}
				
				query.append( " ORDER BY Name" );
			}
		}
		System.out.println(query);
		try {

			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				return new VehicleTypeIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "VehicleType.restore: Could not restore persistent Vehicle Type object; Root cause: " + e );
		}

		throw new RARException( "VehicleType.restore: Could not restore persistent Vehicle Type object" );
	}


}



