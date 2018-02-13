package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
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

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.logic.impl.LogicLayerImpl;
import edu.uga.cs.rentaride.persistence.impl.DbUtils;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@WebServlet("/Reservation")
public class FindAllReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static  String	templateDir = "WEB-INF/templates";
	static  String  resultTemplateName = "FindAllReservation.ftl";


	private Configuration  cfg; 

	public void	init() {

		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading( getServletContext(), 
				templateDir );

	}


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		Template            resultTemplate = null;
		BufferedWriter      toClient = null;
		LogicLayer          logicLayer = null;
		
		List<Reservation>   lstReservation = null;
		List<List<Object>>  reservations = null;
		List<Object>        reservation = null;

		HttpSession         httpSession;
		Session             session;
		String              ssid;
		
		String 				str_customerId = null;
		long 				customerId;

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
		
		customerId = session.getUser().getId();
		
		Map<String,Object> root = new HashMap<String,Object>();

		try {
			lstReservation = logicLayer.findCustomerReservation(customerId);

			// Build the data-model
			//
			reservations = new LinkedList<List<Object>>();
			root.put( "reservations", reservations );

			for (Reservation r : lstReservation){
				
				reservation = new LinkedList<Object>();							
				
				reservation.add(r.getId());								
				
				reservation.add(r.getPickupTime());
				
				reservation.add(r.getRentalDuration());		
				
				if (r.getRentalLocation() != null)
					reservation.add(r.getRentalLocation().getName());
				else
					reservation.add("N/A");
				
				if (r.getVehicleType() != null)
					reservation.add(r.getVehicleType().getType());
				else
					reservation.add("N/A");
				
				if (r.getRental() != null)
					reservation.add(r.getRental().getId());
				else
					reservation.add(0);
				
				if (r.getRental() != null)
					if (r.getRental().getReturnTime() != null) 
						reservation.add("renturend");
					else
						reservation.add("no");
				
				else
					reservation.add("no");
				
				reservations.add(reservation);
			}

		} 
		catch( Exception e) {
			e.printStackTrace();
			RARError.error( cfg, toClient, e );
			return;
		}
		root.put("isAdmin", "no");
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
