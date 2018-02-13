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

import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentalLocation;
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
@WebServlet("/UpdateRentalLocation")
public class UpdateRentalLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;


	static  String            templateDir = "WEB-INF/templates";
	static  String            templateName = "UpdateRentalLocation.ftl";
	static  String            resultTemplateName = "UpdateRentalLocationResult.ftl";
	private  String            name=null;
	int                   id;

    
	
    private Configuration     cfg;

    public void init() 
    {
        // Prepare the FreeMarker configuration;
        // - Load templates from the WEB-INF/templates directory of the Web app.
        //
        cfg = new Configuration();
        cfg.setServletContextForTemplateLoading( getServletContext(), "WEB-INF/templates" );
    }
 
	//****************************************************************************************************************
	@SuppressWarnings("null")
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        

		Template               resultTemplate = null;
		BufferedWriter         toClient = null;
		LogicLayer             logicLayer = null;
		List<VehicleType>           lstVehicleType = null;
		List<List<Object>>     vehicleTypes = null;
		List<Object>           lstrental = null;
		VehicleType                 vt  = null;
		HttpSession            httpSession;
		Session                session;
		String                 ssid;
		String                   id_str;
		String                 address=null,cap=null;
        String              rentallocation_name=null;
        String              rentallocation_address=null;
        String              rentallocation_capacity=null;
        int                 capacity=0;

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

		try {
			 name=req.getParameter("name");
			 address=req.getParameter("address");
			 cap=req.getParameter("capacity");
			 id_str=req.getParameter("Id");
			 id=Integer.parseInt(id_str);
  
		} 
		catch( Exception e ) {
			RARError.error( cfg, toClient, e );
			return;
		}



		
		root.put( "name", name ); // it is for compatibility, but should be here
		root.put( "address", address );
		root.put("cap", cap);

		
		
		try {
			resultTemplate.process( root, toClient );
			toClient.flush();
		} 
		catch (TemplateException e) {
			throw new ServletException( "Error while processing FreeMarker template", e);
		}

		toClient.close();


	}


		
//	****************************************************************************************************************
	

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		Template            resultTemplate = null;
        BufferedWriter      toClient = null;
        LogicLayer          logicLayer = null;
        RentalLocation        RL=null;
        List<RentalLocation>   lstRentalLocation = null;
        List<List<Object>>  rentalLocations = null;
        List<Object>        rentalLocation = null;
//        HourlyPrice   	    h  = null;
        HttpSession         httpSession;
        Session             session;
        String              ssid;
        String              rentallocation_name=null;
        String              rentallocation_address=null;
        String              rentallocation_capacity=null;
        int                 capacity=0;
        
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
        int capc=0;
        Map<String,Object> root = new HashMap<String,Object>();
        
        try {
             rentallocation_name=req.getParameter( "name" );
             rentallocation_address= req.getParameter( "address" );
             rentallocation_capacity= req.getParameter( "capacity" );
             capc=Integer.parseInt(rentallocation_capacity);

            // Build the data-model
            //
         //   rentalLocations = new LinkedList<List<Object>>();
            



        }
     
            catch( Exception e ) {
                RARError.error( cfg, toClient, "Data is in wrong format." );
                return;
            }
            if( rentallocation_name == null || rentallocation_address == null ) {
                RARError.error( cfg, toClient, "Unspecified Name or Adddress  or Capacity" );
                return;
            }
            if( capc <= 0 ) {
                RARError.error( cfg, toClient, "Non-positive id: " + capc );
                return;
            }
            


            try {
            	long Id=id;
            	//Id=1;
            	//rentallocation_name="Georgia1";
            	//rentallocation_address="Baxter";
            	//capacity=900;
            	
               logicLayer.updateRentalLocation(Id,rentallocation_name,rentallocation_address,capc);
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
	//****************************************************************************************************************
	
}



