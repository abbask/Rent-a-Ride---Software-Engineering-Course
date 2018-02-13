package edu.uga.cs.rentaride.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Servlet implementation class RARError
 */
@WebServlet("/RARError")
public class RARError extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static  String	templateDir = "WEB-INF/templates";
       
	static  String   errorTemplateName = "error.ftl";

    public static void error( Configuration cfg, BufferedWriter toClient, Exception e )
            throws ServletException
    {
        error( cfg, toClient, e.toString() );
    }

    public static void error( Configuration cfg, BufferedWriter toClient, String msg )
            throws ServletException
    {
        Template	    errorTemplate = null;
        Map<String, String> root = new HashMap<String, String>();

        // Load the error template from the WEB-INF/templates directory of the Web app
        //
        try {
            errorTemplate = cfg.getTemplate( errorTemplateName );
        } 
        catch( Exception e ) {
            throw new ServletException( "Can't load template: " + errorTemplateName + ": " + e.toString() );
        }

        root.put( "reason", msg );

        try {
            errorTemplate.process( root, toClient );
            toClient.flush();
            toClient.close();
        } 
        catch( Exception e ) {
            throw new ServletException( "Error while processing FreeMarker template", e);
        }

        return;
    }

}
