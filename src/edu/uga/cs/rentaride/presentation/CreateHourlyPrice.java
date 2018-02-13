package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
@WebServlet("/CreateHourlyPrice")
public class CreateHourlyPrice extends HttpServlet {
	private static final long serialVersionUID = 1L;


	static  String            templateDir = "WEB-INF/templates";
	static  String            templateName = "CreateHourlyPrice.ftl";
	static  String            resultTemplateName = "CreateHourlyPriceResult.ftl";

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
		List<List<Object>>     vehicleTypes = null;
		List<Object>           vehicleType = null;
		VehicleType                 vt  = null;
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

		try {
			lstVehicleType = logicLayer.findVehicleType();
		} 
		catch( Exception e ) {
			RARError.error( cfg, toClient, e );
			return;
		}

		root.put( "vehicleTypes", vehicleTypes );
		//
		for( VehicleType v : lstVehicleType) {

			vehicleType = new LinkedList<Object>();
			vehicleType.add(v.getId());
			vehicleType.add(v.getType());
			vehicleTypes.add( vehicleType );
		}

		
		root.put( "hourlyPriceId", 0 ); // it is for compatibility, but should be here
		
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
		String			str_minHours = null;
		String 			str_maxHours = null;
		String			str_price = null;
		String 			str_vehicleTypeId = null;
		int		       	minHours = 0;
		int		       	maxHours = 0;
		int				price = 0;
		//      String         person_id_str;
		long           vehicleTypeId;
		long	       hourlyPriceId = 0;
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
        
        str_minHours =req.getParameter( "minHours" );
        str_maxHours = req.getParameter( "maxHours" );
        str_price = req.getParameter( "price" );
        
        str_vehicleTypeId = req.getParameter( "rentalLocation" );
        
        if( str_minHours == null || str_maxHours == null ) {
            RARError.error( cfg, toClient, "Unspecified min hours or max hours or price" );
            return;
        }

        if( str_vehicleTypeId == null ) {
            RARError.error( cfg, toClient, "Unspecified VehicleTypeId" );
            return;
        }
        
        try {
        	vehicleTypeId = Long.parseLong( str_vehicleTypeId );
        	minHours = Integer.parseInt(str_minHours);
        	maxHours = Integer.parseInt(str_maxHours);
        	price = Integer.parseInt(str_price);
        	
        }
        catch( Exception e ) {
            RARError.error( cfg, toClient, "Data is in wrong format." );
            return;
        }
        
        if( vehicleTypeId <= 0 ) {
            RARError.error( cfg, toClient, "Non-positive vehicle Type Id: " + vehicleTypeId );
            return;
        }

        try {
            hourlyPriceId = logicLayer.createHourlyPrice(minHours, maxHours, price, vehicleTypeId);
        } 
        catch ( Exception e ) {
            RARError.error( cfg, toClient, e );
            return;
        }
        
        Map<String,Object> root = new HashMap<String,Object>();

        // Build the data-model
        //
        root.put( "hourlyPriceId", hourlyPriceId );
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
