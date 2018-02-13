package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class RentalIterator 
	    implements Iterator<Rental>
	{
	    private ResultSet    rs = null;
	    private ObjectLayer  objectLayer = null;
	    private boolean      more = false;

	    // these two will be used to create a new object
	    //
	    public RentalIterator( ResultSet rs, ObjectLayer objectModel )
	            throws RARException
	    { 
	        this.rs = rs;
	        this.objectLayer = objectModel;
	        try {
	            more = rs.next();
	        }
	        catch( Exception e ) {  // just in case...
	            throw new RARException( "RentalIterator: Cannot create Rental iterator; root cause: " + e );
	        }
	    }

	    public boolean hasNext() 
	    { 
	        return more; 
	    }

	    public Rental next() 
	    {   

	    	Rental rental=null;
	        long   id;
	        Date pickuptime;
	        Date returntime;
	        int charges;
	       

	        long reservationId;
	        long vehicleId;
	        long customerId;
	        Reservation reservation;
	        Vehicle vehicle;
	        Customer customer;
	        
	        Date repickuptime;
	        int length;
	        
	        VehicleType vehicletype;
	        long vehicletypeid;
	        String vehicletypename;
	        
	        RentalLocation rentallocation;
	        long rentallocationid;
	        String rentallocationname;
	        String rentallocationaddress;
	        int rentallocationcapacity;
	        
	        String make;
	        String model;int year;
	        int mileage;String tag;
	        Date lastservice;
	        VehicleStatus status;
	        VehicleCondition vehiclecondition;
	        
	            /*    public Customer createCustomer( String firstName, String lastName, String userName, String emailAddress, 
    String password, Date createDate, Date membershipExpiration, String licenseState, 
    String licenseNumber, String residenceAddress, String cardNumber, Date cardExpiration );*/
	        
	        String firstname;String lastname;
	        String username;
	        String psw;
	        String email;
	        String address;
	        UserStatus userstatus;
	        Date createddate;
	        Date memberutil;
	        String licstae;
	        String licnumber;
	        String ccNumber;
	        Date ccExpiry;
	        int  IsAdmin;
	        
	        if( more ) {

	            try {
//	               	    "select r.Id,r.Pickup, r.Return,r.Charges,r.ReservationId,r.VehicleId,r.CustomerId "    
	               
	            	id = rs.getLong( 1 );
	                pickuptime = rs.getDate( 2 );
	                returntime = rs.getDate( 3 );
	                charges = rs.getInt( 4 );
	                reservationId = rs.getLong( 5 );
	                vehicleId = rs.getLong( 6); 
	                customerId = rs.getLong( 7 ); 
	                //+ "re.Id,re.Pickup,re.Length,re.VehicleTypyId,re.ReantalLocationId,re.CustomerId"
	                repickuptime=rs.getDate(9);
	                length=rs.getInt(10);
	    	        vehicletypeid=rs.getLong(11);
	    	        rentallocationid=rs.getLong(12);
//	    			+"vt.Id,vt.Name"
	    	        vehicletypeid=rs.getLong(14);
	    	        vehicletypename=rs.getString(15);
	    	        //+"rl.Id,rl.Name,rl.Address,rl.Capacity"
	    	        
	    	        rentallocationname=rs.getString(17);
	    	        rentallocationaddress=rs.getString(18);
	    	        rentallocationcapacity=rs.getInt(19);
    				//	+"c.Id,c.Firstname,c.Lastname,c.Username,c.Password,c.Email,c.Address,c.Status,c.CreatedDate,c.MemberUtil,c.LicStae,c.LicNumber,c.CCNumber,c.CCExpiry,c.IsAdmin"
	    	        firstname=rs.getString(21);
	    	        lastname=rs.getString(22);
	    	        username=rs.getString(23);
	    	         psw=rs.getString(24);
	    	         email=rs.getString(25);
	    	         address=rs.getString(26);
	    	         userstatus=UserStatus.valueOf(rs.getString(27));
	    	         createddate=rs.getDate(28);//new added
	    	         memberutil=rs.getDate(29);
	    	         licstae=rs.getString(30);
	    	         licnumber=rs.getString(31);
	    	         ccNumber=rs.getString(32);
	    	         ccExpiry=rs.getDate(33);
	    	         IsAdmin=rs.getInt(34);
	    	        
	    	        //    +"v.Id,v.Make,v.Model,v.Year,v.Mileage,v.Tag,v.LastService,v.Status,v.VehicleCondition,v.ReantalLocationId,v.VehicleTypeId"	    		//	+"from Rental r, Reservation re, VehicleType vt, RentalLocation rl, Vehicle v, Customer c "
  	        
	    	        make=rs.getString(36);
	    	        model=rs.getString(37);
	    	        year=rs.getInt(38);
	    	        mileage=rs.getInt(39);
	    	        tag=rs.getString(40);
	    	        lastservice=rs.getDate(41);
	    	        status=VehicleStatus.valueOf(rs.getString(42));
	    	        vehiclecondition=VehicleCondition.valueOf(rs.getString(43));
	    	        

	                more = rs.next();
	            }
	            catch( Exception e ) {      // just in case...
	                throw new NoSuchElementException( "RentalIterator: No next Rental object; root cause: " + e );
	            }
	            
	           
	            
	            try {
	            	
	            	 vehicletype = objectLayer.createVehicleType(vehicletypename);
	                 vehicletype.setId(vehicletypeid);
	                 
	                 rentallocation=objectLayer.createRentalLocation(rentallocationname, rentallocationaddress, rentallocationcapacity);
	                 rentallocation.setId(rentallocationid);
	                 
	 	            vehicle = objectLayer.createVehicle(vehicletype, make, model, year, tag, mileage, lastservice, rentallocation, vehiclecondition, status);
	 	            vehicle.setId(vehicleId);
	 	            
	 	            /* psw=rs.getString(24);
	    	         email=rs.getString(25);
	    	         address=rs.getString(26);
	    	         userstatus=UserStatus.valueOf(rs.getString(27));
	    	         memberutil=rs.getDate(28);
	    	         licstae=rs.getString(29);
	    	         licnumber=rs.getString(30);
	    	         ccNumber=rs.getString(31);
	    	         ccExpiry=rs.getString(32);
	    	         IsAdmin=rs.getInt(33);*/
	 	            
	 	            customer=  objectLayer.createCustomer(firstname, lastname, username, email, psw, createddate, memberutil, licstae, licnumber, address, ccNumber, ccExpiry);
	 	            customer.setId(customerId);
	 	           
	 	            
	 	            /*    public Customer createCustomer( String firstName, String lastName, String userName, String emailAddress, 
            String password, Date createDate, Date membershipExpiration, String licenseState, 
            String licenseNumber, String residenceAddress, String cardNumber, Date cardExpiration );*/
	 	            
	 	            reservation = objectLayer.createReservation(vehicletype, rentallocation, customer, repickuptime, length);
	 	            reservation.setId(reservationId);
	            	
	                rental = objectLayer.createRental(reservation, customer, vehicle, pickuptime);
//	                objectLayer.createRental(reservation, customer, vehicle, pickupTime)

	                rental.setCharges(charges);
	                rental.setReturnTime(returntime);
	                rental.setId( id );
	                //club.setFounderId( founderId );
	            }
	            catch( RARException ce ) {
	                // safe to ignore: we explicitly set the persistent id of the founder Person object above!
	            }
	            
	            return rental;
	        }
	        else {
	            throw new NoSuchElementException( "RentalIterator: No next Rental object" );
	        }
	    }

	    public void remove()
	    {
	        throw new UnsupportedOperationException();
	    }

	};

