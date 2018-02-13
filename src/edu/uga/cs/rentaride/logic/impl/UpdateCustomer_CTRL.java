package edu.uga.cs.rentaride.logic.impl;

import java.util.Date;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class UpdateCustomer_CTRL {
	
	private ObjectLayer objectLayer = null;

	public UpdateCustomer_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}
	
	public void updateCustomer(long id, String password, String email, String address, Customer c, UserStatus k, Date d) throws RARException{
		
		Customer				 CC = null;

        Iterator<Customer>    CustomerIter = null;
        
        // retrieve the vehicle Type
        CC = objectLayer.createCustomer();
        CC.setId(id);
        CustomerIter = objectLayer.findCustomer(CC);
        while (CustomerIter.hasNext()){
        	CC = CustomerIter.next();
        }

        // check if the vehicle Tpye actually exists
        if( CC == null )
            throw new RARException( "A rental Location with this id does not exist: " + CC );

        // create the hourly Price
        
        CC = objectLayer.createCustomer(c.getFirstName(), c.getLastName(), c.getUserName(), email, password, c.getCreatedDate(), d, c.getLicenseState(), c.getLicenseNumber(), address, c.getCreditCardNumber(), c.getCreditCardExpiration());
        CC.setId(id);
        CC.setUserStatus(k);
        
        
        objectLayer.storeCustomer(CC);
        
                  
		
	}

}
