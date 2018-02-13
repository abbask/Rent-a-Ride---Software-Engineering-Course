package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.entity.impl.UserImpl;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class UserIterator implements Iterator<User>{

	private ResultSet    rs = null;
	private ObjectLayer  objectLayer = null;
	private boolean      more = false;

	public UserIterator(ResultSet rs, ObjectLayer objectModel) throws RARException {
		this.rs = rs;
		this.objectLayer = objectModel;
		try {
			more = rs.next();
		}
		catch( Exception e ) {  // just in case...
			throw new RARException( "UserIterator: Cannot create User iterator; root cause: " + e );
		}
	}

	public boolean hasNext() 
	{ 
		return more; 
	}

	public User next() {
		User user=null;

		long id;
		String Firstname;
		String Lastname;
		String Username;
		String Password;
		String Email;
		String Address;
		UserStatus Status;
		Date CreatedDate;

		if( more ) {

			try {
				//h.Id, h.minHrs, h.MaxHrs, h.Price, h.VehicleTypeId, v.Name 
				id = rs.getLong( 1 );
				Firstname = rs.getString( 2 );
				Lastname = rs.getString( 3 );
				Username = rs.getString( 4 );
				Password = rs.getString( 5 );
				Email = rs.getString( 6 );
				Address = rs.getString( 7 );
				Status = UserStatus.valueOf(rs.getString( 8 ));
				CreatedDate = rs.getDate(9);


				more = rs.next();
			}
			catch( Exception e ) {      // just in case...
				throw new NoSuchElementException( "UserIterator: No next User object; root cause: " + e );
			}

			user = new UserImpl(Firstname, Lastname, Username, Email, Password, CreatedDate, Address,Status);            	
			user.setId( id );

			return user;
		}
		else {
			throw new NoSuchElementException( "UserIterator: No next User object" );
		}

	}

	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}
