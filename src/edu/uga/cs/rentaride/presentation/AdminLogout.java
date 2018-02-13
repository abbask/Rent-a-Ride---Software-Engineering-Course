package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;


@WebServlet("/AdminLogout")
public class AdminLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static  String	templateDir = "WEB-INF/templates";
	static  String	errorTemplateName = "error.ftl";
	
	private Configuration  cfg; 

	public void	init() {

		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading( getServletContext(), 
				templateDir );

	}

	public AdminLogout() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//		response.getWriter().append("Served at: ").append(request.getContextPath());
		logout(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//		doGet(request, response);
		logout(request, response);
	}

	public void logout (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Template            errorTemplate = null;
		BufferedWriter      errortoClient = null;
		String username = null;
        HttpSession    httpSession = null;
        String         ssid = null;
       
        
		
        try {
			errorTemplate = cfg.getTemplate( errorTemplateName );
		} 
		catch( IOException e ) {
			throw new ServletException( "Login.doPost: Can't load template in: " + templateDir + ": " + e.toString());

		}
        res.setContentType("text/html; charset=" + errorTemplate.getEncoding());
        errortoClient = new BufferedWriter(new OutputStreamWriter( res.getOutputStream(), errorTemplate.getEncoding() ));
        
        httpSession = req.getSession( false );
        if( httpSession != null ) {
            ssid = (String) httpSession.getAttribute( "ssid" );
            if( ssid != null ) {
                System.out.println( "Already have ssid: " + ssid );
                Session session = SessionManager.getSessionById( ssid );
                if( session == null ) {
                    RARError.error( cfg, errortoClient, "Session expired or illegal; please log in" );
                    return; 
                }
                LogicLayer logicLayer = session.getLogicLayer();
                try {
                    logicLayer.logout( ssid );
                    httpSession.removeAttribute("ssid");
                    httpSession.invalidate();
                    System.out.println( "Invalidated http session" );
                }
                catch( RARException e ) {
                    e.printStackTrace();
                }
            }
            else
                System.out.println( "ssid is null" );
        }
        else
            System.out.println( "No http session" );
        
			String contextPath= "http://localhost:8080/rentaride-assocs/admin.html";
			res.sendRedirect(res.encodeRedirectURL(contextPath));

	}
}
