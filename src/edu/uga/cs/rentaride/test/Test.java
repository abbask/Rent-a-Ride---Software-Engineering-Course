package edu.uga.cs.rentaride.test;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.Comment;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentARideConfig;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.object.impl.ObjectLayerImpl;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;
import edu.uga.cs.rentaride.persistence.impl.DbUtils;
import edu.uga.cs.rentaride.persistence.impl.PersistenceLayerImpl;

public class Test {

	public static void main(String[] args) {
		deleteData();
		saveData();
		restoreData();
		updatingData();

	}

	public static void saveData(){
		Connection conn = null;
		ObjectLayer objectLayer = null;
		PersistenceLayer persistence = null;

		Administrator manager;
		Customer joe;
		Customer eric;
		Customer bob;
		RentalLocation athensCenter;
		RentalLocation hertz;

		VehicleType luxury;
		VehicleType pickupTruck;
		VehicleType sedan;

		Vehicle bmw;
		Vehicle ram;
		Vehicle ford;


		HourlyPrice sedan1hourlyPrice;
		HourlyPrice sedan2hourlyPrice;
		HourlyPrice pickup1hourlyPrice;

		Reservation reservation;
		Rental rental;
		Comment comment;


		try {
			conn = DbUtils.connect();
		} 
		catch (Exception seq) {
			System.err.println( "InsertTest: Unable to obtain a database connection" );
		}

		persistence = new PersistenceLayerImpl( conn, objectLayer ); 
		objectLayer = new ObjectLayerImpl(persistence);


		try {

			System.out.println("*** Start Saving records ***");
			System.out.println();

			final Calendar nextSixMonthCal = Calendar.getInstance();			
			nextSixMonthCal.add(Calendar.MONTH, 6);
			final Calendar next14MonthCal = Calendar.getInstance();
			next14MonthCal.add(Calendar.MONTH, 14);
			final Calendar passedCal = Calendar.getInstance();
			passedCal.set(Calendar.DAY_OF_WEEK, 2);
			final Calendar nextWeekCal = Calendar.getInstance();
			nextWeekCal.add(Calendar.WEEK_OF_MONTH, 1);
			final Calendar next2WeekCal = Calendar.getInstance();
			next2WeekCal.add(Calendar.WEEK_OF_MONTH, 2);

			final Date now = new Date();
			final Date sixMonthLaterDate = (Date)nextSixMonthCal.getTime();
			final Date someMonthLaterDate = (Date)next14MonthCal.getTime();	
			final Date passedDate = (Date)passedCal.getTime();
			final Date nextWeek = (Date)nextWeekCal.getTime();
			final Date next2Week = (Date)next2WeekCal.getTime();

			manager = objectLayer.createAdministrator("John", "Smith", "manager", "manager@cs.uga.edu", "1234",now);		

			joe = objectLayer.createCustomer("Joe", "Smith", "joe_Smith", "joesmith@some.com", "0000", now, sixMonthLaterDate ,"GA", "1x1x1x1", "123 Main St. Athens", "98765432988776",someMonthLaterDate);
			eric = objectLayer.createCustomer("Eric", "Parker", "eric200", "eric200@some.com", "8976", now, sixMonthLaterDate ,"NY", "1weiew", "100 Davis St. Athens", "234",someMonthLaterDate);
			bob = objectLayer.createCustomer("Bob", "Hu", "fereshwater", "bob@some.com", "1234", now, sixMonthLaterDate ,"GA", "00we11", "1462 water Dr. Athens", "1111111111",someMonthLaterDate);

			athensCenter = objectLayer.createRentalLocation("Athens Center", "303 broad St, Athens", 32);
			hertz = objectLayer.createRentalLocation("Hertz Center", "1870 Atlanta Hwy, Athens", 14);

			pickupTruck = objectLayer.createVehicleType("Pickup Truck");
			sedan = objectLayer.createVehicleType("Sedddan");
			luxury = objectLayer.createVehicleType("Luxury");

			bmw = objectLayer.createVehicle(sedan, "BMW", "i8", 2015, "Aaaa3", 300, passedDate, hertz, VehicleCondition.GOOD, VehicleStatus.INLOCATION);
			ford = objectLayer.createVehicle(sedan, "Ford", "Mustang", 2013, "Xxxc1", 1000, passedDate, athensCenter, VehicleCondition.GOOD, VehicleStatus.INLOCATION);
			ram = objectLayer.createVehicle(pickupTruck, "RAM", "1500", 2010, "Rrg94", 14000, passedDate, athensCenter, VehicleCondition.NEEDSCLEANING, VehicleStatus.INLOCATION);

			sedan1hourlyPrice = objectLayer.createHourlyPrice(1, 4, 20, sedan);
			sedan2hourlyPrice = objectLayer.createHourlyPrice(5, 10, 18, sedan);
			pickup1hourlyPrice = objectLayer.createHourlyPrice(1, 10, 50, pickupTruck);

			reservation = objectLayer.createReservation(sedan, athensCenter, joe, nextWeek	, 2);
			rental = objectLayer.createRental(reservation, joe, ford, next2Week);
			comment = objectLayer.createComment("Thank you for your business", rental, joe);


			persistence.storeAdministrator(manager);

			persistence.storeCustomer(joe);
			persistence.storeCustomer(eric);
			persistence.storeCustomer(bob);

			persistence.storeRentalLocation(athensCenter);
			persistence.storeRentalLocation(hertz);

			persistence.storeVehicleType(pickupTruck);
			persistence.storeVehicleType(sedan);
			persistence.storeVehicleType(luxury);

			persistence.storeVehicle(bmw);
			persistence.storeVehicle(ford);
			persistence.storeVehicle(ram);

			persistence.storeHourlyPrice(sedan1hourlyPrice);
			persistence.storeHourlyPrice(sedan2hourlyPrice);
			persistence.storeHourlyPrice(pickup1hourlyPrice);

			persistence.storeReservation(reservation);
			persistence.storeRental(rental);
			persistence.storeComment(comment);


			System.out.println( "Entity objects created and saved in the persistence module" );
			System.out.println();

		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		finally {
			// close the connection
			try {
				conn.close();
			}
			catch( Exception e ) {
				System.err.println( "Exception: " + e );
			}
		}
	}

	public static void restoreData(){
		Connection conn = null;
		ObjectLayerImpl objectLayer = null;
		PersistenceLayer persistence = null;

		// get a database connection
		try {
			conn = DbUtils.connect();
		} 
		catch (Exception seq) {
			System.err.println( "restoreTest: Unable to obtain a database connection" );
		}

		objectLayer = new ObjectLayerImpl();
		persistence = new PersistenceLayerImpl( conn, objectLayer ); 
		objectLayer.setPersistence(persistence);


		try{

			System.out.println("*** Start Restoring record ***");
			System.out.println();

			System.out.println("Parameter object:");
			RentARideConfig cfg = objectLayer.findRentARideCfg();
			System.out.println( cfg );
			System.out.println();

			System.out.println("Administrator objects:");
			Administrator searchForAdministrator = objectLayer.createAdministrator(); 
			Iterator<Administrator> administratorIter = objectLayer.findAdministrator(searchForAdministrator);
			while( administratorIter.hasNext() ) {
				Administrator a = administratorIter.next();
				System.out.println( a );                
			}
			System.out.println();

			System.out.println("Customer objects:");
			Customer searchForCustomers = objectLayer.createCustomer();
			Iterator<Customer> customerIter = objectLayer.findCustomer(searchForCustomers);			
			while( customerIter.hasNext() ) {
				Customer c = customerIter.next();
				System.out.println( c );   
				System.out.print("	Reservations for the customer:");

				Iterator<Reservation> customerReservationIterator =   objectLayer.restoreCustomerReservation(c);
				if (!customerReservationIterator.hasNext()){
					System.out.println(" No Reservation Found.");
				}
				else
					while(customerReservationIterator.hasNext()){
						Reservation r = customerReservationIterator.next();
						System.out.println();
						System.out.println("		" + r);	

					}

			}
			System.out.println();

			System.out.println("Rental Location objects:");
			RentalLocation searchForRentalLocation = objectLayer.createRentalLocation();
			Iterator<RentalLocation> rentalLocationIter = objectLayer.findRentalLocation(searchForRentalLocation);
			while( rentalLocationIter.hasNext() ) {
				RentalLocation r = rentalLocationIter.next();
				System.out.println( r );     

				System.out.print("	Vehicle of Rental Locations:");

				Iterator<Vehicle> vehicleRentalLocationIterator =   objectLayer.restoreVehicleRentalLocation(r);
				if (!vehicleRentalLocationIterator.hasNext()){
					System.out.println(" No Vehicle parked here.");
				}
				else
					while(vehicleRentalLocationIterator.hasNext()){
						Vehicle v = vehicleRentalLocationIterator.next();
						System.out.println();
						System.out.println("		" + v);	

					}

			}
			System.out.println();

			System.out.println("Vehicle Type objects:");
			VehicleType searchForVehicleType = objectLayer.createVehicleType();
			Iterator<VehicleType> vehicleTypeIter = objectLayer.findVehicleType(searchForVehicleType);
			while( vehicleTypeIter.hasNext() ) {
				VehicleType v = vehicleTypeIter.next();
				System.out.println( v );  

				System.out.print("	Vehicle of VehicleTypes:");

				Iterator<Vehicle> vehicleVehicleTypeIterator =   objectLayer.restoreVehicleVehicleType(v);
				if (!vehicleVehicleTypeIterator.hasNext()){
					System.out.println(" No Vehicle assigned.");
				}
				else
					while(vehicleVehicleTypeIterator.hasNext()){
						Vehicle vh = vehicleVehicleTypeIterator.next();
						System.out.println();
						System.out.println("		" + vh);	

					}

			}
			System.out.println();

			System.out.println("Vehicle objects:");
			Vehicle searchForVehicle = objectLayer.createVehicle();
			Iterator<Vehicle> vehicleIter = objectLayer.findVehicle(searchForVehicle);
			while( vehicleIter.hasNext() ) {
				Vehicle v = vehicleIter.next();
				System.out.println( v );     

			}
			System.out.println();


			System.out.println("Hourly Price objects:");
			HourlyPrice searchForHourlyPrice = objectLayer.createHourlyPrice();
			Iterator<HourlyPrice> hourlyPriceIter = objectLayer.findHourlyPrice(searchForHourlyPrice);
			while( hourlyPriceIter.hasNext() ) {
				HourlyPrice h = hourlyPriceIter.next();
				System.out.println( h );     

			}
			System.out.println();

			System.out.println("Reservation objects:");
			Reservation searchForReservation = objectLayer.createReservation();
			Iterator<Reservation> reservationIter = objectLayer.findReservation(searchForReservation);
			while( reservationIter.hasNext() ) {
				Reservation r = reservationIter.next();
				System.out.println( r);     

			}
			System.out.println();

			System.out.println("Retal objects:");
			Rental searchForRental = objectLayer.createRental();
			Iterator<Rental> rentalIter = objectLayer.findRental(searchForRental);
			while( rentalIter.hasNext() ) {
				Rental r = rentalIter.next();
				System.out.println( r);  

				System.out.print("	Comments of Rental:");

				Iterator<Comment> commentsRentalIterator =   objectLayer.restoreRentalComment(r);
				if (!commentsRentalIterator.hasNext()){
					System.out.println(" No Vehicle assigned.");
				}
				else
					while(commentsRentalIterator.hasNext()){
						Comment cr = commentsRentalIterator.next();
						System.out.println();
						System.out.println("		" + cr);	

					}

			}
			System.out.println();

			System.out.println("Comment objects:");
			Comment searchForComment = objectLayer.createComment();
			Iterator<Comment> commentIter = objectLayer.findComment(searchForComment);
			while( commentIter.hasNext() ) {
				Comment c = commentIter.next();
				System.out.println( c);     

			}
			System.out.println();

			//			System.out.println("Start Updating:");										
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		finally {
			// close the connection
			try {
				conn.close();
			}
			catch( Exception e ) {
				System.err.println( "Exception: " + e );
			}
		}
	}

	public static void updatingData(){

		Connection conn = null;
		ObjectLayerImpl objectLayer = null;
		PersistenceLayer persistence = null;

		// get a database connection
		try {
			conn = DbUtils.connect();
		} 
		catch (Exception seq) {
			System.err.println( "restoreTest: Unable to obtain a database connection" );
		}

		objectLayer = new ObjectLayerImpl();
		persistence = new PersistenceLayerImpl( conn, objectLayer ); 
		objectLayer.setPersistence(persistence);


		try{
			System.out.println("*** Start Updating record ***");
			System.out.println();

			//////////////config///////////////////////////////
			System.out.println("Parameter(config) object:");
			RentARideConfig cfg = objectLayer.findRentARideCfg();
			System.out.println("	Found result:");
			System.out.println( "		" + cfg );
			System.out.println("	Modifying membership fee to 1000");
			cfg.setMembershipPrice(1000);
			objectLayer.storeRentARideCfg(cfg);
			System.out.println("	Modified Result:");
			System.out.println( "		" +  cfg );
			System.out.println();


			//////////////Administrator///////////////////////////////
			System.out.println("Administrator objects:");
			Administrator searchForAdministrator = objectLayer.createAdministrator();
			searchForAdministrator.setFirstName("John");;
			Iterator<Administrator> administratorIter = objectLayer.findAdministrator(searchForAdministrator);			
			if ( administratorIter.hasNext() ) {
				Administrator a = administratorIter.next(); 
				System.out.println("	Found result:");
				System.out.println( "		" + a );
				a.setPassword("newPassword1234");                
				objectLayer.storeAdministrator(a);
				System.out.println("	Modifying password to newPassword1234");
				Iterator<Administrator> foundAdministratorIter = objectLayer.findAdministrator(searchForAdministrator);
				if ( foundAdministratorIter.hasNext() ) {
					Administrator fa = foundAdministratorIter.next();
					System.out.println("	Modified result:");
					System.out.println( "		"+fa );
				}                                           
			}
			System.out.println();


			//////////////Customer///////////////////////////////
			System.out.println("Customer objects:");
			Customer searchForCustomers = objectLayer.createCustomer();
			searchForCustomers.setLastName("Smith");
			Iterator<Customer> customerIter = objectLayer.findCustomer(searchForCustomers);
			if( customerIter.hasNext() ) {
				Customer c = customerIter.next();
				System.out.println("	Found Result:");
				System.out.println( "		" + c );    
				c.setCreditCardNumber("1111222233334444");
				objectLayer.storeCustomer(c);
				System.out.println("	Modifying Credit Card Number to 1111222233334444");
				Iterator<Customer> foundCustomerIter = objectLayer.findCustomer(searchForCustomers);
				if( foundCustomerIter.hasNext() ) {
					Customer cf = foundCustomerIter.next();
					System.out.println("	Modified Result:");
					System.out.println( "		" + cf );   
				}

			}
			System.out.println();

			//////////////Rental Location///////////////////////////////			
			System.out.println("Rental Location objects:");
			RentalLocation searchForRentalLocation = objectLayer.createRentalLocation();
			searchForRentalLocation.setName("Athens Center");			
			Iterator<RentalLocation> rentalLocationIter = objectLayer.findRentalLocation(searchForRentalLocation);
			if( rentalLocationIter.hasNext() ) {
				RentalLocation r = rentalLocationIter.next();
				System.out.println( "	Found Result:" );  
				System.out.println( "		" + r );    
				r.setAddress("2000 Athens st, GA");
				objectLayer.storeRentalLocation(r);
				System.out.println("	Modifying Address to 2000 Athens st, GA");
				Iterator<RentalLocation> foundRentalLocationIter = objectLayer.findRentalLocation(searchForRentalLocation);
				if( foundRentalLocationIter.hasNext() ) {
					RentalLocation rf = foundRentalLocationIter.next();
					System.out.println("	Modified Result:");  
					System.out.println( "		" + rf );   
				}

			}
			System.out.println();

			//////////////Vehicle Type///////////////////////////////				
			System.out.println("Vehicle Type objects:");
			VehicleType searchForVehicleType = objectLayer.createVehicleType();
			searchForVehicleType.setType("Sedddan");
			Iterator<VehicleType> vehicleTypeIter = objectLayer.findVehicleType(searchForVehicleType);
			if( vehicleTypeIter.hasNext() ) {
				VehicleType v = vehicleTypeIter.next();
				System.out.println( "	Found Result:" );
				System.out.println( "		" + v );     
				v.setType("Sedan");
				objectLayer.storeVehicleType(v);
				System.out.println("	Modifying Name to Sedan");
				Iterator<VehicleType> resultVehicleTypeIter = objectLayer.findVehicleType(v);
				if( resultVehicleTypeIter.hasNext() ) {
					VehicleType vf = resultVehicleTypeIter.next();
					System.out.println( "	Found Result:" );
					System.out.println( "		" + vf );
				}
			}
			System.out.println();

			//////////////Vehicle///////////////////////////////				
			System.out.println("Vehicle objects:");
			Vehicle searchForVehicle = objectLayer.createVehicle();
			searchForVehicle.setMake("BMW");
			Iterator<Vehicle> vehicleIter = objectLayer.findVehicle(searchForVehicle);
			if( vehicleIter.hasNext() ) {
				Vehicle v = vehicleIter.next();
				System.out.println( "	Found Result:" );
				System.out.println( "		" + v );     
				v.setMake("Dodge");
				v.setModel("Challenger");
				objectLayer.storeVehicle(v);
				System.out.println("	Modifying Make to Dodge and Model to Challenger");
				Iterator<Vehicle> resultVehicleIter = objectLayer.findVehicle(v);
				if( resultVehicleIter.hasNext() ) {
					Vehicle vf = resultVehicleIter.next();
					System.out.println( "	Found Result:" );
					System.out.println( "		" + vf );
				}
			}
			System.out.println();

			//////////////HourlyPrice///////////////////////////////				
			System.out.println("HourlyPrice objects:");
			HourlyPrice searchForHourlyPrice = objectLayer.createHourlyPrice();
			searchForHourlyPrice.setMinHours(1);
			searchForHourlyPrice.setMaxHours(4);
			Iterator<HourlyPrice> hourlyPriceIter = objectLayer.findHourlyPrice(searchForHourlyPrice);
			if( hourlyPriceIter.hasNext() ) {
				HourlyPrice h = hourlyPriceIter.next();
				System.out.println( "	Found Result:" );
				System.out.println( "		" + h );     
				h.setPrice(13);
				objectLayer.storeHourlyPrice(h);
				System.out.println("	Modifying Price to $13 ");
				Iterator<HourlyPrice> resultHourlyPriceIter = objectLayer.findHourlyPrice(h);
				if( resultHourlyPriceIter.hasNext() ) {
					HourlyPrice hf = resultHourlyPriceIter.next();
					System.out.println( "	Found Result:" );
					System.out.println( "		" + hf );
				}
			}
			System.out.println();

			//////		////////Reservation///////////////////////////////				
			System.out.println("Reservation objects:");
			Reservation searchForReservation = objectLayer.createReservation();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateInString = "2015-11-25";					
			Date date = formatter.parse(dateInString);
			java.util.Date jDate = date;
			java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
			searchForReservation.setPickupTime(sDate);
			Iterator<Reservation> reservationIter = objectLayer.findReservation(searchForReservation);
			if( reservationIter.hasNext() ) {
				Reservation r = reservationIter.next();
				System.out.println( "	Found Result:" );
				System.out.println( "		" + r );     
				r.setRentalDuration(3);
				objectLayer.storeReservation(r);
				System.out.println("	Modifying Rental Duration to 3 ");
				Iterator<Reservation> resultReservationIter = objectLayer.findReservation(r);
				if( resultReservationIter.hasNext() ) {
					Reservation rf = resultReservationIter.next();
					System.out.println( "	Found Result:" );
					System.out.println( "		" + rf );
				}
			}
			System.out.println();

			//////////////Rental///////////////////////////////				
			System.out.println("Rental objects:");
			Rental searchForRental  = objectLayer.createRental();
			final Calendar next2WeekCal = Calendar.getInstance();
			next2WeekCal.add(Calendar.WEEK_OF_MONTH, 2);
			final Date next2Week = (Date)next2WeekCal.getTime();
			jDate = next2Week;
			sDate = new java.sql.Date( jDate.getTime() );
			searchForRental.setPickupTime(sDate);			

			Iterator<Rental> rentalIter = objectLayer.findRental(searchForRental);
			if( rentalIter.hasNext() ) {
				Rental rn = rentalIter.next();

				System.out.println( "	Found Result:" );
				System.out.println( "		" + rn );     
				rn.setCharges(10);
				objectLayer.storeRental(rn);
				System.out.println("	Modifying Rental Charges to 10 ");
				Iterator<Rental> resultRentalIter = objectLayer.findRental(rn);
				if( resultRentalIter.hasNext() ) {
					Rental rnf = resultRentalIter.next();
					System.out.println( "	Found Result:" );
					System.out.println( "		" + rnf );

					////////////Comment///////////////////////////////				
					System.out.println("Comment objects:");

					Iterator<Comment> commentIter = objectLayer.restoreRentalComment(rnf);
					if( commentIter.hasNext() ) {
						Comment com = commentIter.next();

						System.out.println( "	Found Result:" );
						System.out.println( "		" + com );     
						com.setComment("New Commment.");
						objectLayer.storeComment(com);
						System.out.println("	Modifying Comment to New Commment ");
						Iterator<Comment> resultCommentIter = objectLayer.findComment(com);
						if( resultCommentIter.hasNext() ) {
							Comment comf = resultCommentIter.next();
							System.out.println( "	Found Result:" );
							System.out.println( "		" + comf );
						}
					}
					System.out.println();


				}
			}
			System.out.println();

			System.out.println("Changing the location of the ford vehicle");

			Vehicle fordVehicle = objectLayer.createVehicle();
			fordVehicle.setMake("Ford");
			Iterator<Vehicle> fordVehicleIter = objectLayer.findVehicle(fordVehicle);
			if( fordVehicleIter.hasNext() ) {
				Vehicle v = fordVehicleIter.next();
				System.out.println("	Ford Vehicle Retireved.");

				////finding a new rental location named Hertz
				RentalLocation hertzRentalLocation = objectLayer.createRentalLocation();
				hertzRentalLocation.setName("Hertz Center");
				Iterator<RentalLocation> hertzRentalLocationIter = objectLayer.findRentalLocation(hertzRentalLocation);
				if( hertzRentalLocationIter.hasNext() ) {
					RentalLocation hrl  = hertzRentalLocationIter.next();
					System.out.println("	Hertz Rental Location Retireved.");
					
					objectLayer.createVehicleRentalLocation(v, hrl);
					System.out.println("	Ford assigned to Hertz Rental Location.");
					
					System.out.println("	Again Listing Rental Location objects:");
					RentalLocation sForRentalLocation = objectLayer.createRentalLocation();
					Iterator<RentalLocation> rentalLocIter = objectLayer.findRentalLocation(sForRentalLocation);
					while( rentalLocIter.hasNext() ) {
						RentalLocation r = rentalLocIter.next();
						System.out.println( r );     

						System.out.print("		Vehicle of Rental Locations:");

						Iterator<Vehicle> vehicleRentalLocationIterator =   objectLayer.restoreVehicleRentalLocation(r);
						if (!vehicleRentalLocationIterator.hasNext()){
							System.out.println(" No Vehicle parked here.");
						}
						else
							while(vehicleRentalLocationIterator.hasNext()){
								Vehicle vrl = vehicleRentalLocationIterator.next();
								System.out.println();
								System.out.println("			" + vrl);	

							}

					}
					System.out.println();
					
				}
				else{
					System.out.println("	Cannot locate the Hertz Rental Location object.");
				}

			}
			else{
				System.out.println("	Cannot locate the Ford Vehicle object.");
			}



		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		finally {
			// close the connection
			try {
				conn.close();
			}
			catch( Exception e ) {
				System.err.println( "Exception: " + e );
			}

		}

	}


	public static void deleteData(){
		Connection conn = null;
		ObjectLayerImpl objectLayer = null;
		PersistenceLayer persistence = null;


		// get a database connection
		try {
			conn = DbUtils.connect();
		} 
		catch (Exception seq) {
			System.err.println( "restoreTest: Unable to obtain a database connection" );
		}

		objectLayer = new ObjectLayerImpl();
		persistence = new PersistenceLayerImpl( conn, objectLayer ); 
		objectLayer.setPersistence(persistence);




		try{
			System.out.println("*** Start deleting record ***");
			System.out.println();

			System.out.println("Comment objects:");
			Comment searchForComment = objectLayer.createComment();
			Iterator<Comment> commentIter = objectLayer.findComment(searchForComment);
			while( commentIter.hasNext() ) {
				Comment c = commentIter.next();
				objectLayer.deleteComment(c);
				System.out.println("	deleted.");
			}
			System.out.println();

			System.out.println("Retal objects:");
			Rental searchForRental = objectLayer.createRental();
			Iterator<Rental> rentalIter = objectLayer.findRental(searchForRental);
			while( rentalIter.hasNext() ) {
				Rental r = rentalIter.next();
				objectLayer.deleteRental(r);     
				System.out.println("	deleted.");
			}
			System.out.println();

			System.out.println("Reservation objects:");
			Reservation searchForReservation = objectLayer.createReservation();
			Iterator<Reservation> reservationIter = objectLayer.findReservation(searchForReservation);
			while( reservationIter.hasNext() ) {
				Reservation r = reservationIter.next();
				objectLayer.deleteReservation(r);
				System.out.println("	deleted.");

			}
			System.out.println();

			System.out.println("Hourly Price objects:");
			HourlyPrice searchForHourlyPrice = objectLayer.createHourlyPrice();
			Iterator<HourlyPrice> hourlyPriceIter = objectLayer.findHourlyPrice(searchForHourlyPrice);
			while( hourlyPriceIter.hasNext() ) {
				HourlyPrice h = hourlyPriceIter.next();
				objectLayer.deleteHourlyPrice(h);
				System.out.println("	deleted.");

			}
			System.out.println();

			System.out.println("Vehicle objects:");
			Vehicle searchForVehicle = objectLayer.createVehicle();
			Iterator<Vehicle> vehicleIter = objectLayer.findVehicle(searchForVehicle);
			while( vehicleIter.hasNext() ) {
				Vehicle v = vehicleIter.next();
				objectLayer.deleteVehicle(v);
				System.out.println("	deleted.");
			}
			System.out.println();

			System.out.println("Vehicle Type objects:");
			VehicleType searchForVehicleType = objectLayer.createVehicleType();
			Iterator<VehicleType> vehicleTypeIter = objectLayer.findVehicleType(searchForVehicleType);
			while( vehicleTypeIter.hasNext() ) {
				VehicleType v = vehicleTypeIter.next();
				objectLayer.deleteVehicleType(v);
				System.out.println("	deleted.");
			}
			System.out.println();

			System.out.println("Rental Location objects:");
			RentalLocation searchForRentalLocation = objectLayer.createRentalLocation();
			Iterator<RentalLocation> rentalLocationIter = objectLayer.findRentalLocation(searchForRentalLocation);
			while( rentalLocationIter.hasNext() ) {
				RentalLocation r = rentalLocationIter.next();
				objectLayer.deleteRentalLocation(r);
				System.out.println("	deleted.");
			}
			System.out.println();

			System.out.println("Customer objects:");
			Customer searchForCustomers = objectLayer.createCustomer();
			Iterator<Customer> customerIter = objectLayer.findCustomer(searchForCustomers);
			while( customerIter.hasNext() ) {
				Customer c = customerIter.next();
				c.setUserStatus(UserStatus.REMOVED);
				objectLayer.storeCustomer(c);
				System.out.println("	deleted.");

			}
			System.out.println();

			System.out.println("Administrator objects:");
			Administrator searchForAdministrator = objectLayer.createAdministrator(); 
			Iterator<Administrator> administratorIter = objectLayer.findAdministrator(searchForAdministrator);
			while( administratorIter.hasNext() ) {
				Administrator a = administratorIter.next();
				objectLayer.deleteAdministrator(a); 
				System.out.println("	deleted.");
			}
			System.out.println();

		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		finally {
			// close the connection
			try {
				conn.close();
			}
			catch( Exception e ) {
				System.err.println( "Exception: " + e );
			}
		}
	}
}


