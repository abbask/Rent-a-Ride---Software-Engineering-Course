package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class CommentManager {

	private ObjectLayer objectLayer = null;
	private Connection   conn = null;

	public CommentManager(Connection conn, ObjectLayer objectLayer) {
		this.conn = conn;
		this.objectLayer = objectLayer;

	}

	public void save (Comment comment) throws RARException{

		String               insertCommentSql = "insert into Comment ( Text, Date, CustomerId, RentalId ) values ( ?, ?, ?, ? )";
		String               updateCommentSql = "update Comment set Text = ?, Date = ?, CustomerId = ?, RentalId = ? where Id = ?";
		PreparedStatement    stmt = null;
		int                  inscnt;
		long                 commentId;

		try {

			if( !comment.isPersistent() )
				stmt = (PreparedStatement) conn.prepareStatement( insertCommentSql );
			else
				stmt = (PreparedStatement) conn.prepareStatement( updateCommentSql );

			if( comment.getComment() != null ) // name is unique unique and non null
				stmt.setString(1, comment.getComment() );
			else 
				throw new RARException( "CommentManager.save: can't save a Comment: Comment is undefined" );

			if( comment.getDate() != null ){
				java.util.Date jDate = comment.getDate();
				java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
				stmt.setDate( 2,  sDate );				
			}
			else
				throw new RARException( "CommentManager.save: can't save a Date: Date is undefined" );

			if( comment.getCustomer() != null && comment.getCustomer().isPersistent() ) {
				stmt.setLong(3, comment.getCustomer().getId());
			}
			else
				throw new RARException( "CommentManager.save: can't save a Customer: Customer is not set or not persistent" );

			if( comment.getRental() != null && comment.getRental().isPersistent() )
				stmt.setLong(4, comment.getRental().getId());
			else 
				throw new RARException( "CommentManager.save: can't save a Customer: Rental is not set or not persistent" );

			if( comment.isPersistent() )
				stmt.setLong( 5, comment.getId() );

			inscnt = stmt.executeUpdate();

			if( !comment.isPersistent() ) {
				if( inscnt >= 1 ) {
					String sql = "select last_insert_id()";
					if( stmt.execute( sql ) ) { // statement returned a result

						// retrieve the result
						ResultSet r = stmt.getResultSet();

						// we will use only the first row!
						//
						while( r.next() ) {

							// retrieve the last insert auto_increment value
							commentId = r.getLong( 1 );
							if( commentId > 0 )
								comment.setId( commentId ); // set this person's db id (proxy object)
						}
					}
				}
				else
					throw new RARException( "CommentManager.save: failed to save a Comment" );
			}
			else {
				if( inscnt < 1 )
					throw new RARException( "CommentManager.save: failed to save a Comment" ); 
			}
		}
		catch( SQLException e ) {
			e.printStackTrace();
			throw new RARException( "CommentManager.save: failed to save a Comment: " + e );
		}


	}// save function ends

	public void delete(Comment comment)
			throws RARException {
		String deleteCommnetSql = "delete from Comment where id = ?";
		PreparedStatement stmt = null;
		int inscnt;

		if (!comment.isPersistent()) // is the HourlyPrice persistent?  If not, nothing to actually delete
		{
			return;
		}

		try {
			stmt = (PreparedStatement) conn.prepareStatement(deleteCommnetSql);
			stmt.setLong(1, comment.getId());
			inscnt = stmt.executeUpdate();
			if (inscnt == 1) {
				return;
			} else {
				throw new RARException("CommentManager.delete: failed to delete a Comment");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RARException("CommentManager.delete: failed to delete a Comment: " + e);
		}
	}// delete function ends

	public Customer restoreCustomer(Comment comment) 
			throws RARException
	{
		String       selectCustomerSql = "select c.Text, c.Date, c.CustomerId, r.FirstName, r.LastName, r.Username, r.Password, r.Email, r.Address, r.Status, r.MemberUntil, r.LicState, r.LicNumber, r.CCNumber, r.CCExpiry, r.IsAdmin from Comment c, Customer r where r.id =c.CustomerId";              
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Person object instance
		query.append( selectCustomerSql );

		if( comment != null ) {
			if( comment.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and c.id = " + comment.getId() );           
			else {

				if( comment.getComment() != null )
					condition.append( " and c.Commnet = '" + comment.getComment() + "'" );   

				if( comment.getDate() != null  ) {
					condition.append( " and c.Date  = '" + comment.getDate() + "'" );
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
				Iterator<Customer> customerIter = new CustomerIterator( r, objectLayer );
				if( customerIter != null && customerIter.hasNext() ) {
					return customerIter.next();
				}
				else
					return null;
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "CommentManager.restoreCustomer: Could not restore persistent CustomerIterator Type object; Root cause: " + e );
		}

		// if we reach this point, it's an error
		throw new RARException( "CommentManager.restoreCustomer: Could not restore persistent object" );

	}///function restoreHasPriceFor ends

	
	public Rental restoreRental(Comment comment) 
			throws RARException
	{
		String       selectRentalSql = "select c.Text, c.Date, c.RentalId, r.Pickup, r.Return, r.Charges, r.Condition where r.id =c.RentalId";              
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Person object instance
		query.append( selectRentalSql );

		if( comment != null ) {
			if( comment.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and c.id = " + comment.getId() );           
			else {

				if( comment.getComment() != null )
					condition.append( " and c.Commnet = '" + comment.getComment() + "'" );   

				if( comment.getDate() != null  ) {
					condition.append( " and c.Date  = '" + comment.getDate() + "'" );
				}

			}
		}

		try {

			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				Iterator<Rental> rentalIter = new RentalIterator( r, objectLayer );
				if( rentalIter != null && rentalIter.hasNext() ) {
					return rentalIter.next();
				}
				else
					return null;
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "CommentManager.restoreRental: Could not restore persistent RentalIterator Type object; Root cause: " + e );
		}

		// if we reach this point, it's an error
		throw new RARException( "CommentManager.restoreRental: Could not restore persistent object" );

	}///function restoreHasPriceFor ends
	
	public Iterator<Comment> restore(Comment comment) 
			throws RARException
	{
		//String       selectClubSql = "select id, name, address, established, founderid from club";
		String       selectClubSql = "SELECT c.id, c.Text, c.Date, c.CustomerId, t.FirstName, t.LastName, t.Username, t.Password, t.Email, t.Address, t.Status, t.MemberUntil, t.LicNumber, t.LicState, t.CCNumber, t.CCExpiry, t.IsAdmin, c.RentalId, r.Pickup, r.ReturnTime, r.Charges  FROM Comment c, Customer t, Rental r WHERE c.RentalId=r.Id And c.CustomerId=t.Id";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectClubSql );

		if( comment != null ) {
			if( comment.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and c.id = " + comment.getId() );
			else {

				if( comment.getComment() != null )
					condition.append( " and Comment = '" + comment.getComment() + "'" );   

				if( comment.getDate() != null ) {
					condition.append( " Date = '" + comment.getDate() + "'" );
				}
			}
		}

		try {

			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				return new CommentIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "CommentManager.restore: Could not restore persistent Comment object; Root cause: " + e );
		}

		throw new RARException( "CommentManager.restore: Could not restore persistent HourlyPrice object" );
	}

	public Iterator<Comment> restoreRentalComment( Rental rental ) throws RARException
	{
		String       selectClubSql = "SELECT c.id, c.Text, c.Date, c.CustomerId, t.FirstName, t.LastName, t.Username, t.Password, t.Email, t.Address, t.Status, t.MemberUntil, t.LicNumber, t.LicState, t.CCNumber, t.CCExpiry, t.IsAdmin, c.RentalId, r.Pickup, r.ReturnTime, r.Charges  FROM Comment c, Customer t, Rental r WHERE c.RentalId=r.Id And c.CustomerId=t.Id";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectClubSql );

		if( rental != null ) {
			if( rental.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and c.RentalId = " + rental.getId() );
			else 
				throw new RARException( "CommentManager.restoreRentalComment: RentalId is not defined.") ;
		}

		try {

			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				return new CommentIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "CommentManager.restoreRentalComment: Could not restore persistent Rental object; Root cause: " + e );
		}

		throw new RARException( "CommentManager.restoreRentalComment: Could not restore persistent Rental object" );
	}
	
	public Iterator<Comment> restoreCustomerComment( Customer customer ) throws RARException
	{
		String       selectClubSql = "SELECT c.id, c.Text, c.Date, c.CustomerId, t.FirstName, t.LastName, t.Username, t.Password, t.Email, t.Address, t.Status, t.MemberUntil, t.LicNumber, t.LicState, t.CCNumber, t.CCExpiry, t.IsAdmin, c.RentalId, r.Pickup, r.ReturnTime, r.Charges  FROM Comment c, Customer t, Rental r WHERE c.RentalId=r.Id And c.Customer=t.Id";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( selectClubSql );

		if( customer != null ) {
			if( customer.getId() >= 0 ) // id is unique, so it is sufficient to get a person
				query.append( " and c.CustomerId = " + customer.getId() );
			else 
				throw new RARException( "CommentManager.restoreCustomerComment: CustomerId is not defined.") ;
		}

		try {

			stmt = conn.createStatement();

			// retrieve the persistent Person object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				return new CommentIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "CommentManager.restoreCustomerComment: Could not restore persistent Rental object; Root cause: " + e );
		}

		throw new RARException( "CommentManager.restoreCustomerComment: Could not restore persistent Rental object" );
	}
}
