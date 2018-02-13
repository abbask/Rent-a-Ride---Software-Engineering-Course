package edu.uga.cs.rentaride.entity.impl;

import java.util.Date;

import edu.uga.cs.rentaride.entity.UserStatus;
import edu.uga.cs.rentaride.entity.Customer;

public class CustomerImpl extends UserImpl implements Customer   {

	private Date membershipExpiration;
	private String licenseState;
	private String licenseNumber;
	private String creditCardNumber;
	private Date creditCardExpiration;
	private int isAdmin;
	
	public CustomerImpl(){
		
	}
	
	public CustomerImpl(String firstName, String lastName, String userName, String password, 
			String email, String address, UserStatus status,Date createdDate, Date membershipExpiration, 
			String licenseState, String licenseNumber, String creditCardNumber, 
			Date creditCardExpiration, int isAdmin){
//		super(firstName, lastName, userName, email, password, createdDate, residenceAddress, userStatus)
		setFirstName(firstName);
		setLastName(lastName);
		setUserName(userName);
		setPassword(password);
		setEmailAddress(email);
		setResidenceAddress(address);
		setUserStatus(status);
		setCreateDate(createdDate);
		
		
	
		this.membershipExpiration=membershipExpiration;
		this.licenseState = licenseState;
		this.licenseNumber = licenseNumber;
		this.creditCardExpiration = creditCardExpiration;
		this.creditCardNumber = creditCardNumber;
		this.isAdmin=isAdmin;
	}

	

	public Date getMembershipExpiration() {
		return membershipExpiration;
	}



	public void setMembershipExpiration(Date membershipExpiration) {
		this.membershipExpiration = membershipExpiration;
	}



	public String getLicenseState() {
		return licenseState;
	}



	public void setLicenseState(String licenseState) {
		this.licenseState = licenseState;
	}



	public String getLicenseNumber() {
		return licenseNumber;
	}



	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}



	public String getCreditCardNumber() {
		return creditCardNumber;
	}



	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}



	public Date getCreditCardExpiration() {
		return creditCardExpiration;
	}



	public void setCreditCardExpiration(Date creditCardExpiration) {
		this.creditCardExpiration = creditCardExpiration;
	}



	public int getIsAdmin() {
		return isAdmin;
	}



	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}



	public String toString(){
		return super.toString() + 
				"Membership Expiration: " + getMembershipExpiration() + " License State: " + getLicenseState() + " License Number: " + getLicenseNumber() + " Credit Card Number: " + getCreditCardNumber() + " Credit Card Expiration: " + getCreditCardExpiration();  
	}

	public boolean equals(Object otherCustomer){
		if (otherCustomer == null)	return false;
		if (otherCustomer instanceof Customer){
			if (
				getUserName() == ((Customer)otherCustomer).getUserName()
				)
			return true;					
		}
		return false;
		
	}



}
