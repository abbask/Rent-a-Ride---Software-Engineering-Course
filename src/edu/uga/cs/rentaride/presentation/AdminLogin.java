package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;
import freemarker.template.Configuration;
import freemarker.template.Template;


@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static  String	templateDir = "WEB-INF/templates";
	static  String  adminResultTemplateName = "admin.ftl";
	static  String	errorTemplateName = "error.ftl";

	private Configuration  cfg; 

	public void	init() {

		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading( getServletContext(), 
				templateDir );

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		login(request, response);
		//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//		doGet(request, response);
		login(request, response);



	}

	public void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Template            adminResultTemplate = null;
		Template            errorTemplate = null;
		BufferedWriter      toClient = null;
		BufferedWriter      errortoClient = null;

		HttpSession    httpSession = null;
		Session        session = null;
		String         ssid = null;

		String userName = null;
		String password = null;

		LogicLayer          logicLayer = null;
//		List<Administrator>          adminlst = null;
//		List<Administrator>          adminLst = null;
//		List<Object>        adminList = null;



		try {
			adminResultTemplate = cfg.getTemplate( adminResultTemplateName );
			errorTemplate = cfg.getTemplate( errorTemplateName );
		} 
		catch( IOException e ) {
			throw new ServletException( "Login.doPost: Can't load template in: " + templateDir + ": " + e.toString());

		}

		res.setContentType("text/html; charset=" + adminResultTemplate.getEncoding());
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

		if( userName == null || password == null ) {
			RARError.error( cfg, errortoClient, "Missing user name or password"  );
			return;
		}

		try {          
			ssid = logicLayer.adminLogin(session, userName, password);
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

			toClient = new BufferedWriter(new OutputStreamWriter( res.getOutputStream(), adminResultTemplate.getEncoding() ));

			adminResultTemplate.process( root, toClient );
			toClient.flush();
			toClient.close();

		}
		catch (Exception e){
			RARError.error(cfg, errortoClient, e);
			return;
		}

	}


}
