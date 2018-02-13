package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class CustomerIterator implements Iterator<Customer>{

	private ResultSet    rs = null;
	private ObjectLayer  objectLayer = null;
	private boolean      more = false;

	public CustomerIterator(ResultSet rs, ObjectLayer objectModel) throws RARException {
		this.rs = rs;
		this.objectLayer = objectModel;
		try {
			more = rs.next();
		}
		catch( Exception e ) {  // just in case...
			throw new RARException( "CustomerIterator: Cannot create Customer iterator; root cause: " + e );
		}
	}

	public boolean hasNext() 
	{ 
		return more; 
	}

	public Customer next() {
		Customer customer=null;
		long id;
		String Firstname;
		String Lastname;
		String Username;
		String Password;
		String Email;
		String Address;
		String Status;
		Date createdDate;
		Date MemberUntil;
		String LicState;
		String LicNumber;
		String CCNumber;
		Date CCExpiry;
		int IsAdmin;

		if( more ) {

			try {
				//Id, Firstname, Lastname, Username, Password, Email, Address, Status, CreatedDate, MemberUntil, 
				//LicState, LicNumber, 
				//CCNumber, CCExpiry FROM Customer Where IsAdmin=0";
				id = rs.getLong( 1 );
				Firstname = rs.getString( 2 );
				Lastname = rs.getString( 3 );
				Username = rs.getString( 4 );
				Password = rs.getString( 5 );
				Email = rs.getString( 6 );
				Address = rs.getString( 7 );
				Status = rs.getString( 8 );
				createdDate = rs.getDate(9);
				MemberUntil = rs.getDate( 10 );
				LicState = rs.getString( 11 );
				LicNumber = rs.getString( 12 );
				CCNumber = rs.getString( 13 );
				CCExpiry = rs.getDate( 14 );



				more = rs.next();
			}
			catch( Exception e ) {      // just in case...
				throw new NoSuchElementException( "CustomerIterator: No next Customer object; root cause: " + e );
			}


			customer = objectLayer.createCustomer(Firstname, Lastname, Username, Email,  
					Password, createdDate,MemberUntil,  LicState, LicNumber, Address,CCNumber, CCExpiry);      

			customer.setId( id );
			//club.setFounderId( founderId );


			return customer;
		}
		else {
			throw new NoSuchElementException( "CustomerIterator: No next Customer object" );
		}

	}

	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}
