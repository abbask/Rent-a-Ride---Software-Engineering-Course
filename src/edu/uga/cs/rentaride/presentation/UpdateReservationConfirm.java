package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


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

@WebServlet("/UpdateReservationConfirm")
public class UpdateReservationConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
static  String            templateDir = "WEB-INF/templates";
	
	static  String            resultTemplateName = "UpdateReservationResult.ftl";

	private Configuration     cfg;

	public void init() 
	{

		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading( getServletContext(), "WEB-INF/templates" );
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Template       resultTemplate = null;
		BufferedWriter toClient = null;
	
		String 			str_reservationId = null;
		String 			str_rentalDuration = null;
		String 			str_vehicleTypeId = null;
		String 			str_rentalLocationId = null;
		String 			str_pickupTime = null;
		
		int				rentalDuration;
		long 			vehicleTypeId;
		long 			rentalLocationId;
		long			customerId;
		long 			reservationId;
		
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
        
        customerId = session.getUser().getId();
        
        str_reservationId = req.getParameter("reservationId");
        str_pickupTime =req.getParameter( "pickupTime" );
        str_rentalDuration =req.getParameter( "rentalDuration" );
        
        str_rentalLocationId = req.getParameter( "rentalLocationId" );
        str_vehicleTypeId = req.getParameter("vehicleTypeId");   
        
        
        
        if( str_pickupTime == null || str_rentalDuration == null || str_rentalLocationId == null || str_vehicleTypeId == null ) {
        	
            RARError.error( cfg, toClient, "Unspecified field(s)" );
            return;
        }
        
        Date date;
        try {
        	DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        	date = format.parse(str_pickupTime);
        	
        	reservationId = Long.parseLong(str_reservationId);
        	rentalDuration = Integer.parseInt(str_rentalDuration);
        	rentalLocationId = Long.parseLong(str_rentalLocationId);
        	vehicleTypeId = Long.parseLong(str_vehicleTypeId);
        	
        }
        catch( Exception e ) {
        	e.printStackTrace();
            RARError.error( cfg, toClient, "Data is in wrong format." );
            return;
        }
        

        System.out.println(date);
        try {
            long rentalId = 0;
        	logicLayer.updateReservation(reservationId, date, rentalDuration, customerId, rentalLocationId, vehicleTypeId, rentalId);
        
        } 
        catch ( Exception e ) {
        	e.printStackTrace();
            RARError.error( cfg, toClient, e );
            return;
        }
        
        
        String contextPath= "http://localhost:8080/rentaride-assocs/Reservation";
		res.sendRedirect(res.encodeRedirectURL(contextPath));

	}
}
