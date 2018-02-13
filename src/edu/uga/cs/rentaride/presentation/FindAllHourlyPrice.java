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
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@WebServlet("/FindAllHourlyPrice")
public class FindAllHourlyPrice extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	static  String            templateDir = "WEB-INF/templates";
    static  String            resultTemplateName = "FindAllHourlyPrice.ftl";
    
	
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
        List<HourlyPrice>   lstHourlyPrice = null;
        List<List<Object>>  hourlyPrices = null;
        List<Object>        hourlyPrice = null;
        
        
        List<VehicleType>   lstVehicleType = null;
        List<List<Object>>  vehicleTypes = null;
        List<Object>        vehicleType = null;
//        HourlyPrice   	    h  = null;
        HttpSession         httpSession;
        Session             session;
        String              ssid;
        
        String str_minHours = null;
        String str_maxHours = null;
        String str_Price = null;
        String str_vehicleTypeSelId = null;
        
        int minHours = 0;
    	int maxHours = 0;
    	int Price = 0;
    	long vehicleTypeSelId = 0;
        
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
//        <td><input name="minHours" type="input" placeholder="Minimum Hours" /></td>
//		<td><input name="maxHours" type="input" placeholder="Maximum Hours"/> </td>
//		<td><input name="Price" type="input" placeholder="Price" /> </td>
//		<td>
//		<select id = "vehicleType" name = "vehicleType">
        try{
        	str_minHours=req.getParameter("minHours");
        	str_maxHours=req.getParameter("maxHours");
        	str_Price=req.getParameter("Price");
        	str_vehicleTypeSelId = req.getParameter("vehicleTypeSelId");
		} 
		catch( Exception e ) {
			RARError.error( cfg, toClient, e );
			return;
		}
		try {
			if (str_minHours != null)
				if (!str_minHours.isEmpty())
					minHours = Integer.parseInt(str_minHours); 	
				else
					minHours = 0 ;
			
			if (str_maxHours != null)
				if (!str_maxHours.isEmpty())
					maxHours = Integer.parseInt(str_maxHours); 	
				else
					maxHours = 0 ;
			
			if (str_Price != null)
				if (!str_Price.isEmpty())
					Price = Integer.parseInt(str_Price); 	
				else
					Price = 0 ;
			
			if (str_vehicleTypeSelId != null)
				if (!str_vehicleTypeSelId.isEmpty())
					vehicleTypeSelId = Long.parseLong(str_vehicleTypeSelId)	;
				else
					vehicleTypeSelId = 0 ;
			
        }
        catch( Exception e ) {
        	e.printStackTrace();
            RARError.error( cfg, toClient, "Data is in wrong format." );
            return;
        }
        
        try {
            lstVehicleType = logicLayer.findVehicleType();
            vehicleTypes = new LinkedList<List<Object>>();
            
            root.put( "vehicleTypes", vehicleTypes );
            
            for (VehicleType v : lstVehicleType){
            	vehicleType = new LinkedList<Object>();
            	vehicleType.add(v.getId());
            	vehicleType.add(v.getType());
            	vehicleTypes.add(vehicleType);
            }
            System.out.println(minHours);
            System.out.println(maxHours);
            System.out.println(Price);
            System.out.println(vehicleTypeSelId);
        	lstHourlyPrice = logicLayer.findHourlyPrice(0, minHours, maxHours, Price, vehicleTypeSelId);
            
            
            // Build the data-model
            //
            hourlyPrices = new LinkedList<List<Object>>();
            root.put( "hourlyPrices", hourlyPrices );
            
            for (HourlyPrice h : lstHourlyPrice){
            	VehicleType v = h.getVehicleType();
            	hourlyPrice = new LinkedList<Object>();
            	hourlyPrice.add(h.getId());
            	hourlyPrice.add(h.getMinHours());
            	hourlyPrice.add(h.getMaxHours());
            	hourlyPrice.add(h.getPrice());
            	hourlyPrice.add(v.toString());
            	hourlyPrices.add(hourlyPrice);
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
