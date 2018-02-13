package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
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

import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static  String	templateDir = "WEB-INF/templates";
	static  String  resultTemplateName = "account.ftl";
	static  String	errorTemplateName = "error.ftl";
	static  String        templateName="accountupdate.ftl";
	Customer c;

	private Configuration  cfg; 

	public void	init() {

		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading( getServletContext(), 
				templateDir );

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		update(request, response);
		//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//		doGet(request, response);
		login(request, response);



	}

	public void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Template            ResultTemplate = null;
		Template            errorTemplate = null;
		BufferedWriter      toClient = null;
		BufferedWriter      errortoClient = null;

		HttpSession    httpSession = null;
		Session        session = null;
		String         ssid = null;

		String userName = null;
		String password = null;

		LogicLayer          logicLayer = null;
		
		List<Customer>          custlst = null;
//		List<Administrator>          adminlst = null;
//		List<Administrator>          adminLst = null;
//		List<Object>        adminList = null;



		try {
			ResultTemplate = cfg.getTemplate( resultTemplateName );
			errorTemplate = cfg.getTemplate( errorTemplateName );
		} 
		catch( IOException e ) {
			throw new ServletException( "Login.doPost: Can't load template in: " + templateDir + ": " + e.toString());

		}

		res.setContentType("text/html; charset=" + ResultTemplate.getEncoding());
		res.setContentType("text/html; charset=" + errorTemplate.getEncoding());



		httpSession = req.getSession();
		ssid = (String) httpSession.getAttribute( "ssid" );
		if( ssid != null ) {
			System.out.println( "Already have ssid: " + ssid );
			session = SessionManager.getSessionById( ssid );
			System.out.println( "Connection: " + session.getConnection() );
		}
		else
			System.out.println( "ssid is null" );


		errortoClient = new BufferedWriter(new OutputStreamWriter( res.getOutputStream(), errorTemplate.getEncoding() ));

		if( session == null ) {
			try {
				session = SessionManager.createSession();
			}
			catch ( Exception e ) {
				RARError.error( cfg, errortoClient, e  );
				return;
			}
		}

		logicLayer = session.getLogicLayer();
		System.out.println(logicLayer);

		userName = req.getParameter( "username" );
		password = req.getParameter( "password" );

		if( password == null ) {
			RARError.error( cfg, errortoClient, "Missing user name or password"  );
			return;
		}

		try {          
			ssid = logicLayer.login(session, userName, password);
			System.out.println( "Obtained ssid: " + ssid );
			httpSession.setAttribute( "ssid", ssid );
			System.out.println( "Connection: " + session.getConnection() );


		} 
		catch ( Exception e ) {
			RARError.error( cfg, errortoClient, e );
			return;
		}

		Map<String,Object> root = new HashMap<String,Object>();

		try{


			custlst = logicLayer.checkCredentials(userName, password);


			for(Customer c : custlst){

				root.put("firstname", c.getFirstName());
				root.put("lastname", c.getLastName());
				root.put("memberUntil", c.getMembershipExpiration());
				root.put("address", c.getResidenceAddress());
				root.put("id", c.getId());
				root.put("status", c.getUserStatus());
				root.put("email", c.getEmailAddress());
				root.put("username", c.getUserName());
				root.put("pwd", c.getPassword());
				//root.put("phone", );
			//	root.put("id", c);
			//	root.put("id", c);


				
			}
			c = custlst.get(0);
            System.out.println(c.getFirstName());
			toClient = new BufferedWriter(new OutputStreamWriter( res.getOutputStream(), ResultTemplate.getEncoding() ));

			ResultTemplate.process( root, toClient );
			toClient.flush();


			toClient.close();


		}
		catch (Exception e){
			RARError.error(cfg, errortoClient, e);
			return;
		}

	}
	//************************************************************************************************************

	public void update(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		Template            resultTemplate = null;
        BufferedWriter      toClient = null;
        LogicLayer          logicLayer = null;

//        HourlyPrice   	    h  = null;
        HttpSession         httpSession;
        Session             session;
        String              ssid;
		String userName = null;
		String password = null;
		String address=null;
		String  email=null;
		String  Id_str=null;
		long    Id;
		Date d;
		System.out.println(c.getFirstName());
		UserStatus k1=null,k=c.getUserStatus() ;


        try {
            resultTemplate = cfg.getTemplate( templateName );
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
        
        try {
        	
        	//**************************************
        	
        	int i;
			//userName = req.getParameter( "username1" );
			password = req.getParameter( "pwd1" );
			address=req.getParameter("address");
			email=req.getParameter("email");
			Id_str=req.getParameter("item");
		    i=Integer.parseInt(Id_str);
		    Id=i;
        	String action = req.getParameter("action");
        	d = c.getCreditCardExpiration();
        	
        	if (req.getParameter("up") != null ) {

        	    
        	} else if (  req.getParameter("ca") != null     ) {
  
    		    
				k=k1.TERMINATED;

        	}
        	else if (req.getParameter("ex") != null) {
        		d = c.getMembershipExpiration();
        		Calendar cal = Calendar.getInstance();
        		cal.setTime(d);
        		cal.add(Calendar.MONTH, 6);
        		d = cal.getTime();
        		
        	}
        	
        	
        	//*******************************************
        	
        	
        	
        	
        	/*
        	
        	int i;
			//userName = req.getParameter( "username1" );
			password = req.getParameter( "pwd1" );
			address=req.getParameter("address");
			email=req.getParameter("email");
			Id_str=req.getParameter("item");
		    i=Integer.parseInt(Id_str);
		    Id=i;*/

        }
     
            catch( Exception e ) {
                RARError.error( cfg, toClient, "Data is in wrong format." );
                return;
            }
            if(  password == null ) {
                RARError.error( cfg, toClient, "Unspecified Username or Password  or email" );
                return;
            }

            


            try {

            	
               logicLayer.updateCustomer(Id,password,email,address,c,k, d);
            } 
            catch ( Exception e ) {
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


