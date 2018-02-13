package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class UserManager{

	private ObjectLayer objectLayer = null;
	private Connection   conn = null;

	public UserManager(Connection conn, ObjectLayer objectLayer) {
		this.conn = conn;
		this.objectLayer = objectLayer;

	}

	public void save (User user) throws RARException{

		String               insertUserSql = "insert into User ( Firstname,Lastname, Username, Password, Email, Address, Status, CreatedDate) values ( ?, ?, ?, ? ,? ,?, ?, ?)";
		String               updateUserSql = "update User set Firstname = ?, Lastname = ?, Username = ?, Password = ?, Email=?, Address=?, Status=?, CreatedDate = ? where Id = ?";
		PreparedStatement    stmt = null;
		int                  inscnt;
		long                 UserId;

		try {

			if( !user.isPersistent() )
				stmt = (PreparedStatement) conn.prepareStatement( insertUserSql );
			else
				stmt = (PreparedStatement) conn.prepareStatement( updateUserSql );

			if( user.getFirstName() != null ) // name is unique unique and non null
				stmt.setString(1, user.getFirstName() );
			else 
				throw new RARException( "UserManager.save: can't save a User: FirstName is undefined" );

			if( user.getLastName() != null )
				stmt.setString( 2, user.getLastName() );
			else
				throw new RARException( "UserManager.save: can't save a User: LastName is undefined" );

			if( user.getUserName() != null ) {
				stmt.setString(3, user.getUserName());
			}
			else
				throw new RARException( "UserManager.save: can't save a User: UserName is undefined" );

			if( user.getEmailAddress() != null ) {
				stmt.setString(4, user.getEmailAddress());
			}
			else
				throw new RARException( "UserManager.save: can't save a User: EmailAddress is undefined" );

			if( user.getPassword() != null ) {
				stmt.setString(5, user.getPassword());
			}
			else
				throw new RARException( "UserManager.save: can't save a User: Password is undefined" );

			if( user.getCreatedDate() != null ) {
				java.util.Date jDate = user.getCreatedDate();
				java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
				stmt.setDate( 6,  sDate );	
			}
			else
				throw new RARException( "UserManager.save: can't save a User: CreatedDate is undefined" );

			if( user.getResidenceAddress() != "" ) {
				stmt.setString(7, user.getResidenceAddress());
			}
			else
				throw new RARException( "UserManager.save: can't save a User: ResidenceAddress is undefined" );

			if( user.getUserStatus() != null ) {
				stmt.setString(8, user.getUserStatus().toString());
			}
			else
				throw new RARException( "UserManager.save: can't save a User: UserStatus is undefined" );


			if( user.isPersistent() )
				stmt.setLong( 9, user.getId() );

			inscnt = stmt.executeUpdate();

			if( !user.isPersistent() ) {
				if( inscnt >= 1 ) {
					String sql = "select last_insert_id()";
					if( stmt.execute( sql ) ) { // statement returned a result

						// retrieve the result
						ResultSet r = stmt.getResultSet();

						// we will use only the first row!
						//
						while( r.next() ) {

							// retrieve the last insert auto_increment value
							UserId = r.getLong( 1 );
							if( UserId > 0 )
								user.setId( UserId ); // set this person's db id (proxy object)
						}
					}
				}
				else
					throw new RARException( "UserManager.save: failed to save a User" );
			}
			else {
				if( inscnt < 1 )
					throw new RARException( "UserManager.save: failed to save a User" ); 
			}
		}
		catch( SQLException e ) {
			e.printStackTrace();
			throw new RARException( "UserManager.save: failed to save a User: " + e );
		}


	}// save function ends

	public void delete(User User)
			throws RARException {
		String deleteUserSql = "delete from User where id = ?";
		PreparedStatement stmt = null;
		int inscnt;

		if (!User.isPersistent()) // is the User persistent?  If not, nothing to actually delete
		{
			return;
		}

		try {
			stmt = (PreparedStatement) conn.prepareStatement(deleteUserSql);
			stmt.setLong(1, User.getId());
			inscnt = stmt.executeUpdate();
			if (inscnt == 1) {
				return;
			} else {
				throw new RARException("UserManager.delete: failed to delete a User");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RARException("UserManager.delete: failed to delete a User: " + e);
		}
	}// delete function ends



	public Iterator<User> restore(User user) 
			throws RARException
	{
		String       selectUserSql = "SELECT u.Id, u.Firstname, u.Lastname, u.Username, u.Password, u.Email, u.Address, u.Status, u.CreatedDate FROM User u";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectUserSql );

		if( user != null ) {
			if( user.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " u.id = " + user.getId() );
			else {
				boolean  andOccured = false;
				if( user.getFirstName() != null )
					if (andOccured == true)  condition.append( " AND" ); 
					condition.append( " FirstName = '" + user.getFirstName() + "'" );
					andOccured = true;

				if( user.getLastName() != null ) {		
					if (andOccured == true)  condition.append( " AND" ); 
					condition.append( " LastName = '" + user.getLastName() + "'" );
					andOccured = true;
				}
				if( user.getUserName() != null ) {	
					if (andOccured == true)  condition.append( " AND" ); 
					condition.append( " UserName = '" + user.getUserName() + "'" );
					andOccured = true;
				}
				if( user.getEmailAddress() != null ) {
					if (andOccured == true)  condition.append( " AND" ); 
					condition.append( " EmailAddress = '" + user.getEmailAddress() + "'" );
					andOccured = true;
				}
				if( user.getPassword() != null ) {	
					if (andOccured == true)  condition.append( " AND" ); 
					condition.append( " Password = '" + user.getPassword() + "'" );
					andOccured = true;
				}
				if( user.getCreatedDate() != null ) {
					if (andOccured == true)  condition.append( " AND" ); 
					condition.append( " CreatedDate = '" + user.getCreatedDate() + "'" );
					andOccured = true;
				}
				if( user.getResidenceAddress() != null ) {
					if (andOccured == true)  condition.append( " AND" ); 
					condition.append( " ResidenceAddress = '" + user.getResidenceAddress() + "'" );
					andOccured = true;
				}
				if( user.getUserStatus() != null ) {
					if (andOccured == true)  condition.append( " AND" ); 
					condition.append( " UserStatus = '" + user.getUserStatus() + "'" );
					andOccured = true;
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
				return new UserIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "UserManager.restore: Could not restore persistent User object; Root cause: " + e );
		}

		throw new RARException( "UserManager.restore: Could not restore persistent User object" );
	}
	
}
