package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentARideConfig;
import edu.uga.cs.rentaride.entity.impl.RentARideConfigImpl;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class RentARideConfigManager {

	private Connection   conn = null;
	private ObjectLayer objectLayer = null;

	public RentARideConfigManager(Connection conn, ObjectLayer objectLayer) {
		this.conn = conn;	
		this.objectLayer = objectLayer;
	}

	public void update (RentARideConfig rentARideConfig) throws RARException{


		String               updateRentARideConfigSql = "update Parameters set MembershipPrice = ?, OvertimePenalty = ? Where id = 1";
		PreparedStatement    stmt = null;
		int                  inscnt;

		try {	
			stmt = (PreparedStatement) conn.prepareStatement( updateRentARideConfigSql );

			if( rentARideConfig.getMembershipPrice() != 0 ) // name is unique unique and non null
				stmt.setInt(1, rentARideConfig.getMembershipPrice() );
			else 
				throw new RARException( "RentARideConfigManager.save: can't save a RentARideConfig: MembershipPrice is undefined" );

			if( rentARideConfig.getOvertimePenalty() != 0 )
				stmt.setInt( 2, rentARideConfig.getOvertimePenalty() );
			else
				throw new RARException( "RentARideConfigManager.save: can't save a RentARideConfig: OvertimePenalty is undefined" );

			inscnt = stmt.executeUpdate();

			if( inscnt < 1 )
				throw new RARException( "RentARideConfigManager.save.save: failed to save a RentARideConfig" ); 
		}
		catch( SQLException e ) {
			e.printStackTrace();
			throw new RARException("RentARideConfigManager.save.save: failed to save a RentARideConfig" + e );
		}

	}// save function ends
	
	public RentARideConfig restore() 
			throws RARException
	{
		//String       selectClubSql = "select id, name, address, established, founderid from club";
		String       updateRentARideConfigSql = "SELECT id, MembershipPrice, OvertimePenalty FROM Parameters WHERE id=1;";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );
		int membershipPrice;
		int overtimePenalty;
		long id;

		condition.setLength( 0 );

		// form the query based on the given Club object instance
		query.append( updateRentARideConfigSql );

		try {
			
			stmt = conn.createStatement();
			
			
			// retrieve the persistent Person object
			//
//			System.out.println(query);
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				r.next();
				id = r.getLong(1);
				membershipPrice = r.getInt(2);
				overtimePenalty = r.getInt(3);
				
				return new RentARideConfigImpl(membershipPrice, overtimePenalty);
			}
		}
		catch( Exception e ) {      // just in case...
			throw new RARException( "RentARideConfigManager.restore: Could not restore persistent RentARideConfig object; Root cause: " + e );
		}

		throw new RARException( "RentARideConfigManager.restore: Could not restore persistent RentARideConfig object" );
	}

}
