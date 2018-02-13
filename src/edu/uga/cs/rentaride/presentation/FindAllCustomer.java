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

import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@WebServlet("/FindAllCustomer")
public class FindAllCustomer extends HttpServlet {

	private static final long serialVersionUID = 1L;
    

	static  String            templateDir = "WEB-INF/templates";
    static  String            resultTemplateName = "FindAllCustomer.ftl";
    
	
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
        List<Customer>   lsCustomer = null;
        List<List<Object>>  customers = null;
        List<Object>        customer = null;
//        HourlyPrice   	    h  = null;
        
        List<Reservation>   lsReservation = null;
        
        HttpSession         httpSession;
        Session             session;
        String              ssid;
        long				customerId;
        List<Object>		reservationIds;
        List<List<Object>>		lsreservationId;
        
        String firstName = null ;
    	String lastName = null;
    	String emailAddress = null;
        
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
        	firstName=req.getParameter("firstName");
        	lastName=req.getParameter("lastName");
        	emailAddress=req.getParameter("emailAddress");
		} 
		catch( Exception e ) {
			RARError.error( cfg, toClient, e );
			return;
		}
		try {
					
			if (firstName != null)
				if ( firstName.isEmpty())
					firstName = null;
			
			if (lastName != null)
				if (lastName.isEmpty())
					lastName = null;
			
			if (emailAddress != null)
				if ( emailAddress.isEmpty())
					emailAddress = null;
			
        }
        catch( Exception e ) {
        	e.printStackTrace();
            RARError.error( cfg, toClient, "Data is in wrong format." );
            return;
        }
        try {
//            lsCustomer = logicLayer.findCustomer(0, null, null, null, null, null, null, null, null, null, null, null, null, null, 0);
            lsCustomer = logicLayer.findCustomer(0, firstName, lastName, null, emailAddress, null, null, null, null, null, null, null, null, null, 0);
            
 
            // Build the data-model
            //
            customers = new LinkedList<List<Object>>();

            root.put( "customers", customers );
            for (Customer v : lsCustomer){
            	
            	customer = new LinkedList<Object>();
            	customer.add(v.getId());
            	customer.add(v.getFirstName());
             	customer.add(v.getLastName());
             	customer.add(v.getUserName());
             	customer.add(v.getEmailAddress());
             	customer.add(v.getPassword());
             	customer.add(v.getCreatedDate());
             	customer.add(v.getResidenceAddress());
             	customer.add(v.getUserStatus());
//1+8+5             	
            	customer.add(v.getMembershipExpiration());
            	customer.add(v.getLicenseState());
            	customer.add(v.getLicenseNumber());
            	customer.add(v.getCreditCardNumber());
            	customer.add(v.getCreditCardExpiration());
            	
            	customers.add(customer);
            }
            

        }
        catch( Exception e) {
            RARError.error( cfg, toClient, e );
            return;
        }
        
     try{   
         lsreservationId=new LinkedList<List<Object>>();
         
         root.put( "lsreservationId", lsreservationId );
        
    	 for (Customer v : lsCustomer){
        	reservationIds=new LinkedList<Object>();
        	customerId=v.getId();
        	lsReservation = logicLayer.findCustomerReservation(customerId);
        	
        	for (Reservation r : lsReservation){
        	reservationIds.add(r.getId());
        	 }
        	
        	 lsreservationId.add(reservationIds);
        	 
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

	

