package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@WebServlet("/RentVehicle")
public class RentVehicle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static  String            templateDir = "WEB-INF/templates";
	static  String            templateName = "RentVehicle.ftl";

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
		
		List<Reservation>		lstReservation = null;
		List<Vehicle>			lstVehicle = null;
		List<List<Object>>     vehicles = null;
		List<Object>					vehicle = null;
		
		
		String 				   str_reservationId=null;
		long 				   reservationId = 0;
		long 				   rentalLocationId = 0;
		long 				   vehicleTypeId = 0;
		String					rentalLocationName = null;
		String 					vehicleTypeName = null;
		
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

		vehicles = new LinkedList<List<Object>>();
		
		str_reservationId =req.getParameter( "Id" );
		
		if( str_reservationId == null ) {
            RARError.error( cfg, toClient, "Unspecified Id " );
            return;
        }
		
		try {
        	reservationId = Long.parseLong( str_reservationId );        	    
        }
        catch( Exception e ) {
            RARError.error( cfg, toClient, "Data is in wrong format." );
            return;
        }
		
		try {
			//find reservation by Id. get VehicleType and rental Location
			lstReservation =  logicLayer.findReservation(reservationId, null, 0, 0, 0, 0, 0);
			for (Reservation r : lstReservation){
				rentalLocationId = r.getRentalLocation().getId();
				vehicleTypeId = r.getVehicleType().getId();
				rentalLocationName= r.getRentalLocation().getName();
				vehicleTypeName = r.getVehicleType().getType();
				
			}
			
			
			
			//to list Available vehicle right Now
			lstVehicle =  logicLayer.findVehicle(0, vehicleTypeId, null, null, 0, null, 0, null, rentalLocationId, null, VehicleStatus.INLOCATION);
			if (lstVehicle.isEmpty()){
				RARError.error( cfg, toClient, "No Vehicle Available. Please contact Customer Service." );
	            return;
			}
			
		} 
		catch( Exception e ) {
			RARError.error( cfg, toClient, e);
			return;
		}
		
		root.put("reservationId", reservationId);
		
		root.put("rentalLocationName",rentalLocationName);
		root.put("rentalLocationId", rentalLocationId);
		
		root.put("vehicleTypeName", vehicleTypeName);
		root.put("vehicleTypeId", vehicleTypeId);

		root.put( "vehicles", vehicles );
		//
		for( Vehicle v : lstVehicle) {

			vehicle = new LinkedList<Object>();
			vehicle.add(v.getId());
			vehicle.add(v.getMake());
			vehicle.add(v.getMake());
			vehicles.add(vehicle);
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
