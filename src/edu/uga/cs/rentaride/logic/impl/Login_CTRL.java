package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.object.impl.ObjectLayerImpl;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;

public class Login_CTRL {
	
	private ObjectLayer objectLayer = null;

	public Login_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public List<Customer> checkCredentials(String username, String password)
			throws RARException
	{
		List<Customer> customers = null;
		Iterator<Customer> 	customerIterator = null;
		Customer customer = null;
		
		Customer modelCustomer = objectLayer.createCustomer(null, null, username, null, password, null, null, null, null, null, null, null);
		customers = new LinkedList<Customer>();
		
		customerIterator = objectLayer.findCustomer(modelCustomer);
		while (customerIterator.hasNext()){
			customer = customerIterator.next();
			customers.add(customer);
		}
		
		return customers;
	}
	
	public List<Customer> checkCredentials(String username)
			throws RARException
	{
		List<Customer> customers = null;
		Iterator<Customer> 	customerIterator = null;
		Customer customer = null;
		
		Customer modelCustomer = objectLayer.createCustomer(null, null, username, null, null, null, null, null, null, null, null, null);
		customers = new LinkedList<Customer>();
		
		customerIterator = objectLayer.findCustomer(modelCustomer);
		while (customerIterator.hasNext()){
			customer = customerIterator.next();
			customers.add(customer);
		}
		
		return customers;
	}
	
	public String login( Session session, String userName, String password )
            throws RARException
    {
        String ssid = null;
        
        Customer modelCustomer = objectLayer.createCustomer();
        modelCustomer.setUserName( userName );
        modelCustomer.setPassword( password );
        Iterator<Customer> customers = objectLayer.findCustomer(modelCustomer);
        if( customers.hasNext() ) {
            Customer customer = customers.next();
            session.setUser( customer );
            ssid = SessionManager.storeSession( session );
        }
        else
            throw new RARException( "SessionManager.login: Invalid User Name or Password" );
        
        return ssid;
    }

	public String adminLogin( Session session, String userName, String password )
            throws RARException
    {
        String ssid = null;
        
        Administrator modelAdministrator = objectLayer.createAdministrator();
        modelAdministrator.setUserName( userName );
        modelAdministrator.setPassword( password );
        Iterator<Administrator> administrators = objectLayer.findAdministrator(modelAdministrator);
        if( administrators.hasNext() ) {
            Administrator administrator = administrators.next();
            session.setUser( administrator );
            ssid = SessionManager.storeSession( session );
        }
        else
            throw new RARException( "SessionManager.login: Invalid User Name or Password" );
        
        return ssid;
    }
}
