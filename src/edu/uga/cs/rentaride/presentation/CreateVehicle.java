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

/**
 * Servlet implementation class CreateHourlyPrice
 */
@WebServlet("/CreateVehicle")
public class CreateVehicle extends HttpServlet {
	private static final long serialVersionUID = 1L;


	static  String            templateDir = "WEB-INF/templates";
	static  String            templateName = "CreateVehicle.ftl";
	static  String            resultTemplateName = "CreateVehicleResult.ftl";

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

		List<VehicleType>      lstvehicletype = null;
		List<List<Object>>     vehicleTypes = null;
		List<Object>           vehicleType = null;

		
		List<RentalLocation>      lstrentallocation = null;
		List<List<Object>>     rentalLocations = null;
		List<Object>           rentalLocation = null;
		
		VehicleType                 vt  = null;
		RentalLocation				rt=null;
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
		
		try {
			lstvehicletype = logicLayer.findVehicleType();
			lstrentallocation = logicLayer.findRentalLocation();
			} 
		catch( Exception e ) {
			RARError.error( cfg, toClient, e );
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
		
		List<String> lstVehicleStatus =  new LinkedList<String>();
		root.put( "lstVehicleStatus", lstVehicleStatus );
		
		for (VehicleStatus vs : VehicleStatus.values()) {
				lstVehicleStatus.add(vs.toString());			 
		}
		
		
		List<String> lstVehicleCondition =  new LinkedList<String>();
		root.put( "lstVehicleCondition", lstVehicleCondition );
		for (VehicleCondition vc : VehicleCondition.values()) {
			lstVehicleCondition.add(vc.toString());			 
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
		Date			lastserviced;
		VehicleStatus	status;
		VehicleCondition condition;
		long 		   vehicleId;
		long           vehicleTypeId;
		long           rentalLocationId;
		
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
        
        Date date;
        try {
//        	vehicleId = Long.parseLong(str_id);        	
        	vehicleTypeId = Long.parseLong( str_vehicletypeId );
        	rentalLocationId = Long.parseLong( str_rentallocationId );
        	year = Integer.parseInt(str_year);
        	mileage = Integer.parseInt(str_mileage);
        	DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			date = format.parse(str_lastserviced);
        	status=VehicleStatus.valueOf(str_status);
        	condition=VehicleCondition.valueOf(str_condition);
        }  
        
        catch( Exception e ) {
//           System.out.print(e);
        	//throw new RARException( "root cause: " + e );
        	RARError.error( cfg, toClient, "Data is in wrong format."  );
            return;
        }
        
        if( vehicleTypeId <= 0 ) {
            RARError.error( cfg, toClient, "Non-positive vehicleType Id: " + vehicleTypeId );
            return;
        }
        if( rentalLocationId <= 0 ) {
            RARError.error( cfg, toClient, "Non-positive rentalLocation Id: " + rentalLocationId );
            return;
        }

        try {
        	vehicleId = logicLayer.storeVehicle(0, vehicleTypeId, str_make, str_model, year, str_registrationtag, mileage, date, rentalLocationId, condition, status);
        	
//        	VehicleId = logicLayer.stor(VehicleId,str_make, str_model, year, str_registrationtag
//         		   ,mileage,lastserviced,status,condition,rentalLocationId,vehicleTypeId);
        } 
        catch ( Exception e ) {
            RARError.error( cfg, toClient, e  );
            return;
        }
        
        Map<String,Object> root = new HashMap<String,Object>();

        // Build the data-model
        //
        root.put( "VehicleId", vehicleId );
//        root.put( "club_id", new Long( club_id ) );

        // Merge the data-model and the template
        //
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
