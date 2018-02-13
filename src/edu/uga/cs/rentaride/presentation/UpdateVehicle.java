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
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@WebServlet("/UpdateVehicle")
public class UpdateVehicle extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	static  String            templateDir = "WEB-INF/templates";
	static  String            templateName = "UpdateVehicle.ftl";
//	static  String            resultTemplateName = "UpdateHourlyPriceResult.ftl";

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
		List<Vehicle>           lstvehicle = null;
		List<List<Object>>     vehicles = null;
		List<Object>           vehicle = null;
		
		List<VehicleType>      lstvehicletype = null;
		List<List<Object>>     vehicleTypes = null;
		List<Object>           vehicleType = null;
		
		List<RentalLocation>      lstrentallocation = null;
		List<List<Object>>     rentalLocations = null;
		List<Object>           rentalLocation = null;
		
		String 				   str_Id=null;
		long 				   Id;
		
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

		vehicleTypes = new LinkedList<List<Object>>();
		rentalLocations = new LinkedList<List<Object>>();
		vehicles= new LinkedList<List<Object>>();
		
		str_Id =req.getParameter( "Id" );
		
		if( str_Id == null ) {
            RARError.error( cfg, toClient, "Unspecified Id " );
            return;
        }
		
		try {
        	Id = Long.parseLong( str_Id );        	    
        }
        catch( Exception e ) {
            RARError.error( cfg, toClient, "Data is in wrong format." );
            return;
        }
		
		try {
			lstvehicletype = logicLayer.findVehicleType();
			lstrentallocation = logicLayer.findRentalLocation();
			lstvehicle = logicLayer.findVehicle(Id, 0, null, null, 0, null, 0, null, 0, null, null);
			
		} 
		catch( Exception e ) {
			RARError.error( cfg, toClient, e);
			return;
		}

		root.put( "vehicleTypes", vehicleTypes );
		//
		for( VehicleType v : lstvehicletype) {

			vehicleType = new LinkedList<Object>();
			vehicleType.add(v.getId());
			vehicleType.add(v.getType());
			vehicleTypes.add( vehicleType );
		}
		
		
		root.put( "rentalLocations", rentalLocations );
		//
		for( RentalLocation h : lstrentallocation) {

			rentalLocation = new LinkedList<Object>();
			rentalLocation.add(h.getId());
			rentalLocation.add(h.getName());
			rentalLocation.add(h.getAddress());
			rentalLocation.add(h.getCapacity());
			rentalLocations.add(rentalLocation);
			
		}
		
		root.put( "vehicles", vehicles );
		//
		for( Vehicle v : lstvehicle) {

			vehicle = new LinkedList<Object>();
        	vehicle.add(v.getId());
        	vehicle.add(v.getMake());
        	vehicle.add(v.getModel());
        	vehicle.add(v.getYear());
        	vehicle.add(v.getRegistrationTag());
        	vehicle.add(v.getMileage());
        	vehicle.add(v.getLastServiced());
        	vehicle.add(v.getStatus());
        	vehicle.add(v.getCondition());
        	vehicle.add(v.getVehicleType().getId());
        	vehicle.add(v.getRentalLocation().getId());
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
	
	/*
	 * 
	 *          
            	vehicle.add(v.getMake());
            	vehicle.add(v.getModel());
            	vehicle.add(v.getYear());
            	vehicle.add(v.getRegistrationTag());
            	vehicle.add(v.getMileage());
            	vehicle.add(v.getLastServiced());
            	vehicle.add(v.getStatus());
            	vehicle.add(v.getCondition());
            	vehicle.add(v.getVehicleType().getType());
            	vehicle.add(v.getRentalLocation().getAddress());
            	vehicles.add(vehicle);(non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Template       resultTemplate = null;
		BufferedWriter toClient = null;
		String			str_id = null;
		String			str_make = null;
		String 			str_model= null;
		String			str_year = null;
		String 			str_registrationtag = null;
		String 			str_mileage = null;
		String 			str_lastserviced= null;
		String 			str_status = null;
		String 			str_condition = null;
		String 			str_vehicletypeId = null;
		String 			str_rentallocationId = null;
		
		int		       	year = 0;
		int 			mileage=0;
		Date			lastserviced=new Date();
		VehicleStatus	status;
		VehicleCondition condition;
		

		long 		   id;
		long           vehicleTypeId;
		long           rentalLocationId;
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
        /*
         * 		String			str_id = null;
		String			str_make = null;
		String 			str_model= null;
		String			str_year = null;
		String 			str_registrationtag = null;
		String 			str_mileage = null;
		String 			str_lastserviced= null;
		String 			str_status = null;
		String 			str_condition = null;
		String 			str_vehicletypeId = null;
		String 			str_rentallocationId = null;
         * 
         * 
         */
        str_id =req.getParameter( "id" );
        str_make =req.getParameter( "make" );
        str_model = req.getParameter( "model" );
        str_year = req.getParameter( "year" );
        str_registrationtag = req.getParameter( "registrationtag" );
        str_mileage = req.getParameter( "mileage" );
        str_lastserviced = req.getParameter( "lastserviced" );
        str_status = req.getParameter( "status" );
        str_condition = req.getParameter( "condition" );
        str_vehicletypeId = req.getParameter( "vehicletypeId" );
        str_rentallocationId = req.getParameter( "rentallocationId" );
        
        if( str_make == null || str_model == null || str_year==null || str_registrationtag==null ) {
            RARError.error( cfg, toClient, "Unspecified make or model or year or registrationtag " );
            return;
        }

        if( str_vehicletypeId == null ) {
            RARError.error( cfg, toClient, "Unspecified VehicleTypeId" );
            return;
        }
        
        if( str_rentallocationId == null ) {
            RARError.error( cfg, toClient, "Unspecified RentalLocationId" );
            return;
        }
        
        try {
        	id = Long.parseLong(str_id);
        	vehicleTypeId = Long.parseLong( str_vehicletypeId );
        	rentalLocationId = Long.parseLong( str_rentallocationId );
        	year = Integer.parseInt(str_year);
        	mileage = Integer.parseInt(str_mileage);
    	    DateFormat df = new SimpleDateFormat("MMM dd,yyyy");
    	    try{
    	    	lastserviced = df.parse(str_lastserviced);
    	    }
    	    catch ( Exception ex ){
    	        System.out.println(ex);
    	    }
        	status=VehicleStatus.valueOf(str_status);
        	condition=VehicleCondition.valueOf(str_condition);
        	
        }
        catch( Exception e ) {
            RARError.error( cfg, toClient, "Data is in wrong format." );
            return;
        }
        if( id <= 0 ) {
            RARError.error( cfg, toClient, "Non-positive id: " + id );
            return;
        }
        
        if( vehicleTypeId <= 0 ) {
            RARError.error( cfg, toClient, "Non-positive vehicle Type Id: " + vehicleTypeId );
            return;
        }
        if( rentalLocationId <= 0 ) {
            RARError.error( cfg, toClient, "Non-positive rentalLocation Id: " + rentalLocationId );
            return;
        }

        try {
           
           logicLayer.updateVehicle(id, vehicleTypeId, str_make, str_model, year, str_registrationtag, mileage, lastserviced, rentalLocationId, condition, status);
        } 
        
        
        catch ( Exception e ) {
            RARError.error( cfg, toClient, e );
            return;
        }
                
        String contextPath= "http://localhost:8080/rentaride-assocs/FindAllVehicle";
		res.sendRedirect(res.encodeRedirectURL(contextPath));

	}
	
}
