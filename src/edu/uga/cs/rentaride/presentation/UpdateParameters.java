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

import edu.uga.cs.rentaride.entity.RentARideConfig;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@WebServlet("/UpdateParameters")
public class UpdateParameters extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static  String            templateDir = "WEB-INF/templates";
	static  String            templateName = "UpdateParameters.ftl";
	static  String            resultTemplateName = "UpdateParametersResult.ftl";
	
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
		
		
		
		RentARideConfig			parameter = null;				
		
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

		
		
		try {
			parameter = logicLayer.findParameter();
			
		} 
		catch( Exception e ) {
			RARError.error( cfg, toClient, e);
			return;
		}
		
		root.put("membershipPrice", parameter.getMembershipPrice());
		root.put("overtimePenalty", parameter.getOvertimePenalty());
		
		
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

		LogicLayer     logicLayer = null;
		HttpSession    httpSession;
		Session        session;
		String         ssid;
		String str_membershipPrice = null;
		String str_overtimePenalty = null;
		
		int membershipPrice = 0;
		int overtimePenalty = 0;

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
        str_membershipPrice =req.getParameter( "membershipPrice" );
        str_overtimePenalty =req.getParameter( "overtimePenalty" );

        
        if( str_membershipPrice == null || str_overtimePenalty == null ) {
            RARError.error( cfg, toClient, "Unspecified parameters" );
            return;
        }

        
        try {
        	membershipPrice = Integer.parseInt(str_membershipPrice);
        	overtimePenalty = Integer.parseInt(str_overtimePenalty);        	
        }
        catch( Exception e ) {
            RARError.error( cfg, toClient, "Data is in wrong format." );
            return;
        }
        Map<String,Object> root = new HashMap<String,Object>();
        
        try {
           logicLayer.updateParameter(membershipPrice, overtimePenalty);
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
