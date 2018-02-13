package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;
import java.util.UUID;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;

import edu.uga.cs.rentaride.object.ObjectLayer;

public class ResetPSW_CTRL {
	
	private ObjectLayer objectLayer = null;
	
	public ResetPSW_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public String resetpsw(long id) throws RARException{
	
	Customer				 customer = null;
	Customer				 modelcustomer = null;
	Iterator<Customer>		customerIter=null;
	String 					newpsw;
	
    modelcustomer = objectLayer.createCustomer();
    modelcustomer.setId(id);
    customerIter=objectLayer.findCustomer(modelcustomer);
    

    while (customerIter.hasNext()){
    	customer = customerIter.next();
    }
    if( customer == null )
        throw new RARException( "A customer with this id does not exist: " + id );

    newpsw="0000";
    customer.setPassword(newpsw);
    objectLayer.storeCustomer(customer);
     return newpsw;
	}
}
