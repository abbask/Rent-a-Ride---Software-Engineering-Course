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

import edu.uga.cs.rentaride.entity.VehicleType;

import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@WebServlet("/FindAllVehicleType")
public class FindAllVehicleType extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	static  String            templateDir = "WEB-INF/templates";
    static  String            resultTemplateName = "FindAllVehicleType.ftl";
    
	
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
        List<VehicleType>   lstVehicleType = null;
        List<List<Object>>  vehicleTypes = null;
        List<Object>        vehicleType = null;
//        VehicleType   	    h  = null;
        HttpSession         httpSession;
        Session             session;
        String              ssid;
        
        String 				typeName = null;
        
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
			typeName = req.getParameter("type");
		} 
		catch( Exception e ) {
			RARError.error( cfg, toClient, e );
			return;
		}
        try {

			
			if (typeName != null)
				if ( typeName.isEmpty())
					typeName = null;
					
        }
        catch( Exception e ) {
        	e.printStackTrace();
            RARError.error( cfg, toClient, "Data is in wrong format." );
            return;
        }
        try {
            lstVehicleType = logicLayer.findVehicleType(0, typeName);

            // Build the data-model
            //
            vehicleTypes = new LinkedList<List<Object>>();
            root.put( "vehicleTypes", vehicleTypes );
            
            for (VehicleType h : lstVehicleType){

            	vehicleType = new LinkedList<Object>();
            	vehicleType.add(h.getId());
            	vehicleType.add(h.getType());
            	vehicleTypes.add(vehicleType);
            }

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
