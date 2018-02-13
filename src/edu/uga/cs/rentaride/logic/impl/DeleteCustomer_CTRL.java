package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class DeleteCustomer_CTRL {

	
	private ObjectLayer objectLayer = null;

	public DeleteCustomer_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public void deleteCustomer(Long id) throws RARException{
		Customer              modelCustomer = null;
        Iterator<Customer>    customerIter = null;
        
    	
    		Reservation              modelReservation= null;
            Iterator<Reservation>    reservationIter = null;

        // check if a hourly price with this information already exists
        modelCustomer = objectLayer.createCustomer();
        modelCustomer.setId(id);
        
        reservationIter=objectLayer.restoreCustomerReservation(modelCustomer);
        if(  reservationIter.hasNext() )
        
        {	modelReservation=reservationIter.next();
        	objectLayer.deleteReservation(modelReservation);  
        	
        }
        	
        customerIter = objectLayer.findCustomer(modelCustomer);
        if( ! customerIter.hasNext() )
            throw new RARException( "No such a Customer exists id=" + id );
        modelCustomer=customerIter.next();
        modelCustomer.setUserStatus(UserStatus.valueOf("TERMINATED"));
        objectLayer.storeCustomer(modelCustomer);                               

	
}
}
