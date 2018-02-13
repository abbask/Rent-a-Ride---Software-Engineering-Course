package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
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
@WebServlet("/RegisterCustomer")
public class RegisterCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;


	static  String            templateDir = "WEB-INF/templates";
	static  String            templateName = "RegisterCustomer.ftl";
	static  String            resultTemplateName = "RegisterCustomerResult.ftl";

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

//		List<Customer>      lstcustomer = null;
//		List<List<Object>>     customers = null;
//		List<Object>           customer = null;

		HttpSession            httpSession;
		Session                session;
		String                 ssid;
		
//		List<Object>           UserStatuslist = null;
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
		

/*
 * 		httpSession = req.getSession();
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
*/
		Map<String,Object> root = new HashMap<String,Object>();

//		customers = new LinkedList<List<Object>>();
//		UserStatuslist=new LinkedList<Object>();
		
		try {
			} 
		catch( Exception e ) {
			RARError.error( cfg, toClient, e );
			return;
		}
		


		root.put( "customerId", 0 ); // it is for compatibility, but should be here
		

		
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
		String			str_firstname = null;
		String 			str_lastname= null;
		String			str_username = null;
		String 			str_email = null;
		String 			str_address= null;
		String 			str_licenseno= null;
		String 			str_creditcardno = null;
		String 			str_creditcardexpiredate = null;
		String 			str_licensestate = null;
		
		long 		   CustomerId;
		Date			ccexpiredate=new Date();
		
		LogicLayer     logicLayer = null;
		HttpSession    httpSession;
		Session        session;
		String         ssid;
		
		Customer	customermodel;
		Iterator<Customer> customerIter;
		


		
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
/*
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
     */
	
		try {
			session = SessionManager.createSession();
		}
		catch ( Exception e ) {
			RARError.error( cfg, toClient, e  );
			return;
		}
	

	logicLayer = session.getLogicLayer();
		
        str_id =req.getParameter( "id" );
        str_firstname =req.getParameter( "FisrtName" );
        str_lastname = req.getParameter( "LastName" );
        str_username = req.getParameter( "UserName" );
        str_email = req.getParameter( "EmailAddress" );
        str_address = req.getParameter( "ResidenceAddress" );
        str_licenseno = req.getParameter( "LicenseNumber" );
        str_creditcardno = req.getParameter( "CrediteCardNumber" );
        str_creditcardexpiredate = req.getParameter( "CrediteCardExpireDate" );
        str_licensestate = req.getParameter( "LicenseState" );
        if( str_firstname == null || str_lastname == null || str_username==null ) {
            RARError.error( cfg, toClient, "Unspecified firsrname or lastname or username " );
            return;
        }
        
        if( str_email == null || str_address == null || str_licenseno==null ) {
            RARError.error( cfg, toClient, "Unspecified email or residence address or license number " );
            return;
        }

        if( str_creditcardno == null || str_creditcardexpiredate == null || str_licensestate==null ) {
            RARError.error( cfg, toClient, "Unspecified credit card number or creditcard expire date or license state " );
            return;
        }
         

    	    DateFormat df = new SimpleDateFormat("MMM dd,yyyy");
    	    try{
    	    	ccexpiredate = df.parse(str_creditcardexpiredate);
    	        //formatteddate = df.format(date);
    	    }
    	    catch ( Exception ex ){
    	        System.out.println(ex);
    	    }
        

        
        try {
        	CustomerId = logicLayer.registercustomer(str_firstname, str_lastname, str_username, str_email ,
        			str_address,str_licenseno, str_creditcardno,
        			ccexpiredate,str_licensestate);
        	} 
        catch ( Exception e ) {
            RARError.error( cfg, toClient, e );
            return;
        	}
        
        Map<String,Object> root = new HashMap<String,Object>();

        // Build the data-model
        //
        root.put( "CustomerId", CustomerId );
        root.put( "username", str_username );
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



