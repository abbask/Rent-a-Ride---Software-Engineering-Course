package edu.uga.cs.rentaride.logic.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class RegisterCustomer_CTRL {

	
	private ObjectLayer objectLayer = null;

	public RegisterCustomer_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public long registercustomer(String firstName,String lastName, 
			String userName,String emailAddress,String residenceAddress, 
			String licenseNumber, String creditcardno, Date creditexpiredate,String licenseState ) throws RARException{
        Customer              customer = null;
        Date createDate;
        Date membershipExpiration;
        String password;
        Customer              modelcustomer;
        Iterator<Customer> customerIter;
        
   
        createDate=new Date();
        Calendar calendar= Calendar.getInstance();
       // int month=calendar.get(Calendar.MONTH);
       // month=month+6;
       // calendar.set(Calendar.YEAR,month,Calendar.DAY_OF_MONTH);
        // 6 months later
        calendar.add(Calendar.MONTH, 6);
        membershipExpiration=calendar.getTime();
//        password=UUID.randomUUID().toString();
        password = "1234";
        
        
        //retrieve customer to check whether this username already exist
        modelcustomer=objectLayer.createCustomer();
        modelcustomer.setUserName(userName);
        customerIter=objectLayer.findCustomer(modelcustomer);
        if(customerIter.hasNext())
        {
        	
            throw new RARException( "A customer with this name already exists: " + userName );
            
        }

        // create the customer
        
        customer = objectLayer.createCustomer( firstName, lastName, userName, 
        		emailAddress, 
    			password, createDate, membershipExpiration, licenseState, 
    			licenseNumber, residenceAddress, creditcardno, 
    			creditexpiredate );
        objectLayer.storeCustomer(customer);
        
     

        return customer.getId();
	}
	
	
}
