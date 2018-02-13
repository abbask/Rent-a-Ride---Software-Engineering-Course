package edu.uga.cs.rentaride.entity.impl;

import java.util.Date;

import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.UserStatus;

public class AdministratorImpl extends UserImpl implements Administrator {
	
	public AdministratorImpl(String firstName, String lastName, String userName, String emailAddress, 
			String password, Date createdDate, String residenceAddress, UserStatus userStatus) {
		super(firstName,lastName, userName, emailAddress, 
				password, createdDate, residenceAddress, userStatus);
	}
	
	

}
