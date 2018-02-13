package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

import com.sun.mail.handlers.message_rfc822;

import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@WebServlet("/ReturnVehicle")
public class ReturnVehicle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static  String            templateDir = "WEB-INF/templates";
	static  String            templateName = "ReturnVehicle.ftl";

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

		List<Rental>			lstRental = null;
		List<List<Object>>     	rentals = null;
		Rental			rental = null;

		String 				   str_rentalId=null;
		
		long 				   rentalId = 0;
		
		long 				   rentalLocationId = 0;
		long 				   vehicleTypeId = 0;
		String					rentalLocationName = null;
		String 					vehicleTypeName = null;
		int 					charges = 0;
		String message= null;

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

		rentals = new LinkedList<List<Object>>();

		str_rentalId =req.getParameter( "Id" );

		if( str_rentalId == null ) {
			RARError.error( cfg, toClient, "Unspecified Id " );
			return;
		}

		try {
			rentalId = Long.parseLong( str_rentalId );        	    
		}
		catch( Exception e ) {
			RARError.error( cfg, toClient, "Data is in wrong format." );
			return;
		}

		try {
			
			//find rental by Id
			lstRental =  logicLayer.findRental(rentalId, 0, 0, 0, null, null, 0);				
			rental = lstRental.get(0);					
			
			//Now.
			Date returnTime = new Date();
			Date reservationPickupTime = rental.getReservation().getPickupTime();
			int reservationDuration = rental.getReservation().getRentalDuration();
			
			Calendar cReservation = Calendar.getInstance();
			Calendar cActual = Calendar.getInstance();
			
			cReservation.setTime(reservationPickupTime);
			cReservation.add(Calendar.HOUR, reservationDuration);
			
			cActual.setTime(returnTime);
			
			int compare = cReservation.compareTo(cActual);
			
			message = "Thank you for returning the car. Good Bye!";
			if (compare == -1){
				message = "You passed the time. We will charge you.";
				charges = 20;
			}
										
			logicLayer.updateRental(rentalId, 0, 0, 0, null, returnTime, charges);				

		} 
		catch( Exception e ) {
			RARError.error( cfg, toClient, e);
			return;
		}

		root.put("message", message);

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

		String str_rentalLocationId = null;
		String str_vehicleTypeId = null;
		String str_reservationId = null;
		String str_vehicleId = null;

		long rentalLocationId;
		long vehicleTypeId;
		long reservationId;
		long vehicleId;
		long customerId;

		LogicLayer     logicLayer = null;
		HttpSession    httpSession;
		Session        session;
		String         ssid;

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

		str_rentalLocationId =req.getParameter( "rentalLocationId" );
		str_vehicleTypeId =req.getParameter( "vehicleTypeId" );
		str_reservationId = req.getParameter( "reservationId" );
		str_vehicleId = req.getParameter( "vehicleId" );

		try {
			rentalLocationId = Long.parseLong(str_rentalLocationId);
			vehicleTypeId = Long.parseLong( str_vehicleTypeId );
			reservationId = Long.parseLong( str_reservationId );
			vehicleId = Long.parseLong(str_vehicleId);

		}
		catch( Exception e ) {
			RARError.error( cfg, toClient, "Data is in wrong format." );
			return;
		}


		try {
			//date time save
			Date pickupTime = new Date();

			long rentalId = logicLayer.createRental(reservationId, customerId, vehicleId, pickupTime);
			//vehicle Status should updated
			logicLayer.updateVehicle(vehicleId, 0, null, null, 0, null, 0, null, 0, null, VehicleStatus.INRENTAL);
			logicLayer.updateReservation(reservationId, null, 0, 0, 0, 0, rentalId);

		} 
		catch ( Exception e ) {
			RARError.error( cfg, toClient, e );
			return;
		}

		String contextPath= "http://localhost:8080/rentaride-assocs/Reservation";
		res.sendRedirect(res.encodeRedirectURL(contextPath));

	}    



}
