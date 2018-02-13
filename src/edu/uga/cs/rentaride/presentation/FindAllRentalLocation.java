package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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


@WebServlet("/FindAllRentalLocation")
public class FindAllRentalLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;


	static  String            templateDir = "WEB-INF/templates";
	static  String            resultTemplateName = "FindAllRentalLocation.ftl";


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
		List<RentalLocation>   lstRentalLocation = null;
		List<List<Object>>  rentalLocations = null;
		List<Object>        rentalLocation = null;
		//        HourlyPrice   	    h  = null;
		HttpSession         httpSession;
		Session             session;
		String              ssid;

		String				name = null;
		String 				address = null;
		String 				str_capacity = null;
		int					capacity = 0;

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
			name=req.getParameter("name");
			address=req.getParameter("address");
			str_capacity=req.getParameter("capacity");
		} 
		catch( Exception e ) {
			RARError.error( cfg, toClient, e );
			return;
		}
		try {
			if (str_capacity != null)
				if (!str_capacity.isEmpty())
					capacity = Integer.parseInt(str_capacity); 	
				else
					capacity = 0 ;				
			if (name != null)
				if ( name.isEmpty())
					name = null;
			
			if (address != null)
				if (address.isEmpty())
					address = null;
			
        }
        catch( Exception e ) {
        	e.printStackTrace();
            RARError.error( cfg, toClient, "Data is in wrong format." );
            return;
        }


		try {
			
			lstRentalLocation = logicLayer.findRentalLocation(name, address, capacity);

			// Build the data-model
			//
			rentalLocations = new LinkedList<List<Object>>();

			for (RentalLocation rl : lstRentalLocation){
				rentalLocation = new LinkedList<Object>();
				rentalLocation.add(rl.getName());
				rentalLocation.add(rl.getAddress());
				rentalLocation.add(rl.getCapacity());
				rentalLocation.add(rl.getId());
				rentalLocations.add(rentalLocation);
			}
			root.put( "rentalLocations", rentalLocations );
			root.put("isAdmin", "yes");
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
