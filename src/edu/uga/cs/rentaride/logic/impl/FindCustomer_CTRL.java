package edu.uga.cs.rentaride.logic.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.object.ObjectLayer;

public class FindCustomer_CTRL {

	private ObjectLayer objectLayer = null;

	public FindCustomer_CTRL( ObjectLayer objectModel )
	{
		this.objectLayer = objectModel;
	}


	public List<Customer> findCustomer(long customerId, String firstName, String lastName, String userName,String emailAddress,String password,	Date createdDate,String residenceAddress,UserStatus userStatus,Date membershipExpiration, String licenseState,String licenseNumber,String creditCardNumber,Date creditCardExpiration, int isAdmin ) throws RARException{

		Customer              customer = null;
		Customer              modelCustomer = null;
		Iterator<Customer>    customerIter = null;
		List<Customer>		  lstCustomer = null;	


//		String firstName, String lastName, String userName,String emailAddress
		//,String password,	Date createdDate,String residenceAddress,UserStatus userStatus
		//,Date membershipExpiration, String licenseState,String licenseNumber,
		//String creditCardNumber,Date creditCardExpiration, int isAdmin
		//Retrieve RentalLocation
		modelCustomer = objectLayer.createCustomer();
		if (customerId != 0){
			modelCustomer.setId(customerId);		
		}
		else{
			modelCustomer.setFirstName(firstName);
			modelCustomer.setLastName(lastName);
			modelCustomer.setUserName(userName);
			modelCustomer.setEmailAddress(emailAddress);
			modelCustomer.setPassword(password);
			modelCustomer.setCreateDate(createdDate);
			modelCustomer.setResidenceAddress(residenceAddress);
			modelCustomer.setUserStatus(userStatus);
			modelCustomer.setMembershipExpiration(membershipExpiration);
			modelCustomer.setLicenseState(licenseState);
			modelCustomer.setLicenseNumber(licenseNumber);
			modelCustomer.setCreditCardNumber(creditCardNumber);
			modelCustomer.setCreditCardExpiration(creditCardExpiration);				
			
		}
		customerIter = objectLayer.findCustomer(modelCustomer);
		lstCustomer = new LinkedList<Customer>();
		while (customerIter.hasNext()){
			customer = customerIter.next();
			lstCustomer.add(customer);
		}              


		if (lstCustomer.size() == 0)
			throw new RARException( "There is no Customer available in this location!");

		return lstCustomer;	

	}

}
