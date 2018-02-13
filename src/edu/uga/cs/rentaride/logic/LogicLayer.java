package edu.uga.cs.rentaride.logic;


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
import edu.uga.cs.rentaride.session.Session;

public interface LogicLayer  {


	public String login (Session session, String userName, String password )throws RARException;	

	public String adminLogin (Session session, String userName, String password )throws RARException;

	public List<Customer> checkCredentials(String username)
			throws RARException;

	public List<Customer> checkCredentials(String username, String password)
			throws RARException;

	public List<VehicleType> findVehicleType() throws RARException;

	public List<RentalLocation> findRentalLocation() throws RARException;

	//	public List<Vehicle> restoreVehicleRentalLocation(RentalLocation rentalLocation) throws RARException;

	public void logout( String ssid ) throws RARException;

	public List<HourlyPrice> findAllHourlyPrices()throws RARException;

	public long createHourlyPrice(int minHours, int maxHours, int price, long vehicelTypeId ) throws RARException;

	public List<HourlyPrice> findHourlyPrice(long hourlyPriceId, int minHours, int maxHours, int price, long vehicleTypeId) throws RARException;

	public void updateHourlyPrice(long id, int minHours, int maxHours, int price, long vehicelTypeId ) throws RARException;

	public void deleteHourlyPrice( Long id ) throws RARException;

	public List<Reservation> findCustomerReservation(long customerId) throws RARException;

	public List<RentalLocation> findRentalLocation( String name, String address,int capacity ) throws RARException;

	//	public long createReservation(int rentalDuration,long vehicleTypeId, long rentalLocationId ) throws RARException;

	public List<Vehicle>restoreVehicleRentalLocation (long rentalLocationId, long vehicleTypeId) throws RARException;

	public List<HourlyPrice> restoreVehicleTypeHourlyPrice( long vehicleTypeId ) throws RARException;

	public List<Customer> findCustomer( long customerId ) throws RARException;

	public List<Reservation> findReservation(long reservationId, Date pickupTime, int rentalDuration, long customerId, long rentalLocationId, long vehicleTypeId, long rentalId) throws RARException;

	public long createReservation( long vehicleTypeId, long rentalLocationId, long customerId,
			Date pickupTime, int rentalDuration ) throws RARException;

	public void updateReservation( long reservationId, Date pickupTime, int rentalDuration, long customerId, long rentalLocationId, long vehicleTypeId, long rentalId ) throws RARException;

	public void deleteReservation( long reservationId) throws RARException;

	public List<Rental> findRental( long rentalId, long reservationId, long customerId, long vehicleId, Date pickupTime, Date returnTime, int charges ) throws RARException;

	public List<Vehicle> findVehicle(long vehicleId, long vehicleTypeId, String make, String model,
			int year, String registrationTag, int mileage, Date lastServiced, 
			long rentalLocationId, VehicleCondition vehicleCondition, VehicleStatus vehicleStatus ) throws RARException;

	public long createRental( long reservationId, long customerId, long vehicleId, Date pickupTime) throws RARException;

	public void updateVehicle(long vehicleId, long vehicleTypeId, String make, String model,
			int year, String registrationTag, int mileage, Date lastServiced, 
			long rentalLocationId, VehicleCondition vehicleCondition, 
			VehicleStatus vehicleStatus ) throws RARException;
	
	public void updateRental( long rentalId, long reservationId, long customerId, 
			long vehicleId, Date pickupTime,  Date returnTime, int charges) throws RARException;
	
	
	public long storeVehicle(long vehicleId, long vehicleTypeId, String make, String model,
			int year, String registrationTag, int mileage, Date lastServiced, 
			long rentalLocationId, VehicleCondition vehicleCondition, 
			VehicleStatus vehicleStatus ) throws RARException;
	
	public void deleteVehicle(Long id) throws RARException;
	
	public RentalLocation createRentalLocation(String rentalLocation_name, String rentalLocation_address, int capacity) throws RARException;
	

	public void updateRentalLocation(long id, String name,String addr, int capacity ) throws RARException;
	
	public void deleteRentalLocation(long id)throws RARException;
	
	public List<Customer> findCustomer(long customerId, String firstName, String lastName, String userName,String emailAddress,String password,	Date createdDate,String residenceAddress,UserStatus userStatus,Date membershipExpiration, String licenseState,String licenseNumber,String creditCardNumber,Date creditCardExpiration, int isAdmin ) throws RARException;
	
	public void deleteCustomer(Long id) throws RARException;
	
	public List<VehicleType> findVehicleType(long vehicleTypeId, String type)throws RARException;
	
	public long createVehicleType( String name  ) throws RARException;
	
	public void updateVehicleType(long id, String name ) throws RARException;
	
	public void deleteVehicleType(Long id) throws RARException;
	
	public long registercustomer(String firstName,String lastName, 
			String userName,String emailAddress,String residenceAddress, 
			String licenseNumber, String creditcardno, Date creditexpiredate,String licenseState ) throws RARException;
	
	public void updateCustomer(long id, String password, String email, String address, Customer c, UserStatus k, Date d) throws RARException;
	
	public String resetpsw(long id) throws RARException;
	
	public RentARideConfig findParameter() throws RARException;
	
	public void updateParameter(int membershipPrice, int overtimePenalty) throws RARException;
	
}


