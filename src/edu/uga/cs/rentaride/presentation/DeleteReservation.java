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

import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@WebServlet("/DeleteReservation")
public class DeleteReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	static  String            templateDir = "WEB-INF/templates";
	static  String            templateName = "DeleteReservation.ftl";

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
		int		       	minHours = 0;
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
        	
        	//check if a rental is assign to reservation
        	List<Rental> lstRental = null;
        	lstRental = new LinkedList<Rental>();
        	lstRental = logicLayer.findRental(0, id, 0, 0, null, null, 0);
        	
        	if (! lstRental.isEmpty()){
        		RARError.error( cfg, toClient, "Cannot delete the reseration, a rental assigned to it. reservation Id:" + id );
        		return;
        	}
        	logicLayer.deleteReservation(id);
        	
        	
        } 
        catch ( Exception e ) {
            RARError.error( cfg, toClient, e );
            return;
        }
        
        Map<String,Object> root = new HashMap<String,Object>();

        root.put( "reservationId", id );

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
