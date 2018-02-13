package edu.uga.cs.rentaride.logic.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
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
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.object.impl.ObjectLayerImpl;
import edu.uga.cs.rentaride.persistence.impl.PersistenceLayerImpl;
import edu.uga.cs.rentaride.presentation.FindAllCustomer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;

public class LogicLayerImpl implements LogicLayer{

	private ObjectLayer objectLayer = null;

	public LogicLayerImpl( Connection conn )
	{
		objectLayer = new ObjectLayerImpl();
		PersistenceLayerImpl persistenceLayer = new PersistenceLayerImpl( conn, objectLayer );
		objectLayer.setPersistence( persistenceLayer );
		System.out.println( "LogicLayerImpl.LogicLayerImpl(conn): initialized" );
	}

	public LogicLayerImpl( ObjectLayer objectLayer )
	{
		this.objectLayer = objectLayer;
		System.out.println( "LogicLayerImpl.LogicLayerImpl(objectLayer): initialized" );
	}

	public String login (Session session, String userName, String password )throws RARException{
		System.out.println("Logic LAYER");
		Login_CTRL loginCTRL = new Login_CTRL(objectLayer);

		return loginCTRL.login(session, userName, password);
	}

	public String adminLogin (Session session, String userName, String password )throws RARException{

		Login_CTRL loginCTRL = new Login_CTRL(objectLayer);

		return loginCTRL.adminLogin(session, userName, password);

	}

	public List<Customer> checkCredentials(String username)
			throws RARException{
		Login_CTRL loginCTRL = new Login_CTRL(objectLayer);
		return loginCTRL.checkCredentials(username);

	}

	public List<Customer> checkCredentials(String username, String password)
			throws RARException{
		Login_CTRL loginCTRL = new Login_CTRL(objectLayer);
		return loginCTRL.checkCredentials(username, password);

	}


	public List<VehicleType> findVehicleType() throws RARException{

		FindVehicleType_CTRL vehicleTypeCTRL = new FindVehicleType_CTRL(objectLayer);
		return vehicleTypeCTRL.findVehicleType(0, null);

	}

	public List<RentalLocation> findRentalLocation() throws RARException{
		RentalLocation_CTRL rentalLocationCTRL = new RentalLocation_CTRL(objectLayer);
		return rentalLocationCTRL.findRentalLocation();
	}

	//	public List<Vehicle> restoreVehicleRentalLocation(RentalLocation rentalLocation) throws RARException{
	//		Vehicle_CTRL vehicleCTRL = new Vehicle_CTRL(objectLayer);
	//		return vehicleCTRL.restoreVehicleRentalLocation(rentalLocation);
	//	}

	public void logout(String ssid) throws RARException{
		SessionManager.logout( ssid );
	}
	public List<HourlyPrice> findAllHourlyPrices()
			throws RARException{



		FindHourlyPrice_CTRL hourlyPriceCTRL  = new FindHourlyPrice_CTRL(objectLayer);
		return hourlyPriceCTRL.findHourlyPrice(0, 0, 0, 0, 0);
	}

	public long createHourlyPrice( int minHours, int maxHours, int price, long vehicelTypeId  ) throws RARException{
		CreateHourlyPrice_CTRL ctrlCreateHourlyPrice = new CreateHourlyPrice_CTRL( objectLayer );
		return ctrlCreateHourlyPrice.createHourlyPrice(minHours, maxHours, price, vehicelTypeId);
	}

	public List<HourlyPrice> findHourlyPrice(long hourlyPriceId, int minHours, int maxHours, int price, long vehicleTypeId) throws RARException{
		FindHourlyPrice_CTRL ctrlHourlyPriceFind = new FindHourlyPrice_CTRL(objectLayer);
		return ctrlHourlyPriceFind.findHourlyPrice(hourlyPriceId, minHours, maxHours, price, vehicleTypeId);
	}

	public void updateHourlyPrice(long id, int minHours, int maxHours, int price, long vehicelTypeId  ) throws RARException{
		UpdateHourlyPrice_CTRL ctrlHourlyPriceUpdate = new UpdateHourlyPrice_CTRL(objectLayer);
		ctrlHourlyPriceUpdate.updateHourlyPrice(id, minHours, maxHours, price, vehicelTypeId);
	}

	public void deleteHourlyPrice( Long id ) throws RARException{
		DeleteHourlyPrice_CTRL ctrlHourlyPriceDelete = new DeleteHourlyPrice_CTRL(objectLayer);
		ctrlHourlyPriceDelete.deleteHourlyPrice(id);

	}

	public List<Reservation> findCustomerReservation(long customerId) throws RARException{
		FindReservation_CTRL ctrlReservationFind = new FindReservation_CTRL(objectLayer);
		return ctrlReservationFind.findCustomerReservation(customerId);
	}

	public List<RentalLocation> findRentalLocation( String name, String address,int capacity ) throws RARException{
		FindRentalLocation_CTRL ctrlRentalLocationFind = new FindRentalLocation_CTRL(objectLayer);
		return ctrlRentalLocationFind.findRentalLocation(name, address, capacity);
	}

