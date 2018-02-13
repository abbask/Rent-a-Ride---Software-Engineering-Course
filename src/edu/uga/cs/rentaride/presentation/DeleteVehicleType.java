package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * Servlet implementation class DeleteVehicleType
 */
@WebServlet("/DeleteVehicleType")
public class DeleteVehicleType extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static  String            templateDir = "WEB-INF/templates";
	static  String            templateName = "DeleteVehicleType.ftl";

	private Configuration     cfg;

	public void init() 
	{

		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading( getServletContext(), "WEB-INF/templates" );
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Template       resultTemplate = null;
		BufferedWriter toClient = null;
		String			str_id;
		long	       id = 0;
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
        
        str_id =req.getParameter( "Id" );
        
        
        System.out.println(str_id);
        
        try {
        	id = Long.parseLong(str_id);
        	
        }
        catch( Exception e ) {
            RARError.error( cfg, toClient, "Data is in wrong format." );
            return;
        }
        
        if( id <= 0 ) {
            RARError.error( cfg, toClient, "Non-positive vehicle Type Id: " + id );
            return;
        }

        try {
            logicLayer.deleteVehicleType(id);
        } 
        catch ( Exception e ) {
            RARError.error( cfg, toClient, e );
            return;
        }
        
        Map<String,Object> root = new HashMap<String,Object>();

        // Build the data-model
        //
        root.put( "vehicleTypeId", id );
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
