package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class AdministratorIterator implements Iterator<Administrator> {
	
	private ResultSet    rs = null;
	private ObjectLayer  objectLayer = null;
	private boolean      more = false;

	public AdministratorIterator(ResultSet rs, ObjectLayer objectModel) throws RARException {
		this.rs = rs;
		this.objectLayer = objectModel;
		try {
			more = rs.next();
		}
		catch( Exception e ) {  // just in case...
			throw new RARException( "AdministratorIterator: Cannot create Administrator iterator; root cause: " + e );
		}
	}
	
	public boolean hasNext() 
	{ 
		return more; 
	}

	public Administrator next() {
		Administrator administrator = null;
		long id;
		String firstName;
		String lastName;
		String userName;
		String emailAddress;
		String password;
		Date createdDate;
		UserStatus userStatus;
				
		if( more ) {

			try {
				//SELECT id, Firstname, Lastname, Username, Password, Email, Status, CreatedDate 							
				id = rs.getLong( 1 );
				firstName = rs.getString( 2 );
				lastName = rs.getString( 3 );
				userName = rs.getString( 4 );
				password = rs.getString( 5 );
				emailAddress = rs.getString( 6 );
				
				userStatus = UserStatus.valueOf(rs.getString(7)) ;
				createdDate = rs.getDate(8);
				
																			

				more = rs.next();
				
				
			}
			catch( Exception e ) {      // just in case...
				throw new NoSuchElementException( "AdministratorIterator: No next Administrator object; root cause: " + e );
			}
			
			administrator = objectLayer.createAdministrator(firstName, lastName, userName, emailAddress, password, createdDate);
			administrator.setId(id);
				


			return administrator;
		}
		else {
			throw new NoSuchElementException( "AdministratorIterator: No next Administrator object" );
		}

	}
	
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

}