	//	public long createReservation(int rentalDuration,long vehicleTypeId, long rentalLocationId ) throws RARException{
	//		
	//	}

	public List<Vehicle>restoreVehicleRentalLocation (long rentalLocationId, long vehicleTypeId) throws RARException{	
		CreateReservation_CTRL ctrlReservationCreate = new CreateReservation_CTRL(objectLayer);
		return ctrlReservationCreate.restoreVehicleVehicleTypeRentalLocation(rentalLocationId, vehicleTypeId);		
	}

	public List<HourlyPrice> restoreVehicleTypeHourlyPrice( long vehicleTypeId ) throws RARException{
		CreateReservation_CTRL ctrlReservationCreate = new CreateReservation_CTRL(objectLayer);
		return ctrlReservationCreate.restoreVehicleTypeHourlyPrice(vehicleTypeId);


	}

	public List<Customer> findCustomer(long customerId) throws RARException{
		//		CreateReservation_CTRL ctrlReservationCreate = new CreateReservation_CTRL(objectLayer);
		//		return ctrlReservationCreate.findCustomer(customerId);
		FindCustomer_CTRL ctrl = new FindCustomer_CTRL(objectLayer);
		return ctrl.findCustomer(customerId, null, null, null, null, null, null, null, null, null, null, null, null, null, 0);
		

	}

	public List<Reservation> findReservation(long reservationId, Date pickupTime, int rentalDuration, long customerId, long rentalLocationId, long vehicleTypeId, long rentalId) throws RARException{
		FindReservation_CTRL ctrlReservationFind = new FindReservation_CTRL(objectLayer);
		return ctrlReservationFind.findReservation(reservationId,pickupTime,rentalDuration, customerId,rentalLocationId,vehicleTypeId,rentalId);
	}

	public long createReservation( long vehicleTypeId, long rentalLocatioIdn, long customerId,
			Date pickupTime, int rentalDuration ) throws RARException{

		CreateReservation_CTRL ctrlReservationCreate = new CreateReservation_CTRL(objectLayer);
		return ctrlReservationCreate.createReservation(vehicleTypeId, rentalLocatioIdn, customerId, pickupTime, rentalDuration);			

	}

	public void updateReservation( long reservationId, Date pickupTime, int rentalDuration, long customerId, long rentalLocationId, long vehicleTypeId, long rentalId ) throws RARException{
		UpdateReservation_CTRL ctrl = new UpdateReservation_CTRL(objectLayer);
		ctrl.updateReservation(reservationId, pickupTime, rentalDuration, customerId, rentalLocationId, vehicleTypeId, rentalId);
	}

	public void deleteReservation( long reservationId) throws RARException{
		DeleteReservation_CTRL ctrl = new DeleteReservation_CTRL(objectLayer);
		ctrl.deleteReservation(reservationId);
	}

	public List<Rental> findRental( long rentalId, long reservationId, long customerId, long vehicleId, Date pickupTime, Date returnTime, int charges ) throws RARException{
		FindRental_CTRL ctrl = new FindRental_CTRL(objectLayer);
		return ctrl.findRental(rentalId, reservationId, customerId, vehicleId, pickupTime, returnTime, charges);

	}


	public List<Vehicle> findVehicle(long vehicleId, long vehicleTypeId, String make, String model,
			int year, String registrationTag, int mileage, Date lastServiced, 
			long rentalLocationId, VehicleCondition vehicleCondition, VehicleStatus vehicleStatus ) throws RARException{

		FindVehicle_CTRL ctrl = new FindVehicle_CTRL(objectLayer);
		return ctrl.findVehicle(vehicleId, vehicleTypeId, make, model, year, registrationTag, mileage, lastServiced, rentalLocationId, vehicleCondition, vehicleStatus);


	}
	
	public long createRental( long reservationId, long customerId, long vehicleId, Date pickupTime) throws RARException{
		CreateRental_CTRL ctrl = new CreateRental_CTRL(objectLayer);
		return ctrl.createRental(reservationId, customerId, vehicleId, pickupTime);
		
	}
	
	public void updateVehicle(long vehicleId, long vehicleTypeId, String make, String model,
			int year, String registrationTag, int mileage, Date lastServiced, 
			long rentalLocationId, VehicleCondition vehicleCondition, 
			VehicleStatus vehicleStatus ) throws RARException{
		UpdateVehicle_CTRL ctrl = new UpdateVehicle_CTRL(objectLayer);
		ctrl.updateVehicle(vehicleId, vehicleTypeId, make, model, year, registrationTag, mileage, lastServiced, rentalLocationId, vehicleCondition, vehicleStatus);
		
	}
	
	public void updateRental( long rentalId, long reservationId, long customerId, 
			long vehicleId, Date pickupTime,  Date returnTime, int charges) throws RARException{
		UpdateRental_CTRL ctrl = new UpdateRental_CTRL(objectLayer);
		ctrl.updateRental(rentalId, reservationId, customerId, vehicleId, pickupTime, returnTime, charges);
		
		
	}
	
