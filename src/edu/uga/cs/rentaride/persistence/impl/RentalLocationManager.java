package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.object.ObjectLayer;

import java.sql.PreparedStatement;


public class RentalLocationManager {
	
	    private ObjectLayer objectLayer = null;
	    private Connection   conn = null;
	    
	    public RentalLocationManager( Connection conn, ObjectLayer objectLayer )
	    {
	        this.conn = conn;
	        this.objectLayer = objectLayer;
	    }
	    
	    public void save(RentalLocation rentallocation) 
	            throws RARException
	    {
	        String               insertRentalLocationSql = "insert into RentalLocation ( Name, Address, Capacity) values ( ?, ?, ? )";
	        String               updateRentalLocationSql = "update RentalLocation set Name = ?, Address = ?, Capacity = ? WHERE Id=?";
	        java.sql.PreparedStatement    stmt = null;
	        int                  inscnt;
	        long                 rentallocationId;

	 
	                 
	        try {

	            if( !rentallocation.isPersistent() )
	                stmt = (PreparedStatement) conn.prepareStatement( insertRentalLocationSql );
	            else
	                stmt = (PreparedStatement) conn.prepareStatement( updateRentalLocationSql );

	            
	            if( rentallocation.getName() != null ) // name is unique unique and non null
	                stmt.setString( 1, rentallocation.getName() );
	            else 
	                throw new RARException( "RentalLocationManager.save: can't save a RentalLocaiton: name undefined" );

	            if( rentallocation.getAddress() != null )
	                stmt.setString( 2, rentallocation.getAddress() );
	            else
	            	throw new RARException( "RentalLocationManager.save: can't save a RentalLocaiton: Address undefined" );

	            if(rentallocation.getCapacity() != 0 )
	                stmt.setInt(3, rentallocation.getCapacity() );
	            else
	            	throw new RARException( "RentalLocationManager.save: can't save a RentalLocaiton: Capacity undefined" );
	           
	            if( rentallocation.isPersistent() )
					stmt.setLong( 4, rentallocation.getId() );
//	            System.err.println(stmt);
	            inscnt = stmt.executeUpdate();

	            if( !rentallocation.isPersistent() ) {
	                if( inscnt >= 1 ) {
	                    String sql = "select last_insert_id()";
	                    if( stmt.execute( sql ) ) { // statement returned a result

	                        // retrieve the result
	                        ResultSet r = stmt.getResultSet();

	                        // we will use only the first row!
	                        //
	                        while( r.next() ) {

	                            // retrieve the last insert auto_increment value
	                        	rentallocationId = r.getLong( 1 );
	                            if( rentallocationId > 0 )
	                            	rentallocation.setId(rentallocationId); // set this person's db id (proxy object)
	                        }
	                    }
	                }
	                else
	                    throw new RARException( "RentalLocaitonManager.save: failed to save a RentalLocaiton" );
	            }
	            else {
	                if( inscnt < 1 )
	                    throw new RARException( "RentalLocaitonManager.save: failed to save a RentalLocaiton" ); 
	            }
	        }
	        catch( SQLException e ) {
	            e.printStackTrace();
	            throw new RARException( "RentalLocaitonManager.save: failed to save a RentalLocaiton: " + e );
	        }
	    }

