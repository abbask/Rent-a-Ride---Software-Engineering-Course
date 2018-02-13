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
import edu.uga.cs.rentaride.entity.RentalLocation;
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
@WebServlet("/CreateRentalLocation")
public class CreateRentalLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;


	static  String            templateDir = "WEB-INF/templates";
	static  String            templateName = "CreateRentalLocation.ftl";
	static  String            resultTemplateName = "CreateRentalLocationResult.ftl";
    
	
    private Configuration     cfg;

    public void init() 
    {
        // Prepare the FreeMarker configuration;
        // - Load templates from the WEB-INF/templates directory of the Web app.
        //
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
		{
			
			Template            resultTemplate = null;
	        BufferedWriter      toClient = null;
	        LogicLayer          logicLayer = null;
	        RentalLocation        RL=null;
	        List<RentalLocation>   lstRentalLocation = null;
	        List<List<Object>>  rentalLocations = null;
	        List<Object>        rentalLocation = null;
//	        HourlyPrice   	    h  = null;
	        HttpSession         httpSession;
	        Session             session;
	        String              ssid;
	        String              rentallocation_name=null;
	        String              rentallocation_address=null;
	        String              rentallocation_capacity=null;
	        int                 capacity=0;
	        
	        try {
	            resultTemplate = cfg.getTemplate( resultTemplateName );
	        } 
	        catch( IOException e ) {
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
	        
	        Map<String,Object> root = new HashMap<String,Object>();
	        
	        try {
	             rentallocation_name=req.getParameter( "name" );
	             rentallocation_address= req.getParameter( "address" );
	             rentallocation_capacity= req.getParameter( "capacity" );
	             capacity=Integer.parseInt(rentallocation_capacity);
	            RL = logicLayer.createRentalLocation(rentallocation_name,rentallocation_address,capacity);

	            // Build the data-model
	            //
	            rentalLocations = new LinkedList<List<Object>>();
	            
	            	rentalLocation = new LinkedList<Object>();
	            	//rentalLocation.add(RL.getId());
	            	rentalLocation.add(RL.getName());
	            	rentalLocation.add(RL.getAddress());
	            	rentalLocation.add(RL.getCapacity());

	            	rentalLocations.add(rentalLocation);
	            	

	            	
                String rental=rentallocation_name+"   "+rentallocation_address+"   "+rentallocation_capacity;	            
	            
	            root.put( "rental", rental );

	        } 
	        catch( Exception e) {
	            RARError.error( cfg, toClient, e );
	            return;
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
	}
}



