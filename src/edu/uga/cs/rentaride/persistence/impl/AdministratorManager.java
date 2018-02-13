package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class AdministratorManager {

	private ObjectLayer objectLayer = null;
	private Connection   conn = null;

	public AdministratorManager(Connection conn, ObjectLayer objectLayer) {
		this.conn = conn;
		this.objectLayer = objectLayer;

	}

	public void save (Administrator administrator) throws RARException{

		String               insertAdministratorSql = "insert into Customer ( Firstname, Lastname, Username, Password, Email, Address, Status, CreatedDate, IsAdmin) values ( ?, ?, ?, ?, ?, ?, ?, ?, 1 )";
		String               updateAdministratorSql = "update Customer set Firstname = ?, Lastname = ?, Username = ?, Password = ?, Email = ?, Address = ?, Status = ?, CreatedDate = ? where Id = ?";
		//		String               insertAdministratorSql = "insert into Customer ( Fisrtname, Lastname, Username, Password, Email, Address, Status, MemeberUntil, LicState, LicNumber, CCNumber, CCExpiry, IsAdmin) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1 )";
		//		String               updateAdministratorSql = "update Customer set Fisrtname = ?, Lastname = ?, Username = ?, Password = ?, Email = ?, Address = ?, Status = ?, MemeberUntil = ?, LicState = ?, LicNumber = ?, CCNumber = ? , CCExpiry = ? where Id = ?";			
		PreparedStatement    stmt = null;
		int                  inscnt;
		long                 administratorId;

		try {

			if( !administrator.isPersistent() )
				stmt = (PreparedStatement) conn.prepareStatement( insertAdministratorSql );
			else
				stmt = (PreparedStatement) conn.prepareStatement( updateAdministratorSql );

			if( administrator.getFirstName() != null )
				stmt.setString(1, administrator.getFirstName() );
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: First Name is undefined" );

			if( administrator.getLastName() != null ) 
				stmt.setString(2, administrator.getLastName() );
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: Last Name is undefined" );


			if( administrator.getUserName()!= null )  
				stmt.setString(3, administrator.getUserName() );
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: Username is undefined" );

			if( administrator.getPassword() != null ) 
				stmt.setString(4, administrator.getPassword() );
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: Password is undefined" );

			if( administrator.getEmailAddress() != null )
				stmt.setString(5, administrator.getEmailAddress() );
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: Email Address is undefined" );

//			if( administrator.getResidenceAddress() != null )
				stmt.setString(6, administrator.getResidenceAddress() );
//			else 
//				throw new RARException( "AdministratorManager.save: can't save a Administator: Residential Address is undefined" );

//			if( administrator.getUserStatus() != null )
//				stmt.setString(7, administrator.getUserStatus().toString() );
//			else 
				stmt.setString(7, UserStatus.ACTIVE.toString());
//				throw new RARException( "AdministratorManager.save: can't save a Administator: User Status is undefined" );

			if( administrator.getCreatedDate() != null ){
				java.util.Date jDate =administrator.getCreatedDate();
				java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
				stmt.setDate( 8,  sDate );
			}
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: Created Date is undefined" );		

			if( administrator.isPersistent() )
				stmt.setLong( 9, administrator.getId() );
			
//			System.out.println(stmt);
			
			inscnt = stmt.executeUpdate();
			
			if( !administrator.isPersistent() ) {
				if( inscnt >= 1 ) {
					String sql = "select last_insert_id()";
					if( stmt.execute( sql ) ) { // statement returned a result

						// retrieve the result
						ResultSet r = stmt.getResultSet();

						// we will use only the first row!
						//
						while( r.next() ) {

							// retrieve the last insert auto_increment value
							administratorId = r.getLong( 1 );
							if( administratorId > 0 )
								administrator.setId( administratorId ); // set this person's db id (proxy object)
						}
					}
				}
				else
					throw new RARException( "AdministratorManager.save: failed to save a Administrator" );
			}
			else {
				if( inscnt < 1 )
					throw new RARException( "AdministratorManager.save: failed to save a Administrator" ); 
			}
		}
		catch( SQLException e ) {
			e.printStackTrace();
			throw new RARException( "AdministratorManager.save: failed to save a Administrator: " + e );
		}

	}

	public void delete(Administrator administrator)
			throws RARException {
		String deleteAdministratorSql = "delete from Customer where id = ?";
		PreparedStatement stmt = null;
		int inscnt;

		if (!administrator.isPersistent()) 
		{
			return;
		}

		try {
			stmt = (PreparedStatement) conn.prepareStatement(deleteAdministratorSql);
			stmt.setLong(1, administrator.getId());
			inscnt = stmt.executeUpdate();
			if (inscnt == 1) {
				return;
			} else {
				throw new RARException("AdministratorManager.delete: failed to delete a Administrator");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RARException("AdministratorManager.delete: failed to delete a Administrator: " + e);
		}
	}// delete function ends

	public Iterator<Administrator> restore(Administrator administrator) 
			throws RARException
	{		
		String       selectAdministratorSql = "SELECT id, Firstname, Lastname, Username, Password, Email, Status, CreatedDate FROM Customer Where IsAdmin=1";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectAdministratorSql );

		if( administrator != null ) {
			if( administrator.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and id = " + administrator.getId() );
			else {

				if(administrator.getFirstName() != null)
					condition.append(" and Firstname = '" + administrator.getFirstName() + "'");

				if(administrator.getLastName() != null)
					condition.append(" and Lastname = '" + administrator.getLastName() + "'");

				if(administrator.getUserName() != null)
					condition.append(" and Username = '" + administrator.getUserName() + "'");

				if(administrator.getPassword() != null)
					condition.append(" and Password = '" + administrator.getPassword() + "'");
				
				if(administrator.getEmailAddress() != null)
					condition.append(" and Email = '" + administrator.getEmailAddress() + "'");

				if(administrator.getResidenceAddress() != null)
					condition.append(" and Address = '" + administrator.getResidenceAddress() + "'");				

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
				return new AdministratorIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "AdministratorManager.restore: Could not restore persistent Administrator object; Root cause: " + e );
		}

		throw new RARException( "AdministratorManager.restore: Could not restore persistent Administrator object" );
	}

}