	    public Iterator<RentalLocation> restore(RentalLocation rentalLocation) 
	            throws RARException
	    {
	        
	        String       selectRentalLocaitonSql = "select Id, Name, Address, Capacity from RentalLocation";
			Statement    stmt = null;
			StringBuffer query = new StringBuffer( 100 );
			StringBuffer condition = new StringBuffer( 100 );

			condition.setLength( 0 );

			// form the query based on the given Club object instance
			query.append( selectRentalLocaitonSql );

			if( rentalLocation != null ) {
				if( rentalLocation.getId() >= 0 ) // id is unique, so it is sufficient to get a person
					query.append( " WHERE id = " + rentalLocation.getId() );
				else {
					
					if( rentalLocation.getName() != null ){
						if( condition.length() > 0 )
	                        condition.append( " and" );
						condition.append( " Name = '" + rentalLocation.getName() + "'" );
					}

					if( rentalLocation.getAddress() != null ) {		
						if( condition.length() > 0 )
	                        condition.append( " and" );
						condition.append( " Address = '" + rentalLocation.getAddress() + "'" );
					}
					
					if( rentalLocation.getCapacity() != 0 ) {	
						if( condition.length() > 0 )
	                        condition.append( " and" );
						condition.append( " Capacity = '" + rentalLocation.getCapacity() + "'" );
					}
					
					if( condition.length() > 0 )
						query.append( " WHERE " );
	                    query.append( condition );
				}
			}
			
			

			try {
				stmt = conn.createStatement();

				// retrieve the persistent Person object
				//
				if( stmt.execute( query.toString() ) ) { // statement returned a result
					ResultSet r = stmt.getResultSet();
					return new RentalLocationIterator(r, objectLayer);
				}
				
			}
			catch( Exception e ) {      // just in case...
				throw new RARException( "RentalLocationManager.restore: Could not restore persistent RentalLocation object; Root cause: " + e );
			}

			throw new RARException( "RentalLocationManager.restore: Could not restore persistent RentalLocation object" );
	    }
	    
	    
	    public Iterator<Vehicle> restoreLocatedAt(RentalLocation rentallocation) 
	            throws RARException
	    {
	        
	        String       selectRentalLocaitonSql = "SELECT v.Id, v.Make, v.Model, v.Year, v.Mileage, v.Tag, v.LastService, v.Status, v.VehicleCondition"
					+",vt.Id,vt.Name"
					+ ",rl.id as RentalLocaiotnId, rl.Name as RentalLocationName, rl.Address as RentalLocationAddress, rl.Capacity "
					+ "FROM RentalLocation rl, "
					+ "Vehicle v, VehicleType vt  WHERE"
					+ " v.RentalLocationId = rl.id "
					+ " AND v.VehicleTypeId = vt.id";
	        
	        Statement    stmt = null;
	        StringBuffer query = new StringBuffer( 100 );
	        StringBuffer condition = new StringBuffer( 100 );
	        
	        condition.setLength( 0 );
	        
	        // form the query based on the given Club object instance
	        query.append( selectRentalLocaitonSql );
	        if( rentallocation != null ) {
	            if( rentallocation.getId() >= 0 ) // id is unique, so it is sufficient to get a rentallocation
	                query.append( " AND rl.Id = " + rentallocation.getId() );
	            else if ( rentallocation.getName() != null ) // name is unique, so it is sufficient to get a rentallocation
	                query.append( " AND rl.Address = '" + rentallocation.getAddress() + "'" );
	            else {

	                if( rentallocation.getAddress() != null )
	                {	condition.append("and");
	                    condition.append( "rl.Name = '" + rentallocation.getName() + "'" );   
	                }
	                if( (Object)rentallocation.getCapacity() != null )
	                {	condition.append("and");
	                	condition.append( " and rl.Capacity =  "+ rentallocation.getCapacity()  );   
	                }
	                
	                
	            }
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
	            throw new RARException( "RentalLocationManager.restore: Could not restore persistent Vehicle object; Root cause: " + e );
	        }

	        throw new RARException( "RentalLocationManager.restore: Could not restore persistent Vehicle object" );
	    }

	    
	    public Iterator<Reservation> restoreHasLocation(RentalLocation rentallocation) 
	            throws RARException
	    {
	        
	        String          selectRentalLocationSql = "SELECT s.Id,s.Pickup, s.Length,s.VehicleTypeId,s.RentalLocationId,s.CustomerId"
					+ ",rl.id as RentalLocaiotnId, rl.Name as RentalLocationName, rl.Address as RentalLocationAddress, rl.Capacity"
					+",vt.Id,vt.Name"
					+",c.Id,c.Firstname,c.Lastname,c.Username,c.Password,c.Email,c.Address,c.Status,c.CreatedDate,c.MemberUntil,c.LicState,c.LicNumber,c.CCNumber,c.CCExpiry,c.IsAdmin"
					+"FROM Reservation s, RentalLocation rl, VehicleType vt, Customer c"
				    +"WHERE s.VehicleTypeId = vt.Id AND s.CustomerId = c.Id AND s.RentalLocationId = rl.Id";
			   Statement    stmt = null;
	        StringBuffer query = new StringBuffer( 100 );
	        StringBuffer condition = new StringBuffer( 100 );
	        
	        condition.setLength( 0 );
	        
	        // form the query based on the given Club object instance
	        query.append( selectRentalLocationSql );
	        if( rentallocation != null ) {
	            if( rentallocation.getId() >= 0 ) // id is unique, so it is sufficient to get a rentallocation
	                query.append( " rl.Id = " + rentallocation.getId() );
	            else if ( rentallocation.getName() != null ) // name is unique, so it is sufficient to get a rentallocation
	                query.append( " rl.Address = '" + rentallocation.getAddress() + "'" );
	            else {

	                if( rentallocation.getAddress() != null )
	                {	condition.append("and");
	                    condition.append( "rl.Name = '" + rentallocation.getName() + "'" );   
	                }
	                if( (Object)rentallocation.getCapacity() != null )
	                {	condition.append("and");
	                	condition.append( " and rl.Capacity = " + rentallocation.getCapacity() );   
	                }
	                if( condition.length() > 0 ) {
	                    query.append(  " where " );
	                    query.append( condition );
	                }
	                
	            }
	        }
	        
	        try {

	            stmt = conn.createStatement();

	            // retrieve the persistent Person object
	            //
	            if( stmt.execute( query.toString() ) ) { // statement returned a result
	                ResultSet r = stmt.getResultSet();
	                return new ReservationIterator( r, objectLayer );
	            }
	        }
	        catch( Exception e ) {      // just in case...
	            throw new RARException( "RentalLocationManager.restore: Could not restore persistent Vehicle object; Root cause: " + e );
	        }

	        throw new RARException( "RentalLocationManager.restore: Could not restore persistent Vehicle object" );
	    }
	    
	    
	    

	  
	    public void delete(RentalLocation rentallocation) 
	            throws RARException
	    {
	        String               deleteRentalLocaitonSql = "delete from RentalLocation where id = ?";              
	        PreparedStatement    stmt = null;
	        int                  inscnt;
	             
	        if( !rentallocation.isPersistent() ) // is the Club object persistent?  If not, nothing to actually delete
	            return;
	        
	        try {
	            stmt = (PreparedStatement) conn.prepareStatement( deleteRentalLocaitonSql );         
	            stmt.setLong( 1, rentallocation.getId() );
	            inscnt = stmt.executeUpdate();          
	            if( inscnt == 1 ) {
	                return;
	            }
	            else
	                throw new RARException( "RenatlLocationManager.delete: failed to delete a RenatlLocation" );
	        }
	        catch( SQLException e ) {
	            e.printStackTrace();
	            throw new RARException( "RenatlLocationManager.delete: failed to delete a RenatlLocation: " + e );        }
	    }
	}

