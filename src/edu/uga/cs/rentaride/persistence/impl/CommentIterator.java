package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class CommentIterator implements Iterator<Comment> {

	private ResultSet    rs = null;
	private ObjectLayer  objectLayer = null;
	private boolean      more = false;

	public CommentIterator(ResultSet rs, ObjectLayer objectModel) throws RARException {
		this.rs = rs;
		this.objectLayer = objectModel;
		try {
			more = rs.next();
		}
		catch( Exception e ) {  // just in case...
			throw new RARException( "CommentIterator: Cannot create Comment iterator; root cause: " + e );
		}
	}

	public boolean hasNext() 
	{ 
		return more; 
	}

	public Comment next() {

		Comment comment=null;
		long id;
		String text;
		Date date;
		
		Customer customer = null;
		Long customerId;
		String firstName;
		String lastName;
		String username;
		String password;
		String email;
		String address;
		String status;
		Date memberUntil;
		String licNumber;
		String licState;
		String ccNumber;
		Date ccExpiry;
		int isAdmin;
		
		Rental rental;
		Long rentalId;
		Date pickup;
		Date returnDate;
		int charges;
		
		///what about other linked here

		if( more ) {

			try {
				//c.id, c.Text, c.Date, c.CustomerId, t.FirstName, t.LastName, t.Username, t.Password, t.Email, 
				//t.Address, t.Status, t.MemberUntil, t.LicNumber, 
				//t.LicState, t.CCNumber, t.CCExpiry, t.RentalId, t.IsAdmin, r.Pickup, r.Return, r.Charges, r.Condition
				
				id = rs.getLong( 1 );
				text = rs.getString( 2 );
				date = rs.getDate( 3 );
				
				customerId = rs.getLong( 4 );
				firstName = rs.getString( 5 );
				lastName = rs.getString(6);
				username = rs.getString(7);
				password = rs.getString(8);
				email = rs.getString(9);
				address = rs.getString(10);
				status = rs.getString(11);
				memberUntil = rs.getDate(12);
				licNumber = rs.getString(13);
				licState = rs.getString(14);
				ccNumber = rs.getString(15);
				ccExpiry = rs.getDate(16);
				isAdmin = rs.getInt(17);
				
				rentalId = rs.getLong(18);
				pickup = rs.getDate(19);
				returnDate = rs.getDate(20);
				charges = rs.getInt(21);
											

				more = rs.next();
			}
			catch( Exception e ) {      // just in case...
				throw new NoSuchElementException( "CommentIterator: No next Comment object; root cause: " + e );
			}
			
			//									firstName,lastName,userName,emailAddress,password, createDate, membershipExpiration, licenseState, licenseNumber, residenceAddress, String cardNumber, Date cardExpiration );
			customer = objectLayer.createCustomer(firstName, lastName, username, email, password, memberUntil, memberUntil, licState ,licNumber, address, ccNumber, ccExpiry);
			customer.setId(customerId);
			
			rental = objectLayer.createRental();
			rental.setId(rentalId);


			try {
				
				comment = objectLayer.createComment(text, rental, customer);           	              
				comment.setId( id );
				//club.setFounderId( founderId );
			}
			catch( RARException ce ) {
				// safe to ignore: we explicitly set the persistent id of the founder Person object above!
			}

			return comment;
		}
		else {
			throw new NoSuchElementException( "CommentIterator: No next Comment object" );
		}

	}


	public void remove()
	{
		throw new UnsupportedOperationException();
	}

}