	public long storeVehicle(long vehicleId, long vehicleTypeId, String make, String model,
			int year, String registrationTag, int mileage, Date lastServiced, 
			long rentalLocationId, VehicleCondition vehicleCondition, 
			VehicleStatus vehicleStatus ) throws RARException{
		CreateVehicle_CTRL ctrl = new CreateVehicle_CTRL(objectLayer);
		return ctrl.storeVehicle(vehicleId, vehicleTypeId, make, model, year, registrationTag, mileage, lastServiced, rentalLocationId, vehicleCondition, vehicleStatus);
				
	}
	
	public void deleteVehicle(Long id) throws RARException{
		DeleteVehicle_CTRL ctrl = new DeleteVehicle_CTRL(objectLayer);
		ctrl.deleteVehicle(id);
		
				
	}
	
	public RentalLocation createRentalLocation(String rentalLocation_name, String rentalLocation_address,
			int capacity)throws RARException {
		CreateRentalLocation_CTRL ctrlRentalLocationCreate=new CreateRentalLocation_CTRL(objectLayer);
		
		
		return ctrlRentalLocationCreate.createRentalLocation(rentalLocation_name, rentalLocation_address, capacity);
	}
	
	public void updateRentalLocation(long id,String rentallocation_name, String rentallocation_address, int capacity) throws RARException {
		// TODO Auto-generated method stub
		UpdateRentalLocation_CTRL  ctrlrentallocationupdate=new UpdateRentalLocation_CTRL(objectLayer);
		ctrlrentallocationupdate.updateRentalLocation(id,rentallocation_name, rentallocation_address, capacity);
		
	}
	
	public void deleteRentalLocation(long id)throws RARException{
		DeleteRentalLocation_CTRL ctrlRentalLocationDelete = new DeleteRentalLocation_CTRL(objectLayer);
		ctrlRentalLocationDelete.deleteRentalLocation(id);
		
	}
	
	public List<Customer> findCustomer(long customerId, String firstName, String lastName, String userName,String emailAddress,String password,	Date createdDate,String residenceAddress,UserStatus userStatus,Date membershipExpiration, String licenseState,String licenseNumber,String creditCardNumber,Date creditCardExpiration, int isAdmin ) throws RARException{
		FindCustomer_CTRL ctrl = new FindCustomer_CTRL(objectLayer);
		return ctrl.findCustomer(customerId, firstName, lastName, userName, emailAddress, password, createdDate, residenceAddress, userStatus, membershipExpiration, licenseState, licenseNumber, creditCardNumber, creditCardExpiration, 0);
		
	}
	
	public void deleteCustomer(Long id) throws RARException{
		DeleteCustomer_CTRL ctrl = new DeleteCustomer_CTRL(objectLayer);
		ctrl.deleteCustomer(id);
		
	}
	
	public List<VehicleType> findVehicleType(long vehicleTypeId, String type)throws RARException{
		FindVehicleType_CTRL ctrl = new FindVehicleType_CTRL(objectLayer);
		return ctrl.findVehicleType(vehicleTypeId, type);
	}
	
	public long createVehicleType( String name  ) throws RARException{
		CreateVehicleType_CTRL ctrl = new CreateVehicleType_CTRL(objectLayer);
		return ctrl.createVehicleType(name);
	}
	
	public void updateVehicleType(long id, String name ) throws RARException{
		UpdateVehicleType_CTRL ctrl = new UpdateVehicleType_CTRL(objectLayer);
		ctrl.updateVehicleType(id, name);
	}
	
	public void deleteVehicleType(Long id) throws RARException{
		DeleteVehicleType_CTRL ctrl = new DeleteVehicleType_CTRL(objectLayer);
		ctrl.deleteVehicleType(id);
	}
	
	public long registercustomer(String firstName,String lastName, 
			String userName,String emailAddress,String residenceAddress, 
			String licenseNumber, String creditcardno, Date creditexpiredate,String licenseState ) throws RARException{
		RegisterCustomer_CTRL ctrl = new RegisterCustomer_CTRL(objectLayer);
		return ctrl.registercustomer(firstName, lastName, userName, emailAddress, residenceAddress, licenseNumber, creditcardno, creditexpiredate, licenseState);
		
	}
	
	public void updateCustomer(long id, String password, String email, String address, Customer c, UserStatus k, Date d) throws RARException{
		UpdateCustomer_CTRL ctrl = new UpdateCustomer_CTRL(objectLayer);
		ctrl.updateCustomer(id, password, email, address, c, k, d);
	}
	
	public String resetpsw(long id) throws RARException{
		ResetPSW_CTRL ctrl = new ResetPSW_CTRL(objectLayer);
		return ctrl.resetpsw(id);
	}
	
	public RentARideConfig findParameter() throws RARException{
		FindParameters_CTRL ctrl = new FindParameters_CTRL(objectLayer);
		return ctrl.findParameter();
	}
	
	public void updateParameter(int membershipPrice, int overtimePenalty) throws RARException{
		UpdateParameter_CTRL ctrl = new UpdateParameter_CTRL(objectLayer);
		ctrl.updateParameter(membershipPrice, overtimePenalty);
	}
}
