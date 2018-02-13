package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@WebServlet("/CreateReservation")
public class CreateReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;


	static  String            templateDir = "WEB-INF/templates";
	static  String            templateName = "CreateReservation.ftl";
	static  String            resultTemplateName = "CreateReservationConfirmation.ftl";

	private Configuration     cfg;

	public void init() 
	{

		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading( getServletContext(), "WEB-INF/templates" );
	}


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		Template               resultTemplate = null;
		BufferedWriter         toClient = null;
		LogicLayer             logicLayer = null;

		List<VehicleType>           lstVehicleType = null;
		List<RentalLocation>           lstRentalLocation = null;

		List<List<Object>>     vehicleTypes = null;
		List<List<Object>>     rentalLocations = null;

		List<Object>           vehicleType = null;
		List<Object>           rentalLocation = null;

		HttpSession            httpSession;
		Session                session;
		String                 ssid;

		try {
			resultTemplate = cfg.getTemplate( templateName );
		} 
		catch (IOException e) {
			throw new ServletException( 
					"Can't load template in: " + templateDir + ": " + e.toString());
		}

		toClient = new BufferedWriter(
				new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() )
				);

		res.setContentType("text/html; charset=" + resultTemplate.getEncoding());

		httpSession = req.getSession();
		if( httpSession == null ) {       // not logged in!
			RARError.error( cfg, toClient, "Session expired or illegal; please log in" );
			return;
		}

		ssid = (String) httpSession.getAttribute( "ssid" );
		if( ssid == null ) {       // assume not logged in!
			RARError.error( cfg, toClient, "Session expired or illegal; please log in" );
			return;
		}

		session = SessionManager.getSessionById( ssid );
		if( session == null ) {
			RARError.error( cfg, toClient, "Session expired or illegal; please log in" );
			return; 
		}

		logicLayer = session.getLogicLayer();
		if( logicLayer == null ) {
			RARError.error( cfg, toClient, "Session expired or illegal; please log in" );
			return; 
		}

		Map<String,Object> root = new HashMap<String,Object>();

		rentalLocations = new LinkedList<List<Object>>();
		vehicleTypes = new LinkedList<List<Object>>();

		try {
			lstVehicleType = logicLayer.findVehicleType();
			lstRentalLocation = logicLayer.findRentalLocation();

		} 
		catch( Exception e ) {
			RARError.error( cfg, toClient, e );
			return;
		}
		root.put( "rentalLocations", rentalLocations );
		root.put( "vehicleTypes", vehicleTypes );
		//
		for( VehicleType v : lstVehicleType) {

			vehicleType = new LinkedList<Object>();
			vehicleType.add(v.getId());
			vehicleType.add(v.getType());
			vehicleTypes.add( vehicleType );
		}


		for( RentalLocation r : lstRentalLocation) {

			rentalLocation = new LinkedList<Object>();
			rentalLocation.add(r.getId());
			rentalLocation.add(r.getName());
			rentalLocation.add(r.getAddress());
			rentalLocations.add( rentalLocation );
		}


		try {
			resultTemplate.process( root, toClient );
			toClient.flush();
		} 
		catch (TemplateException e) {
			throw new ServletException( "Error while processing FreeMarker template", e);
		}

		toClient.close();


	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Template       resultTemplate = null;
		BufferedWriter toClient = null;

		String 			str_rentalDuration = null;
		String 			str_vehicleTypeId = null;
		String 			str_rentalLocationId = null;
		String 			str_pickupTime = null;
		String 			str_pickDate = null;
		String 			str_pickTime = null;

		int				rentalDuration;
		long 			vehicleTypeId;
		long 			rentalLocationId;
		Date			pickupTime = null;
		long 			customerId;
		String 			CCNumber = null;

		List<Vehicle>   lstVehicle;

		List<List<Object>>  vehicles = null;
		List<Object>        vehicle = null;


		List<HourlyPrice>   lstHourlyPrice;

		List<List<Object>>  hourlyPrices = null;
		List<Object>        hourlyPrice = null;

		List<Customer>   lstCustomer;

		List<List<Object>>  customers = null;
		List<Object>        customer = null;

		LogicLayer     logicLayer = null;
		HttpSession    httpSession;
		Session        session;
		String         ssid;



		try {
			resultTemplate = cfg.getTemplate( resultTemplateName );
		} 
		catch (IOException e) {
			throw new ServletException( 
					"Can't load template in: " + templateDir + ": " + e.toString());
		}

		toClient = new BufferedWriter(
				new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() )
				);

		res.setContentType("text/html; charset=" + resultTemplate.getEncoding());

		httpSession = req.getSession();
		if( httpSession == null ) {       // assume not logged in!
			RARError.error( cfg, toClient, "Session expired or illegal; please log in" );
			return;
		}

		ssid = (String) httpSession.getAttribute( "ssid" );
		if( ssid == null ) {       // not logged in!
			RARError.error( cfg, toClient, "Session expired or illegal; please log in" );
			return;
		}

		session = SessionManager.getSessionById( ssid );
		if( session == null ) {
			RARError.error( cfg, toClient, "Session expired or illegal; please log in" );
			return; 
		}

		logicLayer = session.getLogicLayer();
		if( logicLayer == null ) {
			RARError.error( cfg, toClient, "Session expired or illegal; please log in" );
			return; 
		}

		customerId = session.getUser().getId();

		str_pickupTime =req.getParameter( "pickupTime" );
		str_rentalDuration =req.getParameter( "duration" );
		str_rentalLocationId = req.getParameter( "rentalLocation" );
		str_vehicleTypeId = req.getParameter("vehicleType");   
		str_pickDate = req.getParameter("pickdate");   
		str_pickTime = req.getParameter("picktime");   

		System.out.println(str_pickupTime);
		System.out.println(str_pickTime);
		
		if( str_pickTime == null || str_pickDate == null || str_rentalDuration == null || str_rentalLocationId == null || str_vehicleTypeId == null ) {
			RARError.error( cfg, toClient, "Unspecified field(s)" );
			return;
		}

		Date date;
		try {

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			date = format.parse(str_pickDate + " " + str_pickTime);

			rentalDuration = Integer.parseInt(str_rentalDuration);
			rentalLocationId = Integer.parseInt(str_rentalLocationId);
			vehicleTypeId = Integer.parseInt(str_vehicleTypeId);

		}
		catch( Exception e ) {
			RARError.error( cfg, toClient, "Data is in wrong format." );
			return;
		}

		String rentalLocationName = null;
		String rentalLocationAddres = null;
		String vehicleTypeName = null;


		Map<String,Object> root = new HashMap<String,Object>();

		try {		
			
			//if there is vehicle of vehicleType in that rental location
			//if there is enough vehicle Type in the rental location
					
			//check if there is reservation for that vehicle Type
//			List<Reservation> lstCheckReseration = new LinkedList<Reservation>();
//			lstCheckReseration = logicLayer.findReservation(rentalLocationId, vehicleTypeId);
//			for (Reservation r : lstCheckReseration){
//											
//				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//				
//				System.out.println("checking reservation date and time");
//				if (dateFormat.format(date) == dateFormat.format(r.getPickupTime())){
//					Calendar start = Calendar. getInstance();
//					start.setTime(r.getPickupTime());
//					
//					Calendar end = Calendar. getInstance();
//					end.setTime(r.getPickupTime());
//					end.add(Calendar.HOUR, r.getRentalDuration());
//					
//					Calendar compare = Calendar. getInstance();
//					compare.setTime(date);
//					
//					Date time = compare.getTime();
//					
//					if (time.after(start.getTime()) && time.before(end.getTime()) ){
//						
//						//time confiolict , so not avalable
//						
//					}					
//					
//				}
//			}

			//////////////////////////////////

			lstVehicle = logicLayer.restoreVehicleRentalLocation(rentalLocationId, vehicleTypeId);
			vehicles = new LinkedList<List<Object>>();
			root.put( "vehicles", vehicle );

			for (Vehicle v : lstVehicle){

				
				vehicle = new LinkedList<Object>();	
				vehicle.add(v.getId());
				vehicle.add(v.getMake());
				vehicle.add(v.getModel());
				vehicle.add(v.getYear());
				vehicle.add(v.getRegistrationTag());
				vehicle.add(v.getLastServiced());
				vehicle.add(v.getCondition());
				vehicle.add(v.getRentalLocation().getId());
				vehicle.add(v.getVehicleType().getId());

				rentalLocationAddres = v.getRentalLocation().getAddress();
				rentalLocationName = v.getRentalLocation().getName();

				vehicleTypeName = v.getVehicleType().getType();

				vehicles.add(vehicle); 				
			}


			//Price Calculation
			lstHourlyPrice = logicLayer.restoreVehicleTypeHourlyPrice(vehicleTypeId);
			hourlyPrices = new LinkedList<List<Object>>();


			int totalPrice = 0;

			for (HourlyPrice h : lstHourlyPrice){
//				System.out.println(h.getId());
				if (h.getMinHours() <= rentalDuration && h.getMaxHours() >= rentalDuration){
					hourlyPrice = new LinkedList<Object>();	
					hourlyPrice.add(h.getId());
					hourlyPrice.add(h.getMinHours());
					hourlyPrice.add(h.getMaxHours());
					hourlyPrice.add(h.getPrice());					

					totalPrice += h.getPrice() * rentalDuration;

					hourlyPrices.add(hourlyPrice); 	

				}
			}
//			System.out.println("nulber of hourlyPrice: " + hourlyPrices.size());
			root.put( "hourlyPrices", hourlyPrices );
			root.put( "totalPrice", totalPrice );

			//customer
			lstCustomer = logicLayer.findCustomer(customerId);
			customers = new LinkedList<List<Object>>();


			for (Customer c : lstCustomer){

				customer = new LinkedList<Object>();	
				customer.add(c.getId());
				customer.add(c.getFirstName());
				customer.add(c.getLastName());

				CCNumber = c.getCreditCardNumber();

				customers.add(customer); 	

			}

			root.put( "customers", customers );
			root.put( "CCNumber", CCNumber );

		} 
		catch ( Exception e ) {
			e.printStackTrace();
			RARError.error( cfg, toClient, e );
			return;
		}

		root.put("rentalLocationId", rentalLocationId);
		root.put("vehicleTypeId", vehicleTypeId);

		root.put("rentalLocationAddres", rentalLocationAddres);
		root.put("rentalLocationName", rentalLocationName);
		root.put("vehicleTypeName", vehicleTypeName);
		root.put("pickupTime", date);
		root.put("rentalDuration", rentalDuration);
		root.put( "vehicles", vehicles );

		try {
			resultTemplate.process( root, toClient );
			toClient.flush();
		} 
		catch (TemplateException e) {
			throw new ServletException( "Error while processing FreeMarker template", e);
		}

		toClient.close();

	}



}
