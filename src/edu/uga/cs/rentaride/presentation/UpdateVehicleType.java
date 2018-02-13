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


@WebServlet("/UpdateVehicleType")
public class UpdateVehicleType extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	static  String            templateDir = "WEB-INF/templates";
	static  String            templateName = "UpdateVehicleType.ftl";
//	static  String            resultTemplateName = "UpdateVehicleTypeResult.ftl";

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
		
		List<VehicleType>      lstVehicleType = null;
		List<List<Object>>     vehicleTypes = null;
		List<Object>           vehicleType = null;
		
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
			lstVehicleType = logicLayer.findVehicleType(Id, null);
			
		} 
		catch( Exception e ) {
			RARError.error( cfg, toClient, e);
			return;
		}
		
		root.put( "vehicleTypes", vehicleTypes );
		//
		for( VehicleType h : lstVehicleType) {

			vehicleType = new LinkedList<Object>();
			vehicleType.add(h.getId());
			vehicleType.add(h.getType());
			vehicleTypes.add(vehicleType);
			
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
		String			str_name = null;

		//      String         person_id_str;
		long 		   id;
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
        
        str_id =req.getParameter( "id" );
        str_name =req.getParameter( "name" );

        
        if( str_name == null ) {
            RARError.error( cfg, toClient, "Unspecified type" );
            return;
        }

        
        try {
        	id = Long.parseLong(str_id);
    

        	
        }
        catch( Exception e ) {
            RARError.error( cfg, toClient, "Data is in wrong format." );
            return;
        }
        if( id <= 0 ) {
            RARError.error( cfg, toClient, "Non-positive id: " + id );
            return;
        }
        
        try {
           logicLayer.updateVehicleType(id, str_name);
        } 
        catch ( Exception e ) {
            RARError.error( cfg, toClient, e );
            return;
        }
                
        String contextPath= "http://localhost:8080/rentaride-assocs/FindAllVehicleType";
		res.sendRedirect(res.encodeRedirectURL(contextPath));

	}
	
}
