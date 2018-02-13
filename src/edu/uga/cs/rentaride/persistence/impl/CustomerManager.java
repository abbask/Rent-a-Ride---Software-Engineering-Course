package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class CustomerManager {
	
	private ObjectLayer objectLayer = null;
	private Connection   conn = null;

	public CustomerManager(Connection conn, ObjectLayer objectLayer) {
		this.conn = conn;
		this.objectLayer = objectLayer;

	}
	
	public void save (Customer customer) throws RARException{

		String               insertCustomerSql = "insert into Customer ( Firstname, Lastname, Username, Password, Email, Address, Status, CreatedDate, MemberUntil, LicState, LicNumber, CCNumber, CCExpiry, IsAdmin) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0 )";
		String               updateCustomerSql = "update Customer set Firstname = ?, Lastname = ?, Username = ?, Password = ?, Email = ?, Address = ?, Status = ?, CreatedDate = ?, MemberUntil = ?, LicState = ? , LicNumber = ? , CCNumber = ? , CCExpiry = ?  where Id = ?";			
		PreparedStatement    stmt = null;
		int                  inscnt;
		long                 customerId;

		try {

			if( !customer.isPersistent() )
				stmt = (PreparedStatement) conn.prepareStatement( insertCustomerSql );
			else
				stmt = (PreparedStatement) conn.prepareStatement( updateCustomerSql );

			if( customer.getFirstName() != null )
				stmt.setString(1, customer.getFirstName() );
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: First Name is undefined" );

			if( customer.getLastName() != null ) 
				stmt.setString(2, customer.getLastName() );
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: Last Name is undefined" );


			if( customer.getUserName()!= null )  
				stmt.setString(3, customer.getUserName() );
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: Username is undefined" );

			if( customer.getPassword() != null ) 
				stmt.setString(4, customer.getPassword() );
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: Password is undefined" );

			if( customer.getEmailAddress() != null )
				stmt.setString(5, customer.getEmailAddress() );
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: Email Address is undefined" );

			if( customer.getResidenceAddress() != null )
				stmt.setString(6, customer.getResidenceAddress() );
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: Residential Address is undefined" );

			if( customer.getUserStatus() != null )
				stmt.setString(7, customer.getUserStatus().toString() );
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: User Status is undefined" );
			
			if( customer.getCreatedDate() != null ){
				java.util.Date jDate = customer.getCreatedDate();
				java.sql.Timestamp sTime = new java.sql.Timestamp(jDate.getTime());
				stmt.setObject( 8,  sTime );				
			}
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: Created Date is undefined" );

			if( customer.getMembershipExpiration() != null ){
				java.util.Date jDate = customer.getMembershipExpiration();
				java.sql.Timestamp sTime = new java.sql.Timestamp(jDate.getTime());
				stmt.setObject( 9,  sTime );
			}
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: Created Date is undefined" );
			
			if( customer.getLicenseState() != null )
				stmt.setString(10, customer.getLicenseState() );
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: License State is undefined" );
			
			if( customer.getLicenseNumber() != null )
				stmt.setString(11, customer.getLicenseNumber() );
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: License Number is undefined" );
			
			if( customer.getCreditCardNumber() != null )
				stmt.setString(12, customer.getCreditCardNumber() );
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: Credit Card Number is undefined" );
			
			if( customer.getCreditCardExpiration() != null ){
				java.util.Date jDate = customer.getCreditCardExpiration();
				java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
				stmt.setDate( 13,  sDate );	
				
			}
			else 
				throw new RARException( "AdministratorManager.save: can't save a Administator: Credit Card Expiration is undefined" );
			
			
			
			if( customer.isPersistent() )
				stmt.setLong( 14, customer.getId() );
			
			System.out.println(stmt);
			inscnt = stmt.executeUpdate();

			if( !customer.isPersistent() ) {
				if( inscnt >= 1 ) {
					String sql = "select last_insert_id()";
					if( stmt.execute( sql ) ) { // statement returned a result

						// retrieve the result
						ResultSet r = stmt.getResultSet();

						// we will use only the first row!
						//
						while( r.next() ) {

							// retrieve the last insert auto_increment value
							customerId = r.getLong( 1 );
							if( customerId > 0 )
								customer.setId( customerId ); // set this person's db id (proxy object)
						}
					}
				}
				else
					throw new RARException( "CustomerManager.save: failed to save a Customer" );
			}
			else {
				if( inscnt < 1 )
					throw new RARException( "CustomerManager.save: failed to save a customer" ); 
			}
		}
		catch( SQLException e ) {
			e.printStackTrace();
			throw new RARException( "CustomerManager.save: failed to save a Customer: " + e );
		}

	}
	
	public void delete(Customer customer)
			throws RARException {
		String deleteCustomerSql = "delete from Customer where id = ?";
		PreparedStatement stmt = null;
		int inscnt;

		if (!customer.isPersistent()) 
		{
			return;
		}

		try {
			stmt = (PreparedStatement) conn.prepareStatement(deleteCustomerSql);
			stmt.setLong(1, customer.getId());
			inscnt = stmt.executeUpdate();
			if (inscnt == 1) {
				return;
			} else {
				throw new RARException("CustomerManager.delete: failed to delete a Customer");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RARException("CustomerManager.delete: failed to delete a Customer: " + e);
		}
	}// delete function ends
	
	public Iterator<Customer> restore(Customer customer) 
			throws RARException
	{		
		String       selectCustomerSql = "SELECT Id, Firstname, Lastname, Username, Password, Email, Address, Status, CreatedDate, MemberUntil, LicState, LicNumber, CCNumber, CCExpiry FROM Customer Where IsAdmin=0 AND Status='ACTIVE'";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );
		

		query.append( selectCustomerSql );

		if( customer != null ) {
			if( customer.getId() >= 0 ) 
				query.append( " and id = " + customer.getId() );
			else {

				if(customer.getFirstName() != null)
					condition.append(" and Firstname = '" + customer.getFirstName() + "'");

				if(customer.getLastName() != null)
					condition.append(" and Lastname = '" + customer.getLastName() + "'");

				if(customer.getUserName() != null)
					condition.append(" and Username = '" + customer.getUserName() + "'");
				
				if(customer.getPassword() != null)
					condition.append(" and Password = '" + customer.getPassword() + "'");
				
				if(customer.getEmailAddress() != null)
					condition.append(" and Email = '" + customer.getEmailAddress() + "'");

				if(customer.getResidenceAddress() != null)
					condition.append(" and Address = '" + customer.getResidenceAddress() + "'");				
				
				if(customer.getLicenseNumber() != null)
					condition.append(" and LicNumber = '" + customer.getLicenseNumber() + "'");
				
				if(customer.getCreditCardNumber() != null)
					condition.append(" and CCNumber = '" + customer.getCreditCardNumber() + "'");
				
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
				return new CustomerIterator( r, objectLayer );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "CustomerManager.restore: Could not restore persistent Customer object; Root cause: " + e );
		}

		throw new RARException( "CustomerManager.restore: Could not restore persistent Customer object" );
	}

	
	
}
