package edu.uga.cs.rentaride.presentation;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.spi.RegisterableService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@WebServlet("/FindAllVehicle")
public class FindAllVehicle extends HttpServlet {

	private static final long serialVersionUID = 1L;
    

	static  String            templateDir = "WEB-INF/templates";
    static  String            resultTemplateName = "FindAllVehicle.ftl";
    
	
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
		
		Template            resultTemplate = null;
        BufferedWriter      toClient = null;
        LogicLayer          logicLayer = null;
        List<Vehicle>   lsVehicle = null;
        List<List<Object>>  vehicles = null;
        List<Object>        vehicle = null;
//        HourlyPrice   	    h  = null;
        HttpSession         httpSession;
        Session             session;
        String              ssid;
        
        String make = null;
		String model = null;
		String str_year =null;
		String registrationTag = null;
		String str_mileage = null;
		
        int year = 0;
        int mileage = 0;
        
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
        try{
			make = req.getParameter("make");
			model = req.getParameter("model");
			str_year = req.getParameter("year");
			registrationTag = req.getParameter("registrationTag");
			str_mileage = req.getParameter("mileage");
		} 
		catch( Exception e ) {
			RARError.error( cfg, toClient, e );
			return;
		}
        try {
			if (str_year != null)
				if (!str_year.isEmpty())
					year = Integer.parseInt(str_year); 	
				else
					year = 0 ;	
			
			if (str_mileage != null)
				if (!str_mileage.isEmpty())
					mileage = Integer.parseInt(str_mileage); 	
				else
					mileage = 0 ;	
			
			if (make != null)
				if ( make.isEmpty())
					make = null;
			
			if (model != null)
				if (model.isEmpty())
					model = null;
			
			if (registrationTag != null)
				if (registrationTag.isEmpty())
					registrationTag = null;
        }
        catch( Exception e ) {
        	e.printStackTrace();
            RARError.error( cfg, toClient, "Data is in wrong format." );
            return;
        }
        try {
            lsVehicle = logicLayer.findVehicle(0, 0, make, model, year, registrationTag, mileage, null, 0, null, null);
            

            // Build the data-model
            //
            vehicles = new LinkedList<List<Object>>();
            root.put( "vehicles", vehicles );
            
            for (Vehicle v : lsVehicle){
            	
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
            	vehicle.add(v.getVehicleType().getType());
            	vehicle.add(v.getRentalLocation().getAddress());
            	vehicles.add(vehicle);
            }

        } 
        catch( Exception e) {
            RARError.error( cfg, toClient, e );
            return;
        }
        root.put("isAdmin", "yes");
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

	

